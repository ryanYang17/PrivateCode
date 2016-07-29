package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by ryan on 2016/7/25.
 */
public class LoginAction extends ActionSupport {
    String LoginName = "";
    String Password = "";
    String LoginMode = "";
    String ForgetpassPhone = "";
    private String LogError = "";

    public String loginuser() {
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(Session session) {
                String sql = "";
                if (LoginMode.equals("0"))
                    sql = "from User where cell_phone = '" + LoginName + "' and password = '" + Password +"'";
                else
                    sql = "from User where email = '" + LoginName + "' and password = '" + Password +"'";
                CBPrint.println(sql);
                List<User> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    LogError = "No user found, please register firstly!";
                    return null;
                }
                User user = new User();
                user= list.get(0);
                if (user == null){
                    return  null;
                }
                return user;
            }
            @Override
            public User onTaskFailed(Exception e) { return null;}
        }.execute();
        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Success"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, LogError));
        }
        return null;
    }

    public String SendEmailForUser(){
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(org.hibernate.Session session) {
                String sql = "";
                sql = "from User where cell_phone = '" + ForgetpassPhone + "'";
                CBPrint.println(sql);
                List<User> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    LogError = "No user found, please contact our service!";
                    return null;
                }
                User user = new User();
                user= list.get(0);
                if (user == null){
                    return  null;
                }
                return user;
            }
            @Override
            public User onTaskFailed(Exception e) { return null;}
        }.execute();
        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Password has been sent to your Email!"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, LogError));
        }
        return null;
    }

    public String getLoginName() { return LoginName;}
    public void setLoginName(String LoginName) {this.LoginName = LoginName;}

    public String getPassword() { return Password;}
    public void setPassword(String Password) {this.Password = Password;}

    public String getLoginMode() { return LoginMode;}
    public void setLoginMode(String LoginMode) {this.LoginMode = LoginMode;}

    public String getForgetpassPhone() { return ForgetpassPhone;}
    public void setForgetpassPhone(String ForgetpassPhone) {this.ForgetpassPhone = ForgetpassPhone;}
}
