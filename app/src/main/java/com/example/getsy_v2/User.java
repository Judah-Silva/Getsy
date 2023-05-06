package com.example.getsy_v2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.getsy_v2.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;

    private boolean mIsAdmin;

    public User(String username, String password, boolean isAdmin) {
        mUsername = username;
        mPassword = password;
        mIsAdmin = isAdmin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserId=" + mUserId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
