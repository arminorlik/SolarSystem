package com.example.armin.solarsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolarObjectActivity extends AppCompatActivity implements SolarObjectAdapter.SolarObjectClickedListener {

    public static final String OBJECT_KEY = "object";
    @BindView(R.id.objectImageView)
    ImageView objectImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.objectTextView)
    TextView objectTextView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.moonsLabel)
    TextView moonsLabel;
    @BindView(R.id.moonsRecyclerView)
    RecyclerView moonsRecyclerView;
    private SolarObject solarObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_object);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        solarObject = (SolarObject) getIntent().getSerializableExtra(OBJECT_KEY);

        toolbarLayout.setTitle(solarObject.getName());

        try { //ustawienie tekstu opisowego planety
            String text = SolarObject.loadFromAssest(this, solarObject.getText());
            objectTextView.setText(Html.fromHtml(text));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Glide.with(this)
                .load(solarObject.getImagePath())
                .into(objectImageView);

        moonsRecyclerView.setVisibility(solarObject.hasMoon() ? View.VISIBLE : View.GONE); //sprawdzenie czy są księżyce, jeśli tak
        //Visible jeśli nie ukrywanie recycleView
        moonsLabel.setVisibility(solarObject.hasMoon() ? View.VISIBLE : View.GONE); //podobne ukrywanie w przypadku labelki

        if (solarObject.hasMoon()) {
            SolarObjectAdapter solarObjectAdapter = new SolarObjectAdapter(solarObject.getMoons());
            solarObjectAdapter.setSolarObjectClickedListener(this);
            moonsRecyclerView.setAdapter(solarObjectAdapter);
            moonsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            moonsRecyclerView.setNestedScrollingEnabled(false); //wymuszenie aby jeden scroll był odpowiedzialny za przwijanie
        }
    }

    public static void start(Activity activity, SolarObject solarObject) {
        Intent intent = new Intent(activity, SolarObjectActivity.class);
        intent.putExtra(OBJECT_KEY, solarObject);
        activity.startActivity(intent);
    }

    @Override
    public void SolarObjectClicked(SolarObject solarObject) {
        SolarObjectActivity.start(this, solarObject);
    }
}
