package com.example.first_half_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class dash extends AppCompatActivity {

    LinearLayout btn_start ;
    BluetoothAdapter myBluetoothAdapter;
    int requestCodeForeEnable;
    Intent btEnablingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        btn_start = findViewById(R.id.start_btn);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForeEnable = 1;

       // btn_start.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View view) {
                bluetoothONMethod();
            //}
        //});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeForeEnable) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth is Enable", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(dash.this, start.class);
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth is Enabling Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void bluetoothONMethod() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBluetoothAdapter==null){
                    Toast.makeText(dash.this, "Bluetooth does not support this Device", Toast.LENGTH_SHORT).show();
                }else{
                    if(!myBluetoothAdapter.isEnabled()){
                        startActivityForResult(btEnablingIntent,requestCodeForeEnable);
                        Intent intent=new Intent(dash.this, start.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Bluetooth is connected", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(dash.this, start.class);
                        startActivity(intent);
                    }
                }


            }
        });
    }
}