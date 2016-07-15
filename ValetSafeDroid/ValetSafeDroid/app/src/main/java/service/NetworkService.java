package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;

import bean.CBCommonResult;
import bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by zhenya huang on 2016/6/28.
 */
public class NetworkService {

    /**
     * 注册新用户
     * @param name 用户名
     * @param cell_phone 用户手机号
     * @param email 用户email
     * @param password 用户密码
     * @return 返回结果 Map结构。
     */
    public Map<String, String> registerUserAction(String name, String cell_phone, String email, String password){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("cell_phone", cell_phone);
        data.put("email", email);
        data.put("password", password);

        Map<String, String> return_data = new HashMap<>();
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            return_data.put("result", result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return return_data;
    }


    public CBCommonResult<String> registerDriverAction(String name, String cell_phone, String email, String password, String driver_age){
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
        //System.out.println(data);

        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            //String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.106:8080/valetsafe/addRegisterUser";
            String baseURL = "http://192.168.1.102:8080/valetsafe/addRegisterDriver";
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

    public CBCommonResult<String> createReserveOrderAction(String create_user, String current_place, String destination_place, String reserve_time, String state){
        String result = null;

        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = formatter.format(date);

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("create_user", create_user);
        data.put("current_place", current_place);
        data.put("destination_place", destination_place);
        data.put("create_time", create_time);
        data.put("reserve_time", reserve_time);
        data.put("state", state);
        System.out.println(data);

        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            //String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            String baseURL = "http://192.168.1.101:8080/valetsafe/addReserveOrder";
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

    public CBCommonResult<String> updateReserveOrderAfterReceiveDriver(long id, String receive_driver, String state){
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
            //String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            String baseURL = "http://192.168.1.101:8080/valetsafe/updateReserveOrderAfterReceiveDriver";
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

    public CBCommonResult<String> updateReserveOrderAfterPaid(long id, String pay_time, String pay_money, String state){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        data.put("pay_time", pay_time);
        data.put("pay_money", pay_money);
        data.put("state", state);

        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            //String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            String baseURL = "http://192.168.1.101:8080/valetsafe/updateReserveOrderAfterPaid";
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

    public CBCommonResult<User> loadUser(long id, String name, String cell_phone){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("id", String.valueOf(id));
        data.put("cell_phone", cell_phone);
        //System.out.println(data);
        CBCommonResult<User> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            //String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            String baseURL = "http://192.168.1.101:8080/valetsafe/loadUser";
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
     * 个人设置
     * @param name 用户名
     * @param cell_phone 用户手机号
     * @param email 用户email
     * @return 返回结果 Map结构。
     */
    public Map<String, String> SetPassengerSetting(String name, String cell_phone, String email, String ModifyNum){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("cell_phone", cell_phone);
        data.put("email", email);
        data.put("ModifyNum", ModifyNum);

        Map<String, String> return_data = new HashMap<>();
        try {
            CBConnection connection = CBHttp.getInstance();
            //String baseURL = "http://10.24.6.151:8080/valetsafe/PassengerSet";
            String baseURL = "http://47.88.192.36:8080/valetsafe/PassengerSet";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            return_data.put("result", result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return return_data;
    }
}
