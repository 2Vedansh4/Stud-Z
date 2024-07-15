package com.example.studz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView ;

    private ActionBarDrawerToggle toggle ;
    private NavController navController ;
    private DrawerLayout drawerLayout ;
    NavigationView navigationView ;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.my_drawer);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this,R.id.main_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        if (item.getItemId() == R.id.profile){
            startActivity(new Intent(MainActivity.this, test.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);

        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.pdf) {
            Intent intent = new Intent(MainActivity.this, test.class);
            startActivity(intent);
        } else if (itemId == R.id.rate) {
            Intent intent = new Intent(MainActivity.this, Tasky.class);
            startActivity(intent);

            Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show();
            // Handle the Rate menu item here
        } else if (itemId == R.id.share) {
            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
            // Handle the Share menu item here
        }else if (itemId == R.id.about) {
            Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
            // Handle the Share menu item here
        }
        return true ;

    }
    @Override
    public void onBackPressed() {
        // Check if the navigation drawer is open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // If it's open, close it
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // If it's not open, perform the default back button behavior
            super.onBackPressed();
        }
    }
}