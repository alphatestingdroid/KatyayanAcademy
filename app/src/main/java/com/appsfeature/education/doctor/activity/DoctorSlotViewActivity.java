package com.appsfeature.education.doctor.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.adapter.ViewPagerAdapter;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.doctor.fragment.BaseRefreshFragment;
import com.appsfeature.education.doctor.fragment.DoctorSlotViewFragment;
import com.appsfeature.education.model.ExtraProperty;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.util.AppConstant;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.login.util.DatePickerDialog;
import com.google.android.material.tabs.TabLayout;
import com.helper.task.TaskRunner;
import com.helper.util.DayNightPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;


/**
 * @author Created by Abhijit on 01-05-2019.
 */

public class DoctorSlotViewActivity extends BaseActivity {

    private static final String DEFAULT_DATE_FORMAT = DateTimeUtil.DEFAULT_DATE_FORMAT;
    //    private static final int DEFAULT_DAYS_DIFFERENCE = 180;
    private static final int DEFAULT_DAYS_DIFFERENCE = 20;
    private static final int INVALID_DATE_POSITION = -1;
    private TabLayout tabLayout;
    private LayoutInflater layoutInflater;
    private ViewPager viewPager;
    private List<String> dateRange = new ArrayList<>();
    private int colorBlue, colorText;
    private String mSelectedDate;
    private DoctorModel doctorModel;
    private String patientName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_slot_view);

        doctorModel = getExtraProperty().getDoctorModel();
        patientName = getExtraProperty().getParentName(); //for reBooking
        initObjects();
        setUpToolBar("Select Time Slot");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isLoaded) {
            loadData();
            isLoaded = true;
        }
    }

    private boolean isLoaded = false;

    private Handler handler;

    private void loadData() {
        if (handler == null) {
            handler = new Handler();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setDataInTabView();
            }
        }, 400);

    }

    private void initObjects() {
        if (!DayNightPreference.isNightModeEnabled(this)) {
            colorBlue = Color.parseColor("#1876FA");
            colorText = Color.parseColor("#828A95");
        } else {
            colorBlue = Color.parseColor("#ffffff");
            colorText = Color.parseColor("#80ffffff");
        }
    }

    private void setDataInTabView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        startShimmerAnimation();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position != tabLayout.getSelectedTabPosition()) {
                    tabLayout.setScrollPosition(position, 0f, true);
                    Objects.requireNonNull(tabLayout.getTabAt(position)).select();
                }
                refreshFragment(position);

                if (position >= (dateRange.size() - (DEFAULT_DAYS_DIFFERENCE / 4))) {
                    addMoreDateRangeInViewPager(dateRange.get(dateRange.size() - 1), viewPager.getCurrentItem());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != viewPager.getCurrentItem()) {
                    viewPager.setCurrentItem(tab.getPosition());
                }
                updateSelectedTabView(tab, true, colorBlue);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateSelectedTabView(tab, false, colorText);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        findViewById(R.id.iv_calender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateRange != null && dateRange.size() > 0) {
                    String minDate = dateRange.get(dateRange.size() - 1);
                    String currentDate = getCurrentDate();
                    DatePickerDialog.newInstance(DoctorSlotViewActivity.this, true, new DatePickerDialog.DateSelectListener() {
                        @Override
                        public void onSelectDateClick(Date date, String yyyyMMdd) {
                            getCalenderSelectedDatePosition(yyyyMMdd);
                        }
                    }).show(currentDate);
                }
            }
        });
        tabLayout.removeAllTabs();
        layoutInflater = LayoutInflater.from(this);
        Date currentDate = Calendar.getInstance().getTime();
        addTabLayout(currentDate, 0);
    }

    private void startShimmerAnimation() {
    }

    private void stopShimmerAnimation() {
    }

    private String getCurrentDate() {
        return dateRange.get(tabLayout.getSelectedTabPosition());
    }


    private class OnDateChangeTask extends AsyncTask<Void, Void, Integer> {

        private final String date;

        public OnDateChangeTask(String date) {
            this.date = date;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int index = dateRange.indexOf(date); // index = 2
            return index;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer >= 0) {
                updateCurrentPositionOnUi(integer);
            } else {
                addTabLayoutOnRange(dateRange.get(dateRange.size()-1), date);
            }
        }
    }

    private void updateCurrentPositionOnUi(int currentPosition) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPosition >= 0) {
//                    tabLayout.selectTab(tabLayout.getTabAt(currentPosition));
                    viewPager.setCurrentItem(currentPosition,false);
                }
            }
        }, 100);
    }

    private void addMoreDateRangeInViewPager(String date, int currentPos) {
        SimpleDateFormat df1 = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US);
        Date date1;
        try {
            date1 = df1.parse(date);
            if (date1 != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                calendar.add(Calendar.DATE, 1);
                date1 = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            date1 = Calendar.getInstance().getTime();
        }
        addTabLayout(date1, currentPos);
    }

    private void getCalenderSelectedDatePosition(String date) {
        mSelectedDate = date;
        new OnDateChangeTask(date).execute();
    }


    private void addTabLayout(Date currentDate, int currentPosition) {
        TaskRunner.getInstance().executeAsync(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE, DEFAULT_DAYS_DIFFERENCE);
                String previousDate = df.format(calendar.getTime());
                String presentDate = df.format(currentDate);
                return DateTimeUtil.getDates(previousDate, presentDate);
            }
        }, new TaskRunner.Callback<List<String>>() {
            @Override
            public void onComplete(List<String> result) {
                dateRange.addAll(result);
                setupViewPager(viewPager, result, currentPosition);
            }
        });
    }

    /**
     * @param presentDate lastDate in list
     * @param previousDate chosen date from calender
     */
    private void addTabLayoutOnRange(String presentDate, String previousDate) {
        TaskRunner.getInstance().executeAsync(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US);
                return DateTimeUtil.getDates(previousDate, presentDate);
            }
        }, new TaskRunner.Callback<List<String>>() {
            @Override
            public void onComplete(List<String> result) {
                dateRange.addAll(result);
                setupViewPager(viewPager, result, INVALID_DATE_POSITION);
            }
        });
    }


    private void addTab(TabLayout tabLayout, CharSequence date, CharSequence month) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setCustomView(getTabView(date, month));
        tabLayout.addTab(tab);
    }

    private View getTabView(CharSequence date, CharSequence month) {
        View tabView = layoutInflater.inflate(R.layout.tab_category_calender, null, false);
        TextView text1 = tabView.findViewById(R.id.text1);
        TextView text2 = tabView.findViewById(R.id.text2);
        text1.setText(date);
        text2.setText(month);
        return tabView;
    }

    private void updateSelectedTabView(TabLayout.Tab tab, boolean isTabSelected, int colorRes) {
        View tabView = tab.getCustomView();
        if (tabView != null) {
            TextView text1 = tabView.findViewById(R.id.text1);
            TextView text2 = tabView.findViewById(R.id.text2);
            View indicator = tabView.findViewById(R.id.iv_indicator);
            text1.setTextColor(colorRes);
            text2.setTextColor(colorRes);
            if (isTabSelected) {
                indicator.setVisibility(View.VISIBLE);
            } else {
                indicator.setVisibility(View.INVISIBLE);
            }
        }
    }

    private ViewPagerAdapter adapter;

    private void setupViewPager(ViewPager viewPager, List<String> dateRange, int currentPosition) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            addList(dateRange);
            viewPager.setAdapter(adapter);
        } else {
            addList(dateRange);
            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//            viewPager.setCurrentItem(currentPosition);
            if (currentPosition != INVALID_DATE_POSITION) {
                updateCurrentPositionOnUi(currentPosition);
            } else {
                if (!TextUtils.isEmpty(mSelectedDate)) {
                    new OnDateChangeTask(mSelectedDate).execute();
                }
            }
        }
        viewPager.setOffscreenPageLimit(2);
        stopShimmerAnimation();
    }

    private void addList(List<String> dateRange) {
        DoctorSlotViewFragment category;
        for (String date : dateRange) {
            String[] mDate = getDate(date);
            category = new DoctorSlotViewFragment();
            Bundle bundle = new Bundle();
            ExtraProperty extraProperty = getExtraProperty().getClone();
            extraProperty.setAppointmentDate(date);
            extraProperty.setDoctorModel(doctorModel);
            extraProperty.setParentName(patientName);
            bundle.putSerializable(AppConstant.EXTRA_PROPERTY, extraProperty);
            category.setArguments(bundle);
            addTab(tabLayout, mDate[2], mDate[1]);
            adapter.addFragment(category, "Latest");
        }
    }

    private String[] getDate(String date) {
        return date.split("-");
    }


    public void refreshFragment(int position) {
        Fragment fragment = adapter.getItem(position);
        if (fragment instanceof BaseRefreshFragment) {
            ((BaseRefreshFragment) fragment).onRefreshFragment();
        }
    }

    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void onStopProgressBar() {

    }

}

