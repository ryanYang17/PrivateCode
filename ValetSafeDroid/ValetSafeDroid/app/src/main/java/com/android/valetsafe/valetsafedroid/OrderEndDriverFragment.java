package com.android.valetsafe.valetsafedroid;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnOrderEndDriverFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderEndDriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderEndDriverFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView pickUpText;
    private TextView destinationText;
    private TextView phoneNumText;
    private TextView nameText;
    private TextView numText;
    private ImageView headImg;
    private RatingBar rating;

    private OnOrderEndDriverFragmentInteractionListener mListener;

    public OrderEndDriverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderEndDriverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderEndDriverFragment newInstance(String param1, String param2) {
        OrderEndDriverFragment fragment = new OrderEndDriverFragment();
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
        View v = inflater.inflate(R.layout.order_end_driver_layout, container, false);
        pickUpText = (TextView)v.findViewById(R.id.order_end_driver_pickup_txt);
        destinationText = (TextView)v.findViewById(R.id.order_end_driver_destination_txt);
        phoneNumText = (TextView)v.findViewById(R.id.order_end_driver_phone_txt);
        nameText = (TextView)v.findViewById(R.id.order_end_driver_name_txt);
        numText = (TextView)v.findViewById(R.id.order_end_driver_num_txt);
        headImg = (ImageView) v.findViewById(R.id.order_end_driver_head_img);
        rating = (RatingBar)  v.findViewById(R.id.order_end_driver_rating);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOrderEndDriverFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderEndDriverFragmentInteractionListener) {
            mListener = (OnOrderEndDriverFragmentInteractionListener) context;
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
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnOrderEndDriverFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOrderEndDriverFragmentInteraction();
    }
}
