package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.ReserveOrder;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

import java.util.Date;

/**
 * Created by zhenya huang on 2016/7/8.
 */
public class ReserveOrderAction extends ActionSupport {

    long order_id;
    String create_user;
    String current_place;
    String destination_place;
    String receive_driver;
    String isPaid;
    String pay_money;
    Date create_time;
    Date reserve_time;
    Date receive_time;
    Date pay_time;
    String state;
    String priority;

    public String addReserveOrder() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from ReserveOrder where order_id=" + order_id;
                //String sql = "from RegularOrder where name='" + name + "' or email='"+ email +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return false;
                }

                ReserveOrder order = new ReserveOrder();
                order.setCreate_user(create_user);
                order.setCurrent_place(current_place);
                order.setDestination_place(destination_place);
                order.setCreate_time(new Date());
                order.setState("create");
                session.save(order);
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
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "注册失败,可能用户用户名已存在"));
        }
        return null;
    }

    public String updateReserveOrderByDriver() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from ReserveOrder where order_id=" + order_id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return false;
                }


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
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "注册失败,可能用户用户名已存在"));
        }
        return null;
    }

}
