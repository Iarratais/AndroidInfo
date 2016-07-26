/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import development.iarratais.androidinfo.R;
import development.iarratais.utils.NetworkInfoUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkFragment extends Fragment {

    View rootView;

    NetworkInfoUtil networkInfoUtil;

    AdView adView;

    public NetworkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_network, container, false);

        networkInfoUtil = new NetworkInfoUtil(getActivity());

        injectNetworkInformation();

        adView = (AdView) rootView.findViewById(R.id.adview_network_information_main);
        AdRequest adReq = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("901E79CF386C70EF8ED8FC97C04EF003").build();

        if (adView != null) {
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    adView.setVisibility(View.GONE);
                }
            });
        }
        if (adView != null) {
            adView.loadAd(adReq);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

    private void injectNetworkInformation(){

        // Set the textview with wifi status
        TextView wiFiStatusTextView = (TextView) rootView.findViewById(R.id
                .textview_network_information_wifi_status);

        if (isWiFiEnabled()){
            String enabled = getString(R.string.enabled);
            wiFiStatusTextView.setText(getString(R.string.network_information_wifi_status, enabled));
        } else {
            String disabled = getString(R.string.disabled);
            wiFiStatusTextView.setText(getString(R.string.network_information_wifi_status, disabled));
        }

        // Set the SSID
        TextView SSIDTextView = (TextView) rootView.findViewById(R.id
                .textView_network_information_wifi_SSID);

        if(isWiFiEnabled()){
            String SSIDtext = getString(R.string.network_information_wifi_SSID, getSSID());
            SSIDTextView.setText(SSIDtext);
            SSIDTextView.setVisibility(View.VISIBLE);
        } else {
            SSIDTextView.setVisibility(View.GONE);
        }

        // Set the IP Address
        TextView IPAdressTextView = (TextView) rootView.findViewById(R.id
                .textview_network_information_ip_address);

        String ipadresstext = getString(R.string.network_information_ip_address, getIPAddress());
        IPAdressTextView.setText(ipadresstext);

        // Set the textview for mobile status
        TextView mobileDataStatusTextView = (TextView) rootView.findViewById(R.id
                .textview_network_information_mobile_data_status);
        if(deviceHasMobileData()) {
            if (isMobileDataEnabled()) {
                String enabled = getString(R.string.enabled);
                mobileDataStatusTextView.setText(getString(R.string
                        .network_information_mobile_data_status, enabled));
            } else {
                String disabled = getString(R.string.disabled);
                mobileDataStatusTextView.setText(getString(R.string
                        .network_information_mobile_data_status, disabled));
            }
        } else {
            String notAvailable = getString(R.string.network_information_mobile_data_status,
                    getString(R.string.network_information_mobile_data_not_on_device));
            mobileDataStatusTextView.setText(notAvailable);
        }

        // Set the textview for bluetooth status.
        TextView bluetoothStatusTextView = (TextView) rootView.findViewById(R.id
                .textview_network_information_bluetooth_status);

        if(isBluetoothAvailable()){
            String enabled = getString(R.string.enabled);
            bluetoothStatusTextView.setText(getString(R.string
                        .network_information_bluetooth_status, enabled));
        } else {
            String disabled = getString(R.string.disabled);
            bluetoothStatusTextView.setText(getString(R.string
                    .network_information_bluetooth_status, disabled));
        }

    }

    /**
     * Check if the WiFi is enabled.
     *
     * @return true if enabled.
     */
    private boolean isWiFiEnabled(){
        ConnectivityManager conMan = (ConnectivityManager) getActivity().getSystemService(Context
                .CONNECTIVITY_SERVICE);

        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();

        return wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING;
    }

    /**
     * Check if the mobile data is enabled.
     *
     * @return true if enabled.
     */
    private boolean isMobileDataEnabled() {
        ConnectivityManager conMan = (ConnectivityManager) getActivity().getSystemService(Context
                .CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile;

        if(deviceHasMobileData()) {
            mobile = conMan.getNetworkInfo(0).getState();
            return mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING;
        }

        return false;
    }

    private boolean deviceHasMobileData(){
        ConnectivityManager conMan = (ConnectivityManager) getActivity().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo ni = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ni != null;
    }

    private String getSSID(){
        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState()== SupplicantState.COMPLETED) {
            return wifiInfo.getSSID();
        }
        return "None";
    }

    private String getIPAddress(){
        WifiManager wm = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    /**
     * Check for Bluetooth.
     *
     * @return True if Bluetooth is available.
     */
    public static boolean isBluetoothAvailable() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }
}
