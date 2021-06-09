package sistem.Smarta.grandcikarangcity2.rt.isibutton.penguruspack;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import sistem.Smarta.grandcikarangcity2.BuildConfig;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.rt.isibutton.Pengurus;

public class FragmentNavigationManager implements NavigationManager {
 private static FragmentNavigationManager mInstance;
 private FragmentManager mFragmentManager;
 private Pengurus pengurus;

 public static FragmentNavigationManager getmInstance(Pengurus pengurus)
 {
     if (mInstance==null)
         mInstance = new FragmentNavigationManager();
     mInstance.configure(pengurus);
     return mInstance;
 }

 private void configure(Pengurus pengurus){
        pengurus = pengurus;
        mFragmentManager = pengurus.getSupportFragmentManager();
 }

    @Override
    public void showFragment(String title) {
     showFragment(FragmentContent.newInstance(title),false);
 }

    private void showFragment(Fragment fragmentContent, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.containers,fragmentContent);
        ft.addToBackStack(null);
        if (b || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();

    }


}
