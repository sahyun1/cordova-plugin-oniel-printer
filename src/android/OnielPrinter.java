package com.megatdaharus;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;

public class OnielPrinter extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("connect".equals(action)) {
            connect(callbackContext);
            return true;
        } else if ("printBarcode".equals(action)) {
            printBarcode(args.getString(0), callbackContext);
            return true;
        } else if ("printImage".equals(action)) {
            printImage(args.getString(0), callbackContext);
            return true;
        } else if ("printText".equals(action)) {
            printText(args.getString(0), callbackContext);
            return true;
        } else if ("show".equals(action)) {
            show(args.getString(0), callbackContext);
            return true;
        }

        return false;
    }

    private void connect(CallbackContext callbackContext) {
        Toast.makeText(webView.getContext(), "Connected", Toast.LENGTH_LONG).show();
        callbackContext.success("Connected");
    }

    private void printBarcode(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty Barcode!");
        } else {
            Toast.makeText(webView.getContext(), "printBarcode: " + msg, Toast.LENGTH_LONG).show();
            callbackContext.success(msg);
        }
    }

    private void printImage(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty Image!");
        } else {
            Toast.makeText(webView.getContext(), "printImage: " + msg, Toast.LENGTH_LONG).show();
            callbackContext.success(msg);
        }
    }

    private void printText(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty Text!");
        } else {
            Toast.makeText(webView.getContext(), "printText: " + msg, Toast.LENGTH_LONG).show();
            callbackContext.success(msg);
        }
    }

    private void show(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
            callbackContext.error("Empty message!");
        } else {
            Toast.makeText(webView.getContext(), msg, Toast.LENGTH_LONG).show();
            callbackContext.success(msg);
        }
    }
}