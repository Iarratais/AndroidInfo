/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/**
 * Created by KarlJones on 24/07/16.
 *
 * This class is involved with getting information about the network states of the device.
 */
public class NetworkInfoUtil {

    Context context;

    public NetworkInfoUtil(Context context){
        this.context = context;
    }

    /**
     * Check if the WiFi is enabled.
     *
     * @return true if enabled.
     */
    public boolean isWiFiEnabled(){
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService
                (Context
                .CONNECTIVITY_SERVICE);

        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();

        return wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING;
    }

    /**
     * Check if the mobile data is enabled.
     *
     * @return true if enabled.
     */
    public boolean isMobileDataEnabled() {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile;

        if(deviceHasMobileData()) {
            mobile = conMan.getNetworkInfo(0).getState();
            return mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING;
        }

        return false;
    }

    public boolean deviceHasMobileData(){
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo ni = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ni != null;
    }

    public String getSSID(){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState()== SupplicantState.COMPLETED) {
            return wifiInfo.getSSID();
        }
        return "None";
    }

    public String getIPAddress(){
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    public String getMACAddress(){
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.getConnectionInfo().getMacAddress();
    }

    /**
     * Check for Bluetooth.
     *
     * @return True if Bluetooth is available.
     */
    public boolean isBluetoothAvailable() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }
}
