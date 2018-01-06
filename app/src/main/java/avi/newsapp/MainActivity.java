package avi.newsapp;

import avi.newsapp.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler mHandler;
    String sources="google-news-in";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHandler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       loadHomeFragment();
    }

    private void loadHomeFragment() {


        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Bundle bundle = new Bundle();
                bundle.putString("msg",sources);
                Fragment fragment = new HomeFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.goo_in:
                sources="google-news-in";
                loadHomeFragment();
                break;
            case R.id.times:
                sources="the-times-of-india";
                loadHomeFragment();
                break;
            case R.id.hindu:
                sources="the-hindu";
                loadHomeFragment();
                break;
            case R.id.bbc:
                sources="bbc-news";
                loadHomeFragment();
                break;
            case R.id.cnn:
                sources="cnn";
                loadHomeFragment();
                break;
            case R.id.tele:
                sources="the-telegraph";
                loadHomeFragment();
                break;
            case R.id.espn_cric:
                sources="espn-cric-info";
                loadHomeFragment();
                break;
            case R.id.espn:
                sources="espn";
                loadHomeFragment();
                break;
            case R.id.sb:
                sources="the-sport-bible";
                loadHomeFragment();
                break;
            case R.id.techc:
                sources="techcrunch";
                loadHomeFragment();
                break;
            case R.id.techr:
                sources="techradar";
                loadHomeFragment();
                break;
            case R.id.hacker:
                sources="hacker-news";
                loadHomeFragment();
                break;
            case R.id.gadget:
                sources="engadget";
                loadHomeFragment();
                break;
            case R.id.ng:
                sources="national-geographic";
                loadHomeFragment();
                break;
            case R.id.nsci:
                sources="new-scientist";
                loadHomeFragment();
                break;
            case R.id.nbig:
                sources="next-big-future";
                loadHomeFragment();
                break;

            default:
                sources="google-news-in";
                loadHomeFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
