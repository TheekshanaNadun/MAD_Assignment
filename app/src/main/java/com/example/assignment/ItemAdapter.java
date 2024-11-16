package com.example.assignment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION; // Track selected item position

    // Constructor for initializing the list and listener
    public ItemAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    // Update list of items
    public void updateList(List<Item> newItemList) {
        itemList = newItemList;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item_layout.xml layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        // Binding data to item view
        Item item = itemList.get(position);
        holder.name.setText(item.getName());

        // Highlight the selected item
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#8e8ff7")); // Light gray for selected item
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // No background for unselected items
        }

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            // Update selected item
            int previousPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(previousPosition); // Update previous item
            notifyItemChanged(selectedPosition); // Update selected item
            listener.onItemClick(item.getId()); // Notify the listener
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // Bind TextView
            name = itemView.findViewById(R.id.item_name);
        }
    }

    // Interface to handle item click events
    public interface OnItemClickListener {
        void onItemClick(int id);
    }
}
