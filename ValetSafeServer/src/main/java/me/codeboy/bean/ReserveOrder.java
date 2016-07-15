package me.codeboy.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhenya huang on 2016/7/6.
 */
@Entity
@Table(name = "reserve_order")
public class ReserveOrder {
    @Expose
    private long id;
    @Expose
    private String create_user;
    @Expose
    private String current_place;
    @Expose
    private String destination_place;
    @Expose
    private String receive_driver;
    @Expose
    private String isPaid;
    @Expose
    private String pay_money;
    @Expose
    private String create_time;
    @Expose
    private String reserve_time;
    @Expose
    private String receive_time;
    @Expose
    private String pay_time;
    @Expose
    private String state;
    private String priority;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public long getOrder_id() {
        return id;
    }
    public void setOrder_id(long id) {
        this.id = id;
    }

    @Column(name = "create_user")
    public String getCreate_user() {
        return create_user;
    }
    public void setCreate_user(String name) {
        this.create_user = create_user;
    }

    @Column(name = "current_place")
    public String getCurrent_place() {return current_place;}
    public void setCurrent_place(String cell_phone) {
        this.current_place = current_place;
    }

    @Column(name = "destination_place")
    public String getDestination_place() {return destination_place;}
    public void setDestination_place(String email) {
        this.destination_place = destination_place;
    }

    @Column(name = "receive_driver")
    public String getReceive_driver() {return receive_driver;}
    public void setReceive_driver(String password) {
        this.receive_driver = receive_driver;
    }

    @Column(name = "isPaid")
    public String getIsPaid() {return isPaid;}
    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    @Column(name = "pay_money")
    public String getPay_money() {return pay_money;}
    public void setPay_money(String pay_money) {this.pay_money = pay_money;}

    @Column(name = "create_time")
    public String getCreate_time() {return create_time;}
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Column(name = "reserve_time")
    public String getReserve_time() {return reserve_time;}
    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    @Column(name = "receive_time")
    public String getReceive_time() {return receive_time;}
    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    @Column(name = "pay_time")
    public String getPay_time() {return pay_time;}
    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    @Column(name = "state")
    public String getState() {return state;}
    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "priority")
    public String getPriority() {return priority;}
    public void setPriority(String priority) {
        this.priority = priority;
    }
}
