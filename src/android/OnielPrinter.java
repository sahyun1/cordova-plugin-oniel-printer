package com.megatdaharus.onielprinter;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import android.util.Log;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.util.Set;

import datamaxoneil.connection.ConnectionBase;
import datamaxoneil.connection.Connection_Bluetooth;
import datamaxoneil.printer.DocumentEZ;
import datamaxoneil.printer.DocumentLP;
import datamaxoneil.printer.ParametersEZ;

public class OnielPrinter extends CordovaPlugin {

    private String mBluetoothAddress;
    private DocumentLP docLP;
    private DocumentEZ docEZ;
    private ParametersEZ paramEZ;
    private String address;
    private int m_printHeadWidth = 832;
    private byte[] printData = {0};
    private ConnectionBase conn = null;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("printBarcode".equals(action)) {
            printBarcode(args.getString(0), callbackContext);
            return true;
        } else if ("printImage".equals(action)) {
            printImage(args.getString(0), callbackContext);
            return true;
        } else if ("printText".equals(action)) {
            printText(args.getString(0), callbackContext);
            return true;
        }

        return false;
    }


    public String getMacAddress(){
        String address = "";
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null){
            address = "Device does not support bluetooth";
        } else {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    address = device.getAddress();
                }
            }
        }

        return address;
    }

    private void printBarcode(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty Barcode!");
        } else {
            Toast.makeText(webView.getContext(), "printBarcode: " + mBluetoothAddress, Toast.LENGTH_SHORT).show();
            callbackContext.success(msg);
        }
    }

    private void printImage(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty Image!");
        } else {
            Toast.makeText(webView.getContext(), "printImage: " + msg, Toast.LENGTH_SHORT).show();
            callbackContext.success(msg);
        }
    }

    private void printText(String msg, CallbackContext callbackContext) {
        final ProgressDialog progress = new ProgressDialog(webView.getContext());
        progress.setTitle("Connecting");
        progress.setMessage("Please wait while we connect to devices...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        String address = getMacAddress();

        try {
            conn = Connection_Bluetooth.createClient(address);
            conn = Connection_Bluetooth.createClient(address);
            conn.open();

            docLP = new DocumentLP("!");
            docLP.writeText("PRINT TEST");
            docLP.writeText(msg);
            docLP.writeText("  ");
            docLP.writeText("  ");
            conn.write(docLP.getDocumentData());
            conn.close();
            progress.dismiss();

        } catch (Exception e) {
            if(conn != null) {
                conn.close();
                e.printStackTrace();
            }
        }

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
    }
}