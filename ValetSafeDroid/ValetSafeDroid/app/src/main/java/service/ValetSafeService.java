package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.CBCommonResult;
import bean.ValetOrder;
import bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;

/**
 * Created by zhenya huang on 2016/8/1.
 */
public class ValetSafeService {
    /**
     * 注册新用户
     * @param name 用户名
     * @param cell_phone 用户手机号
     * @param email 用户email
     * @param password 用户密码
     * @return 返回结果CBCommonRest<User>
     */
    public CBCommonResult<User> registerUserAction(String name, String cell_phone, String email, String password){
        String result = null;

        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String register_time = formatter.format(date);

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("cell_phone", cell_phone);
        data.put("email", email);
        data.put("password", password);
        data.put("register_time", register_time);
        System.out.println(data);

        //Map<String, String> return_data = new HashMap<>();
        CBCommonResult<User> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.102:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.106:8080/valetsafe/addRegisterDriver";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            CBPrint.println(result);
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<User>>(){}.getType());
            //return_data.put("result", result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }

    /**
     * 注册司机
     * @param name
     * @param cell_phone
     * @param email
     * @param password
     * @param driver_age
     * @return
     */
    public CBCommonResult<String> registerDriverAction(String name, String cell_phone, String email, String password, String driver_age,String car_number,String account_number,String car_type){
        String result = null;

        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String register_time = formatter.format(date);

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("cell_phone", cell_phone);
        data.put("email", email);
        data.put("password", password);
        data.put("register_time", register_time);
        data.put("driver_age", driver_age);
        data.put("car_number", car_number);
        data.put("account_number", account_number);
        data.put("car_type", car_type);
        //System.out.println(data);


        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            //String baseURL = "http://192.168.1.102:8080/valetsafe/addRegisterDriver";
            String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterDriver";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<String>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }


    /**
     * 创建订单
     * @param create_user 创建用户id
     * @param current_place 预约地点
     * @param destination_place 目标地点
     * @param reserve_time 预约时间
     * @param state 订单状态{create, received, completed}
     * @return CBCommonResult<RerseveOrder>, ReserveOrder包含订单完整信息
     */
    public CBCommonResult<ValetOrder> createOrderAction(String create_user, String type, String current_place, String reserve_place, String destination_place, String reserve_time, String state, String is_paid){
        String result = null;

        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = formatter.format(date);

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("create_user", create_user);
        data.put("type", type);
        data.put("current_place", current_place);
        data.put("reserve_place", reserve_place);
        data.put("destination_place", destination_place);
        data.put("create_time", create_time);
        data.put("reserve_time", reserve_time);
        data.put("state", state);
        data.put("isPaid", is_paid);
        System.out.println(data);


        CBCommonResult<ValetOrder> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/createOrder";
            //String baseURL = "http://192.168.1.102:8080/valetsafe/createOrder";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<ValetOrder>>(){}.getType());
            CBPrint.println(cbResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }


    /**
     * 司机接单时，更新相应订单信息
     * @param id 订单的id
     * @param receive_driver 司机的id
     * @param state 订单状态{create, received, completed}
     * @return CBCommonResult<String>
     */
    public CBCommonResult<String> updateOrderAfterReceiveDriver(long id, String receive_driver, String state){
        String result = null;

        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String receive_time = formatter.format(date);

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        data.put("receive_driver", receive_driver);
        data.put("receive_time", receive_time);
        data.put("state", state);
        //System.out.println(data);

        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.101:8080/valetsafe/updateOrderAfterReceiveDriver";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<String>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }

    /**
     * 付款之后，由司机更新订单状态
     * @param id 订单id
     * @param pay_money 付款金额
     * @param state 订单状态{create, received, completed}
     * @return CBCommonResult<String>
     */
    public CBCommonResult<String> updateOrderAfterPaid(long id, String pay_money, String state){
        String result = null;

        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pay_time = formatter.format(date);

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        data.put("pay_time", pay_time);
        data.put("pay_money", pay_money);
        data.put("state", state);

        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.101:8080/valetsafe/updateOrderAfterPaid";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<String>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }



    /**
     * 通过用户id获得用户信息
     * @param id 用户id
     * @return CBCommonResult<User>
     */
    public CBCommonResult<User> loadUserById(long id){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        //System.out.println(data);
        CBCommonResult<User> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/loadUserById";
            //String baseURL = "http://192.168.1.100:8080/test/loadUserById";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            CBPrint.println(result);
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<User>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }


    /**
     * 通过用户信息获得用户id
     * @param name 用户name
     * @param cell_phone 用户手机号
     * @param email 用户email
     * @return CBCommonResult<User>
     */
    public CBCommonResult<User> loadUserByInfo(String name, String cell_phone, String email){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("cell_phone", cell_phone);
        data.put("email", email);
        //System.out.println(data);
        CBCommonResult<User> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/loadUserByInfo";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            CBPrint.println(result);
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<User>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }


    /**
     * 司机查询待接单的预约订单
     * @return CBCommonResult<List<RerseveOrder>>
     */
    public CBCommonResult<List<ValetOrder>> getOrderList(){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("state", "create");
        //System.out.println(data);
        CBCommonResult<List<ValetOrder>> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/getReserveOrderList";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            CBPrint.println(result);
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<List<ValetOrder>>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
    }




}
