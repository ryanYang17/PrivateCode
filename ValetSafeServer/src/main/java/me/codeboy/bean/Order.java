package me.codeboy.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by zhenya huang on 2016/7/29.
 */

@Entity
@Table(name = "reserve_order")
public class Order {

    @Expose
    private long id;
    @Expose
    private String type;
    @Expose
    private String create_user;
    @Expose
    private String current_place;
    @Expose
    private String reserve_place;
    @Expose
    private String destination_place;
    @Expose
    private String receive_driver;
    @Expose
    private String isPaid;
    @Expose
    private String money;
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
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "create_time")
    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Column(name = "create_user")
    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    @Column(name = "current_place")
    public String getCurrent_place() {
        return current_place;
    }

    public void setCurrent_place(String current_place) {
        this.current_place = current_place;
    }

    @Column(name = "destination_place")
    public String getDestination_place() {
        return destination_place;
    }

    public void setDestination_place(String destination_place) {
        this.destination_place = destination_place;
    }

    @Column(name = "is_paid")
    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    @Column(name = "money")
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Column(name = "pay_time")
    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    @Column(name = "priority")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "receive_driver")
    public String getReceive_driver() {
        return receive_driver;
    }

    public void setReceive_driver(String receive_driver) {
        this.receive_driver = receive_driver;
    }

    @Column(name = "receive_time")
    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    @Column(name = "reserve_place")
    public String getReserve_place() {
        return reserve_place;
    }

    public void setReserve_place(String reserve_place) {
        this.reserve_place = reserve_place;
    }

    @Column(name = "reserve_time")
    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
