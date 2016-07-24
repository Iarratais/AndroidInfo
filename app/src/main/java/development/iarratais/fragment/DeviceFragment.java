/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import development.iarratais.androidinfo.R;
import development.iarratais.utils.DeviceInfoUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    View rootView;

    DeviceInfoUtil deviceInfoUtil;

    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_device, container, false);

        deviceInfoUtil = new DeviceInfoUtil(getActivity());

        injectDeviceInformation();

        return rootView;
    }

    /**
     * This adds the information into the views regarding information about the device.
     */
    public void injectDeviceInformation(){
        TextView manufacturerText = (TextView) rootView.findViewById(R.id
                .textview_device_information_manufacturer);
        TextView modelText = (TextView) rootView.findViewById(R.id
                .textview_device_information_model);
        TextView androidVersionText = (TextView) rootView.findViewById(R.id
                .textview_device_information_android_version);

        if(manufacturerText != null){
            String manufacturer = getString(R.string.device_information_manufacturer,
                    deviceInfoUtil.getManufacturer());
            manufacturerText.setText(manufacturer);
        }

        if(modelText != null){
            String model = getString(R.string.device_information_device_model, deviceInfoUtil
                    .getModel());
            modelText.setText(model);
        }

        if(androidVersionText != null){
            String androidVersion = getString(R.string.device_information_android_version,
                    deviceInfoUtil.getAndroidVersion());
            androidVersionText.setText(androidVersion);
        }
    }
}
