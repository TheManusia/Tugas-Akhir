package com.ian.tugasakhir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.viewmodel.HomeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvAbsen)
    TextView tvAbsen;

    @BindView(R.id.status)
    ImageView status;

    @BindView(R.id.pbHome)
    ProgressBar pbHome;

    HomeViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        pbHome.setVisibility(View.VISIBLE);

        viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        viewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
            String absen = profile.isAbsen() ? getString(R.string.absened) : getString(R.string.not_absen);

            tvName.setText(profile.getUsername());
            tvAbsen.setText(absen);
            if (profile.isAbsen()) {
                Glide.with(this)
                        .load(R.drawable.ic_baseline_check_240)
                        .apply(new RequestOptions().override(300, 300))
                        .into(status);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_baseline_clear_24)
                        .apply(new RequestOptions().override(300, 300))
                        .into(status);
            }
            pbHome.setVisibility(View.GONE);
        });
        return view;
    }
}