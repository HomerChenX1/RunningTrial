package com.example.runningtrial;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Toast;

public class TestAppBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbTestApp);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icons8_menu_64);
        toolbar.setLogo(R.drawable.icons8_puffin_bird_48);
        // toolbar.setSubtitle("Subtitle");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getBaseContext(), "Setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(getBaseContext(), "Favorite", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.app_bar_search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                // SearchView searchView =(SearchView) MenuItemCompat.getActionView(searchItem);
                Toast.makeText(getBaseContext(), "Search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
