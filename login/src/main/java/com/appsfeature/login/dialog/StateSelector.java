package com.appsfeature.login.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.appsfeature.login.R;
import com.appsfeature.login.adapter.StateSelectorAdapter;
import com.appsfeature.login.model.LoginModel;
import com.appsfeature.login.model.StateModel;
import com.appsfeature.login.util.LoginUtil;
import com.config.config.ConfigManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class StateSelector{
    private static final String STATE_FILE_NAME = "global/stateList.json";
    private SelectListener mListener;
    private StateSelectorAdapter adapter;
    private BottomSheetDialog mBottomSheetDialog;

    public interface SelectListener {
        void onStateSelect(StateModel selectedState);
    }

    public static StateSelector newInstance(SelectListener listener) {
        StateSelector fragment = new StateSelector();
        fragment.mListener = listener;
        return fragment;
    }

    public void show(Context context) {
        if (context != null) {
            mBottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
            LayoutInflater inflater = LayoutInflater.from(context);
            mBottomSheetDialog.setContentView(viewHolder(inflater.inflate(R.layout.dialog_menu_state, null)));
            mBottomSheetDialog.show();
        }
    }

    private View viewHolder(View v) {
        EditText etSearch = (EditText) v.findViewById(R.id.et_search);
        ListView lvMain = (ListView) v.findViewById(R.id.lv_main);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapter != null)
                    adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
                if (adapter != null) {
                    StateModel model = adapter.getItem(pos);
                    if (mListener != null) {
                        mListener.onStateSelect(model);
                    }
                }
                mBottomSheetDialog = null;
            }
        });
        List<StateModel> cModel = ConfigManager.getGson().fromJson(LoginUtil.getAssets(v.getContext(), STATE_FILE_NAME), new TypeToken<List<StateModel>>() {
        }.getType());
        if (cModel != null) {
            adapter = new StateSelectorAdapter(v.getContext(), cModel);
            lvMain.setAdapter(adapter);
        } else {
            ArrayList<String> errorList = new ArrayList<>();
            errorList.add("State not found");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), R.layout.slot_empty_product,
                    R.id.tv_message, errorList);
            lvMain.setAdapter(adapter);
        }
        return v;
    }

}
