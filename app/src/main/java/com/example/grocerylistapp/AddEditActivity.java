package com.example.grocerylistapp;

import android.os.Bundle;
import android.text.TextUtils;
<<<<<<< HEAD
import android.util.Log;
import android.view.MenuItem;
=======
import android.util.Log; // Make sure Log is imported
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

=======
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener; // Ensure this is imported for detailed error
import com.google.android.gms.tasks.OnSuccessListener; // Ensure this is imported
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

<<<<<<< HEAD
=======
// Removed OnCompleteListener and Task imports as we are using OnSuccessListener/OnFailureListener directly

>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";

    private EditText etItemName, etQuantity;
    private Button btnSave;
<<<<<<< HEAD
    private Button btnBack; // NEW: Declare the back button

    private String receivedItemId;
    private DatabaseReference userSpecificGroceriesRef;
=======

    private String receivedItemId; // This is the ID from MainActivity if editing
    private DatabaseReference userSpecificGroceriesRef; // Reference to /groceries/{uid}
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

<<<<<<< HEAD
        // --- NEW: Add this block to enable the ActionBar back arrow ---
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // ----------------------------------------------------

        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack); // NEW: Initialize the back button

=======
        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnSave = findViewById(R.id.btnSave);

        // --- Get current authenticated user and set up database reference ---
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Error: You are not logged in.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "User is not authenticated. Cannot save or edit item.");
<<<<<<< HEAD
            finish();
=======
            finish(); // Close activity if no user
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
            return;
        }
        String currentUserId = currentUser.getUid();
        userSpecificGroceriesRef = FirebaseDatabase.getInstance()
<<<<<<< HEAD
                .getReference("Users")
                .child(currentUserId);
        Log.d(TAG, "Database reference for user operations: " + userSpecificGroceriesRef.toString());

        receivedItemId = getIntent().getStringExtra("ITEM_ID");
        if (receivedItemId != null) {
            setTitle("Edit Item");
            String itemName = getIntent().getStringExtra("ITEM_NAME");
            int qty = getIntent().getIntExtra("ITEM_QTY", 1);

            etItemName.setText(itemName);
            etQuantity.setText(String.valueOf(qty));
            Log.d(TAG, "Editing item. ID: " + receivedItemId + ", Name: " + itemName + ", Qty: " + qty);
=======
                .getReference("groceries")
                .child(currentUserId);
        Log.d(TAG, "Database reference for user operations: " + userSpecificGroceriesRef.toString());


        // --- Check if this is an edit operation ---
        // MainActivity should pass these extras if it's an edit
        receivedItemId = getIntent().getStringExtra("ITEM_ID");
        if (receivedItemId != null) {
            setTitle("Edit Item");
            String name = getIntent().getStringExtra("ITEM_NAME");
            int qty = getIntent().getIntExtra("ITEM_QTY", 1); // Default to 1 if not found

            etItemName.setText(name);
            etQuantity.setText(String.valueOf(qty));
            Log.d(TAG, "Editing item. ID: " + receivedItemId + ", Name: " + name + ", Qty: " + qty);
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
        } else {
            setTitle("Add New Item");
            Log.d(TAG, "Adding new item.");
        }

<<<<<<< HEAD
        btnSave.setOnClickListener(v -> {
            String itemName = etItemName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            int quantity;
=======

        btnSave.setOnClickListener(v -> {
            String itemName = etItemName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            int quantity = 1; // Default quantity
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847

            if (TextUtils.isEmpty(itemName)) {
                etItemName.setError("Item name cannot be empty.");
                Toast.makeText(this, "Please enter item name.", Toast.LENGTH_SHORT).show();
                return;
            }

<<<<<<< HEAD
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

=======
            if (!TextUtils.isEmpty(quantityStr)) {
                try {
                    quantity = Integer.parseInt(quantityStr);
                    if (quantity <= 0) {
                        etQuantity.setError("Quantity must be positive.");
                        Toast.makeText(this, "Quantity must be a positive number.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    etQuantity.setError("Invalid quantity.");
                    Toast.makeText(this, "Invalid quantity format.", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "Invalid quantity format: " + quantityStr, e);
                    return; // Stop processing if quantity is invalid
                }
            } else {
                // If quantity field is empty, you might want to default it or require it
                // For now, it defaults to 1 as initialized.
                // If quantity is required:
                // etQuantity.setError("Quantity cannot be empty.");
                // Toast.makeText(this, "Please enter quantity.", Toast.LENGTH_SHORT).show();
                // return;
            }


>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
            if (userSpecificGroceriesRef == null) {
                Toast.makeText(this, "Database error. Please restart the app.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "userSpecificGroceriesRef is null. Cannot proceed with save.");
                return;
            }

<<<<<<< HEAD
=======
            // --- Logic for saving or updating ---
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
            if (receivedItemId == null) {
                addNewItem(itemName, quantity);
            } else {
                updateExistingItem(receivedItemId, itemName, quantity);
            }
        });
<<<<<<< HEAD

        // NEW: Add the click listener for the explicit "Back" button
        btnBack.setOnClickListener(v -> {
            // Simply finish the current activity to go back
            finish();
        });
    }

    // --- NEW: Add this method to handle clicks on the ActionBar ---
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // The 'android.R.id.home' is the ID of the back arrow
        if (item.getItemId() == android.R.id.home) {
            finish(); // Closes the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- NEW: Override finish() to apply the return animation consistently ---
    @Override
    public void finish() {
        super.finish();
        // This ensures the animation runs whether the user clicks our "Back" button,
        // the ActionBar arrow, or the physical device back button.
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.fade_in, R.anim.fade_out);
    }

    private void addNewItem(String itemName, int quantity) {
        String newFirebaseKey = userSpecificGroceriesRef.push().getKey();
        if (newFirebaseKey == null) {
            Toast.makeText(this, "Could not create item entry.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Firebase push().getKey() returned null.");
            return;
        }

        GroceryItem item = new GroceryItem(newFirebaseKey, itemName, quantity);
        userSpecificGroceriesRef.child(newFirebaseKey).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddEditActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to MainActivity after success
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddEditActivity.this, "Save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to save new item", e);
                });
    }

    private void updateExistingItem(String itemIdToUpdate, String itemName, int quantity) {
        GroceryItem item = new GroceryItem(itemIdToUpdate, itemName, quantity);
        userSpecificGroceriesRef.child(itemIdToUpdate).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddEditActivity.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to MainActivity after success
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddEditActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to update item", e);
=======
    }

    private void addNewItem(String name, int quantity) {
        String newFirebaseKey = userSpecificGroceriesRef.push().getKey();

        if (newFirebaseKey == null) {
            Toast.makeText(this, "Could not create item entry. Please try again.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Firebase push().getKey() returned null for new item.");
            return;
        }

        // Assuming your GroceryItem constructor is: public GroceryItem(String id, String name, int quantity)
        GroceryItem item = new GroceryItem(newFirebaseKey, name, quantity);

        Log.d(TAG, "Attempting to save NEW item to path: " + userSpecificGroceriesRef.child(newFirebaseKey).toString() + " with data: " + item.toString());

        userSpecificGroceriesRef.child(newFirebaseKey).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddEditActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully added new item with key: " + newFirebaseKey);
                    finish(); // Go back to MainActivity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddEditActivity.this, "Save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to save new item to " + userSpecificGroceriesRef.child(newFirebaseKey).toString(), e);
                });
    }

    private void updateExistingItem(String itemIdToUpdate, String name, int quantity) {
        // Assuming your GroceryItem constructor is: public GroceryItem(String id, String name, int quantity)
        GroceryItem item = new GroceryItem(itemIdToUpdate, name, quantity);
        // Note: When using setValue() with a POJO that contains an 'id' field,
        // Firebase will overwrite the entire node at itemIdToUpdate with this new object.
        // If the 'id' field in your 'item' object (itemIdToUpdate) matches the key of the node being updated,
        // it's effectively just updating the fields.

        Log.d(TAG, "Attempting to UPDATE item at path: " + userSpecificGroceriesRef.child(itemIdToUpdate).toString() + " with data: " + item.toString());

        userSpecificGroceriesRef.child(itemIdToUpdate).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddEditActivity.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully updated item with ID: " + itemIdToUpdate);
                    finish(); // Go back to MainActivity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddEditActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to update item at " + userSpecificGroceriesRef.child(itemIdToUpdate).toString(), e);
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
                });
    }
}