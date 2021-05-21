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
import datamaxoneil.printer.Document;
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
    }
    else if ("setLandscape".equals(action)) {
      setLandscape(args.getBoolean(0), callbackContext);
      return true;
    }
    else if ("printText".equals(action)) {
      printText(args.getString(0), callbackContext);
      return true;
    }
    else if ("printTextObj".equals(action)) {
      printTextObj(args, callbackContext);
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
          System.out.println("name is " + device.getName());
        }
      }

    }

    return address;
  }

  private void setLandscape(boolean isLandscape, CallbackContext callbackContext) {
    docEZ = new DocumentEZ("!");
    docEZ.setIsLandscapeMode(isLandscape);
    callbackContext.success("now in landscape");
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
       //conn = Connection_Bluetooth.createClient(address);
       conn.open();
       // docLP = new DocumentLP("!");
       // docLP.setIsLandscapeMode(true);
       // docLP.writeText("PRINT TEST");
       // docLP.writeText(msg);
       // docLP.writeText("  ");
       // docLP.writeText("  ");

       ParametersEZ parameter = new ParametersEZ();
       //   parameter.setHorizontalMultiplier(3);
       //   parameter.setVerticalMultiplier(3);
       parameter.setFont("MF102");

       docEZ = new DocumentEZ("MF204");
       docEZ.setIsLandscapeMode(true);

       int startCol = 200;

       docEZ.writeText("Notice Number:",50,1 + startCol, parameter);
       docEZ.writeText("5601205",50,280 + startCol, parameter);

       docEZ.writeText("Offence Code:",50,600 + startCol);
       docEZ.writeText("728",50,750 + startCol);

       // ------------------------------------------------------------------------------

       docEZ.writeText("Issue Date:",80,1 + startCol);
       docEZ.writeText("19/03/21 15:35",80,130 + startCol);

       docEZ.writeText("Offence:",80,600 + startCol);
       docEZ.writeText("Stopped-In a permit zone",80,700 + startCol);

       // ------------------------------------------------------------------------------

       docEZ.writeText("Offence Date:",110,1 + startCol);
       docEZ.writeText("19/03/21",110,130 + startCol);

       docEZ.writeText("(Road Rule 185(1))",110,600 + startCol);

       // ------------------------------------------------------------------------------

       docEZ.writeText("Offence Time:",140,1 + startCol);
       docEZ.writeText("15:35",140,130 + startCol);

       // ------------------------------------------------------------------------
       docEZ.writeText("Officer ID:",170,1 + startCol);
       docEZ.writeText("105",170,130 + startCol);

       // -----------------------------------------------------------------------
       docEZ.writeText("Vehicle Registration:",200,1 + startCol, parameter);

       docEZ.writeText("Place:",200,600 + startCol);
       docEZ.writeText("PUCKLE STREET",200,700 + startCol);
       // ---------------------------------------------------------------------------
       docEZ.writeText("REW567",230,1 + startCol, parameter);
       docEZ.writeText("MOONEE PONDS",230,700 + startCol);
       // ---------------------------------------------------------------------------
       docEZ.writeText("State:", 260,1+startCol);
       docEZ.writeText("VIC", 260,100+startCol);

       //--------------------------------------------------------------------------
       docEZ.writeText("Due Date:",290,600 + startCol, parameter);

       parameter.setAlignment(ParametersEZ.Alignment.Right);
       docEZ.writeText("09/04/21",290,1000 + startCol, parameter);

       //--------------------------------------------------------------------------
       parameter.setAlignment(ParametersEZ.Alignment.Left);
       docEZ.writeText("Penalty:",310,600 + startCol, parameter);

       parameter.setAlignment(ParametersEZ.Alignment.Right);
       docEZ.writeText("$99.50",310,1000 + startCol, parameter);
 //
 //
 //
 //      conn.write(docEZ.getDocumentData());
 //      System.out.println("paper length:" + docEZ.getPageLength());
 //      System.out.println("QmarkDotLine:" + docEZ.getQMarkDotLines());
 //      System.out.println("Qmark Stop:" + docEZ.getQMarkStop());

 //      docEZ.setQMarkStop(DocumentEZ.QStop.Front);
       conn.write(docEZ.getDocumentData());

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

  private void printTextObj(JSONArray obj, CallbackContext callbackContext) {
    final ProgressDialog progress = new ProgressDialog(webView.getContext());
    progress.setTitle("Connecting");
    progress.setMessage("Please wait while we connect to devices...");
    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progress.setCanceledOnTouchOutside(false);
    progress.show();
    System.out.println(obj);

    String address = getMacAddress();

    try {
      conn = Connection_Bluetooth.createClient(address);
      conn.open();

      docEZ = new DocumentEZ("MF204");
      docEZ.setIsLandscapeMode(true);

      for (int i = 0; i < obj.length(); i++) {
        JSONObject exploreObject = obj.getJSONObject(i); // you will get the json object
        if(exploreObject.has("param")) {

          ParametersEZ parameter = new ParametersEZ();
          if(exploreObject.getString("param").contains("bold")) {
            parameter.setFont("MF102");
          }
          if(exploreObject.getString("param").contains("align_right")) {
            parameter.setAlignment(ParametersEZ.Alignment.Right);
          }
          docEZ.writeText(exploreObject.getString("label"),exploreObject.getInt("row"),exploreObject.getInt("col"), parameter);

        }
        else {
          docEZ.writeText(exploreObject.getString("label"),exploreObject.getInt("row"),exploreObject.getInt("col"));
        }

      }

      // to give buffer to roll the paper to the end
      docEZ.writeText("  ", 200, 1500);
      conn.write(docEZ.getDocumentData());
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
