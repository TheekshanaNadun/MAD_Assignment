package com.example.assignment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.DatabaseHelper;
import com.example.assignment.Item;
import com.example.assignment.ItemAdapter;
import com.example.assignment.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText etItemName;
    Button btnAdd, btnUpdate, btnDelete;
    RecyclerView recyclerView;
    ArrayList<Item> itemList;
    ItemAdapter itemAdapter;
    DatabaseHelper dbHelper;
    int selectedId = -1; // Tracks selected item for update/delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etItemName = findViewById(R.id.etItemName);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize DatabaseHelper and RecyclerView
        dbHelper = new DatabaseHelper(this);
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList, id -> {
            // On item click, store the id of the selected item
            selectedId = id;
            Item selectedItem = itemList.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
            if (selectedItem != null) {
                etItemName.setText(selectedItem.getName());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        loadItems();

        // Add button functionality
        btnAdd.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            if (!name.isEmpty()) {
                dbHelper.insertItem(name);  // Insert item into DB
                etItemName.setText("");  // Clear the input field
                loadItems();  // Reload items in RecyclerView
            }
        });

        // Update button functionality
        btnUpdate.setOnClickListener(v -> {
            if (selectedId != -1) {
                String name = etItemName.getText().toString().trim();
                dbHelper.updateItem(selectedId, name); // Update item in DB
                etItemName.setText("");  // Clear input field
                loadItems();  // Reload items in RecyclerView
                selectedId = -1;  // Reset selected ID
            }
        });

        // Delete button functionality
        btnDelete.setOnClickListener(v -> {
            if (selectedId != -1) {
                dbHelper.deleteItem(selectedId); // Delete item from DB
                loadItems();  // Reload items in RecyclerView
                selectedId = -1;  // Reset selected ID
            }
        });
    }

    // Method to load items from database and display in RecyclerView
    private void loadItems() {
        itemList.clear();
        Cursor cursor = dbHelper.getAllItems();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            itemList.add(new Item(id, name));
        }
        cursor.close();
        itemAdapter.notifyDataSetChanged();
    }
}
