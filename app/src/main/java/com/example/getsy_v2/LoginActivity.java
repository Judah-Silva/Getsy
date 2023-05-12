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
import com.example.getsy_v2.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private EditText mUsernameInput;
    private EditText mPasswordInput;

    private String mUsername;
    private String mPassword;

    private Button mLogIn;

    private UserDAO UserDAO;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getDatabase();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsernameInput = binding.childUserNameInput;
        mPasswordInput = binding.childPasswordInput;
        mLogIn = binding.childLogInButton;

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromDisplay();
                if (!checkCredentials()) {
                    backToMain();
                }
                forwardUntoLanding();
            }
        });
    }

    private void forwardUntoLanding() {
        Intent intent = LandingActivity.intentFactory(this, mUser.getUserId());
        startActivity(intent);
    }

    private void getDataFromDisplay() {
        mUsername = mUsernameInput.getText().toString();
        mPassword = mPasswordInput.getText().toString();
    }

    private boolean checkCredentials() {
        mUser = UserDAO.getUserByUsername(mUsername);
        if (mUser == null) {
            Toast.makeText(getApplicationContext(), "Incorrect username or password; returning to main activity", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mUser.getPassword().equals(mPassword)) {
            Toast.makeText(getApplicationContext(), "Incorrect username or password; returning to main activity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getDatabase() {
        UserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME).build().UserDAO();
    }

    private void backToMain() {
        Intent intent = MainActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}