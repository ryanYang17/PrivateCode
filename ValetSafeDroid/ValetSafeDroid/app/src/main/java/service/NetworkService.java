package service;

import android.os.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bean.CBCommonResult;
import bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;
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
    public CBCommonResult<String> registerUserAction(String name, String cell_phone, String email, String password){
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
        //System.out.println(data);

        //Map<String, String> return_data = new HashMap<>();
        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.101:8080/valetsafe/addRegisterUser";
            //String baseURL = "http://192.168.1.106:8080/valetsafe/addRegisterDriver";
            CBPrint.println(baseURL);
            result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(data).execute();
            CBPrint.println(result);
            Gson gson =new Gson();
            cbResult = gson.fromJson(result, new TypeToken<CBCommonResult<String>>(){}.getType());
            //return_data.put("result", result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cbResult;
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

    public CBCommonResult<User> LoginUserAction(String name, String pwd, String LoginMode){
        String result = null;
        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("LoginName", name);
        data.put("pwd", pwd);
        data.put("LoginMode", LoginMode);
        CBCommonResult<User> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/loginuser";
            //String baseURL = "http://192.168.1.101:8080/valetsafe/loadUser";
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

    public CBCommonResult<User> loadUser(long id){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("id", String.valueOf(id));
        //System.out.println(data);
        CBCommonResult<User> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/loadUserById";
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
     * @param ModifyText 修改文本
     * @param UserID UserID
     * @return 返回结果 Map结构。
     */
    public CBCommonResult<String> SetPassengerSetting(String ModifyText, String UserID, String ModifyNum){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("ModifyText", ModifyText);
        data.put("UserID", UserID);
        data.put("ModifyNum", ModifyNum);

        CBCommonResult<String> cbResult;
        try {
            CBConnection connection = CBHttp.getInstance();
            String baseURL = "http://47.88.192.36:8080/valetsafe/PassengerSet";
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


    public static void main(String[] args){

        /**
        String name = "hzytest"
        String cell_phone = "123123123";
        String email = "aaaaa@qq.com";
        String password = "hzyhzyhzy";
        Date register_time =new Date();

        //调用网络服务进行注册用户操作
        NetworkService service = new NetworkService();
        Map<String, String> data = service.registerUserAction(name,cell_phone,email,password,register_time);

        String result = data.get("result");
        Message msg = new Message();
        msg.arg1 = 0;
        msg.getData().putString("result", result);
        */
    }

}
