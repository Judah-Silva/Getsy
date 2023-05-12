package com.example.getsy_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.getsy_v2.DB.AppDatabase;
import com.example.getsy_v2.DB.UserDAO;
import com.example.getsy_v2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.getsy_v2_userIdKey";
    private static final String PREFERENCES_KEY = "com.example.getsy_v2.preferencesKey";

    private ActivityMainBinding binding;

    private Button mLogIn;

    private Button mSignUp;

    private SharedPreferences mPreferences = null;

    private int mUserId;

    private UserDAO mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();

        checkForUser();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mLogIn = binding.mainLogInButton;
        mSignUp = binding.mainSignUpButton;

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

//        mSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Call the intent factory of the sign up activity
//            }
//        });
    }

    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build().UserDAO();
    }

    private void checkForUser() {
        //check in preferences
        if (mPreferences == null) {
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if (mUserId != -1) {
            forwardUntoLanding();
        }

        //do we have any users?
        List<User> users = mUserDAO.getAllUsers();
        if (users.size() <= 0) {
            User testUser = new User("testUser", "password", false);
            User adminUser = new User("admin1", "password", true);
            mUserDAO.insert(testUser, adminUser);
        }
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logIn() {
        Intent intent = LoginActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    private void forwardUntoLanding() {
        Intent intent = LandingActivity.intentFactory(getApplicationContext(), mUserId);
        startActivity(intent);
    }

    private void signUp() {

    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

}