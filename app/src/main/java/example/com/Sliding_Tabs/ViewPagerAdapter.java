package example.com.Sliding_Tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toshiba on 20-Jan-16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    String Titles[];
    int No_of_Tabs;
    Context mContext;


    public ViewPagerAdapter(FragmentManager fm, String[] titles, int no_of_Tabs,Context mcontext) {
        super(fm);
        Titles = titles;
        No_of_Tabs = no_of_Tabs;
        this.mContext = mcontext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            Tab_Categories t1 = new Tab_Categories(mContext);
            return t1;
        }
        else if (position == 1)
        {
            Tab_ViewTasks t2 = new Tab_ViewTasks();
            return t2;
        }
        else if (position == 2)
        {
            Tab_Create t3 = new Tab_Create(mContext);
            return t3;
        }
        else
        {
            Tab_Settings t4 = new Tab_Settings();
            return t4;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return No_of_Tabs;
    }
}

