
package com.android.iab.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.iab.R;

/**
 * AddTagFragment for loading Ad Tag fragmnet in main layouts
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class AddTagFragment extends Fragment {

	EditText add_tag_editText;
	String add_tag_String;


	public static AddTagFragment fragment;

	public static AddTagFragment getInstance() {
		if (fragment == null) {
			fragment = new AddTagFragment();
		}
		return fragment;
	}


	public AddTagFragment() {
		fragment = this;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.fragment_ad_tag, container, false);
		view.setOnClickListener(null);

		add_tag_editText = (EditText) view.findViewById(R.id.add_tag_editText);




		return view;
	}

	public Boolean validateScript()
	{
		add_tag_String =  add_tag_editText.getText().toString().trim();

		if(add_tag_String.length()==0) {

			Log.e("validateScript", "false");
			return false;
		}
		else
			return true;
	}

}