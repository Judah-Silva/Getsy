package com.example.getsy_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getsy_v2.DB.AppDatabase;
import com.example.getsy_v2.DB.UserDAO;
import com.example.getsy_v2.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private EditText mUsernameInput;
    private EditText mPasswordInput;
    private EditText mConfirmPasswordInput;

    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;

    private Button mSignUpButton;

    private UserDAO UserDAO;

    private User mUser;

    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getDatabase();

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsernameInput = binding.signUpUsernameInput;
        mPasswordInput = binding.signUpPasswordInput;
        mConfirmPasswordInput = binding.signUpConfirmPassword;
        mSignUpButton = binding.signUpButton;

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromDisplay();
                if (validateUser()) {
                    addUserToDB();
                    Toast.makeText(SignUpActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                    forwardUntoLanding();
                }
            }
        });
    }

    private void forwardUntoLanding() {
        Intent intent = LandingActivity.intentFactory(getApplicationContext(), mUser.getUserId());
        startActivity(intent);
    }

    private void addUserToDB() {
        UserDAO.insert(mUser);
    }

    private void getDatabase() {
        UserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build().UserDAO();
    }

    private void getDataFromDisplay() {
        mUsername = mUsernameInput.getText().toString();
        mPassword = mPasswordInput.getText().toString();
        mConfirmPassword = mConfirmPasswordInput.getText().toString();
    }

    private boolean validateUser() {
        mUser = UserDAO.getUserByUsername(mUsername);
        if (mUser != null) {
            if (attempts == 2) {
                Toast.makeText(this, "Too many bad attempts", Toast.LENGTH_SHORT).show();
                backToMain();
                return false;
            }
            Toast.makeText(this, "Username already in use", Toast.LENGTH_SHORT).show();
            attempts++;
            return false;
        } else if (!mPassword.equals(mConfirmPassword)){
            if (attempts == 2) {
                Toast.makeText(this, "Too many bad attempts", Toast.LENGTH_SHORT).show();
                backToMain();
                return false;
            }
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            attempts++;
            return false;
        }
        mUser = new User(mUsername, mPassword, false);
        return true;
    }

    private void backToMain() {
        Intent intent = MainActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }
}