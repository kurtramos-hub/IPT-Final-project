package com.example.grocerylistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
<<<<<<< HEAD
import android.util.Log; // Make sure Log is imported
=======
<<<<<<< HEAD
import android.util.Log;
// NEW: Import View and MotionEvent for animations
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
=======
import android.util.Log; // Make sure Log is imported
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import com.example.grocerylistapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
=======
<<<<<<< HEAD
import com.example.grocerylistapp.databinding.ActivityLoginBinding;
// NEW: Import specific Firebase exceptions for better error handling
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
// Other imports
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity_DEBUG";

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
<<<<<<< HEAD
=======
=======
import com.example.grocerylistapp.databinding.ActivityLoginBinding; // For View Binding
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser; // Import FirebaseUser

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity_DEBUG"; // Tag for logging

    private ActivityLoginBinding binding; // View Binding variable
    private FirebaseAuth auth;            // Firebase Auth variable
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate - START");

<<<<<<< HEAD

=======
<<<<<<< HEAD
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        // Check if user is already logged in (your code is good here)
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Log.d(TAG, "User already logged in: " + currentUser.getUid() + ". Redirecting to MainActivity.");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        // --- NEW: Add Button and Page Animations ---
        final Animation buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press);
        final Animation buttonReleaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release);

        // --- FIX 2: Use OnTouchListener for Login Button to handle animations ---
        binding.btnLogin.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    view.startAnimation(buttonPressAnim);
                    break;
                case MotionEvent.ACTION_UP:
                    view.startAnimation(buttonReleaseAnim);
                    // Perform the login logic on finger lift
                    loginUser();
                    break;
            }
            return true; // We handled the touch event
        });


        // --- Listener for Register TextView ---
        binding.tvRegister.setOnClickListener(v -> {
            Log.d(TAG, "Register TextView clicked. Starting RegisterActivity.");
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            // Apply a fade transition when going to Register page
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out);
=======
        // Inflate the layout using View Binding
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {

            auth = FirebaseAuth.getInstance();
            Log.d(TAG, "FirebaseAuth.getInstance() SUCCEEDED");


            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                Log.d(TAG, "User already logged in: " + currentUser.getUid() + ". Redirecting to MainActivity.");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                return;
            } else {
                Log.d(TAG, "No user currently logged in. Proceeding with login/register UI.");
            }

        } catch (Exception e) {

            Log.e(TAG, "Error during FirebaseAuth setup or current user check", e);
            Toast.makeText(this, "Initialization error. Please try restarting the app.", Toast.LENGTH_LONG).show();

        }



        binding.btnLogin.setOnClickListener(v -> {
            Log.d(TAG, "Login button clicked.");
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                binding.etEmail.setError("Email is required.");
                Toast.makeText(LoginActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Login attempt with empty email.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                binding.etPassword.setError("Password is required.");
                Toast.makeText(LoginActivity.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Login attempt with empty password.");
                return;
            }


            if (auth == null) {
                Toast.makeText(LoginActivity.this, "Authentication service not ready. Please restart.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "auth is null in btnLogin.setOnClickListener. Firebase might not have initialized correctly.");
                return;
            }

            Log.d(TAG, "Attempting signInWithEmailAndPassword for: " + email);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Login SUCCESSFUL for: " + email);
                                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Log.e(TAG, "Login FAILED for: " + email, task.getException());
                                // Provide a more user-friendly error or specific ones based on task.getException()
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                Toast.makeText(LoginActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });


        binding.tvRegister.setOnClickListener(v -> {
            Log.d(TAG, "Register TextView clicked. Starting RegisterActivity.");
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
<<<<<<< HEAD

=======
            // Typically, you don't finish LoginActivity when going to RegisterActivity,
            // so the user can come back if they decide not to register.
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
        });

        Log.d(TAG, "onCreate - END (UI and listeners set up)");
    }
<<<<<<< HEAD
=======
<<<<<<< HEAD

    private void loginUser() {
        Log.d(TAG, "loginUser() method called.");
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.etPassword.setError("Password is required.");
            return;
        }

        Log.d(TAG, "Attempting signInWithEmailAndPassword for: " + email);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // --- Login Successful ---
                        Log.d(TAG, "Login SUCCESSFUL for: " + email);
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        // Apply fade transition on successful login
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else {
                        // --- Login Failed: Check the exception ---
                        Log.w(TAG, "Login FAILED for: " + email, task.getException());
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            // This error means the email address does not exist
                            Toast.makeText(LoginActivity.this, "No account found with this email.", Toast.LENGTH_LONG).show();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            // **FIX 1: THIS IS THE FIX YOU ASKED FOR**
                            // This error means the password is wrong
                            Toast.makeText(LoginActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            // Handle any other exceptions
                            Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Login failed with unknown exception: " + e.getMessage());
                        }
                    }
                });
    }
=======
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
>>>>>>> 873bff10e629059db8520d58dd18e8178813b5f6
}