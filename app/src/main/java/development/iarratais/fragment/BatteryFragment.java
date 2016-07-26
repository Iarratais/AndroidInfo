/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import development.iarratais.androidinfo.R;
import development.iarratais.utils.BatteryInfoUtil;
import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryFragment extends Fragment {

    View rootView;

    BatteryInfoUtil batteryInfoUtil;

    public BatteryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_battery, container, false);

        batteryInfoUtil = new BatteryInfoUtil(getActivity());

        injectInformation();

        return rootView;
    }

    /**
     * Inject the information into the fragmeent view.
     */
    private void injectInformation(){
        TextView batteryChargingTextView = (TextView) rootView.findViewById(R.id
                .textview_battery_information_charge_status);
        TextView batteryChargeLevelTextView = (TextView) rootView.findViewById(R.id
                .textview_battery_information_charge_level);
        TextView batteryChargingViaTextView = (TextView) rootView.findViewById(R.id
                .textview_battery_information_charge_method);

        if(batteryChargingTextView != null){
            if(batteryInfoUtil.isConnected().equals(getString(R.string.battery_information_not_charging))){
                // Hide the charging via as the device is not charging.
                batteryChargingViaTextView.setVisibility(View.GONE);

                String batteryCharging = getString(R.string.battery_information_charging_status,
                        getString(R.string.battery_information_not_charging));
                batteryChargingTextView.setText(batteryCharging);
            } else {
                // Show the charing via as the device is charging.
                batteryChargingViaTextView.setVisibility(View.VISIBLE);

                String chargingVia = getString(R.string.battery_information_charging_method,
                        batteryInfoUtil.isConnected());
                batteryChargingViaTextView.setText(chargingVia);

                // Set the battery charging to charging.
                String batteryCharging = getString(R.string.battery_information_charging_status,
                        getString(R.string.battery_information_charging));
                batteryChargingTextView.setText(batteryCharging);
            }
        }

        if(batteryChargeLevelTextView != null){
            String batteryLevel = getString(R.string.battery_information_charge_level,
                    batteryInfoUtil.getBatteryLevel());
            batteryChargeLevelTextView.setText(batteryLevel);

            StackedHorizontalProgressBar stackedHorizontalProgressBar;
            stackedHorizontalProgressBar = (StackedHorizontalProgressBar) rootView.findViewById(R.id
                    .progress_bar_battery_level);
            stackedHorizontalProgressBar.setMax(100);
            stackedHorizontalProgressBar.setProgress(batteryInfoUtil.getBatteryLevel());
        }
    }
}
