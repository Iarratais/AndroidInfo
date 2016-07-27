/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.androidinfo;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import development.iarratais.fragment.BatteryFragment;
import development.iarratais.fragment.DeviceFragment;
import development.iarratais.fragment.DisplayFragment;
import development.iarratais.fragment.NetworkFragment;
import development.iarratais.fragment.PlayServicesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (rc == PackageManager.PERMISSION_DENIED) {
            requestReadPhoneStatePermission();
        }

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new PlayServicesFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fm = getFragmentManager();

        if (id == R.id.nav_play_servies) {
            fm.beginTransaction().replace(R.id.content_frame, new PlayServicesFragment()).commit();
        } else if (id == R.id.nav_device_information){
            fm.beginTransaction().replace(R.id.content_frame, new DeviceFragment()).commit();
        } else if(id == R.id.nav_network_information){
            fm.beginTransaction().replace(R.id.content_frame, new NetworkFragment()).commit();
        } else if(id == R.id.nav_battery_information){
            fm.beginTransaction().replace(R.id.content_frame, new BatteryFragment()).commit();
        } else if(id == R.id.nav_display_information){
            fm.beginTransaction().replace(R.id.content_frame, new DisplayFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    /**
     * Request the READ_PHONE_STATE permission for access to the IMEI.
     */
    private void requestReadPhoneStatePermission() {
        Log.d(getClass().getSimpleName(), "READ_PHONE_STATE permission not granted, requesting...");

        final String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                .READ_PHONE_STATE)) {
            ActivityCompat.requestPermissions(this, permissions, 1001);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions, 1001);
            }
        };
    }
}
