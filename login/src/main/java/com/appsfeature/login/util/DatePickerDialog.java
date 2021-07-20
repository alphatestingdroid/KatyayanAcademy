package com.appsfeature.login.util;


import android.app.Activity;
import android.widget.DatePicker;


import com.appsfeature.login.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * @author Created by Abhijit on 21-Dec-16.
 */


public class DatePickerDialog {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private DateSelectListener mListener;
    private int day, month, year;
    private Boolean minDate, maxDate;
    private Activity activity;


    public static String getFormattedDate(int day, int month, int year) {
        return formattedDate(getDate(day, month, year));
    }

    public interface DateSelectListener {
        void onSelectDateClick(Date date, String yyyyMMdd);
    }

    public static DatePickerDialog newInstance(Activity activity, boolean minDateRestriction , DateSelectListener listener) {
        DatePickerDialog fragment = new DatePickerDialog();
        fragment.activity = activity;
        fragment.mListener = listener;
        fragment.minDate = minDateRestriction;
        fragment.maxDate = false;
        return fragment;
    }

    public void show() {
        show(null);
    }

    public void show(String currentDate) {
        if (activity != null) {
            final Calendar calendar = Calendar.getInstance();
            if (currentDate == null) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } else {
                int[] mDate = getDate(currentDate);
                year = mDate[0];
                month = mDate[1];
                day = mDate[2];
            }
            android.app.DatePickerDialog dpd;
            dpd = new android.app.DatePickerDialog(activity, R.style.DatePicker, new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if (mListener != null) {
                        Date date = getDate(dayOfMonth, month, year);
//                        Log.d("@getTime", date.getTime() + "");
//                        Log.d("@currentTimeMillis()", getCurrentDate().getTime() + "");
                        if (!minDate || date.getTime() >= getCurrentDate().getTime()) {
                            mListener.onSelectDateClick(date, formattedDate(date));
                        } else {
                            LoginUtil.showToast(activity, "Slot not available on selected date.");
                        }
                    }
                }
            }, year, month, day);

            dpd.getDatePicker().setBackgroundResource(R.color.primary_color);

            if (minDate) {
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);// get the current day
            }
            if (maxDate) {
                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());// get the current day
            }
            dpd.show();
        }
    }


    public static long getFormattedDate(String date) {
        try {
            Date format = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US).parse(date);
            return format.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int[] getDate(String inputDate) {
        Calendar calendar;
        try {
            calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US).parse(inputDate));
        } catch (ParseException e) {
            calendar = Calendar.getInstance();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new int[]{year, month, day};
    }


    public static String formattedDate(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US).format(date);
    }

    public static Date getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, year);
//        cal.set(Calendar.DAY_OF_MONTH, day);
//        cal.set(Calendar.MONTH, month);
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    public static Date getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return cal.getTime();
    }

    public static String getViewFormat(String cDate) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
        try {
            Date date = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US).parse(cDate);
            return date!=null ? outputFormat.format(date) : "0";
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }
    }

}