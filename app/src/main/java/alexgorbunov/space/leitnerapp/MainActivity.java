package alexgorbunov.space.leitnerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import alexgorbunov.space.leitnerapp.databinding.ActivityMainBinding;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    Context context;
    private AppBarConfiguration appBarConfiguration;

    CardsViewModel cardsViewModel;

    private void initAppStorage() {
        try {
            File cards_storage = new File(context.getFilesDir(), Consts.CARDS_STORE);
            if (!cards_storage.exists()) {
                boolean isCreated = cards_storage.createNewFile();
                if (!isCreated) {
                    throw new RuntimeException("Can't create cards storage");
                } else {
                    Log.i(Consts.MAIN_ACTIVITY_TAG, "Cards storage created");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        cardsViewModel = new CardsViewModel(this.context);

        alexgorbunov.space.leitnerapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert hostFragment != null;
        NavController navController = hostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        initAppStorage();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.FirstFragment);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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