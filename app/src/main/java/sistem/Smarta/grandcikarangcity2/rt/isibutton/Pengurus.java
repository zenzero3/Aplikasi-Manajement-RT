package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.penguruspack.CustomExpandableListAdapter;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.penguruspack.FragmentNavigationManager;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.penguruspack.NavigationManager;


public class Pengurus extends AppCompatActivity {
private DrawerLayout mDrawerlayout;
private ActionBarDrawerToggle mDrawerToggle;
private String mActivityTitle;
private String[] items;

private ExpandableListView expandableListView;
private ExpandableListAdapter adapter;
private List<String>lsTitle;
String tititl;
private Map<String,List<String>>lstchild;
private NavigationManager navigationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_rt);
        mDrawerlayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = findViewById(R.id.navlist);
        navigationManager = FragmentNavigationManager.getmInstance(this);
        initItems();
        View listHeadeerViw = getLayoutInflater().inflate(R.layout.nav_header_main,null,false);
        expandableListView.addHeaderView(listHeadeerViw);
        gendatat();
        addDrawerItems();
        setupDrawer();
        if (savedInstanceState == null)
            selectFristItemasdefault();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Menu Pengurus");
        tititl ="Menu Pengurus";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#41C88E")));

        
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFristItemasdefault() {
    if (navigationManager != null)
    {
        String fristitem = lsTitle.get(0);
        navigationManager.showFragment(fristitem);
        getSupportActionBar().setTitle(fristitem);
    }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.bb,R.string.cc)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Masuk Menu Kepengurusan");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(tititl);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerlayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {
        adapter = new CustomExpandableListAdapter(this,lsTitle,lstchild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
              getSupportActionBar().setTitle(lsTitle.get(groupPosition));

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("Kepengurusan");
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selectedItem = ((List)(lstchild.get(lsTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);
                tititl = selectedItem;
                if (items[0].equals(lsTitle.get(groupPosition)))
                    navigationManager.showFragment(selectedItem);
                else
                    throw new IllegalArgumentException("Not Supported Fragment");

                mDrawerlayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    private void gendatat() {
        List<String>Title = Arrays.asList("Desa","Kepengurusan","Warga","Kebersihan & Lingkungan","Keungangan");
        List<String>childitem =Arrays.asList("satu","dua","tiga");
        List<String>childi =Arrays.asList("satudd","duadd","tigdda");
        lstchild = new TreeMap<>();
        lstchild.put(Title.get(0),childitem);
        lstchild.put(Title.get(1),childi);
        lstchild.put(Title.get(2),childitem);
        lsTitle = new ArrayList<>(lstchild.keySet());
        Log.d("hasil", "gendatat: "+lstchild.get(lsTitle.get(1)));

    }

    private void initItems() {
        items =new  String[]{"Desa","Kepengurusan","Warga","Kebersihan & Lingkungan","Keungangan"};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}