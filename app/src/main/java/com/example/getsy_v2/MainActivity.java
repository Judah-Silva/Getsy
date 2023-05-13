package com.example.getsy_v2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.getsy_v2.DB.AppDatabase;
import com.example.getsy_v2.DB.UserDAO;
import com.example.getsy_v2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String USER_ID_KEY = "com.example.getsy_v2_userIdKey";

    private ActivityMainBinding binding;

    private Button mLogIn;

    private Button mSignUp;

    private int mUserId;

    private UserDAO UserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();

        checkProducts();

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

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void checkProducts() {
        List<Product> productList = UserDAO.getAllProducts();
        if (productList.size() <= 0) {
            Product product1 = new Product(1, 66.0, "Vader's Lightsaber", "The lightsaber of the strongest force user");
            Product product2 = new Product(1, 100.0, "Anakin's Lightsaber", "A lightsaber that was used to kill younglings");
            Product product3 = new Product(5, 15.0, "Rorkid Bread", "Yummy bread made by the Ewoks");
            Product product4 = new Product(10, 20.0, "StormTrooper Blaster", "Blaster with terrible aim");
            Product product5 = new Product(20, 5.0, "Ice cream cone", "Mint Chocolate Chip");
            Product product6 = new Product(5, 50.0, "Robe", "So that you can look like a jedi");
            Product product7 = new Product(3, 30.0, "Bacta tank", "The lifeblood of the commandos");
            UserDAO.insert(product1, product2, product3, product4, product5, product6, product7);
        }
    }

    private void getDatabase() {
        UserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build().UserDAO();
    }

    private void checkForUser() {
        //do we have any users?
        List<User> users = UserDAO.getAllUsers();
        if (users.size() <= 0) {
            User testUser = new User("testUser", "password", false);
            User adminUser = new User("admin1", "password", true);
            UserDAO.insert(testUser, adminUser);
        }
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
        Intent intent = SignUpActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

}