package tuyosi_wada_music.outlook.jp.imacoconya_n;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Timer;

import twitter4j.Twitter;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener, LocationListener, LoaderManager.LoaderCallbacks {

    private SharedPreferences sp;

    private Context mContext;
    private LocationManager gpsManager;
    private Timer timer;
    private TextView txtBearing;
    private TextView txtLongitude;
    private TextView txtLatitude;
    private TextView txtAltitude;
    private TextView txtAddress;
    private TextView txtSpeed;
    private TextView txtAccuracy;
    private TextView txtLast;
    private TextView txtCity;
    private TextView txtTweet;

    private boolean isSensing = false;

    private Twitter mTwitter;
    private RequestToken mRequestToken;
    private OAuthAuthorization _oauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        //右下ボタンさん処理
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.tweet);
        fab.setOnClickListener(this);

        //左側ナビゲーションビューさん
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //左側メニューさん
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.main_start_Button);
        toggleButton.setOnClickListener(this);

        //文字表示の取得
        txtBearing = (TextView) findViewById(R.id.txtBearing);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtAltitude = (TextView) findViewById(R.id.txtAltitude);
        txtAddress = (TextView) findViewById(R.id.txtAddress);

        timer = new Timer();
        //GPSを取得
        gpsManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_area) {

        } else if (id == R.id.menu_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_save) {

        } else if (id == R.id.menu_load) {

        } else if (id == R.id.menu_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sensing(View v){
        if (!isSensing) {
            Log.d("Main", String.valueOf(v.getId()));


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gpsManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

            isSensing = true;
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("service",isSensing);
            editor.apply();
        } else {
            gpsManager.removeUpdates(this);
            isSensing = false;
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("service",isSensing);
            editor.apply();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.tweet):
                Intent intent = new Intent(this,TweetActivity.class);
                startActivity(intent);
                break;

            case (R.id.main_start_Button):
                sensing(v);
                break;
            case (R.id.map_button):
                Intent intent1 = new Intent(this,MapsActivity.class);
                startActivity(intent1);
                break;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        txtLongitude.setText(String.valueOf(location.getLongitude()));
        txtLatitude.setText(String.valueOf(location.getLatitude()));
        txtAltitude.setText(String.valueOf(location.getAltitude()));
        txtBearing.setText(String.valueOf(location.getBearing()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
