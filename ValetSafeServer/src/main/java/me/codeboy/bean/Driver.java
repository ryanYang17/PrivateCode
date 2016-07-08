package me.codeboy.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhenya huang on 2016/7/6.
 */

@Entity
@Table(name = "driver")
public class Driver {

    private long id;
    private String name;
    private String cell_phone;
    private String email;
    private String password;
    private Date register_time;
    private Date delete_time;
    private String drive_age;

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
    public Date getRegister_time() {return register_time;}
    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    @Column(name = "delete_time")
    public Date getDelete_time() {return delete_time;}
    public void setDelete_time(Date delete_time) {
        this.delete_time = delete_time;
    }

    @Column(name = "drive_age")
    public String getDrive_age() {return drive_age;}
    public void setDrive_age(String drive_age) {
        this.drive_age = drive_age;
    }

}
