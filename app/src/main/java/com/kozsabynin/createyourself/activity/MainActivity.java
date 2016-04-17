package com.kozsabynin.createyourself.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.fragments.CashflowFragment;
import com.kozsabynin.createyourself.fragments.FaveFragment;
import com.kozsabynin.createyourself.fragments.HistoryFragment;
import com.kozsabynin.createyourself.fragments.TemplateFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CashflowFragment()).commit();
        getSupportActionBar().setTitle("Поток денег");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent layouts.layouts.activity in AndroidManifest.xml.
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

        android.support.v4.app.FragmentManager fm;
        int id = item.getItemId();

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        frameLayout.removeAllViews();

        Fragment fragment = null;
        String title = "";
        if (id == R.id.nav_camera) {
            fragment = new CashflowFragment();
            title="Поток денег";
        } else if (id == R.id.category) {
            title = "Категории";
            fragment = new CategoryActivityFragment();

/*            fm = getSupportFragmentManager();

            fm.beginTransaction().replace(R.id.content_frame, new CategoryActivityFragment()).commit();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.content_frame));
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();*/
        } else if (id == R.id.templates) {
            title="Шаблоны";
            fragment = new TemplateFragment();
        } else if (id == R.id.history) {
            title="История";
            fragment = new HistoryFragment();
        }
        if(fragment != null){
            fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
