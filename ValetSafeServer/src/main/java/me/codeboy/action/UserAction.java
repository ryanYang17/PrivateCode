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

    public String loadUserById() {
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
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "User Get"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "Can't find User"));
        }
        return null;
    }

    public String loadUserByInfo() {
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(Session session) {
                String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'";
                User user = null;

                List<User> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }
                user = list.get(0);
                if (user == null){
                    return  null;
                }
                return user;
            }
            @Override
            public User onTaskFailed(Exception e) { return null;}
        }.execute();
        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "User Get"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "Can't find User"));
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
