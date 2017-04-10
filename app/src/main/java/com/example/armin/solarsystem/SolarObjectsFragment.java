package com.example.armin.solarsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolarObjectsFragment extends Fragment implements SolarObjectAdapter.SolarObjectClickedListener {

    public static final String OBJECTS_KEY = "objects";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Unbinder unbinder;
    private SolarObjectAdapter adapter;

    public SolarObjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solar_objects, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SolarObject[] objects = (SolarObject[]) getArguments().getSerializable(OBJECTS_KEY);

        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); //ustawienie layout managera i grid o dwóch kolumnach
        adapter = new SolarObjectAdapter(objects);
        adapter.setSolarObjectClickedListener(this);
        recycleView.setAdapter(adapter);
    }

    public static SolarObjectsFragment newInstance(SolarObject[] objects) {
        SolarObjectsFragment fragment = new SolarObjectsFragment();
        Bundle args = new Bundle();
        args.putSerializable(OBJECTS_KEY, objects);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void SolarObjectClicked(SolarObject solarObject) {
        Log.d(SolarObjectsFragment.class.getSimpleName(), "Clicked: " + solarObject.getName());

        SolarObjectActivity.start(getActivity(), solarObject);
    }
}
