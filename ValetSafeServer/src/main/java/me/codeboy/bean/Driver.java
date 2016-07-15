package me.codeboy.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhenya huang on 2016/7/6.
 */

@Entity
@Table(name = "driver")
public class Driver {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String cell_phone;
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private String register_time;
    private String delete_time;
    @Expose
    private String driver_age;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "cell_phone")
    public String getCell_phone() {return cell_phone;}
    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    @Column(name = "email")
    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {return password;}
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "register_time")
    public String getRegister_time() {return register_time;}
    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    @Column(name = "delete_time")
    public String getDelete_time() {return delete_time;}
    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    @Column(name = "drive_age")
    public String getDriver_age() {return driver_age;}
    public void setDriver_age(String driver_age) {
        this.driver_age = driver_age;
    }

}
