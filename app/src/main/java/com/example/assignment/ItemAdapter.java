package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private OnItemClickListener listener;

    // Use List<Item> for generalization instead of ArrayList<Item>
    public void updateList(List<Item> newItemList) {
        itemList = newItemList;
        notifyDataSetChanged(); // No need to override this method
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    public ItemAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflating the item_layout.xml layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        // Binding the data to the item view
        Item item = itemList.get(position);
        holder.name.setText(item.getName());

        // Handling item click
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item.getId()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // Binding the name TextView from the layout
            name = itemView.findViewById(R.id.item_name);
        }
    }
}
