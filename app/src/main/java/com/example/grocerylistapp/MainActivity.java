package com.example.grocerylistapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
=======
<<<<<<< HEAD
// Import View and MotionEvent for the OnTouchListener
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
=======
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
import android.widget.Button;
import android.widget.TextView;
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

<<<<<<< HEAD
import com.example.grocerylistapp.databinding.ActivityMainBinding;
=======
<<<<<<< HEAD
=======

>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
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

<<<<<<< HEAD
    private ActivityMainBinding binding;
    private GroceryAdapter adapter;
=======
    private TextView tvWelcome;
    private Button btnLogout, btnAdd;
    private RecyclerView rvGroceries;

<<<<<<< HEAD
    private GroceryAdapter adapter;
=======
    private GroceryAdapter adapter; // Now this should resolve
    private List<GroceryItem> groceryItemsListFromFirebase; // Store data from Firebase
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
    private DatabaseReference userGroceriesRef;

    // --- FIX 1: Declare the listener as a class field ---
    private ValueEventListener groceryListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD
=======
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        btnAdd = findViewById(R.id.btnAdd);
        rvGroceries = findViewById(R.id.rvGroceries);

<<<<<<< HEAD
        // Load BOTH button animations for press and release
        final Animation buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press);
        final Animation buttonReleaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release);

=======
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
        FirebaseUser currentUser = FirebaseUtils.getAuth().getCurrentUser();

        if (currentUser == null) {
            Log.w(TAG, "No user logged in. Redirecting to LoginActivity.");
            startActivity(new Intent(this, LoginActivity.class));
<<<<<<< HEAD
=======
<<<<<<< HEAD
            // Apply page transition on entry
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out);
=======
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
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

<<<<<<< HEAD
    private void setupUI(FirebaseUser currentUser) {
        String displayName = currentUser.getDisplayName();
=======
<<<<<<< HEAD
        // Adapter setup
        adapter = new GroceryAdapter(new ArrayList<>(), new GroceryAdapter.OnItemActionListener() {
            @Override
            public void onEdit(GroceryItem item) {
                if (item == null || item.getId() == null) {
                    Toast.makeText(MainActivity.this, "Cannot edit: Item data is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("ITEM_ID", item.getId());
                intent.putExtra("ITEM_NAME", item.getItem());
                intent.putExtra("ITEM_QTY", item.getQuantity());
                startActivity(intent);
                // Apply page transition when editing an item
                overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out);
=======
        groceryItemsListFromFirebase = new ArrayList<>(); // Initialize the list
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6


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
<<<<<<< HEAD
=======
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
            }

            @Override
            public void onDelete(GroceryItem item) {
                if (item == null || item.getId() == null) {
<<<<<<< HEAD
                    Toast.makeText(MainActivity.this, "Cannot delete item.", Toast.LENGTH_SHORT).show();
=======
<<<<<<< HEAD
                    Toast.makeText(MainActivity.this, "Cannot delete: Item data is missing.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userGroceriesRef == null) {
=======
                    Toast.makeText(MainActivity.this, "Cannot delete: Item data or ID is missing.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onDelete: Item or Item ID is null. Name: " + (item != null ? item.getName() : "N/A"));
                    return;
                }
                if (userGroceriesRef == null) {
                    Log.e(TAG, "userGroceriesRef is null, cannot delete item.");
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
                    Toast.makeText(MainActivity.this, "Database error. Please try again.", Toast.LENGTH_SHORT).show();
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
                    return;
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Item")
<<<<<<< HEAD
                        .setMessage("Are you sure you want to delete '" + item.getName() + "'?")
                        .setPositiveButton("Delete", (dialog, which) -> deleteItemFromFirebase(item))
=======
<<<<<<< HEAD
                        .setMessage("Are you sure you want to delete '" + item.getItem() + "'?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            userGroceriesRef.child(item.getId()).removeValue()
                                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "'" + item.getItem() + "' deleted.", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_LONG).show());
=======
                        .setMessage("Are you sure you want to delete '" + item.getName() + "'?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            Log.d(TAG, "Attempting to delete item: " + item.getName() + " (ID: " + item.getId() + ")");
                            userGroceriesRef.child(item.getId()).removeValue()
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "Successfully deleted item with ID: " + item.getId());
                                        Toast.makeText(MainActivity.this, "'" + item.getName() + "' deleted.", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e(TAG, "Failed to delete item with ID: " + item.getId(), e);
                                        Toast.makeText(MainActivity.this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    });
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
                        })
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

<<<<<<< HEAD
        binding.rvGroceries.setLayoutManager(new LinearLayoutManager(this));
        binding.rvGroceries.setAdapter(adapter);
    }

    private void setupDatabaseReference(FirebaseUser currentUser) {
        String userId = currentUser.getUid();
        userGroceriesRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId);
        Log.d(TAG, "Listening for data at path: " + userGroceriesRef.toString());
=======
        rvGroceries.setLayoutManager(new LinearLayoutManager(this));
<<<<<<< HEAD
        rvGroceries.setAdapter(adapter);

        String userId = currentUser.getUid();
        userGroceriesRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        loadGroceries();

        // Create a reusable OnTouchListener for animations and actions
        View.OnTouchListener buttonTouchListener = (view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Action: Finger presses down
                    view.startAnimation(buttonPressAnim);
                    break;
                case MotionEvent.ACTION_UP:
                    // Action: Finger lifts up
                    view.startAnimation(buttonReleaseAnim);
                    // Perform the actual click action on finger lift
                    if (view.getId() == R.id.btnAdd) {
                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        startActivity(intent);
                        // Apply page transition
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out);
                    } else if (view.getId() == R.id.btnLogout) {
                        FirebaseUtils.getAuth().signOut();
                        Log.d(TAG, "User signed out.");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        // Apply page transition
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                    break;
            }
            // Return true to indicate we handled the touch event
            return true;
        };

        // Apply the new listener to the buttons, replacing the old OnClickListeners
        btnAdd.setOnTouchListener(buttonTouchListener);
        btnLogout.setOnTouchListener(buttonTouchListener);
=======
        rvGroceries.setAdapter(adapter); // This should now be fine

        String userId = currentUser.getUid();
        userGroceriesRef = FirebaseDatabase.getInstance()
                .getReference("groceries")
                .child(userId);

        Log.d(TAG, "Listening for data at path: " + userGroceriesRef.toString());
        loadGroceries();

        btnLogout.setOnClickListener(v -> {
            FirebaseUtils.getAuth().signOut();
            Log.d(TAG, "User signed out.");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEditActivity.class)));
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
    }

    private void loadGroceries() {
        if (userGroceriesRef == null) {
            Log.e(TAG, "Database reference is null. Cannot load groceries.");
            return;
        }


        groceryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
<<<<<<< HEAD
                List<GroceryItem> tempItems = new ArrayList<>();
=======
<<<<<<< HEAD
                List<GroceryItem> tempItems = new ArrayList<>();
=======
                List<GroceryItem> tempItems = new ArrayList<>(); // Create a temporary list
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        try {
                            GroceryItem item = childSnapshot.getValue(GroceryItem.class);
                            if (item != null) {
                                item.setId(childSnapshot.getKey());
                                tempItems.add(item);
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
                                Log.d(TAG, "Loaded item: " + item.getName() + " (ID: " + item.getId() + ")");
                            } else {
                                Log.w(TAG, "Null item loaded from snapshot key: " + childSnapshot.getKey());
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing item: " + childSnapshot.getKey(), e);
                        }
                    }
                }
<<<<<<< HEAD
                adapter.setItems(tempItems);
                Log.d(TAG, "Grocery list updated. Item count: " + tempItems.size());
=======
<<<<<<< HEAD
                adapter.setItems(tempItems);
                Log.d(TAG, "Grocery list updated. Item count: " + tempItems.size());
=======
                // Use the adapter's setItems method to update the list and notify
                adapter.setItems(tempItems); // << This will call notifyDataSetChanged internally
                Log.d(TAG, "Grocery list updated. Item count: " + tempItems.size());

                if (tempItems.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No items yet. Add something!", Toast.LENGTH_SHORT).show();
                }
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
<<<<<<< HEAD
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
=======
                Log.e(TAG, "Failed to load groceries: ", error.toException());
<<<<<<< HEAD
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
=======
                Toast.makeText(MainActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
