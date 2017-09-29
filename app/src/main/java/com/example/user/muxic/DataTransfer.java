package com.example.user.muxic;

/**
 * Created by Aman on 10/6/2016.
 */
import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

public class DataTransfer extends AppCompatActivity {

    String TAG = "DataTransfer";

    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    Button bt1;
    TextView screen;
    ListView list;
    private ArrayAdapter adapter=null;
    int i=0;

    public String path= Environment.getExternalStorageDirectory().getAbsolutePath() ;

    DBHandler db;
    private RecyclerView.LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer);

        db=new DBHandler(getApplicationContext());

        screen= (TextView) findViewById(R.id.screen);
        list= (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);

        bluetoothDevice = bluetooth.mBluetoothDevice;
        bluetoothSocket = bluetooth.mBluetoothSocket;
        final ConnectedThread ct = new ConnectedThread(bluetoothSocket);
        ct.start();
        final List<String> data=db.access_data();

        while(i<(db.access_data().size())){

            adapter.add(data.get(i));
            i++;

        }
        i=0;
      /*  ll1= (LinearLayout) findViewById(R.id.ll1);
        textview2= (TextView) senderlayoyt.findViewById(R.id.text1);*/


        bt1 = (Button) findViewById(R.id.send);
        bt1.setOnClickListener(new View.OnClickListener() {
                                   @TargetApi(Build.VERSION_CODES.KITKAT)
                                   @Override
                                   public void onClick(View v) {
                                       if (!Objects.equals(db.access_data().toString(), "")){
                                           Log.d(TAG, "Clicked");
                                           List msg = data;
                                           while(i<(db.access_data().size())) {
                                               byte[] bytes = String.valueOf(msg.get(i)).getBytes();
                                               ct.write(bytes);
                                               i++;
                                           }

                                       }
                                   }
                               }
        );
    }
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI Activity
                    final String readMessage = new String(buffer, 0, bytes);
                    Log.i(TAG,"Listening "+readMessage);
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    if(readMessage!=null) {
                                        File file=new File(path + "/myfile.txt");

                                        try {
                                            FileOutputStream fo = new FileOutputStream(file);
                                            fo.write(String.valueOf(readMessage).getBytes());
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        adapter.add(readMessage);


                                        Log.d("Query", readMessage);
/*                                        textview1.setText(readMessage);
                                    ll1.addView(reciverlayout);*/
                                    }

                                }
                            }
                    );
                } catch (Exception e) {
                    Log.e(TAG, "disconnected", e);
                    //connectionLost();
                    //break;
                }
            }
        }
        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                Log.d(TAG, "Writing");
                mmOutStream.write(buffer);
                // Share the sent message back to the UI Activity
                /*mHandler.obtainMessage(BluetoothChat.MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();*/
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

}