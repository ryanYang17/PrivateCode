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
 * Created by zhenya huang on 2016/6/28.
 */
public class PassengerSettingAction extends ActionSupport {
    String ModifyText;
    String UserID;
    String ModifyNum;

    public String PassengerSet() {
        String res = new CBHibernateTask<String>() {
            @Override
            public String doTask(Session session) {
                String sql = "from User where id=" + Integer.parseInt(UserID);
                List<User> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }
                User user = list.get(0);
                if (user == null)
                    return null;
                if (ModifyNum.equals("1"))
                    user.setName(ModifyText);
                else if (ModifyNum.equals("2"))
                    user.setCell_phone(ModifyText);
                else
                    user.setEmail(ModifyText);
                session.update(user);
                return "succ";
            }
            @Override
            public String onTaskFailed(Exception e) {
                return null;
            }
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Modify Successfully"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "Modify Failed"));
        }
        return null;
    }

    public String getModifyText() {
        return ModifyText;
    }
    public void setModifyText(String ModifyText) {
        this.ModifyText = ModifyText;
    }

    public String getUserID() {
        return UserID;
    }
    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getModifyNum(){
        return ModifyNum;
    }
    public void setModifyNum(String modifyNum){
        this.ModifyNum = modifyNum;
    }

}
