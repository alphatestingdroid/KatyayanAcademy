package com.appsfeature.login.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

class BaseActivity extends AppCompatActivity {

    protected void addFragmentWithoutBackstack(Fragment fragment, String tag) {
        addFragmentWithoutBackstack(fragment, android.R.id.content, tag);
    }

    protected void addFragmentWithoutBackstack(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.commit();
    }


    protected void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

//    protected void addFragment(Fragment fragment, int container, String tag) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(container, fragment, tag);
//        transaction.addToBackStack(tag);
//        transaction.commit();
//    }

    protected void addFragment(Fragment addFragment, int container, String tag) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Hide other fragments
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment frag : fragments){
                if (frag != null && frag.isAdded()) {
                    ft.hide(frag);
                }
            }
            if (addFragment != null) {
                if (addFragment.isAdded()) { // if the fragment is already in container
                    ft.show(addFragment);
                } else { // fragment needs to be added to frame container
                    ft.add(container, addFragment, tag);
                }
            }
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
