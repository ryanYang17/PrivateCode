package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.ReserveOrder;
import me.codeboy.bean.User;
import me.codeboy.bean.Driver;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by zhenya huang on 2016/6/28.
 */
public class RegisterAction extends ActionSupport {

    String name;
    String cell_phone;
    String email;
    String password;
    String register_time;
    String driver_age;
    String delete_time;






    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getCell_phone() {
        return cell_phone;
    }
    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password;}
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegister_time() { return register_time;}
    public void setRegister_time(String register_time) {this.register_time = register_time;}

    public String getDriver_age() { return driver_age;}
    public void setDriver_age(String drive_age) {this.driver_age = drive_age;}

    public String getDelete_time() { return delete_time;}
    public void setDelete_time(String delete_time) {this.delete_time = delete_time;}



}
