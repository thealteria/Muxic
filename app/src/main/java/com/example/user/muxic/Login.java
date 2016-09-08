package com.example.user.muxic;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    private Uri mImageCaptureUri;
    private ImageView mImageView;
    private AlertDialog dialog;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        captureImage();

        TextView button = (TextView) findViewById(R.id.btnSelectPhoto);
        mImageView = (ImageView) findViewById(R.id.viewImage);

        TextView switchtxt = (TextView) findViewById(R.id.ok);
        switchtxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MusicActivity.class);
                startActivity(intent);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    private void captureImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                            mImageCaptureUri);

                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
                else {

                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_FILE);
                }

            }
        });

        dialog = builder.create();
    }

    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();

                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    mImageView.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists())
                    f.delete();

                break;

        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);


            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {

                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

}