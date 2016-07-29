package com.android.valetsafe.valetsafedroid;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * 司机端等待界面
 * <p/>
 * author lhy
 */
public class WaitingDriverFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView label;
    private RelativeLayout order1;
    private RelativeLayout order2;
    private RelativeLayout order3;
    private RelativeLayout order4;

    private TextView pickup1;
    private TextView destination1;
    private TextView type1;
    private TextView time1;
    private TextView date1;
    private TextView pickup2;
    private TextView destination2;
    private TextView type2;
    private TextView time2;
    private TextView date2;
    private TextView pickup3;
    private TextView destination3;
    private TextView type3;
    private TextView time3;
    private TextView date3;
    private TextView pickup4;
    private TextView destination4;
    private TextView type4;
    private TextView time4;
    private TextView date4;

    private Button cancel;
    private Button accept;

    private boolean order1Active = false;
    private boolean order2Active = false;
    private boolean order3Active = false;
    private boolean order4Active = false;

    private boolean isAdvanced = false;

    private OnWaitingDriverFragmentInteractionListener mListener;

    public WaitingDriverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create shape_circle_button new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingDriverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingDriverFragment newInstance(String param1, String param2) {
        WaitingDriverFragment fragment = new WaitingDriverFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.waiting_driver_fragment, container, false);
        label = (TextView) v.findViewById(R.id.waiting_driver_text_msg);
        label.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        order1 = (RelativeLayout) v.findViewById(R.id.waiting_driver_relativeLayout1);
        pickup1 = (TextView) v.findViewById(R.id.waiting_driver_txt_pickup1);
        destination1 = (TextView) v.findViewById(R.id.waiting_driver_txt_destination1);
        type1 = (TextView) v.findViewById(R.id.waiting_driver_txt_type1);
        time1 = (TextView) v.findViewById(R.id.waiting_driver_txt_time1);
        date1 = (TextView) v.findViewById(R.id.waiting_driver_txt_date1);

        order2 = (RelativeLayout) v.findViewById(R.id.waiting_driver_relativeLayout2);
        pickup2 = (TextView) v.findViewById(R.id.waiting_driver_txt_pickup2);
        destination2 = (TextView) v.findViewById(R.id.waiting_driver_txt_destination2);
        type2 = (TextView) v.findViewById(R.id.waiting_driver_txt_type2);
        time2 = (TextView) v.findViewById(R.id.waiting_driver_txt_time2);
        date2 = (TextView) v.findViewById(R.id.waiting_driver_txt_date2);

        order3 = (RelativeLayout) v.findViewById(R.id.waiting_driver_relativeLayout3);
        pickup3 = (TextView) v.findViewById(R.id.waiting_driver_txt_pickup3);
        destination3 = (TextView) v.findViewById(R.id.waiting_driver_txt_destination3);
        type3 = (TextView) v.findViewById(R.id.waiting_driver_txt_type3);
        time3 = (TextView) v.findViewById(R.id.waiting_driver_txt_time3);
        date3 = (TextView) v.findViewById(R.id.waiting_driver_txt_date3);

        order4 = (RelativeLayout) v.findViewById(R.id.waiting_driver_relativeLayout4);
        pickup4 = (TextView) v.findViewById(R.id.waiting_driver_txt_pickup4);
        destination4 = (TextView) v.findViewById(R.id.waiting_driver_txt_destination4);
        type4 = (TextView) v.findViewById(R.id.waiting_driver_txt_type4);
        time4 = (TextView) v.findViewById(R.id.waiting_driver_txt_time4);
        date4 = (TextView) v.findViewById(R.id.waiting_driver_txt_date4);

        cancel = (Button) v.findViewById(R.id.waiting_driver_btn_cancel);
        accept = (Button) v.findViewById(R.id.waiting_driver_btn_accept);

        order1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order1Active) {
                    order1Active = false;
                    order1.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                } else {
                    order1Active = true;
                    order1.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_gray_corner_frame));
                    order2.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order3.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order4.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                }
            }
        });

        order2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order2Active) {
                    order2Active = false;
                    order2.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                } else {
                    order2Active = true;
                    order2.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_gray_corner_frame));
                    order1.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order3.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order4.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                }
            }
        });

        order3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order3Active) {
                    order3Active = false;
                    order3.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                } else {
                    order3Active = true;
                    order3.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_gray_corner_frame));
                    order2.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order1.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order4.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                }
            }
        });

        order4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order4Active) {
                    order4Active = false;
                    order4.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                } else {
                    order4Active = true;
                    order4.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_gray_corner_frame));
                    order2.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order3.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                    order1.setBackground(WaitingDriverFragment.this.getResources().getDrawable(R.drawable.shape_white_corner_frame));
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdvanced) {
                    AcceptAdvanced();
                }else{
                    AcceptNow();
                }

            }
        });

        return v;
    }

    public void AcceptAdvanced(){
        Intent intent = new Intent(WaitingDriverFragment.this.getActivity(), AdvancedOrderActivity.class);
        startActivity(intent);
    }

    public void AcceptNow(){
        System.out.println("asdasd");
        if (mListener != null) {

            mListener.onAcceptNowOrder();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWaitingDriverFragmentInteractionListener) {
            mListener = (OnWaitingDriverFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderEndDriverFragmentInteractionListener");
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
    public interface OnWaitingDriverFragmentInteractionListener {
        // TODO: Update argument type and name
        void onWaittingDriverFragmentOrderFailed();

        void onWaitingDriverFragmentOrderSucceed();

        void onAcceptNowOrder();
    }
}
