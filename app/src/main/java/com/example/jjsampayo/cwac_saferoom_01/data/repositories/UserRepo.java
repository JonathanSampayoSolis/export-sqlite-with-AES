package com.example.jjsampayo.cwac_saferoom_01.data.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;

import com.example.jjsampayo.cwac_saferoom_01.data.AppDatabase;
import com.example.jjsampayo.cwac_saferoom_01.data.daos.UserDao;
import com.example.jjsampayo.cwac_saferoom_01.data.entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepo {

    private UserDao dao;
    private ExecutorService executors;

    public UserRepo(Context context, int repoSize) {
        dao = AppDatabase.getAppDatabase(context, new SpannableStringBuilder("hello_world")).userDao();
        executors = Executors.newFixedThreadPool(3);

        setMockedData(repoSize);
    }

    public void getData(final Callbacks callbacks) {
        new GetData(dao, callbacks).execute();
    }

    private void setMockedData(int size) {
        User[] mockedData = new User[size];

        for (int i = 0; i < size; i++)
            mockedData[i] = new User("Jonathan Jairo", "Sampayo Solis"
                    , "775-152-93-16", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                    "tempor incididunt ut labore et dolore magna aliqua.");

        executors.execute(() -> {
            dao.clearTable();
            dao.insert(mockedData);
        });
    }

    public interface Callbacks {

        void onGetDataFinished(List<User> userList);

    }

    public static class GetData extends AsyncTask<Void, Void, List<User>> {

        private UserDao dao;
        private Callbacks callbacks;

        GetData(UserDao dao, Callbacks callbacks) {
            this.dao = dao;
            this.callbacks = callbacks;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return dao.getAll();
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            callbacks.onGetDataFinished(userList);
        }
    }
}
