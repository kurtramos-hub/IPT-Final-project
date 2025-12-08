package com.example.grocerylistapp;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

=======
<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
// Import View and MotionEvent for animations
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

=======

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
import com.example.grocerylistapp.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

=======
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;

=======
import com.google.firebase.auth.UserProfileChangeRequest;


>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD
        mAuth = FirebaseUtils.getAuth();

        binding.btnRegister.setOnClickListener(v -> registerUser());

=======
<<<<<<< HEAD
        auth = FirebaseUtils.getAuth();

        // Load button animations
        final Animation buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press);
        final Animation buttonReleaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release);

        // Use OnTouchListener for register button animation
        binding.btnRegister.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    view.startAnimation(buttonPressAnim);
                    break;
                case MotionEvent.ACTION_UP:
                    view.startAnimation(buttonReleaseAnim);
                    registerUser(); // Call the register logic
                    break;
            }
            return true;
        });

        // --- NEW: Add OnClickListener for the Login TextView ---
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
        binding.tvLogin.setOnClickListener(v -> {
            // Navigate back to LoginActivity
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {

        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            binding.etName.setError("Full name is required.");
            binding.etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email is required.");
            binding.etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Please enter a valid email.");
            binding.etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.etPassword.setError("Password is required.");
            binding.etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            binding.etPassword.setError("Password must be at least 6 characters.");
            binding.etPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        updateUserProfile(user, name);
                    } else {

                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateUserProfile(FirebaseUser user, String name) {
        if (user == null) return;

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated successfully with name: " + name);
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.w(TAG, "Failed to update user profile.", task.getException());

                        Toast.makeText(RegisterActivity.this, "Account created, but failed to set name.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
<<<<<<< HEAD
=======
=======

        auth = FirebaseUtils.getAuth();


        binding.btnRegister.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();


            if (TextUtils.isEmpty(email) || password.length() < 6) {
                Toast.makeText(this, "Please enter valid email and password (6+)", Toast.LENGTH_SHORT).show();
                return;
            }


            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (auth.getCurrentUser() != null) {
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            auth.getCurrentUser().updateProfile(profile);
                        }
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }
}
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
