package com.example.grocerylistapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.grocerylistapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private GroceryAdapter adapter;
    private DatabaseReference userGroceriesRef;

    // --- FIX 1: Declare the listener as a class field ---
    private ValueEventListener groceryListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser currentUser = FirebaseUtils.getAuth().getCurrentUser();

        if (currentUser == null) {
            Log.w(TAG, "No user logged in. Redirecting to LoginActivity.");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setupUI(currentUser);
        setupRecyclerView();
        setupDatabaseReference(currentUser);
        loadGroceries();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (userGroceriesRef != null && groceryListener != null) {
            userGroceriesRef.removeEventListener(groceryListener);
            Log.d(TAG, "Firebase listener removed.");
        }
    }

    private void setupUI(FirebaseUser currentUser) {
        String displayName = currentUser.getDisplayName();


        if (displayName != null && !displayName.trim().isEmpty()) {
            binding.tvWelcome.setText("Welcome, " + displayName);
        } else {
            // Fallback if the name is not set for any reason.
            String email = currentUser.getEmail();
            if (email != null && !email.isEmpty()) {
                binding.tvWelcome.setText("Welcome, " + email);
            } else {

                binding.tvWelcome.setText("Welcome!");
            }
        }

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseUtils.getAuth().signOut();
            Log.d(TAG, "User signed out.");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // This will trigger onDestroy(), which removes the listener
        });

        binding.btnAdd.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEditActivity.class)));
    }

    private void setupRecyclerView() {
        adapter = new GroceryAdapter(new ArrayList<>(), new GroceryAdapter.OnItemActionListener() {
            @Override
            public void onEdit(GroceryItem item) {
                if (item == null || item.getId() == null) {
                    Toast.makeText(MainActivity.this, "Cannot edit item.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("ITEM_ID", item.getId());
                intent.putExtra("ITEM_NAME", item.getName());
                intent.putExtra("ITEM_QTY", item.getQuantity());
                startActivity(intent);
            }

            @Override
            public void onDelete(GroceryItem item) {
                if (item == null || item.getId() == null) {
                    Toast.makeText(MainActivity.this, "Cannot delete item.", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete '" + item.getName() + "'?")
                        .setPositiveButton("Delete", (dialog, which) -> deleteItemFromFirebase(item))
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        binding.rvGroceries.setLayoutManager(new LinearLayoutManager(this));
        binding.rvGroceries.setAdapter(adapter);
    }

    private void setupDatabaseReference(FirebaseUser currentUser) {
        String userId = currentUser.getUid();
        userGroceriesRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId);
        Log.d(TAG, "Listening for data at path: " + userGroceriesRef.toString());
    }

    private void loadGroceries() {
        if (userGroceriesRef == null) {
            Log.e(TAG, "Database reference is null. Cannot load groceries.");
            return;
        }


        groceryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<GroceryItem> tempItems = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        try {
                            GroceryItem item = childSnapshot.getValue(GroceryItem.class);
                            if (item != null) {
                                item.setId(childSnapshot.getKey());
                                tempItems.add(item);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing item: " + childSnapshot.getKey(), e);
                        }
                    }
                }
                adapter.setItems(tempItems);
                Log.d(TAG, "Grocery list updated. Item count: " + tempItems.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Check if the user is logged out. If so, the error is expected.
                if (FirebaseUtils.getAuth().getCurrentUser() == null) {
                    // This happens during logout, so show a cleaner message.
                    Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onCancelled triggered during logout, which is expected.");
                } else {
                    // If the user is still logged in, it's a real error.
                    Log.e(TAG, "Failed to load groceries: " + error.getMessage(), error.toException());
                    Toast.makeText(MainActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };


        userGroceriesRef.addValueEventListener(groceryListener);
    }

    private void deleteItemFromFirebase(GroceryItem item) {
        if (userGroceriesRef == null) return;
        userGroceriesRef.child(item.getId()).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "'" + item.getName() + "' deleted.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
