package com.appsfeature.education.doctor.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.adapter.DoctorSlotAdapter;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.model.AppointmentSlot;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.model.SlotModel;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.util.ArrayList;
import java.util.List;

public class DoctorSlotViewFragment extends BaseRefreshFragment {
    private View view;
    private Activity activity;

    private View llNoData;
    private DoctorSlotAdapter adapter;
    private List<AppointmentSlot> mList = new ArrayList<>();
    private DoctorModel doctorModel;
    private String patientName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor_slot_view, container, false);
        activity = getActivity();
        doctorModel = getExtraProperty().getDoctorModel();
        patientName = getExtraProperty().getParentName(); //for reBooking
        initUi();
        return view;
    }

    private void initUi() {
        llNoData = view.findViewById(R.id.ll_no_data);
        RecyclerView rvList = view.findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new GridLayoutManager(activity, 4));
        adapter = new DoctorSlotAdapter(mList, new Response.OnClickListener<AppointmentSlot>() {
            @Override
            public void onItemClicked(View view, AppointmentSlot item) {
                ClassUtil.openBookingSummaryActivity(activity, doctorModel, getTime(item.getStart(), item.getEnd()),  getExtraProperty().getAppointmentDate(), patientName);
            }
        });
        rvList.setAdapter(adapter);
    }

    private String getTime(String start, String end) {
        return start + " - " + end;
    }

    @Override
    public void onRefreshFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getExtraProperty() != null) {
            appPresenter.getDoctorSlotView(doctorModel.getId(), DateTimeUtil.getDateInServerFormat(getExtraProperty().getAppointmentDate()));
        }
    }

    @Override
    public void onUpdateUI(PresenterModel response) {
        SupportUtil.showNoData(llNoData, View.GONE);
        llNoData.setVisibility(View.GONE);
        mList.clear();
        if (response.getSlotModel() != null && response.getSlotModel().size() > 0) {
            SlotModel slotModel = response.getSlotModel().get(0);
            if (slotModel != null && slotModel.getAppointmentSlot() != null && slotModel.getAppointmentSlot().size() > 0) {
                mList.addAll(slotModel.getAppointmentSlot());
            } else {
                SupportUtil.showNoData(llNoData, View.VISIBLE);
            }
        } else {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorOccurred(Exception e) {
//        SupportUtil.showToast(activity, e.getMessage());
        SupportUtil.showNoData(llNoData, View.VISIBLE);
    }

    @Override
    public void onStartProgressBar() {
        SupportUtil.showNoDataProgress(llNoData);
    }

    @Override
    public void onStopProgressBar() {

    }
}