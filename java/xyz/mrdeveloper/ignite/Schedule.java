package xyz.mrdeveloper.ignite;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static xyz.mrdeveloper.ignite.MainActivity.dayTabSelected;
import static xyz.mrdeveloper.ignite.MainActivity.isDetailsOpened;

/**
 * Created by Vaibhav on 26-01-2017.
 */

public class Schedule extends Fragment {
    public int mImageResIds;
    public String title;
    public String description;
    public String time;
    public String date;
    public String fee;
    public String prize;
    public String venue;
    public String day;
    public String category;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Schedule newInstance() {
        return new Schedule();
    }

    public Schedule() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.schedule, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        setTab();

        setupTabIcons();
        return view;
    }

    public void setupTabIcons() {
        //Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Calibri.ttf");
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("Feb 17");
        tabLayout.getTabAt(0).setCustomView(tabOne);
        //changeTabsFont(tabLayout, typeface);
//        tabLayout.getTabAt(0).setText("17 Feb");

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Feb 18");
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        //changeTabsFont(tabLayout, typeface);
//        tabLayout.getTabAt(1).setText("18 Feb");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        EventList eventList = new EventList();
        eventList.setEventList("1", 1);
        adapter.addFragment(eventList, "DAY 1");
        eventList = new EventList();
        eventList.setEventList("2", 1);
        adapter.addFragment(eventList, "DAY 2");
        //adapter.addFragment(new Day2("Day2", "https://api.myjson.com/bins/6kjqz", "day2events", "https://api.myjson.com/bins/k75sb", "day2eventdetails"), "DAY 2");
        viewPager.setAdapter(adapter);
    }

    private void changeTabsFont(TabLayout tabLayout, Typeface typeface) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }
    }

    public void setTab() {
        viewPager.setCurrentItem(dayTabSelected);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
