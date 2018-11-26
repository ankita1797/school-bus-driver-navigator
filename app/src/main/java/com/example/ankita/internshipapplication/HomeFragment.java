package com.example.ankita.internshipapplication;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fragmentstabs.SelectBusFragment;
import fragmentstabs.StoppageFragment;
import fragmentstabs.StudentListFragment;

public class HomeFragment extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivity mainActivity;
    public static int int_items = 3 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views./*/
            View x = inflater.inflate(R.layout.home_tab_layout, container, false);
            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);
            viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
            return x;
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("Select Bus");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bus, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Student List");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.student_list_white, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("Stoppage");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bus_stoppage_white, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

    }


    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new SelectBusFragment();
                case 1 : return new StudentListFragment();
                case 2 : return new StoppageFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Select Bus";
                case 1 :
                    return "Student List";
                case 2 :
                    return "Stoppages";
            }
            return null;
        }

    }

}


