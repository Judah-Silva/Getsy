package com.example.getsy_v2.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.getsy_v2.Product;
import com.example.getsy_v2.ShoppingCart;
import com.example.getsy_v2.User;

@Database(entities = {User.class, ShoppingCart.class, Product.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Getsy.db";
    public static final String USER_TABLE = "user_table";
    public static final String SHOPPING_CART_TABLE = "shopping_cart_table";
    public static final String PRODUCT_TABLE = "product_table";

    private static volatile AppDatabase instance;
    public static final Object LOCK = new Object();

    public abstract UserDAO UserDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
