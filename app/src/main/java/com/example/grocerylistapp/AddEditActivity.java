package com.example.grocerylistapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";

    private EditText etItemName, etQuantity;
    private Button btnSave;

    private Button btnBack;

    private String receivedItemId;
    private DatabaseReference userSpecificGroceriesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnSave = findViewById(R.id.btnSave);
        // Initialize the Back button from your layout
        btnBack = findViewById(R.id.btnBack);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Error: You are not logged in.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "User is not authenticated. Cannot save or edit item.");
            finish();
            return;
        }


        String currentUserId = currentUser.getUid();
        userSpecificGroceriesRef = FirebaseDatabase.getInstance()
                .getReference("users") // <-- CORRECTED PATH
                .child(currentUserId);


        Log.d(TAG, "Database reference for user operations: " + userSpecificGroceriesRef.toString());

        receivedItemId = getIntent().getStringExtra("ITEM_ID");
        if (receivedItemId != null) {
            setTitle("Edit Item");
            String name = getIntent().getStringExtra("ITEM_NAME");
            int qty = getIntent().getIntExtra("ITEM_QTY", 1);
            etItemName.setText(name);
            etQuantity.setText(String.valueOf(qty));
            Log.d(TAG, "Editing item. ID: " + receivedItemId);
        } else {
            setTitle("Add New Item");
            Log.d(TAG, "Adding new item.");
        }

        btnSave.setOnClickListener(v -> validateAndSave());


        btnBack.setOnClickListener(v -> finish());
    }

    private void validateAndSave() {
        String itemName = etItemName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        int quantity;

        if (TextUtils.isEmpty(itemName)) {
            etItemName.setError("Item name cannot be empty.");
            Toast.makeText(this, "Please enter item name.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            quantity = TextUtils.isEmpty(quantityStr) ? 1 : Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                etQuantity.setError("Quantity must be positive.");
                Toast.makeText(this, "Quantity must be a positive number.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            etQuantity.setError("Invalid quantity.");
            Toast.makeText(this, "Invalid quantity format.", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "Invalid quantity format: " + quantityStr, e);
            return;
        }

        if (userSpecificGroceriesRef == null) {
            Toast.makeText(this, "Database error. Please restart the app.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "userSpecificGroceriesRef is null. Cannot proceed with save.");
            return;
        }

        if (receivedItemId == null) {
            addNewItem(itemName, quantity);
        } else {
            updateExistingItem(receivedItemId, itemName, quantity);
        }
    }

    private void addNewItem(String name, int quantity) {
        String newFirebaseKey = userSpecificGroceriesRef.push().getKey();

        if (newFirebaseKey == null) {
            Toast.makeText(this, "Could not create item entry. Please try again.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Firebase push().getKey() returned null for new item.");
            return;
        }

        GroceryItem item = new GroceryItem(newFirebaseKey, name, quantity);

        Log.d(TAG, "Attempting to save NEW item...");
        Log.d(TAG, " -> Path: " + userSpecificGroceriesRef.child(newFirebaseKey).toString());
        Log.d(TAG, " -> Data: ID=" + item.getId() + ", Name=" + item.getName() + ", Qty=" + item.getQuantity());

        userSpecificGroceriesRef.child(newFirebaseKey).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "SUCCESS: Item added to database.");
                    Toast.makeText(AddEditActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "FAILURE: Failed to save new item to Firebase.", e);
                    Toast.makeText(AddEditActivity.this, "Save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void updateExistingItem(String itemIdToUpdate, String name, int quantity) {
        GroceryItem item = new GroceryItem(itemIdToUpdate, name, quantity);

        Log.d(TAG, "Attempting to UPDATE item...");
        Log.d(TAG, " -> Path: " + userSpecificGroceriesRef.child(itemIdToUpdate).toString());
        Log.d(TAG, " -> Data: ID=" + item.getId() + ", Name=" + item.getName() + ", Qty=" + item.getQuantity());

        userSpecificGroceriesRef.child(itemIdToUpdate).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "SUCCESS: Item updated in database.");
                    Toast.makeText(AddEditActivity.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "FAILURE: Failed to update item in Firebase.", e);
                    Toast.makeText(AddEditActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
