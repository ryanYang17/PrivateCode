package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

/**
 * Created by zhenya huang on 2016/6/28.
 */
public class PassengerSettingAction extends ActionSupport {

    String name;
    String cell_phone;
    String email;

    public String PassengerSet() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                //String sql = "from User where name='" + name + "'";
                //String sql = "from User where name='" + name + "' and password='"+ password +"'";
                //session.createQuery()
                String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return false; }

                User user = new User();
                user.setName(name);
                user.setCell_phone(cell_phone);
                user.setEmail(email);
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
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "注册失败,可能用户名已存在"));
        }
        return null;
    }

    public String getName() {
        return name;
    }
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
}
