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

    /**
     * 用户创建预约订单的时候调用的action
     * @return
     */
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
                order.setCreate_time(create_time);
                order.setReserve_time(reserve_time);
                order.setState(state);
                order.setIsPaid("false");
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

    /**
     * 司机接收某一订单的时候调用的操作
     * @return
     */
    public String updateReserveOrderAfterReciveDriver() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from ReserveOrder where order_id=" + order_id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size <= 0) {
                    return false;
                }

                ReserveOrder order = (ReserveOrder)session.load(ReserveOrder.class, order_id);
                order.setReceive_driver(receive_driver);
                order.setReceive_time(receive_time);
                order.setState(state);
                session.update(order);
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

    /**
     * 用户付款过后，由司机更新订单状态时调用的action
     * @return
     */
    public String updateReserveOrderAfterPaid() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from ReserveOrder where order_id=" + order_id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size <= 0) {
                    return false;
                }

                ReserveOrder order = (ReserveOrder)session.load(ReserveOrder.class, order_id);
                order.setIsPaid("true");
                order.setPay_time(pay_time);
                order.setPay_money(pay_money);
                order.setState(state);
                session.update(order);
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
