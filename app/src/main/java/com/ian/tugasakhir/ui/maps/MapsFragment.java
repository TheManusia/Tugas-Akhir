package com.ian.tugasakhir.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.ian.tugasakhir.data.Response;
import com.ian.tugasakhir.data.network.retrofit.Network;
import com.ian.tugasakhir.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class MapsFragment extends Fragment {
    @BindView(R.id.btnAbsen)
    Button btnAbsen;

    @BindView(R.id.pbAbsen)
    ProgressBar pbAbsen;

    HomeViewModel homeViewModel;
    GoogleMap mGoogleMap;
    final List<LatLng> points = new ArrayList<>();

    @SuppressLint("MissingPermission")
    private final OnMapReadyCallback callback = googleMap -> {
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
        pbAbsen.setVisibility(View.GONE);
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
        pbAbsen.setVisibility(View.VISIBLE);
        if (PolyUtil.containsLocation(getLatitude(), getLongitude(), points, true)) {
            homeViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
            homeViewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
                String username = profile.getId();
                Network.getService()
                        .absen(username)
                        .enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                                Response responseData = response.body();
                                if (responseData != null) {
                                    if (responseData.getSuccess() > 0) {
                                        Toast.makeText(getContext(), "Berhasil Absen", Toast.LENGTH_SHORT).show();
                                        profile.setAbsen(true);
                                    } else {
                                        Toast.makeText(getContext(), responseData.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                pbAbsen.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                pbAbsen.setVisibility(View.GONE);
                            }
                        });
            });
        } else {
            Toast.makeText(getContext(), "Anda tidak berada di sekolah", Toast.LENGTH_SHORT).show();
            pbAbsen.setVisibility(View.GONE);
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