package com.android.valetsafe.valetsafedroid;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnOrderFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RelativeLayout nowBtn;
    private RelativeLayout advancedBtn;

    private OnOrderFragmentInteractionListener mListener;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create shape_circle_button new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        View v = inflater.inflate(R.layout.order_layout, container, false);
        nowBtn = (RelativeLayout) v.findViewById(R.id.order_now_btn);
        advancedBtn = (RelativeLayout) v.findViewById(R.id.order_advance_btn);
        nowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNowBtn();
            }
        });

        advancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdvancedBtn();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onNowBtn() {
        if (mListener != null) {
            mListener.onOrderFragmentNowBtn();
        }else{
            Toast.makeText(OrderFragment.this.getActivity(), "abc" , Toast.LENGTH_SHORT).show();
        }
    }

    public void onAdvancedBtn() {
        if (mListener != null) {
            mListener.onOrderFragmentAdvancedBtn();
        }else{
            Toast.makeText(OrderFragment.this.getActivity(), "abc" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderFragmentInteractionListener) {
            mListener = (OnOrderFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderFragmentInteractionListener");
        }

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof OnOrderFragmentInteractionListener) {
                mListener = (OnOrderFragmentInteractionListener) activity;
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
    public interface OnOrderFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOrderFragmentNowBtn();

        void onOrderFragmentAdvancedBtn();
    }
}
