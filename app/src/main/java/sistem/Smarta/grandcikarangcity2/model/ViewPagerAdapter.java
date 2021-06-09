package sistem.Smarta.grandcikarangcity2.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import sistem.Smarta.grandcikarangcity2.rt.isibutton.fragmenthistorysurat.Fragmenall;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmenlist = new ArrayList<>();
    private final List<String>mfragtitle = new ArrayList<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmenlist.get(position);
    }

    @Override
    public int getCount() {
        return mFragmenlist.size();
    }

    public void addfrag(Fragment fragment, String titlet) {
        mFragmenlist.add(fragment);
        mfragtitle.add(titlet);
    }
    public CharSequence getPageTitle(int position){
        return mfragtitle.get(position);

    }
}
