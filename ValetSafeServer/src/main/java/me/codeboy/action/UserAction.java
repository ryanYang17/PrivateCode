package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;
import com.google.gson.Gson;

import java.util.List;


/**
 * usere action
 * Created by yuedong.li on 6/8/16.
 */
public class UserAction extends ActionSupport {
    long id;
    String name;
    String cell_phone;
    String email;
    String password;
    String register_time;
    String delete_time;
    String LoginName;
    String pwd;
    String LoginMode;

    public String addUser() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from User where name='" + name + "'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return false;
                }
                User user = new User();
                user.setName(name);
                session.save(user);
                return true;
            }

            @Override
            public Boolean onTaskFailed(Exception e) {
                return false;
            }
        }.execute();

        if (res) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, "添加成功"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "添加失败,可能用户名已存在"));
        }
        return null;
    }

    public String loadUser() {
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(Session session) {
                String sql = "from User where id=" + id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size <= 0) {
                    return null;
                }
                User user=(User)session.get(User.class, id);
                if (user == null){
                    return null;
                }
                return user;
            }
            @Override
            public User onTaskFailed(Exception e) { return null;}
        }.execute();
        if (res != null) {
            //CBPrint.println("cheng gong");
            //CBPrint.println(res.getId());
            //CBPrint.println(res.getName());
            CBResponseController.process(res);
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "查询用户失败"));
        }
        return null;
    }

    public String loginuser() {
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(Session session) {
                String sql = "";
                if (LoginMode.equals("0"))
                    sql = "select u.id from User u where u.cell_phone = '" + LoginName + "' and u.password = '" + pwd +"'";
                else
                    sql = "select u.id from User u where u.email = '" + LoginName + "' and u.password = '" + pwd +"'";
                CBPrint.println(sql);
                List<User> logUsers = session.createQuery(sql).list();
                int size = logUsers.size();
                CBPrint.println(size);
                if (size <= 0) {
                    return null;
                }
                User user= logUsers.get(0);
                if (user == null){
                    return null;
                }
                return user;
            }
            @Override
            public User onTaskFailed(Exception e) { return null;}
        }.execute();
        if (res != null) {
            CBResponseController.process(res);
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "Login Failed  "));
        }
        return null;
    }

    public long getId() {return id;}
    public void setId(long id) {
        this.id = id;
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

    public String getDelete_time() { return delete_time;}
    public void setDelete_time(String delete_time) {this.delete_time = delete_time;}
}
