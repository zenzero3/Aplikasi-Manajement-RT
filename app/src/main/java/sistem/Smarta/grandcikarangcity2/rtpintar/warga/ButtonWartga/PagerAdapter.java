package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
int satu;
boolean swipeable = false;
    public PagerAdapter(@NonNull FragmentManager fm,int satu) {
        super(fm);
        this.satu = satu;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new pengajuansurat();
            case 1:
                return  new Riwayatsurat();
            default:return null;
        }
    }
    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }
    @Override
    public int getCount() {
        return satu;
    }
}
