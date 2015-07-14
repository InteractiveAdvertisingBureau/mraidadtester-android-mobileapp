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
	  
	  


	  
	  public static String convertDateFromDatePicker(String str)
	  {
		  
		  String dob;
		try {
			dob = null;
			  String[] date=str.split("-");
			  
			  String day=date[0];
			  String month=date[1];
			  String year=date[2];
			  
			  if(date[0].length()==4)
			  {
				  dob=day+"-"+month+"-"+year;
			  }
			  else
			  {
				  
				  dob=year+"-"+month+"-"+day;
			  }
			  return dob;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

		  
		
	  }
	  


	  
	  public static String getImageExt(String imageName)
	  {
		 String ext=null;
		  String[] date=imageName.split("\\.");
		  
		
		   ext=date[1];
		
		  
		  return ext;
	  }
	  
	  public  static String convertBitmapToBase64(Bitmap b)
		 {
			 ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 b.compress(Bitmap.CompressFormat.PNG, 100, stream);
			 byte[] byteArray = stream.toByteArray();
			/* int length=byteArray.length;
			 size =length/1024;
			 Log.e("size", ""+size);*/
			// String image_size=byteArray
			 String imageEncoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
			 

			    Log.d("encoded image", "encoded image" + imageEncoded);
			    return imageEncoded;
		 }
	  
	  public static void saveBase64InSharePrefrence_profilePicture(Activity activity,Bitmap profilePicture)
	  {
		  SharedPreferences mPrefs = activity.getSharedPreferences("Location", Context.MODE_PRIVATE);
		  Editor editor = mPrefs.edit();
	      editor.putString("USER_PROFILE_PICTURE", encodeTobase64(profilePicture));
	     
	     
	      editor.commit();
	  }
	  
	// Encode bitmap in base64
	  
	  public static String encodeTobase64(Bitmap image) {
		    Bitmap immage = image;
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
		    byte[] b = baos.toByteArray();
		    String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		    Log.d("Image Log:", imageEncoded);
		    return imageEncoded;

		}
	  
	  // get bitmap from base64
	  public static Bitmap decodeBase64(String input) {
	       byte[] decodedByte = Base64.decode(input, 0);
	       return BitmapFactory
	               .decodeByteArray(decodedByte, 0, decodedByte.length);
	   }
	  
	/*  
	  public static String getSplitAddress(String str)
	  {
		  String address="";
		 try
		 {
			 
			 if(str.length()>0)
			 {
				 
				
				  String [] address_array=str.split(",");
				  
				  String addess1=address_array[0];
				  String addess2=address_array[1];
				   address=addess1+","+addess2;
			 }
		 }
		 catch(Exception e)
		 {
			 
		 }
			 
			  
			
		  return address;
	  }
	  */
}
