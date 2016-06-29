package me.codeboy.bean;

import javax.persistence.*;

/**
 * SignUpUser
 * Created by yuedong.li on 6/8/16.
 */
@Entity
@Table(name = "signupuser")
public class SignUpUser {

    private long id;
    private String name;
    private String pwd;

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

    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
