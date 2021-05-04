package com.ian.tugasakhir.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.databinding.FragmentHomeBinding;
import com.ian.tugasakhir.network.ApiConfig;
import com.ian.tugasakhir.viewmodel.ViewModelFactory;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(new ApiConfig());
        HomeViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel.class);

        viewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                binding.pbHome.setVisibility(View.VISIBLE);
            else
                binding.pbHome.setVisibility(View.GONE);
        });

        viewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
            String absen = profile.isAbsen() ? getString(R.string.absened) : getString(R.string.not_absen);

            binding.tvName.setText(profile.getUsername());
            binding.tvAbsen.setText(absen);
            if (profile.isAbsen()) {
                Glide.with(this)
                        .load(R.drawable.ic_baseline_check_240)
                        .apply(new RequestOptions().override(300, 300))
                        .into(binding.status);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_baseline_clear_24)
                        .apply(new RequestOptions().override(300, 300))
                        .into(binding.status);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}