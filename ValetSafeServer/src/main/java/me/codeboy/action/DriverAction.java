package me.codeboy.action;

import me.codeboy.bean.Driver;
import me.codeboy.bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

/**
 * Created by zhenya huang on 2016/7/14.
 */
public class DriverAction {

    long id;
    String name;
    String cell_phone;
    String email;
    String password;
    String register_time;
    String delete_time;

    public String loadDriver() {
        Driver res = new CBHibernateTask<Driver>() {
            @Override
            public Driver doTask(Session session) {
                String sql = "from Driver where id=" + id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size <= 0) {
                    return null;
                }
                Driver driver=(Driver)session.get(Driver.class, id);
                if (driver == null){
                    return null;
                }
                return driver;
            }
            @Override
            public Driver onTaskFailed(Exception e) { return null;}
        }.execute();
        if (res != null) {
            //CBPrint.println("cheng gong");
            //CBPrint.println(res.getId());
            //CBPrint.println(res.getName());
            CBResponseController.process(res);
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "查询司机用户失败"));
        }
        return null;
    }

    public String getCell_phone() {return cell_phone;}

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }
}
