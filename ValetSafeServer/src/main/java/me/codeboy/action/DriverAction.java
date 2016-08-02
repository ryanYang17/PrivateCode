package me.codeboy.action;

import me.codeboy.bean.Driver;
import me.codeboy.bean.Order;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by zhenya huang on 2016/7/14.
 */
public class DriverAction {
    //司机属性
    long id;
    String name;
    String cell_phone;
    String email;
    String password;
    String register_time;
    String delete_time;
    String driver_age;
    String car_type;
    String account_number;
    long account_money;
    String car_number;
    String is_online;



    //订单属性
    String type;
    String create_user;
    String current_place;
    String reserve_place;
    String destination_place;
    String receive_driver;
    String isPaid;
    String money;
    String create_time;
    String reserve_time;
    String receive_time;
    String pay_time;
    String state;
    String priority;


    public String addRegisterDriver() {
        Driver res = new CBHibernateTask<Driver>() {
            @Override
            public Driver doTask(Session session) {
                //String sql = "from User where name='" + name + "'";
                String sql = "from User where name='" + name + "' or email='"+ email +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return null;
                }

                Driver driver = new Driver();
                driver.setName(name);
                driver.setCell_phone(cell_phone);
                driver.setEmail(email);
                driver.setPassword(password);
                driver.setRegister_time(register_time);
                driver.setDriver_age(driver_age);
                driver.setCar_number(car_number);
                driver.setCar_number(account_number);
                driver.setCar_number(car_type);
                session.save(driver);


                sql = "from Driver where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'";
                List<Driver> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }else{
                    driver = list.get(0);
                    if (driver == null){
                        return  null;
                    }
                }
                return driver;
            }

            @Override
            public Driver onTaskFailed(Exception e) {
                return null;
            }
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "注册成功"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "注册失败,可能司机用户名已存在"));
        }
        return null;
    }


    public String loadDriverById() {
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
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "查询司机用户失败"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "查询司机用户失败"));
        }
        return null;
    }


    /**
     * 司机接收某一订单的时候调用的操作
     * @return
     */
    public String updateOrderAfterReceiveDriver() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from Order where id=" + id;
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                List<Order> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return false;
                }else{
                    Order order = list.get(0);
                    if (!order.getState().equals("create")){
                        return  false;
                    }
                }

                Order order = (Order)session.get(Order.class, id);
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
    public String updateOrderAfterPaid() {
        boolean res = new CBHibernateTask<Boolean>() {
            @Override
            public Boolean doTask(Session session) {
                String sql = "from Order where id=" + id;
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<Order> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return false;
                }else{
                    Order order = list.get(0);
                    if (!order.getState().equals("received")){
                        return  false;
                    }
                }

                Order order = (Order)session.get(Order.class, id);
                order.setIsPaid("true");
                order.setPay_time(pay_time);
                order.setMoney(money);
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



    public String getOrderList() {
        List<Order> res = new CBHibernateTask<List<Order>>() {
            @Override
            public List<Order> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from Order where state='create'";
                CBPrint.println(sql);
                //int size = session.createQuery(sql).list().size();
                //CBPrint.println(size);
                //if (size <= 0) {return false;}
                List<Order> list =  session.createQuery(sql).list();
                if (list.size() <= 0) {
                    return null;
                }
                return list;
            }
            @Override
            public List<Order> onTaskFailed(Exception e) {return null;}
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "Get History ReserveOrder"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "History ReserveOrder Failed"));
        }
        return null;
    }



    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getDriver_age() {
        return driver_age;
    }

    public void setDriver_age(String driver_age) {
        this.driver_age = driver_age;
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

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
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

    public long getAccount_money() {
        return account_money;
    }

    public void setAccount_money(long account_money) {
        this.account_money = account_money;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }





    //订单
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

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getReserve_place() {
        return reserve_place;
    }

    public void setReserve_place(String reserve_place) {
        this.reserve_place = reserve_place;
    }

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
