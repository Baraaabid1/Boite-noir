package com.example.first_half_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class start extends AppCompatActivity {
    LinearLayout ref_color;
    TextView ref_text;
    Button Listen;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;
    Send send;

    int REQUEST_ENABLE_BLUETOOTH = 1;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    private static final String APP_NAME = "Color_APP";
    private static final UUID MY_UUID = UUID.fromString("485246c5-686e-45ff-bcc9-899c23bc073c");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ref_color = findViewById(R.id.ref_image);
        ref_text = findViewById(R.id.ref_tex);
        Listen = findViewById(R.id.listen_button);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
        }

        implimentListeners();


    }

    private void implimentListeners() {
    Listen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    });
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case STATE_LISTENING:
                    Toast.makeText(start.this, "Listening...", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTING:
                    Toast.makeText(start.this, "Connecting ...", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTED:
                    Toast.makeText(start.this, "Connected", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTION_FAILED:
                    Toast.makeText(start.this, "connection failed", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[]readBuff= (byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    ref_text.setText(tempMsg);

                    break;

            }
            return true;
        }
    });

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;


        public ClientClass(BluetoothDevice device1) {
            device = device1;
            try {
                if (ActivityCompat.checkSelfPermission(start.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return ;
                }
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                {
                    if (ActivityCompat.checkSelfPermission(start.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    socket.connect();
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message);
                    send=new Send(socket);
                    send.start();


                }
            }catch (IOException e ){
                e.printStackTrace();
                Message message=Message.obtain();
                message.what=STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }


    }
    private class Send extends Thread{
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;


        public Send(BluetoothSocket socket){
            bluetoothSocket=socket;
            InputStream tempIn=null;
            try {
                tempIn=bluetoothSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream=tempIn;
             }

        private Send(BluetoothSocket bluetoothSocket, InputStream inputStream) {
            this.bluetoothSocket = bluetoothSocket;
            this.inputStream = inputStream;
        }

        public void run(){
            byte[]buffer=new byte[1024];
            int bytes = 0;
            while (true){
                try {
                    inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
             }


    }

}