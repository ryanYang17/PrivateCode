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

    public String addRegisterUser() {
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(Session session) {
                //String sql = "from User where name='" + name + "'";
                String sql = "from User where name='" + name + "' or email='"+ email +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return null;
                }

                User user = new User();
                user.setName(name);
                user.setCell_phone(cell_phone);
                user.setEmail(email);
                user.setPassword(password);
                user.setRegister_time(register_time);
                session.save(user);

                sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'";
                List<User> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }else{
                    user = list.get(0);
                    if (user == null){
                        return  null;
                    }
                }
                return user;
            }

            @Override
            public User onTaskFailed(Exception e) {
                return null;
            }
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "User Register Success"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "User Register Failed"));
        }
        return null;
    }

    public String addRegisterDriver() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                //String sql = "from User where name='" + name + "'";
                String sql = "from User where name='" + name + "' or email='"+ email +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return false;
                }

                Driver user = new Driver();
                user.setName(name);
                user.setCell_phone(cell_phone);
                user.setEmail(email);
                user.setPassword(password);
                user.setRegister_time(register_time);
                user.setDriver_age(driver_age);
                session.save(user);
                return true;
            }

            @Override
            public Boolean onTaskFailed(Exception e) {
                return false;
            }
        }.execute();

        if (res) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, "注册成功"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "注册失败,可能司机用户名已存在"));
        }
        return null;
    }


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
