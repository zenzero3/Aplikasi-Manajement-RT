package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import sistem.Smarta.grandcikarangcity2.R;

public class SuratWarga extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suratwarga);
        TabLayout tabLayout = findViewById(R.id.tping);
        TabItem satu = findViewById(R.id.pengajuan);
      ImageButton button = findViewById(R.id.backbuttwarga);
        TabItem dua = findViewById(R.id.pengajuanriwayat);

        final ViewPager viewPager = findViewById(R.id.isitampilan);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
