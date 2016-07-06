package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;

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

    /**
     * 个人设置
     * @param name 用户名
     * @param cell_phone 用户手机号
     * @param email 用户email
     * @return 返回结果 Map结构。
     */
    public Map<String, String> SetPassengerSetting(String name, String cell_phone, String email){
        String result = null;

        // 构造传输给服务器的消息，与数据库结构一致。
        Map<String,String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("cell_phone", cell_phone);
        data.put("email", email);

        Map<String, String> return_data = new HashMap<>();
        try {
            CBConnection connection = CBHttp.getInstance();
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
