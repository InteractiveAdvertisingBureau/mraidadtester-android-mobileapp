
package com.android.iab.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.iab.R;
import com.android.iab.adapter.SdkListAdapter;
import com.android.iab.bean.SDK_Bean;

import java.util.ArrayList;


/**
 * SelectSDKFragment for loading Select SDK fragmnet in main layouts
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class SelectSDKFragment extends Fragment {

	SdkListAdapter mSdkListAdapter;
	ArrayList<SDK_Bean> sdk_list = new ArrayList<SDK_Bean>();

	private static String[] sdk_name = {"Google ","Ad Mob","PubMatic"};
	private static String[] sdk_version = {"V4.0.1 ","V3.2.6","V2.1"};

	ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.fragment_sdk, container, false);
		view.setOnClickListener(null);

		lv = (ListView)view.findViewById(R.id.sdk_list_item);

		for(int i=0;i<sdk_name.length;i++)
		{
			SDK_Bean sdk_bean = new SDK_Bean();
			sdk_bean.setSdkName(sdk_name[i]);
			sdk_bean.setSdkversion(sdk_version[i]);

			sdk_bean.setIsSelected(false);

			sdk_list.add(sdk_bean);
		}

		SdkListAdapter sdkListAdapter=new SdkListAdapter(getActivity(), sdk_list);

		lv.setAdapter(sdkListAdapter);




		return view;
	}


}