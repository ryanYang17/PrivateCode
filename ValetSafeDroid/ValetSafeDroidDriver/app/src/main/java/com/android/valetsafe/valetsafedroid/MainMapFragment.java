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
import bean.RerseveOrder;
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

    private Context m_context;

    private ProgressDialog pd;
    private Double m_Lat, m_Lon;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {//获取订单创建结果
                CBCommonResult<RerseveOrder> result = (CBCommonResult<RerseveOrder>) msg.getData().get("result");
                if (result.getCode() == 0) {//success
                    new Thread() {
                        public void run() {
                            try {
                                CBCommonResult<String> resultU = null;
                                NetworkService service = new NetworkService();
                                Message msg;
                                while (!receiveOrderDone) {
                                    //resultU = service.updateReserveOrderAfterReceiveDriver(2, "receive_driver", "receive");
                                    msg = new Message();
                                    msg.arg1 = 1;
                                    msg.getData().putSerializable("result", resultU);
                                    handler.sendMessage(msg);
                                    sleep(5000); //暂停，每一秒输出一次
                                }

                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    }.start();
                    receiveOrderDone = false;
                    //Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                } else {
                    onCancel();
                    Toast.makeText(MainMapFragment.this.getActivity(), "订单创建失败！", Toast.LENGTH_SHORT).show();
                }

            } else if (msg.arg1 == 1) {//获取订单接收结果
                CBCommonResult<String> result = (CBCommonResult<String>) msg.getData().get("result");
                if(result.getCode() == 0){
                    // User user = result.getData();
                    receiveOrderDone = true;
                    Toast.makeText(MainMapFragment.this.getActivity(), "订单已经接收", Toast.LENGTH_SHORT).show();
                    onSuccess();
                }else{
                    //Toast.makeText(WaitingFragment.this.getActivity(), result.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }
            if (mListener != null) {
                mListener.onMainMapFragmentNextBtn();
            }

            super.handleMessage(msg);
        }
    };

    private boolean dataReady = false;
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

        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_main_fragment);
        // System.out.println(mapFragment);
        mapFragment.getMapAsync(this);


        return v;
    }

    public void onCancel() {
        if (mListener != null) {
            mListener.onMainMapFragmenRegularOrderFailed();
        }
    }

    public void onSuccess() {
        if (mListener != null) {
            mListener.onMainMapFragmenRegularOrderSucceed();
        }
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
        void onMainMapFragmenRegularOrderFailed();
        void onMainMapFragmenRegularOrderSucceed();

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
