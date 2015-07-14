
package com.android.iab.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.iab.R;


/**
 * AddTypeFragment for loading Ad Type fragmnet in main layouts
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */


public class AddTypeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.fragment_ad_type, container, false);
		view.setOnClickListener(null);





		return view;
	}


}