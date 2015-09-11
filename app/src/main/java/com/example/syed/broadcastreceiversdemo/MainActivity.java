package com.example.syed.broadcastreceiversdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomBroadcast();
            }
        });

        //Create an intent filter and register the broadcast receiver.
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName() + ".uniqueIntent");
        registerReceiver(broadcastReceiver, intentFilter);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                } else {
                    bluetoothAdapter.disable();
                }

            }
        });

        IntentFilter intentFilterBT = new IntentFilter();
        intentFilterBT.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiverBT, intentFilterBT);
    }

    /*
    * Define the broadcast receiver
    * */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(MainActivity.this, " received: " + action, Toast.LENGTH_LONG).show();
            TextView textViewCustom = (TextView) findViewById(R.id.textViewCustom);
            textViewCustom.setText("Custom broadcast received.");
        }
    };

    /*
    * Define the broadcast receiver
    * */
    private final BroadcastReceiver broadcastReceiverBT = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(MainActivity.this, " BT toggled: " + action, Toast.LENGTH_LONG).show();
            TextView textViewBT = (TextView) findViewById(R.id.textViewBT);
            textViewBT.setText("Bluetooth toggled");
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverBT);
    }

    public void sendCustomBroadcast() {
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".uniqueIntent");
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clear:
                clearFields();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearFields() {
        ((TextView)findViewById(R.id.textViewCustom)).setText("");
        ((TextView)findViewById(R.id.textViewBT)).setText("");
    }
}
