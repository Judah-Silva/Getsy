package com.example.getsy_v2;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.getsy_v2.DB.AppDatabase;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppDatabase.SHOPPING_CART_TABLE)
public class ShoppingCart {

    @PrimaryKey(autoGenerate = true)
    private int mCartId;

    private int mUserId;

    private int mCartCount;

    public ShoppingCart(int userId, int cartCount) {
        mUserId = userId;
        mCartCount = cartCount;
    }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int cartId) {
        mCartId = cartId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getCartCount() {
        return mCartCount;
    }

    public void setCartCount(int cartCount) {
        mCartCount = cartCount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "mCartId=" + mCartId +
                ", mUserId=" + mUserId +
                ", mCartCount=" + mCartCount +
                '}';
    }
}
