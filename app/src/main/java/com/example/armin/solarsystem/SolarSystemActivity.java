package com.example.armin.solarsystem;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolarSystemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MoonsFragment.TabCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.moonsTabLayout)
    TabLayout moonsTabLayout;
    @BindView(R.id.containerLayout)
    FrameLayout containerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private SolarObject[] planets;
    private SolarObject[] others;
    private SolarObject[] objectsWithMoons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_system);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        planets = SolarObject.loadArrayFromJson(this, "planets");
        others = SolarObject.loadArrayFromJson(this, "others");

        ArrayList<SolarObject> arrayList = new ArrayList<>();

        for (SolarObject planet : planets) {
            if (planet.hasMoon()) {
                arrayList.add(planet);
            }
        }

        for (SolarObject other : others) {
            if (other.hasMoon()) {
                arrayList.add(other);
            }
        }

        objectsWithMoons = new SolarObject[arrayList.size()];
        objectsWithMoons = arrayList.toArray(objectsWithMoons);

        navView.setCheckedItem(R.id.nav_planets);
        onNavigationItemSelected(navView.getMenu().findItem(R.id.nav_planets)); //domyślnie zaznaczona pierwsza opcja w nav
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

        if (id == R.id.nav_planets) {

            SolarObjectsFragment fragment = SolarObjectsFragment.newInstance(planets);

            replaceFragment(fragment);
        } else if (id == R.id.nav_moons) {

            replaceFragment(MoonsFragment.newInstance(objectsWithMoons));
        } else if (id == R.id.nav_other) {
            SolarObjectsFragment fragment = SolarObjectsFragment.newInstance(others);

            replaceFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showTabs(ViewPager viewPager) {
        moonsTabLayout.setVisibility(View.VISIBLE);
        moonsTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void hideTabs() {
        moonsTabLayout.removeAllTabs();
        moonsTabLayout.setOnTabSelectedListener(null);
        moonsTabLayout.setVisibility(View.GONE);
    }
}
