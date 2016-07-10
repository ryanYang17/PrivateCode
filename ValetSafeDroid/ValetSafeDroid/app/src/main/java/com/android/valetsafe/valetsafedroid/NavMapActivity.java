package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.widget.Toast;

public class NavMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OrderFragment.OnOrderFragmentInteractionListener,
        OrderDetailFragment.OnOrderDetailFragmentInteractionListener,
        MainMapFragment.OnMainMapFragmentInteractionListener,
        OrderTakingFragment.OnOrderTakingFragmentInteractionListener {

    private OrderFragment order;
    double m_Lat = 0.0, m_Lon = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mainfab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_menu_view);
        navigationView.setNavigationItemSelectedListener(this);
        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
/**设置MenuItem默认选中项**/
        navigationView.getMenu().getItem(0).setChecked(true);

        setOrderFragment();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
            setOrderFragment();
        }

        return false;

    }

    private void setOrderFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        order = new OrderFragment();
        transaction.replace(R.id.main_fragment_content, order);
        transaction.commit();

    }

    private void setMainMapFragment() {
        lo();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainMapFragment m = new MainMapFragment();
        m.SetLatLon(NavMapActivity.this.getApplicationContext(), 50, 50);
        transaction.replace(R.id.main_fragment_content, m);
        transaction.commit();
    }

    private void lo() {
        LocationManager locationManager = (LocationManager) getSystemService(NavMapActivity.this.getApplicationContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                m_Lat = location.getLatitude();
                m_Lon = location.getLongitude();
            }
        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }
                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }
                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                m_Lat = location.getLatitude(); //经度
                m_Lon = location.getLongitude(); //纬度
            }
        }
    }

    private void setOrderDetailFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        OrderDetailFragment order = new OrderDetailFragment();
        transaction.replace(R.id.main_fragment_content, order);
        transaction.commit();

    }

    private void setOrderTakingFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        OrderTakingFragment order = new OrderTakingFragment();
        transaction.replace(R.id.main_fragment_content, order);
        transaction.commit();

    }

    private void setWaitingFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        WaitingFragment w = new WaitingFragment();
        transaction.replace(R.id.main_fragment_content, w);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.nav_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify shape_circle_button parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_payment) {
            Intent intent = new Intent(NavMapActivity.this, PaymentActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_processing) {
            Intent intent = new Intent(NavMapActivity.this, AdvancedOrderActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_history) {
            Intent intent = new Intent(NavMapActivity.this, HistoryOrderActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_settings) {
            Intent intent = new Intent(NavMapActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_help) {
            Intent intent = new Intent(NavMapActivity.this, HistoryOrderActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_about) {
            Intent intent = new Intent(NavMapActivity.this, HistoryOrderActivity.class);
            startActivity(intent);
        }
//        if (id == R.id.n) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onOrderFragmentNowBtn() {
        setMainMapFragment();
        Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderFragmentAdvancedBtn() {
        setOrderDetailFragment();
        Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderDetailFragmentBackBtn() {
        setOrderFragment();
        Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderDetailFragmentNextBtn() {
        setWaitingFragment();
        Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMainMapFragmentNextBtn() {
        setOrderTakingFragment();
        Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onOrderTakingFragmentInteraction() {
        //setOrderTakingFragment();
       // Toast.makeText(NavMapActivity.this, "abc", Toast.LENGTH_SHORT).show();
    }

}
