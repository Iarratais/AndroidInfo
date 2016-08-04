/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
        TextView manufacturerTextView = (TextView) rootView.findViewById(R.id
                .textview_device_information_manufacturer);
        TextView modelTextView = (TextView) rootView.findViewById(R.id
                .textview_device_information_model);
        TextView androidVersionTextView = (TextView) rootView.findViewById(R.id
                .textview_device_information_android_version);
        TextView isRootedTextView = (TextView) rootView.findViewById(R.id
                .textview_device_information_is_rooted);
        TextView IMEITextView = (TextView) rootView.findViewById(R.id
                .textview_device_information_device_imei);

        // Set the text for the manufacturer.
        if(manufacturerTextView != null){
            String manufacturer = getString(R.string.device_information_manufacturer,
                    deviceInfoUtil.getManufacturer());
            manufacturerTextView.setText(manufacturer);
        }

        // Set the text for the model name.
        if(modelTextView != null){
            String model = getString(R.string.device_information_device_model, deviceInfoUtil
                    .getModel());
            modelTextView.setText(model);
        }

        // Set the text for the android version.
        if(androidVersionTextView != null){
            String androidVersion = getString(R.string.device_information_android_version,
                    deviceInfoUtil.getAndroidVersion(), deviceInfoUtil.getAndroidVersionName());
            androidVersionTextView.setText(androidVersion);
        }

        // Set the text for the rooted status of the device.
        if(isRootedTextView != null){
            String isRooted;
            if(deviceInfoUtil.isRooted())
                isRooted = getString(R.string.device_information_is_rooted, getString(R.string
                        .yes));
            else
                isRooted = getString(R.string.device_information_is_rooted, getString(R.string.no));

            isRootedTextView.setText(isRooted);
        }

        // Set the text for the imei of the device.
        if(IMEITextView != null) {
            int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
            if (rc == PackageManager.PERMISSION_GRANTED) {
                String imeiNumber;

                if(deviceInfoUtil.getIMEI() != null) {
                    imeiNumber = getString(R.string.device_information_imei, deviceInfoUtil
                            .getIMEI());
                } else {
                    imeiNumber = getString(R.string.device_information_imei, getString(R.string
                            .not_supported));
                }
                IMEITextView.setText(imeiNumber);
            } else {
                IMEITextView.setVisibility(View.GONE);
            }
        }
    }


}
