package me.codeboy.action;

import com.opensymphony.xwork2.ActionSupport;
import me.codeboy.bean.Order;
import me.codeboy.bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.bean.CBCommonResult;
import me.codeboy.common.framework.workflow.core.CBCommonResultCode;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;

import java.util.List;


/**
 * usere action
 * Created by yuedong.li on 6/8/16.
 */
public class UserAction extends ActionSupport {
    //用户属性
    long id;
    String name;
    String cell_phone;
    String email;
    String password;
    String register_time;
    String delete_time;

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


    public String addRegisterUser() {
        User res = new CBHibernateTask<User>() {
            @Override
            public User doTask(Session session) {
                //String sql = "from User where name='" + name + "'";
                String sql = "from User where name='" + name + "' or email='"+ email +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return null;
                }

                User user = new User();
                user.setName(name);
                user.setCell_phone(cell_phone);
                user.setEmail(email);
                user.setPassword(password);
                user.setRegister_time(register_time);
                session.save(user);

                sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'";
                List<User> list =  session.createQuery(sql).list();
                CBPrint.println(list.size());
                if (list.size() <= 0) {
                    return null;
                }else{
                    user = list.get(0);
                    if (user == null){
                        return  null;
                    }
                }
                return user;
            }

            @Override
            public User onTaskFailed(Exception e) {
                return null;
            }
        }.execute();

        if (res != null) {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.SUCCESS, res, "User Register Success"));
        } else {
            CBResponseController.process(new CBCommonResult<>(CBCommonResultCode.FAILED, "User has been existed!"));
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


    /**
     * 用户创建预约订单的时候调用的action
     * @return
     */
    public String createOrder() {
        Order res = new CBHibernateTask<Order>() {
            @Override
            public Order doTask(Session session) {

                //String sql = "from ReserveOrder where id=" + id;
                //String sql = "from ReserveOrder where create_user='" + create_user + "' and state='"+ state +"'";
                //String sql = "from User where name='" + name + "' and cell_phone='"+ cell_phone +"'and email='"+ email +"'and password='"+ password +"'";
                String sql = "from Order where create_user='" + create_user + "' and state <> 'completed'";
                CBPrint.println(sql);
                int size = session.createQuery(sql).list().size();
                CBPrint.println(size);
                if (size > 0) {
                    return null;
                }

                Order order = new Order();
                order.setType(type);
                order.setCreate_user(create_user);
                order.setCurrent_place(current_place);
                order.setReserve_place(reserve_place);
                order.setDestination_place(destination_place);
                order.setCreate_time(create_time);
                order.setReserve_time(reserve_time);
                order.setState(state);
                order.setIsPaid("false");
                session.save(order);

                sql = "from Order where create_user='" + create_user + "' and state='"+ state +"'";
                List<Order> list = session.createQuery(sql).list();
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
            public Order onTaskFailed(Exception e) {
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


    public String getHistoryOrderByUser() {
        List<Order> res = new CBHibernateTask<List<Order>>() {
            @Override
            public List<Order> doTask(Session session) {
                //String sql = "from ReserveOrder where id=" + id;
                String sql = "from Order where create_user='" + create_user + "'";
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





    //用户
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
