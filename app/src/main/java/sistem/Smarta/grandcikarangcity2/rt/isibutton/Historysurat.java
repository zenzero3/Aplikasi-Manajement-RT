package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.ViewPagerAdapter;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmenthistorysurat.Fragmenall;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmenthistorysurat.Fragmenunsed;

public class Historysurat extends AppCompatActivity {
    TabLayout tab;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historysuratwarga);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        finish();
            }
        });
        viewPager = findViewById(R.id.tampilan);
        setupViewPager(viewPager);
        tab = findViewById(R.id.tabhistory);
        tab.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addfrag(new Fragmenall(),"Semua Surat");
        adapter.addfrag(new Fragmenunsed(),"Surat Pengajuan");
        viewPager.setAdapter(adapter);

    }
}