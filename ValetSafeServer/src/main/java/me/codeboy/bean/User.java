package me.codeboy.bean;

import javax.persistence.*;

/**
 * user
 * Created by yuedong.li on 6/8/16.
 */
@Entity
@Table(name = "user")
public class User {
    private long id;
    private String name;
    private String cell_phone;
    private String email;
    private String password;

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
}
