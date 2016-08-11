package com.android.valetsafe.valetsafedroid;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import bean.CBCommonResult;
import bean.ValetOrder;
import service.ValetSafeService;


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
            if (msg.arg1 == 0) {//获取订单创建结果
                CBCommonResult<ValetOrder> result = (CBCommonResult<ValetOrder>) msg.getData().get("result");
                if (result.getCode() == 0) {//success
                    new Thread() {
                        public void run() {
                            try {
                                CBCommonResult<String> resultU = null;
                                ValetSafeService service = new ValetSafeService();
                                Message msg;
                                while (!receiveOrderDone) {
                                    resultU = service.updateOrderAfterReceiveDriver(2, "receive_driver", "receive");
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
                    Toast.makeText(WaitingFragment.this.getActivity(), "订单创建失败！", Toast.LENGTH_SHORT).show();
                    onCancel();

                }

            } else if (msg.arg1 == 1) {//获取订单接收结果
                    CBCommonResult<String> result = (CBCommonResult<String>) msg.getData().get("result");
                    if(result.getCode() == 0){
                       // User user = result.getData();
                        receiveOrderDone = true;
                        Toast.makeText(WaitingFragment.this.getActivity(), "订单已经接收", Toast.LENGTH_SHORT).show();
                        onSuccess();
                    }else{
                        //Toast.makeText(WaitingFragment.this.getActivity(), result.getDescription(), Toast.LENGTH_SHORT).show();
                    }
            }
            super.handleMessage(msg);
        }
    };
    private boolean dataReady = false;
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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
        serviceThread = new Thread() {
            @Override
            public void run() {

//                String email = mailEdit.getText().toString();
//                String password = passwordEdit.getText().toString();

                //调用网络服务进行注册用户操作
                ValetSafeService service = new ValetSafeService();
                CBCommonResult<ValetOrder> resultC = null;

                // CBCommonResult<User> result = service.loadUser(2, name, cell_phone);
                Message msg;
                while (!dataReady) {
                }
                resultC = service.createOrderAction(""+UserAttribute.getId(),UserAttribute.TYPE_ADVANCED, pickup, "",destination, reserveTime, "create","0");

                msg = new Message();
                msg.arg1 = 0;
                msg.getData().putSerializable("result", resultC);
                handler.sendMessage(msg);

            }
        };
        serviceThread.start();

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onCancel() {
        if (mListener != null) {
            mListener.onWaittingFragmentReserveOrderFailed();
        }
    }

    public void onSuccess() {
        if (mListener != null) {
            mListener.onWaittingFragmentReserveOrderReceived();
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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof OnWaitingFragmentInteractionListener) {
                mListener = (OnWaitingFragmentInteractionListener) activity;
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

    public void setDetail(String p, String d, String t) {
        pickup = p;
        destination = d;
        reserveTime = t;
        dataReady = true;
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
        void onWaittingFragmentReserveOrderFailed();

        void onWaittingFragmentReserveOrderReceived();
    }
}
