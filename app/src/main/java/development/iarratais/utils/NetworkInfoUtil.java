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

    ConnectivityManager conMan;

    WifiManager wifiManager;

    public NetworkInfoUtil(Context context){
        this.context = context;

        conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Check if the WiFi is enabled.
     *
     * @return true if enabled.
     */
    public boolean isWiFiEnabled(){
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();

        return wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING;
    }

    /**
     * Check if the mobile data is enabled.
     *
     * @return true if enabled, false if disabled.
     */
    public boolean isMobileDataEnabled() {
        NetworkInfo.State mobile;

        if(doesDeviceHaveMobileDataOption()) {
            mobile = conMan.getNetworkInfo(0).getState();
            return mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING;
        }

        return false;
    }

    /**
     * Check if the device has the option to connect to a mobile network or not.
     *
     * @return true if it is able to connect, false otherwise.
     */
    public boolean doesDeviceHaveMobileDataOption(){
        NetworkInfo ni = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ni != null;
    }

    /**
     * Get the SSID of the WiFi network that the device is connected to.
     *
     * @return string containing the SSID that the device is connected to.
     */
    public String getConnectedSSID(){
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState()== SupplicantState.COMPLETED) {
            return wifiInfo.getSSID();
        }
        return "None";
    }

    /**
     * Get the IP address of the device.
     *
     * @return string containing the IP address.
     */
    public String getConnectedIPAddress(){
        return Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
    }

    /**
     * Get the MAC address of the device.
     *
     * @return string containing the MAC address.
     */
    public String getConnectedMACAddress(){
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    /**
     * Check if the device has a bluetooth option.
     *
     * @return True if Bluetooth is available.
     */
    public boolean doesDeviceHaveBluetoothOption() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }
}
