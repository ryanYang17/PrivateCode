package com.android.valetsafe.valetsafedroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bean.CBCommonResult;
import bean.RerseveOrder;
import service.NetworkService;


/**
 * 乘客端等待界面
 * <p/>
 * author lhy
 */
public class WaitingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String pickup;
    private String destination;
    private String reserveTime;

    private Button cancelBtn;//取消按钮
    private Thread serviceThread;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
//                    CBCommonResult<String> result = (CBCommonResult<String>) msg.getData().get("result");
//                    if(result.getCode() == 0){
//                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
//                    }

            } else if (msg.arg1 == 1) {
//                    CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
//                    if(result.getCode() == 0){
//                        User user = result.getData();
//                        Toast.makeText(RegisterActivity.this, String.valueOf(user.getId()), Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
//                    }
            }
            super.handleMessage(msg);
        }
    };
    private boolean reserveOrderDone = false;
    private boolean receiveOrderDone = false;
    private OnWaitingFragmentInteractionListener mListener;

    public WaitingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create shape_circle_button new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingFragment newInstance(String param1, String param2) {
        WaitingFragment fragment = new WaitingFragment();
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
        View v = inflater.inflate(R.layout.waiting_fragment, container, false);
        cancelBtn = (Button) v.findViewById(R.id.waiting_btn_cancel);
        serviceThread = new Thread() {
            @Override
            public void run() {

//                String email = mailEdit.getText().toString();
//                String password = passwordEdit.getText().toString();

                //调用网络服务进行注册用户操作
                NetworkService service = new NetworkService();
                CBCommonResult<RerseveOrder> resultC = null;
                CBCommonResult<String> resultU = null;
                // CBCommonResult<User> result = service.loadUser(2, name, cell_phone);
                try {
                    while (!reserveOrderDone) {
                        resultC = service.createReserveOrderAction("lhy", "current_place", "destination_place", "reserve_time", "create");
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.getData().putSerializable("result", resultC);
                        handler.sendMessage(msg);
                        sleep(100); //暂停，每一秒输出一次

                    }
                    while (!receiveOrderDone) {
                        resultU = service.updateReserveOrderAfterReceiveDriver(2, "receive_driver", "receive");
                        sleep(100); //暂停，每一秒输出一次
                    }

                } catch (InterruptedException e) {
                    return;
                }



            }
        };
        serviceThread.start();

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWaitingFragmentInteractionListener) {
            mListener = (OnWaitingFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWaitingFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setDetail(String p, String d, String t) {
        pickup = p;
        destination = d;
        reserveTime = t;
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
    public interface OnWaitingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
