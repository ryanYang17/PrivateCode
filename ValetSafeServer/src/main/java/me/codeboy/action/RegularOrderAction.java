package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.RegularOrder;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by zhenya huang on 2016/7/8.
 */
public class RegularOrderAction extends ActionSupport {
    long id;
    String create_user;
    String current_place;
    String destination_place;
    String receive_driver;
    String isPaid;
    String pay_money;
    String create_time;
    String receive_time;
    String pay_time;
    String state;
    String priority;

    /**
     * 用户创建预约订单的时候调用的action
     * @return
     */
    public String addRegularOrder() {
        RegularOrder res = new CBHibernateTask<RegularOrder>() {
            @Override
            public RegularOrder doTask(Session session) {

                //String sql = "from ReserveOrder where id=" + id;
                //String sql = "from ReserveOrder where create_user='" + create_user + "' and state='"+ state +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                String sql = "from RegularOrder where create_user='" + create_user + "' and state <> 'completed'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return null;
                }

                RegularOrder order = new RegularOrder();
                order.setCreate_user(create_user);
                order.setCurrent_place(current_place);
                order.setDestination_place(destination_place);
                order.setCreate_time(create_time);
                order.setState(state);
                order.setIsPaid("false");
                session.save(order);

                sql = "from ReserveOrder where create_user='" + create_user + "' and state='"+ state +"'";
                List<RegularOrder> list = session.createQuery(sql).list();
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
            public RegularOrder onTaskFailed(Exception e) {
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


    public String loadRegularOrder(){
        RegularOrder res = new CBHibernateTask<RegularOrder>() {
            @Override
            public RegularOrder doTask(Session session) {
                String sql = "from RegularOrder where id=" + id;
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size <= 0) {
                    return null;
                }

                RegularOrder order = (RegularOrder)session.get(RegularOrder.class, id);
                if(order == null) {
                    return null;
                }
                return order;
            }
            @Override
            public RegularOrder onTaskFailed(Exception e) {
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
    public String updateRegularOrderAfterReceiveDriver() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from RegularOrder where id=" + id;
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                List<RegularOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return false;
                }else{
                    RegularOrder order = list.get(0);
                    if (!order.getState().equals("create")){
                        return  false;
                    }
                }

                RegularOrder order = (RegularOrder)session.get(RegularOrder.class, id);
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
    public String updateRegularOrderAfterPaid() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from RegularOrder where id=" + id;
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<RegularOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return false;
                }else{
                    RegularOrder order = list.get(0);
                    if (!order.getState().equals("received")){
                        return  false;
                    }
                }

                RegularOrder order = (RegularOrder)session.get(RegularOrder.class, id);
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

    public String getHistoryRegularOrderByUser() {
        List<RegularOrder> res = new CBHibernateTask<List<RegularOrder>>() {
            @Override
            public List<RegularOrder> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from RegularOrder where create_user='" + create_user + "'";
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<RegularOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }

                return list;
            }
            @Override
            public List<RegularOrder> onTaskFailed(Exception e) {return null;}
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Get History ReserveOrder"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "History ReserveOrder Failed"));
        }
        return null;
    }


    public String getRegularOrderList() {
        List<RegularOrder> res = new CBHibernateTask<List<RegularOrder>>() {
            @Override
            public List<RegularOrder> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from RegularOrder where state='create'";
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<RegularOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }
                return list;
            }
            @Override
            public List<RegularOrder> onTaskFailed(Exception e) {return null;}
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Get History ReserveOrder"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "History ReserveOrder Failed"));
        }
        return null;
    }


    public String getRegularOrderByDriver() {
        List<RegularOrder> res = new CBHibernateTask<List<RegularOrder>>() {
            @Override
            public List<RegularOrder> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from RegularOrder where state='" + state + "'";
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<RegularOrder> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }

                return list;
            }
            @Override
            public List<RegularOrder> onTaskFailed(Exception e) {return null;}
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Get History ReserveOrder"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "History ReserveOrder Failed"));
        }
        return null;
    }




    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCurrent_place() {
        return current_place;
    }

    public void setCurrent_place(String current_place) {
        this.current_place = current_place;
    }

    public String getDestination_place() {
        return destination_place;
    }

    public void setDestination_place(String destination_place) {
        this.destination_place = destination_place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReceive_driver() {
        return receive_driver;
    }

    public void setReceive_driver(String receive_driver) {
        this.receive_driver = receive_driver;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
