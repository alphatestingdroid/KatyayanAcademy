package com.appsfeature.education.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.helper.util.BaseUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SupportUtil extends BaseUtil {

    public static SpannableString generateBoldSubTitle(String title, String boldText) {
        SpannableString s = new SpannableString(title + " " + boldText);
        s.setSpan(new StyleSpan(Typeface.BOLD), title.length(), title.length() + boldText.length() + 1, 0);
        return s;
    }

    public static SpannableString generateBoldTitle(String title, String boldText) {
        SpannableString s = new SpannableString(title + " " + boldText);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);
        return s;
    }


    public static void showNoDataProgress(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
            if (view.findViewById(com.helper.R.id.player_progressbar) != null) {
                view.findViewById(com.helper.R.id.player_progressbar).setVisibility(VISIBLE);
            }
            TextView tvNoData = view.findViewById(com.helper.R.id.tv_no_data);
            if (tvNoData != null) {
                tvNoData.setVisibility(GONE);
            }
        }
    }



    public static String decodeBase64(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        String text;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            text = new String(data, StandardCharsets.UTF_8);
        }else {
            try {
                text = new String(data,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
        return text;
    }

    public static String getDateFormatted(String inputDate, String time) {
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yy | hh:mm a", Locale.US);
//        try {
//            Date date = new SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.US).parse(inputDate + "-" + time);
//            return date!=null ? outputFormat.format(date) : "0";
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
        return BaseUtil.getTimeSpanString(inputDate + " " + time + ":00");
    }


    public static Date getDate(String cDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).parse(cDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static int parseInt(String value) {
        if(TextUtils.isEmpty(value)){
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
