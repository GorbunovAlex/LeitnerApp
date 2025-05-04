package alexgorbunov.space.leitnerapp;

import static android.view.View.INVISIBLE;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.view.View;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import alexgorbunov.space.leitnerapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;

import dagger.hilt.android.AndroidEntryPoint;

import java.time.Year;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    Context context;
    ExecutorService executorService;

    private AppBarConfiguration appBarConfiguration;

    void setThemeMode(int mode) {
        boolean isDarkMode = mode == R.id.dark_mode;
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("dark_mode", isDarkMode);
        editor.apply();

        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();
        this.executorService = Executors.newFixedThreadPool(4);

        alexgorbunov.space.leitnerapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert hostFragment != null;
        NavController navController = hostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navController.navigate(R.id.EditingFragment);
//            }
//        });

        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean("dark_mode", false);
        setThemeMode(isDarkMode ? R.id.dark_mode : R.id.light_mode);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
//        bottomAppBar.findViewById(R.id.fab).setVisibility(INVISIBLE);
        TextView bottomAppBarAuthor = bottomAppBar.findViewById(R.id.bottom_app_bar_author);
        bottomAppBarAuthor.setText(String.format("Aleksandr Gorbunov | %s", Year.now()));
        TextView bottomAppBarVersion = bottomAppBar.findViewById(R.id.bottom_app_bar_version);
        bottomAppBarVersion.setText("v1.0.0");

        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_starter_fragment_to_settings_fragment);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
//        assert hostFragment != null;
//        NavController navController = hostFragment.getNavController();
//        NavDestination currentDestination = navController.getCurrentDestination();
//
//        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
//        boolean isDarkMode = preferences.getBoolean("dark_mode", false);
//        if (isDarkMode) {
//            menu.findItem(R.id.dark_mode).setVisible(false);
//            menu.findItem(R.id.light_mode).setVisible(true);
//        } else {
//            menu.findItem(R.id.dark_mode).setVisible(true);
//            menu.findItem(R.id.light_mode).setVisible(false);
//        }
//
//
//        if (currentDestination != null) {
//            if (currentDestination.getId() != R.id.CardFragment) {
//                menu.findItem(R.id.edit_card).setVisible(false);
//                menu.findItem(R.id.delete_card).setVisible(false);
//            }
//        }
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        AppCompatDelegate delegate = AppCompatDelegate.create(this, null);

        if (id == R.id.dark_mode || id == R.id.light_mode) {
            setThemeMode(id);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}