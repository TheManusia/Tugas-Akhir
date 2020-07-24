package com.ian.tugasakhir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ian.tugasakhir.R;
import com.ian.tugasakhir.model.Laporan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {
    private final ArrayList<Laporan> mData;

    public LaporanAdapter(ArrayList<Laporan> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kehadiran_items, parent, false);
        return new ViewHolder(view);
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
        @BindView(R.id.tvTanggal)
        TextView tvTanggal;

        @BindView(R.id.tvType)
        TextView tvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Laporan laporan) {
            tvTanggal.setText(laporan.getTanggal());
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
            tvType.setText(type);
        }
    }
}
