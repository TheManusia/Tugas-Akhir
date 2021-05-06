package com.ian.tugasakhir.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ian.tugasakhir.BuildConfig;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.data.Profile;
import com.ian.tugasakhir.databinding.FragmentProfileBinding;
import com.ian.tugasakhir.network.ApiConfig;
import com.ian.tugasakhir.tools.ProfilePreference;
import com.ian.tugasakhir.ui.home.HomeViewModel;
import com.ian.tugasakhir.ui.login.LoginActivity;
import com.ian.tugasakhir.ui.setting.SettingActivity;
import com.ian.tugasakhir.viewmodel.ViewModelFactory;

import java.util.List;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(new ApiConfig());
        homeViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel.class);
        homeViewModel.getProfileData().observe(getViewLifecycleOwner(), this::setData);
        homeViewModel.isLoading().observe(getViewLifecycleOwner(), this::pbController);

        binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
        binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(requireActivity(), R.color.transparent));

        binding.btnSetting.setOnClickListener(v -> goSetting());
        binding.btnLogout.setOnClickListener(v -> logout());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void setData(Profile profile) {
        Glide.with(this)
                .load(BuildConfig.SERVER + profile.getGambar())
                .apply(new RequestOptions().override(150, 150))
                .into(binding.civAvatar);
        binding.tvUsername.setText(profile.getUsername());
        binding.tvName.setText(profile.getName());
        binding.collapsingToolbar.setTitle(profile.getName());

        String hadir = String.format(getString(R.string.hadir), profile.getHadir());
        String izin = String.format(getString(R.string.izin), profile.getIzin());
        String alpa = String.format(getString(R.string.alpa), profile.getAlpa());

        binding.tvHadir.setText(hadir);
        binding.tvIzin.setText(izin);
        binding.tvAlpa.setText(alpa);

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(new ApiConfig());
        ProfileViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(ProfileViewModel.class);
        viewModel.setUsername(profile.getUsername());
        viewModel.getListLaporan().observe(getViewLifecycleOwner(), this::setLaporan);

        viewModel.isLoading().observe(this, this::pbController);
    }

    private void pbController(boolean status) {
        if (status)
            binding.pbProfile.setVisibility(View.VISIBLE);
        else
            binding.pbProfile.setVisibility(View.GONE);
    }

    private void setLaporan(List<Laporan> laporans) {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ProfileAdapter adapter = new ProfileAdapter(laporans);
        binding.recyclerView.setAdapter(adapter);
    }

    private void goSetting() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    private void logout() {
        homeViewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
            ProfilePreference preference = new ProfilePreference(requireActivity());
            profile.setSession(false);
            preference.setProfile(profile);
        });
        startActivity(new Intent(getActivity(), LoginActivity.class));
        requireActivity().finish();
    }
}