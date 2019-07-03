package com.example.runningtrial.UI;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runningtrial.R;

import java.util.ArrayList;

class MainRecyclerViewGridAdapter extends RecyclerView.Adapter<MainRecyclerViewGridAdapter.ViewHolder> {
    private ArrayList<GridData> gridDataArray;
    private boolean isGrid = true;

    public MainRecyclerViewGridAdapter(ArrayList<GridData> gridDataArray) {
        this.gridDataArray = gridDataArray;
    }
    public MainRecyclerViewGridAdapter(ArrayList<GridData> gridDataArray, boolean isGrid) {
        this(gridDataArray);
        this.isGrid = isGrid;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gridImage;
        TextView gridText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // find views in the main_grid_item
            gridImage = itemView.findViewById(R.id.grid_image);
            gridText = itemView.findViewById(R.id.grid_text);
        }

        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @NonNull
    @Override
    public MainRecyclerViewGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        int layoutId = R.layout.main_grid_item;
        if (!isGrid) layoutId = R.layout.main_linear_item;
        v = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewGridAdapter.ViewHolder holder, final int position) {
        holder.gridImage.setImageResource(gridDataArray.get(position).gridResId);
        holder.gridText.setText(gridDataArray.get(position).gridText);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Item " + position + " is clicked.", Toast.LENGTH_LONG).show();
                // DataWarehouse.getRef().TvDebug.setText("Item " + position + " is clicked.");
            }
        });
    }

    @Override
    public int getItemCount() {
        return gridDataArray.size();
    }
}

class GridData {
    int gridResId;
    String gridText;

    public GridData(int gridResId, String gridText) {
        this.gridResId = gridResId;
        this.gridText = gridText;
    }
}
