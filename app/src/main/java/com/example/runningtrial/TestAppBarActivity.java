package com.example.runningtrial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/*
http://huli.logdown.com/posts/285806-android-searchview-notes
 */
public class TestAppBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    boolean navigationViewOn = false;
    SearchView searchView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbTestApp);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationIcon(R.drawable.icons8_menu_64);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), "NavigationIcon", Toast.LENGTH_SHORT).show();

                if (navigationViewOn) {
                    navigationViewOn = false;
                    navigationView.setVisibility(View.GONE);
                } else {
                    navigationViewOn = true;
                    navigationView.setVisibility(View.VISIBLE);
                }

            }
        });
        toolbar.setLogo(R.drawable.icons8_puffin_bird_48);
        // toolbar.setSubtitle("Subtitle");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(queryListener);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始搜索的时候，设置显示搜索页面
                // mVpContent.setCurrentItem(1);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //关闭搜索按钮的时候，设置显示默认页面
                // mVpContent.setCurrentItem(0);
                return false;
            }
        });
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
                Toast.makeText(getBaseContext(), "Search", Toast.LENGTH_SHORT).show();

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    // return : true to display the item as the selected item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.navigation_item_1:
                Toast.makeText(getApplicationContext(), "navigation_item_1 is clicked", Toast.LENGTH_SHORT).show();
                navigationViewOn = false;
                navigationView.setVisibility(View.GONE);
                return true;
            case R.id.navigation_item_2:
                Toast.makeText(getApplicationContext(), "navigation_item_2 is clicked", Toast.LENGTH_SHORT).show();
                navigationViewOn = false;
                navigationView.setVisibility(View.GONE);
                // return true;
                break;
            case R.id.navigation_sub_item_1:
                Toast.makeText(getApplicationContext(), "navigation_sub_item_1 is clicked", Toast.LENGTH_SHORT).show();
                navigationViewOn = false;
                navigationView.setVisibility(View.GONE);
                return true;
            case R.id.navigation_sub_item_2:
                Toast.makeText(getApplicationContext(), "navigation_sub_item_2 is clicked", Toast.LENGTH_SHORT).show();
                navigationViewOn = false;
                navigationView.setVisibility(View.GONE);
                // return true;
                break;
        }
        return false;
    }

    final private android.support.v7.widget.SearchView.OnQueryTextListener queryListener =
            new android.support.v7.widget.SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {
            //在文字改变的时候回调，query是改变之后的文字
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            //文字提交的时候哦回调，newText是最后提交搜索的文字
            // mSearchFragment.setSearchStr(query);
            return false;
        }
    };
}
