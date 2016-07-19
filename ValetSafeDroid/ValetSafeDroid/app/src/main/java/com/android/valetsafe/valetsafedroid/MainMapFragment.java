package com.android.valetsafe.valetsafedroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bean.CBCommonResult;
import bean.User;
import service.NetworkService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMainMapFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnMainMapFragmentInteractionListener mListener;

    private View main_v;
    private Button nextBtn;
    private EditText pickupEdit;
    private EditText destinationEdit;
    private EditText addMidWayEdit1;
    private EditText addMidWayEdit2;
    private EditText addMidWayEdit;
    private RelativeLayout addMidWayBtn;
    private RelativeLayout addMidWay1;
    private RelativeLayout addMidWay2;
    private LinearLayout economyBtn;
    private LinearLayout limoBtn;
    private LinearLayout sportBtn;
    private Context m_context;

    private ProgressDialog pd;
    private Double m_Lat, m_Lon;


    private Handler handler;

    private boolean reserveOrderDone = false;
    private boolean receiveOrderDone = false;

    private int addMidWayClickTimes = 0;

    public MainMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMapFragment newInstance(String param1, String param2) {
        MainMapFragment fragment = new MainMapFragment();
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
        View v = inflater.inflate(R.layout.map_layout, container, false);
        main_v = v;
        pickupEdit = (EditText) v.findViewById(R.id.main_map_pick_up_edit);
        destinationEdit = (EditText) v.findViewById(R.id.main_map_destination_edit);
        addMidWayBtn = (RelativeLayout) v.findViewById(R.id.main_map_add_midway_layout);
        addMidWayEdit = (EditText) v.findViewById(R.id.main_map_add_midway_edit);
        addMidWay1 = (RelativeLayout) v.findViewById(R.id.main_map_add_midway_layout1);
        addMidWay2 = (RelativeLayout) v.findViewById(R.id.main_map_add_midway_layout2);
        addMidWayEdit1 = (EditText) v.findViewById(R.id.main_map_add_midway_edit1);
        addMidWayEdit2 = (EditText) v.findViewById(R.id.main_map_add_midway_edit2);
        economyBtn = (LinearLayout) v.findViewById(R.id.main_map_economy_layout);
        limoBtn = (LinearLayout) v.findViewById(R.id.main_map_limo_layout);
        sportBtn = (LinearLayout) v.findViewById(R.id.main_map_sport_layout);
        addMidWayEdit1.setEnabled(false);
        addMidWayEdit2.setEnabled(false);
        nextBtn = (Button) v.findViewById(R.id.map_next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextBtn();
            }
        });

        addMidWayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("asdasdas");
                if (addMidWayClickTimes < 2) {
                    if (addMidWayClickTimes == 0) {
                        int left = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                        int right = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                        int top = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics()));
                        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(addMidWayBtn.getLayoutParams());
                        margin.setMargins(left, top, right, margin.bottomMargin);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
                        addMidWayBtn.setLayoutParams(layoutParams);
                        addMidWayEdit1.setEnabled(true);
                        addMidWayEdit2.setEnabled(false);
                    } else {
                        int left = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                        int right = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                        int top = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics()));
                        int a_top = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
                        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(addMidWay2.getLayoutParams());

                        margin.setMargins(left, top, right, margin.bottomMargin);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
                        addMidWay2.setLayoutParams(layoutParams);
                        margin.setMargins(left, a_top, right, margin.bottomMargin);
                        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(margin);
                        addMidWayBtn.setLayoutParams(layoutParams1);
                        addMidWayBtn.setVisibility(View.INVISIBLE);
                        addMidWayEdit1.setEnabled(true);
                        addMidWayEdit2.setEnabled(true);
                    }

                } else {

                }
                addMidWayClickTimes++;
            }
        });

        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_main_fragment);
        // System.out.println(mapFragment);
        mapFragment.getMapAsync(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == 0) {
//                    CBCommonResult<String> result = (CBCommonResult<String>) msg.getData().get("result");
//                    if(result.getCode() == 0){
//                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
//                    }
                    pd.dismiss();// 关闭ProgressDialog
                } else if (msg.arg1 == 1) {
//                    CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
//                    if(result.getCode() == 0){
//                        User user = result.getData();
//                        Toast.makeText(RegisterActivity.this, String.valueOf(user.getId()), Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
//                    }
                }
                if (mListener != null) {
                    mListener.onMainMapFragmentNextBtn();
                }

                super.handleMessage(msg);
            }
        };
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onNextBtn() {


        pd = ProgressDialog.show(MainMapFragment.this.getActivity(), "叫车中", "叫车中，请稍后……");
        new Thread() {
            @Override
            public void run() {
                String pickup = pickupEdit.getText().toString();
                String destination = destinationEdit.getText().toString();
//                String email = mailEdit.getText().toString();
//                String password = passwordEdit.getText().toString();

                //调用网络服务进行注册用户操作
                NetworkService service = new NetworkService();
                CBCommonResult<String> result = null;
                // CBCommonResult<User> result = service.loadUser(2, name, cell_phone);
                try {
                    while (!reserveOrderDone) {
                        // CBCommonResult<String> result= service.createReserveOrderAction("hzy","current_place","destination_place","reserve_time","create");
                        sleep(100); //暂停，每一秒输出一次
                    }
                    while (!receiveOrderDone) {
                        // CBCommonResult<String> result= service.updateReserveOrderAfterReceiveDriver(2,"receive_driver", "receive");
                        sleep(100); //暂停，每一秒输出一次
                    }

                } catch (InterruptedException e) {
                    return;
                }


                Message msg = new Message();
                msg.arg1 = 0;
                msg.getData().putSerializable("result", result);
                handler.sendMessage(msg);

            }
        }.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        m_context = context;
        if (context instanceof OnMainMapFragmentInteractionListener) {
            mListener = (OnMainMapFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderFragmentInteractionListener");
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof OnMainMapFragmentInteractionListener) {
                mListener = (OnMainMapFragmentInteractionListener) activity;
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
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMainMapFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMainMapFragmentNextBtn();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(m_context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(m_context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LatLng loc = new LatLng(m_Lat, m_Lon);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
        googleMap.addMarker(new MarkerOptions().position(loc).title("Marker"));
    }

    public void SetLatLon(Context context, double dLat, double dLon) {
        m_context = context;
        m_Lat = dLat;
        m_Lon = dLon;
    }

}
