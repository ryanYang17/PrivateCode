package com.android.valetsafe.valetsafedroid;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 预约时间地点界面
 * <p/>
 * author lhy
 */
public class OrderDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String pickup;
    private String destination;
    private String date;
    private String time;
    private TextView setDateTxt;//预约时间的月份日期
    private TextView setTimeTxt;//预约时间的小时分钟
    private EditText setPickupTxt;//预约的乘车地点
    private EditText setDestinationTxt;//预约的目的地点
    private Button backBtn;//返回
    private Button nextBtn;//预约下一步

    private OnOrderDetailFragmentInteractionListener mListener;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create shape_circle_button new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(String param1, String param2) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_detail_layout, container, false);
        setDateTxt = (TextView) v.findViewById(R.id.order_detail_text_setdate);
        setTimeTxt = (TextView) v.findViewById(R.id.order_detail_text_settime);
        setPickupTxt = (EditText) v.findViewById(R.id.order_detail_text_pickedup);
        setDestinationTxt = (EditText) v.findViewById(R.id.order_detail_text_destination);

        backBtn = (Button) v.findViewById(R.id.order_detail_btn_back);
        nextBtn = (Button) v.findViewById(R.id.order_detail_btn_next);

        setDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OrderDetailFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        setDateTxt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        OrderDetailFragment.this.year = year;
                        OrderDetailFragment.this.month = monthOfYear + 1;
                        OrderDetailFragment.this.day = dayOfMonth;
                    }
                }, year, month, day).show();
            }
        });

        setTimeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(OrderDetailFragment.this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTimeTxt.setText(hourOfDay + ":" + minute);
                        OrderDetailFragment.this.hour = hourOfDay;
                        OrderDetailFragment.this.minute = minute;
                    }
                }, hour, minute, true).show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackBtn();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickup = setPickupTxt.getText().toString();
                destination = setDestinationTxt.getText().toString();
                date = setDateTxt.getText().toString();
                time = setTimeTxt.getText().toString();
                String reserveTime = date + " " + time;
                onNextBtn(pickup,destination,reserveTime);
            }
        });
        return v;
    }

    public void setDate() {
    }

    public void setTime() {
    }

    public void setPickUp() {
    }

    public void setDestination() {
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onBackBtn() {
        if (mListener != null) {
            mListener.onOrderDetailFragmentBackBtn();
        }
    }

    public void onNextBtn(String p,String d,String reserveT) {
        if (mListener != null) {
            mListener.onOrderDetailFragmentNextBtn(p,d,reserveT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderDetailFragmentInteractionListener) {
            mListener = (OnOrderDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderFragmentInteractionListener");
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof OnOrderDetailFragmentInteractionListener) {
                mListener = (OnOrderDetailFragmentInteractionListener) activity;
            } else {
                throw new RuntimeException(activity.toString()
                        + " must implement OnOrderFragmentInteractionListener");
            }
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <shape_circle_button href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</shape_circle_button> for more information.
     */
    public interface OnOrderDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOrderDetailFragmentBackBtn();

        void onOrderDetailFragmentNextBtn(String p,String d,String t);
    }
}
