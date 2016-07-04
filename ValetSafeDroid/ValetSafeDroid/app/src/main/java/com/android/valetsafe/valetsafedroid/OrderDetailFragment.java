package com.android.valetsafe.valetsafedroid;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnOrderDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button backBtn;
    private Button nextBtn;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_detail_layout, container, false);
        backBtn = (Button) v.findViewById(R.id.order_detail_btn_back);
        nextBtn = (Button) v.findViewById(R.id.order_detail_btn_next);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackBtn();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextBtn();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onBackBtn() {
        if (mListener != null) {
            mListener.onOrderDetailFragmentBackBtn();
        }
    }

    public void onNextBtn() {
        if (mListener != null) {
            mListener.onOrderDetailFragmentNextBtn();
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

        void onOrderDetailFragmentNextBtn();
    }
}
