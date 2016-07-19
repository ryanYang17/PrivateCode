package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.ReserveOrder;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;


import java.util.List;
import java.util.Map;

/**
 * Created by zhenya huang on 2016/7/8.
 */
public class ReserveOrderAction extends ActionSupport {

    long id;
    String create_user;
    String current_place;
    String destination_place;
    String receive_driver;
    String isPaid;
    String pay_money;
    String create_time;
    String reserve_time;
    String receive_time;
    String pay_time;
    String state;
    String priority;

    /**
     * 用户创建预约订单的时候调用的action
     * @return
     */
    public String addReserveOrder() {
        ReserveOrder res = new CBHibernateTask<ReserveOrder>() {
            @Override
            public ReserveOrder doTask(Session session) {

                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from ReserveOrder where create_user='" + create_user + "' and state='"+ state +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return null;
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

                List<ReserveOrder> list = session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }
                order = list.get(0);
                if (order == null) {
                    return null;
                }
                return order;
            }
            @Override
            public ReserveOrder onTaskFailed(Exception e) {
                return null;
            }
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "订单创建成功"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "订单创建失败"));
        }
        return null;
    }

    public String loadReserveOrder(){
        ReserveOrder res = new CBHibernateTask<ReserveOrder>() {
            @Override
            public ReserveOrder doTask(Session session) {
                String sql = "from ReserveOrder where id=" + id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size <= 0) {
                    return null;
                }

                ReserveOrder order = (ReserveOrder)session.get(ReserveOrder.class, id);
                if(order == null) {
                    return null;
                }
                return order;
            }
            @Override
            public ReserveOrder onTaskFailed(Exception e) {
                return null;
            }
        }.execute();

        if (res != null) {
            CBResponseController.process(res);
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "订单不存在"));
        }
        return null;
    }

    /**
     * 司机接收某一订单的时候调用的操作
     * @return
     */
    public String updateReserveOrderAfterReceiveDriver() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from ReserveOrder where id=" + id;
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                List<ReserveOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return false;
                }else{
                    ReserveOrder order = list.get(0);
                    if (!order.getState().equals("create")){
                        return  false;
                    }
                }

                ReserveOrder order = (ReserveOrder)session.get(ReserveOrder.class, id);
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
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, "更新订单成功，司机已接单"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "更新订单失败，可能已被抢单"));
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
                String sql = "from ReserveOrder where id=" + id;
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<ReserveOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return false;
                }else{
                    ReserveOrder order = list.get(0);
                    if (!order.getState().equals("received")){
                        return  false;
                    }
                }

                ReserveOrder order = (ReserveOrder)session.get(ReserveOrder.class, id);
                order.setIsPaid("true");
                order.setPay_time(pay_time);
                order.setPay_money(pay_money);
                order.setState(state);
                session.update(order);
                return true;
            }
            @Override
            public Boolean onTaskFailed(Exception e) {return false;}
        }.execute();

        if (res) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, "支付完成"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "支付失败"));
        }
        return null;
    }

    public String getHistoryReserveOrderByUser() {
        List<ReserveOrder> res = new CBHibernateTask<List<ReserveOrder>>() {
            @Override
            public List<ReserveOrder> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from ReserveOrder where create_user='" + create_user + "'";
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<ReserveOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }

                return list;
            }
            @Override
            public List<ReserveOrder> onTaskFailed(Exception e) {return null;}
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Get History ReserveOrder"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "History ReserveOrder Failed"));
        }
        return null;
    }

    public String getReserveOrderByDriver() {
        List<ReserveOrder> res = new CBHibernateTask<List<ReserveOrder>>() {
            @Override
            public List<ReserveOrder> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from ReserveOrder where state='" + state + "'";
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<ReserveOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }

                return list;
            }
            @Override
            public List<ReserveOrder> onTaskFailed(Exception e) {return null;}
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Get History ReserveOrder"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "History ReserveOrder Failed"));
        }
        return null;
    }

    public String getCreate_time() {return create_time;}
    public void setCreate_time(String create_time) {this.create_time = create_time;}

    public String getCreate_user() {return create_user;}
    public void setCreate_user(String create_user) {this.create_user = create_user;}

    public String getCurrent_place() {return current_place;}
    public void setCurrent_place(String current_place) {this.current_place = current_place;}

    public String getDestination_place() {return destination_place;}
    public void setDestination_place(String destination_place) {this.destination_place = destination_place;}

    public String getIsPaid() {return isPaid;}
    public void setIsPaid(String isPaid) {this.isPaid = isPaid;}

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getPay_money() {return pay_money;}
    public void setPay_money(String pay_money) {this.pay_money = pay_money;}

    public String getPay_time() {return pay_time;}
    public void setPay_time(String pay_time) {this.pay_time = pay_time;}

    public String getPriority() {return priority;}
    public void setPriority(String priority) {this.priority = priority;}

    public String getReceive_driver() {return receive_driver;}
    public void setReceive_driver(String receive_driver) {this.receive_driver = receive_driver;}

    public String getReceive_time() {return receive_time;}
    public void setReceive_time(String receive_time) {this.receive_time = receive_time;}

    public String getReserve_time() {return reserve_time;}
    public void setReserve_time(String reserve_time) {this.reserve_time = reserve_time;}

    public String getState() {return state;}
    public void setState(String state) {this.state = state;}
}
