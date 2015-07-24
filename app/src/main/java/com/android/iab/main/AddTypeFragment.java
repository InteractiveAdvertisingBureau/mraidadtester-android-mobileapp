
package com.android.iab.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.iab.R;
import com.android.iab.adapter.AdTypeAdapter;
import com.android.iab.adapter.SdkListAdapter;
import com.android.iab.bean.Ad_Type_Bean;
import com.android.iab.bean.SDK_Bean;
import com.android.iab.helper.HelperMethods;

import java.util.ArrayList;


/**
 * AddTypeFragment for loading Ad Type fragmnet in main layouts
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */


public class AddTypeFragment extends Fragment  implements View.OnClickListener {

	SdkListAdapter mSdkListAdapter;
	ArrayList<Ad_Type_Bean> ad_type_list = new ArrayList<Ad_Type_Bean>();

	private static String[] ad_type = {"Banner ","Interstitial"};

	public static String AD_TYPE_TAG ="Ad_Type";
	SharedPreferences mPrefs;
	int ad_type_position;

	ListView lv;
	Ad_Type_Bean ad_type_bean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.fragment_ad_type, container, false);
		view.setOnClickListener(null);

		lv = (ListView)view.findViewById(R.id.ad_type_list_item);


		mPrefs= this.getActivity().getSharedPreferences(HelperMethods.MODE_TYPE, Context.MODE_PRIVATE);

		ad_type_position = mPrefs.getInt(AD_TYPE_TAG, 100);

		view.findViewById(R.id.button_test_creative).setOnClickListener(this);

		for(int i=0;i<ad_type.length;i++)
		{
			ad_type_bean = new Ad_Type_Bean();
			ad_type_bean.setAdtype(ad_type[i]);

			ad_type_bean.setIsSelected(false);

			ad_type_list.add(ad_type_bean);
		}

		AdTypeAdapter adTypeAdapter=new AdTypeAdapter(getActivity(), ad_type_list);

		lv.setAdapter(adTypeAdapter);

		if(ad_type_position==0){
			ad_type_bean.setIsSelected(true);



		}




		return view;

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case  R.id.button_test_creative:  // calling Add/Banner Page
				Intent  intent=new Intent(getActivity(),TestAdActivity.class);
				startActivity(intent);

				break;

			default:
				break;


		}

	}
}