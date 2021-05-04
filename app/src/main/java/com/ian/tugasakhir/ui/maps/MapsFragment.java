package com.ian.tugasakhir.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.databinding.FragmentMapsBinding;
import com.ian.tugasakhir.network.ApiConfig;
import com.ian.tugasakhir.ui.home.HomeViewModel;
import com.ian.tugasakhir.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    private FragmentMapsBinding binding;

    private final List<LatLng> points = new ArrayList<>();
    private MapsViewModel viewModel;

    @SuppressLint("MissingPermission")
    private final OnMapReadyCallback callback = googleMap -> {
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
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pbAbsen.setVisibility(View.GONE);

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(new ApiConfig());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MapsViewModel.class);
        viewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                binding.pbAbsen.setVisibility(View.VISIBLE);
            else
                binding.pbAbsen.setVisibility(View.GONE);
        });
        binding.btnAbsen.setOnClickListener(v -> absen());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void absen() {
        binding.pbAbsen.setVisibility(View.VISIBLE);
        if (PolyUtil.containsLocation(getLatitude(), getLongitude(), points, true)) {
            ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(new ApiConfig());
            HomeViewModel homeViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel.class);
            homeViewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
                String username = profile.getId();
                viewModel.setUsername(username);
                viewModel.absen().observe(getViewLifecycleOwner(), response -> {
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
            binding.pbAbsen.setVisibility(View.GONE);
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