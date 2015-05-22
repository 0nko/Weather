package com.ondrejruttkay.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ondrejruttkay.weather.R;
import com.ondrejruttkay.weather.WeatherConfig;
import com.ondrejruttkay.weather.adapter.DrawerListAdapter;
import com.ondrejruttkay.weather.entity.DrawerListItem;
import com.ondrejruttkay.weather.fragment.WeatherFragment;
import com.ondrejruttkay.weather.utility.Logcat;
import com.ondrejruttkay.weather.utility.PlayServices;
import com.ondrejruttkay.weather.view.FragmentNavigationDrawer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnSharedPreferenceChangeListener {
    private boolean mPreferencesChanged = false;
    private boolean mShowPlayServicesError = false;

    private FragmentNavigationDrawer mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout = (FragmentNavigationDrawer)findViewById(R.id.navigation_drawer);

        setSupportActionBar(mToolbar);

        // Setup drawer view
        mDrawerLayout.setupDrawerConfiguration((ListView) findViewById(R.id.left_drawer), mToolbar, R.id.content_frame);
        // Add nav items
        mDrawerLayout.addNavItem(getString(R.string.drawer_menu_today), R.drawable.ic_drawer_today_dark, getString(R.string.drawer_menu_today), WeatherFragment.class);
        mDrawerLayout.addNavItem(getString(R.string.drawer_menu_forecast), R.drawable.ic_drawer_forecast_dark, getString(R.string.drawer_menu_forecast), WeatherFragment.class);
        // Select default
        if (savedInstanceState == null) {
            mDrawerLayout.selectDrawerItem(0);
        }

        // register listener
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        // preferences have changed so refresh data
        if (mPreferencesChanged) {
            refreshData();
            mPreferencesChanged = false;
        }
    }

    private void refreshData() {
    }


    @Override
    public void onDestroy() {
        // unregister listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

        super.onDestroy();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.prefs_key_user_id))) {
            mPreferencesChanged = true;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (mShowPlayServicesError) {
            mShowPlayServicesError = false;
            PlayServices.showPlayServicesErrorDialog(this);
        }
    }

    /*
         * Handle results returned to this Activity by other Activities started with
         * startActivityForResult(). In particular, the method onConnectionFailed() in
         * LocationUpdateRemover and LocationUpdateRequester may call startResolutionForResult() to
         * start an Activity that handles Google Play services problems. The result of this
         * call returns here, to onActivityResult.
         */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case WeatherConfig.PLAY_SERVICES_FAILURE_RESOLUTION_REQUEST:

                String message;
                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:
                        PlayServices.retryConnect();
                        break;

                    // If any other result was returned by Google Play services
                    default:
                        message = getString(R.string.dialog_play_services_no_resolution);
                        Logcat.d(message);
                        mShowPlayServicesError = true;
                        break;
                }
                // If any other request code was received
            default:
                // Report that this Activity received an unknown requestCode
//                Logcat.d(getString(R.string.unknown_activity_request_code, requestCode));
                break;
        }
    }
}
