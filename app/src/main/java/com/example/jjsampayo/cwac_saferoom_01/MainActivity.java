package com.example.jjsampayo.cwac_saferoom_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jjsampayo.cwac_saferoom_01.data.AppDatabase;
import com.example.jjsampayo.cwac_saferoom_01.data.entities.User;
import com.example.jjsampayo.cwac_saferoom_01.data.repositories.UserRepo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UserRepo.Callbacks {

    private MainActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityAdapter   = new MainActivityAdapter();
        UserRepo userRepo = new UserRepo(this, 50);

        RecyclerView recyclerView = findViewById(R.id.act_main_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(activityAdapter);

        userRepo.getData(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_export:
                try {
                    if (AppDatabase.exportDB(this))
                    Toast.makeText(this, "BD Exportada.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al exportar la BD.", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }

    @Override
    public void onGetDataFinished(List<User> userList) {
        activityAdapter.setData(userList);
    }
}
