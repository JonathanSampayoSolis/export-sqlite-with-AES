package com.example.jjsampayo.cwac_saferoom_01.data.daos;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jjsampayo.cwac_saferoom_01.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User... user);

    @Query("DELETE FROM user")
    void clearTable();

}
