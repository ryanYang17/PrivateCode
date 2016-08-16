package com.android.valetsafe.valetsafedroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class MainMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
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
    private AutoCompleteTextView pickupEdit;
    private AutoCompleteTextView destinationEdit;
    private AutoCompleteTextView addMidWayEdit1;
    private AutoCompleteTextView addMidWayEdit2;
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
    private GoogleMap googleMap;
    private Marker m_Marker;
    private ArrayList<Marker> m_CurCarMarker;
    private GoogleApiClient mGoogleApiClient;
    private AutocompleteFilter.Builder mAutocompleteFilter;
    private ArrayAdapter<String> arrayAdapter;


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
                if (result.getCode() == 0) {
                    // User user = result.getData();
                    receiveOrderDone = true;
                    Toast.makeText(MainMapFragment.this.getActivity(), "订单已经接收", Toast.LENGTH_SHORT).show();
                    onSuccess();
                } else {
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
        mGoogleApiClient = new GoogleApiClient
                .Builder(m_context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mAutocompleteFilter = new AutocompleteFilter.Builder();
        mAutocompleteFilter.setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS);
        m_CurCarMarker = new ArrayList<Marker>();
        main_v = v;
        pickupEdit = (AutoCompleteTextView) v.findViewById(R.id.main_map_pick_up_edit);
        destinationEdit = (AutoCompleteTextView) v.findViewById(R.id.main_map_destination_edit);
        addMidWayBtn = (RelativeLayout) v.findViewById(R.id.main_map_add_midway_layout);
        addMidWayEdit = (EditText) v.findViewById(R.id.main_map_add_midway_edit);
        addMidWay1 = (RelativeLayout) v.findViewById(R.id.main_map_add_midway_layout1);
        addMidWay2 = (RelativeLayout) v.findViewById(R.id.main_map_add_midway_layout2);
        addMidWayEdit1 = (AutoCompleteTextView) v.findViewById(R.id.main_map_add_midway_edit1);
        addMidWayEdit2 = (AutoCompleteTextView) v.findViewById(R.id.main_map_add_midway_edit2);
        economyBtn = (LinearLayout) v.findViewById(R.id.main_map_economy_layout);
        limoBtn = (LinearLayout) v.findViewById(R.id.main_map_limo_layout);
        sportBtn = (LinearLayout) v.findViewById(R.id.main_map_sport_layout);
        addMidWayEdit1.setEnabled(false);
        addMidWayEdit2.setEnabled(false);
        nextBtn = (Button) v.findViewById(R.id.map_next_btn);
        pickupEdit.setOnFocusChangeListener(new EditFocusListener());
        pickupEdit.addTextChangedListener(new EditChangedListener(pickupEdit));
        destinationEdit.setOnFocusChangeListener(new EditFocusListener());
        destinationEdit.addTextChangedListener(new EditChangedListener(destinationEdit));
        addMidWayEdit1.setOnFocusChangeListener(new EditFocusListener());
        addMidWayEdit1.addTextChangedListener(new EditChangedListener(addMidWayEdit1));
        addMidWayEdit2.setOnFocusChangeListener(new EditFocusListener());
        addMidWayEdit2.addTextChangedListener(new EditChangedListener(addMidWayEdit2));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextBtn();
            }
        });
        economyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReCreateMarker(0);
            }
        });
        limoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReCreateMarker(1);
            }
        });
        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReCreateMarker(2);
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
        googleMap = mapFragment.getMap();
        if (googleMap != null) {
            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    m_Lat = marker.getPosition().latitude;
                    m_Lon = marker.getPosition().longitude;
                    UpdateMapView(m_Lat, m_Lon);
                }
            });
        }

        return v;
    }


    private String AddressReplace(String strInput) {
        Pattern p = Pattern.compile("nothing");
        Matcher m = p.matcher(strInput);
        return m.replaceFirst("");
    }

    /**
     * 初始化AutoCompleteTextView，最多显示5项提示，使
     * AutoCompleteTextView在一开始获得焦点时自动提示
     * @param auto 要操作的AutoCompleteTextView
     */
    private void initAutoComplete(final AutoCompleteTextView auto, boolean bShowHistory, String[] strPeakAddress) {
        ArrayAdapter<String> adapter;
        if (bShowHistory) {
            SharedPreferences sp = m_context.getSharedPreferences("search_history", 0);
            String longhistory = sp.getString("history", "nothing");
            longhistory = AddressReplace(longhistory);
            String[] hisArrays = longhistory.split(",");
            adapter = new ArrayAdapter<String>(m_context, R.layout.simple_dropdown_item_1line, hisArrays);
            //只保留最近的50条的记录
            if (hisArrays.length > 50) {
                String[] newArrays = new String[50];
                System.arraycopy(hisArrays, 0, newArrays, 0, 50);
                adapter = new ArrayAdapter<String>(m_context, R.layout.simple_dropdown_item_1line, newArrays);
            }
            auto.setAdapter(adapter);
        }
        else {
            auto.dismissDropDown();
            adapter = new ArrayAdapter<String>(m_context, R.layout.simple_dropdown_item_1line, strPeakAddress);
            auto.setAdapter(adapter);
            auto.showDropDown();
        }
        auto.refreshDrawableState();
    }

    private void save(AutoCompleteTextView auto) {
        // 获取搜索框信息
        String text = auto.getText().toString();
        if (text.equals("")){
            return;
        }
        SharedPreferences mysp = m_context.getSharedPreferences("search_history", 0);
        String old_text = mysp.getString("history", "nothing");

        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(text + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(text + ",")) {
            SharedPreferences.Editor myeditor = mysp.edit();
            myeditor.putString("history", builder.toString());
            myeditor.commit();
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onNextBtn() {
        final String pickup = pickupEdit.getText().toString();
        final String destination = destinationEdit.getText().toString();

        pd = ProgressDialog.show(MainMapFragment.this.getActivity(), "叫车中", "叫车中，请稍后……");
        new Thread() {
            @Override
            public void run() {

//                String email = mailEdit.getText().toString();
//                String password = passwordEdit.getText().toString();

                //调用网络服务进行注册用户操作
                NetworkService service = new NetworkService();
                CBCommonResult<RerseveOrder> resultC = null;
                // CBCommonResult<User> result = service.loadUser(2, name, cell_phone);
                Message msg;

                //resultC = service.createReserveOrderAction("lhy", pickup, destination, reserveTime, "create");
                //System.out.println(resultC.getCode());
                msg = new Message();
                msg.arg1 = 0;
                msg.getData().putSerializable("result", resultC);
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

        void onMainMapFragmenRegularOrderFailed();

        void onMainMapFragmenRegularOrderSucceed();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(m_context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(m_context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LatLng loc = new LatLng(m_Lat, m_Lon);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
        m_Marker = googleMap.addMarker(new MarkerOptions().position(loc).draggable(true).title("my position"));
        ReCreateMarker(0);
    }

    private void ReCreateMarker(int CarType) {
        for (int i = 0; i < m_CurCarMarker.size(); i++) {
            m_CurCarMarker.get(i).remove();
        }
        m_CurCarMarker.clear();
        Random rand = new Random();
        int CarCount = 0;
        switch (CarType) {
            case 0:
                CarCount = 12;
                break;
            case 1:
                CarCount = 8;
                break;
            case 2:
                CarCount = 5;
                break;
        }
        for (int j = 0; j < CarCount; j++) {
            int randNum = rand.nextInt(800) + 500;
            int randForLat = rand.nextInt(2);
            int randForLon = rand.nextInt(2);
            Double nLat, nLon;

            if (randForLat == 0) {
                nLat = m_Lat - randNum / 100 * 0.0009;
            } else {
                nLat = m_Lat + randNum / 100 * 0.0009;
            }
            if (randForLon == 0) {
                nLon = m_Lon - randNum / 100 * (0.0009 / java.lang.Math.cos(m_Lat));
            } else {
                nLon = m_Lon + randNum / 100 * (0.0009 / java.lang.Math.cos(m_Lat));
            }
            LatLng newLoc = new LatLng(nLat, nLon);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(newLoc).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_driver)));
            m_CurCarMarker.add(marker);
        }
    }

    public void UpdateMapView(Double lat, Double lon) {
        LatLng loc = new LatLng(lat, lon);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
    }

    public void SetLatLon(Context context, double dLat, double dLon) {
        m_context = context;
        m_Lat = dLat;
        m_Lon = dLon;
    }

    class EditFocusListener implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            AutoCompleteTextView view = (AutoCompleteTextView) v;
            if (hasFocus) {
                if (view.getText().toString().equals("")){
                    initAutoComplete(view, true, null);
                }
                view.showDropDown();
            }
            else {
                save(view);
                view.dismissDropDown();
            }
        }
    }

    class EditChangedListener implements TextWatcher {
        private AutoCompleteTextView edit;

        public EditChangedListener(AutoCompleteTextView editText) {
            edit = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edit.setAdapter(null);
            if (ActivityCompat.checkSelfPermission(m_context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            PendingResult<AutocompletePredictionBuffer> result = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, s.toString(), null, mAutocompleteFilter.build());
            result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                @Override
                public void onResult(AutocompletePredictionBuffer autocompletePredictionBuffer) {
                    String[] strPeakAddress = new String[autocompletePredictionBuffer.getCount()];
                    for (int i = 0; i < autocompletePredictionBuffer.getCount(); i++) {
                        AutocompletePrediction autocompletePrediction = autocompletePredictionBuffer.get(i);
                        strPeakAddress[i] = autocompletePrediction.getFullText(new CharacterStyle() {
                            @Override
                            public void updateDrawState(TextPaint tp) {

                            }
                        }).toString();
                    }
                    initAutoComplete(edit, false, strPeakAddress);
                    autocompletePredictionBuffer.release();
                }
            });
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i){

    }

    @Override
    public void onConnected(Bundle bundle){

    }
}
