package com.android.iab.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	public static ArrayList<Activity> activityArrayList=new ArrayList<Activity>();
	
	public static void add(Activity activity){
		activityArrayList.add(activity);
	}
	
	public static void remove(Activity activity){
		activityArrayList.remove(activity);
	}
	
	public static void killAllActivity(){
		for(int i=0;i<activityArrayList.size();i++){
			if(!activityArrayList.get(i).isFinishing()){
				activityArrayList.get(i).finish();
			}
		}
		activityArrayList.clear();
	}

	public static void gotoActivity(Context context, Class<?> classActivity,
			Bundle bundle) {
		Intent intent = new Intent(context, classActivity);
		if (bundle != null)
			intent.putExtra("android.intent.extra.INTENT", bundle);
		context.startActivity(intent);
	}

	public static void showToastS(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showToastL(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;
		String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static SharedPreferences getPrefrence(Context c) {
		return c.getSharedPreferences("user_info", Context.MODE_PRIVATE);
	}
	public static SharedPreferences getPatientPrefrence(Context c) {
		return c.getSharedPreferences("patient_info", Context.MODE_PRIVATE);
	}
	public static SharedPreferences getChatUserPrefrence(Context c) {
		return c.getSharedPreferences("chat_user_info", Context.MODE_PRIVATE);
	}
	
	public static SharedPreferences getAppointmentPrefrence(Context c) {
		return c.getSharedPreferences("appointment_ids", Context.MODE_PRIVATE);
	}

	public static interface Method {
		void execute();
	}

	public static Dialog createSimpleDialog(Context context, String title,
			String msg, String btnLabel1, String btnLabel2,
			final Method method1, final Method method2) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(btnLabel1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (method1 != null)
							method1.execute();
					}
				});

		builder.setNegativeButton(btnLabel2,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (method2 != null)
							method2.execute();
					}
				});

		return builder.create();
	}
	public static Dialog createSimpleDialog (Context context, String title, String msg, String btnLabel, final Method method)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder (context);
		builder.setTitle (title);
		builder.setMessage (msg);
		builder.setPositiveButton (btnLabel, new DialogInterface.OnClickListener ()
		{
			public void onClick (DialogInterface dialog, int which)
			{
				method.execute ();
			}
		});

		return builder.create ();
	}
	public static Dialog createInfoDialog (Context context, String title, String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder (context);
		builder.setTitle (title);
		builder.setMessage (msg);
		builder.setPositiveButton ("OK", new DialogInterface.OnClickListener ()
		{
			public void onClick (DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});

		return builder.create ();
	}
	public static void dumpIntent(Intent i){

	    Bundle bundle = i.getExtras();
	    if (bundle != null) {
	        Set<String> keys = bundle.keySet();
	        Iterator<String> it = keys.iterator();
	        while (it.hasNext()) {
	            String key = it.next();
	            Log.e("Intent Data", "[" + key + "=" + bundle.get(key) + "]");
	        }
	       
	    }
	}
	
	public static void write(String message){
		try{
		System.out.println(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
