/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import development.iarratais.androidinfo.R;

/**
 * Created by KarlJones on 26/07/16.
 *
 * Determine information about the devices battery, including the battery level, if the device is
 * charging or not, and if it is, how it is charging.
 */
public class BatteryInfoUtil {

    Context context;

    public BatteryInfoUtil(Context context){
        this.context = context;
    }

    /**
     * Get the battery level of the device.
     *
     * @return int containing the battery level of the device.
     */
    public int getDeviceBatteryLevel() {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent
                .ACTION_BATTERY_CHANGED));
        int level = 0;
        if (batteryIntent != null) {
            level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        }
        int scale = 0;
        if (batteryIntent != null) {
            scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }

        // Error checking.
        if(level == -1 || scale == -1) {
            return (int)50.0f;
        }

        float batteryLevel = ((float)level / (float)scale) * 100.0f;

        return (int)batteryLevel;
    }

    /**
     * Check if the device is charging.
     *
     * @return string to which the device is connected.
     */
    public String isDeviceConnectedToCharger(){
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int plugged = 1000;

        if (intent != null) {
            plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        }

        // Check the method that the device is connected.
        if (plugged == BatteryManager.BATTERY_PLUGGED_AC){
            return context.getString(R.string.battery_information_charging_via_ac);
        } else if (plugged == BatteryManager.BATTERY_PLUGGED_USB){
            return context.getString(R.string.battery_information_charging_via_usb);
        } else if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS){
            return context.getString(R.string.battery_information_charging_via_wireless);
        }

        return context.getString(R.string.battery_information_not_charging);
    }
}
