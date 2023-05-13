package com.example.getsy_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getsy_v2.DB.AppDatabase;
import com.example.getsy_v2.DB.UserDAO;
import com.example.getsy_v2.databinding.ActivitySearchBinding;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.getsy_v2_userIdKey";

    private ActivitySearchBinding binding;

    private AutoCompleteTextView searchBar;

    private TextView itemNameDisplay;
    private TextView itemInfoDisplay;

    private String itemName;

    private Button searchButton;
    private Button buyButton;

    private String[] itemList;

    private UserDAO UserDAO;

    private Product item;

    private int mUserId;

    private ShoppingCart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getDatabase();

        getId();

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchBar = binding.searchItemInput;
        itemNameDisplay = binding.itemName;
        itemInfoDisplay = binding.itemInformation;
        searchButton = binding.searchButtonSearchActivity;
        buyButton = binding.buyButton;

        setUpSearchBar();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromDisplay();
                displayItemInfo();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToCart();
            }
        });

    }

    private void getId() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    private void addProductToCart() {
        if (mUserId != -1) {
            cart = UserDAO.getCartByUserId(mUserId);
        }
        if (cart == null) {
            cart = new ShoppingCart(mUserId, 0, 0);
            UserDAO.insert(cart);
        }
        if (cart != null) {
            if (item.getQuantity() > 0) {
                cart.setProductId(item.getProductId());
                item.setQuantity(item.getQuantity() - 1);
                Toast.makeText(this, "Item successfully bought", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, "Item is out of stock, cannot buy", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, "Could not buy item", Toast.LENGTH_SHORT).show();
    }

    private void getDataFromDisplay() {
        itemName = searchBar.getText().toString();
    }

    private void displayItemInfo() {
        item = UserDAO.getProductByName(itemName);
        if (item == null) {
            Toast.makeText(this, "Product does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        itemNameDisplay.setText(itemName);
        itemNameDisplay.setVisibility(View.VISIBLE);

        StringBuilder info = new StringBuilder();

        info.append("Price: ").append(item.getPrice()).append(" credits\n");
        info.append("In Stock: ").append(item.getQuantity()).append("\n");
        info.append("Description: ").append(item.getDescription());
        itemInfoDisplay.setText(info.toString());
        itemInfoDisplay.setVisibility(View.VISIBLE);

        buyButton.setVisibility(View.VISIBLE);
    }

    private void getDatabase() {
        UserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build().UserDAO();
    }

    private void setUpSearchBar() {
        List<Product> productList = UserDAO.getAllProducts();
        itemList = new String[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            itemList[i] = productList.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, itemList);
        searchBar.setAdapter(adapter);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}