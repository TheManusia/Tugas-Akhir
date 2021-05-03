package com.ian.tugasakhir.ui.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ian.tugasakhir.data.Laporan;
import com.ian.tugasakhir.databinding.KehadiranItemsBinding;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private final ArrayList<Laporan> mData;

    public ProfileAdapter(ArrayList<Laporan> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        KehadiranItemsBinding binding = KehadiranItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final KehadiranItemsBinding binding;

        public ViewHolder(@NonNull KehadiranItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Laporan laporan) {
            binding.tvTanggal.setText(laporan.getTanggal());
            String type;
            switch (laporan.getType()) {
                case "hadir":
                    type = "Siswa Hadir";
                    break;
                case "alpa":
                    type = "Siswa Alpa";
                    break;
                default:
                    type = "Siswa izin";
                    break;
            }
            binding.tvType.setText(type);
        }
    }
}
