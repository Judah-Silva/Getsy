package com.example.getsy_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.getsy_v2.DB.AppDatabase;
import com.example.getsy_v2.DB.UserDAO;
import com.example.getsy_v2.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.getsy_v2_userIdKey";

    ActivityLandingBinding binding;

    private Button searchButton;
    private Button orderHistoryButton;
    private Button adminButton;

    private UserDAO UserDAO;

    private User mUser;

    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getDatabase();

        getId();

        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchButton = binding.searchButton;
        orderHistoryButton = binding.orderHistoryButton;
        adminButton = binding.adminButton;

        checkAdmin();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make search activity first
            }
        });

        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make order history activity first
            }
        });

    }

    private void checkAdmin() {
        if (mUser.isAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
        } else {
            adminButton.setVisibility(View.INVISIBLE);
        }
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void getId() {
        mUser = UserDAO.getUserById(getIntent().getIntExtra(USER_ID_KEY, -1));
        if (mUser != null) {
            mUserId = mUser.getUserId();
        }
        invalidateOptionsMenu();
    }

    private void getDatabase() {
        UserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build().UserDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mUser != null) {
            MenuItem item = menu.findItem(R.id.user_button);
            item.setTitle(mUser.getUsername());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.user_button) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Logout?");

            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    clearUserFromIntent();
                    clearUserFromPreferences();
                    mUserId = -1;
                    backToMain();
                }
            });

            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            alertBuilder.setCancelable(true);

            alertBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void backToMain() {
        Intent intent = MainActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    private void clearUserFromPreferences() {
        LoginActivity.putUserInPreferences(-1);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}