package com.ian.tugasakhir.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ian.tugasakhir.R;
import com.ian.tugasakhir.activity.LoginActivity;
import com.ian.tugasakhir.activity.SettingActivity;
import com.ian.tugasakhir.adapter.LaporanAdapter;
import com.ian.tugasakhir.model.Laporan;
import com.ian.tugasakhir.model.Profile;
import com.ian.tugasakhir.preference.ProfilePreference;
import com.ian.tugasakhir.viewmodel.HomeViewModel;
import com.ian.tugasakhir.viewmodel.ProfileViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    @BindView(R.id.btnSetting)
    Button btnSetting;

    @BindView(R.id.civAvatar)
    CircleImageView civAvatar;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.pbProfile)
    ProgressBar pbProfile;

    @BindView(R.id.tvHadir)
    TextView tvHadir;

    @BindView(R.id.tvIzin)
    TextView tvIzin;

    @BindView(R.id.tvAlpa)
    TextView tvAlpa;

    @BindView(R.id.btnLogout)
    Button btnLogout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.recyclerView)
    RecyclerView rvProfile;

    LaporanAdapter adapter;
    HomeViewModel homeViewModel;
    ProfileViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

        pbProfile.setVisibility(View.VISIBLE);
        homeViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.getProfileData().observe(getViewLifecycleOwner(), this::setData);

        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(requireActivity(), R.color.transparent));

        return view;
    }

    void setData(Profile profile) {
        Glide.with(this)
                .load(profile.getGambar())
                .apply(new RequestOptions().override(150, 150))
                .into(civAvatar);
        tvUsername.setText(profile.getId());
        tvName.setText(profile.getUsername());
        collapsingToolbarLayout.setTitle(profile.getUsername());

        String hadir = String.format(getString(R.string.hadir), profile.getHadir());
        String izin = String.format(getString(R.string.izin), profile.getIzin());
        String alpa = String.format(getString(R.string.alpa), profile.getAlpa());

        tvHadir.setText(hadir);
        tvIzin.setText(izin);
        tvAlpa.setText(alpa);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel.class);
        viewModel.setListLaporan(profile.getId());
        viewModel.getListLaporan().observe(getViewLifecycleOwner(), this::setLaporan);

        pbProfile.setVisibility(View.GONE);
    }

    private void setLaporan(ArrayList<Laporan> laporans) {
        rvProfile.setHasFixedSize(true);
        rvProfile.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LaporanAdapter(laporans);
        rvProfile.setAdapter(adapter);
    }

    @OnClick(R.id.btnSetting)
    void goSetting() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @OnClick(R.id.btnLogout)
    void logout() {
        homeViewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
            ProfilePreference preference = new ProfilePreference(requireActivity());
            profile.setSession(false);
            preference.setProfile(profile);
        });
        startActivity(new Intent(getActivity(), LoginActivity.class));
        requireActivity().finish();
    }
}