package com.android.iab.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;


public class Helper {
	
	

	
	 
	  public static void defaultOneButtonDialog(Activity activity,String msg)
	  {
		  AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		  builder.setMessage(msg)
		         .setCancelable(false)
		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
		                  //do things
		            	 
		            	 dialog.cancel();
		             }
		         });
		  AlertDialog alert = builder.create();
		  alert.show();

	  }
	  
	  public static void defaultOneButtonDialogWithClear(Activity activity,String msg,
			  final EditText eText1, final EditText eText2)
	  {
		  AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		  builder.setMessage(msg)
		         .setCancelable(false)
		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
		                  //do things
		            	 eText1.setText("");
		            	 eText2.setText("");
		            	 dialog.cancel();
		             }
		         });
		  AlertDialog alert = builder.create();
		  alert.show();

	  }
	  
	  
	  public static void defaultOneButtonDialogWithClear(Activity activity,String msg,
			  final EditText eText1)
	  {
		  AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		  builder.setMessage(msg)
		         .setCancelable(false)
		         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
		                  //do things
		            	 eText1.setText("");
		            	 dialog.cancel();
		             }
		         });
		  AlertDialog alert = builder.create();
		  alert.show();

	  }
	  
	  
}
