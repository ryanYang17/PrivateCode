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
    String ModifyNum;

    public String PassengerSet() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                session.beginTransaction();
                String sql = "";
                if (ModifyNum.equals("1")) {
                    sql = "update User u set u.name = '" + name + "' where u.cell_phone='" + cell_phone + "'and u.email='" + email + "'";
                }
                else if (ModifyNum.equals("2"))
                {
                    sql = "update User u set u.cell_phone = '" + cell_phone + "' where u.name='" + name + "'and u.email='" + email + "'";
                }
                else
                {
                    sql = "update User u set u.email = '" + email + "' where u.name='" + name + "'and u.cell_phone='" + cell_phone + "'";
                }
                int size = session.createQuery(sql).list().size();
                if (size <= 0) {
                    return false; }

                session.createQuery(sql).executeUpdate();
                session.getTransaction().commit();

                return true;
            }

            @Override
            public Boolean onTaskFailed(Exception e) {
                return false;
            }
        }.execute();

        if (res) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, "修改成功"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "失败"));
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
