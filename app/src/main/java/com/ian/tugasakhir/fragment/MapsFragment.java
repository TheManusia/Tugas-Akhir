package com.ian.tugasakhir.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.viewmodel.HomeViewModel;
import com.ian.tugasakhir.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsFragment extends Fragment {
    @BindView(R.id.btnAbsen)
    Button btnAbsen;

    MapViewModel viewModel;
    HomeViewModel homeViewModel;
    GoogleMap mGoogleMap;
    List<LatLng> points = new ArrayList<>();

    @SuppressLint("MissingPermission")
    private OnMapReadyCallback callback = googleMap -> {
        mGoogleMap = googleMap;
        points.add(new LatLng(-7.927215, 112.602200));
        points.add(new LatLng(-7.927304, 112.602122));
        points.add(new LatLng(-7.927326, 112.602322));
        points.add(new LatLng(-7.927786, 112.602117));
        points.add(new LatLng(-7.927487, 112.601382));
        points.add(new LatLng(-7.927285, 112.601467));
        points.add(new LatLng(-7.927316, 112.601541));
        points.add(new LatLng(-7.926833, 112.601838));

        googleMap.addPolygon(new PolygonOptions()
                .addAll(points)
                .fillColor(R.color.colorAccent)
                .strokeColor(R.color.colorPrimary));

        LatLng skariga = new LatLng(-7.927534, 112.6018175);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(skariga));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(skariga, 18.0f));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @OnClick(R.id.btnAbsen)
    public void absen() {
        if (PolyUtil.containsLocation(getLatitude(), getLongitude(), points, true)) {
            viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MapViewModel.class);
            homeViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
            homeViewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
                String username = profile.getId();
                viewModel.setResponse(username);
                viewModel.getResponseData().observe(getViewLifecycleOwner(), response -> {
                    if (response.getSuccess() > 0) {
                        Toast.makeText(getContext(), "Berhasil Absen", Toast.LENGTH_SHORT).show();
                        profile.setAbsen(true);
                    } else {
                        Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });
        } else {
            Toast.makeText(getContext(), "Anda tidak berada di sekolah", Toast.LENGTH_SHORT).show();
        }
    }

    double longitude;
    double latitude;

    double getLongitude() {
        FusedLocationProviderClient mFused = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFused.getLastLocation().addOnSuccessListener(location -> longitude = location.getLongitude());
        }
        return longitude;
    }

    double getLatitude() {
        FusedLocationProviderClient mFused = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFused.getLastLocation().addOnSuccessListener(location -> latitude = location.getLatitude());
        }
        return latitude;
    }
}