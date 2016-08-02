package bean;

/**
 * Created by zhenya huang on 2016/7/13.
 */
public class Driver {
    private long id;
    private String name;
    private String cell_phone;
    private String email;
    private String password;
    private String register_time;
    private String delete_time;
    private String driver_age;
    private String car_type;
    private String account_number;
    private long account_money;
    private String car_number;
    private String is_online;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCell_phone() {return cell_phone;}
    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegister_time() {return register_time;}
    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getDelete_time() {return delete_time;}
    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getDriver_age() {return driver_age;}
    public void setDriver_age(String driver_age) {
        this.driver_age = driver_age;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account) {
        this.account_number = account_number;
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

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public long getAccount_money() {
        return account_money;
    }

    public void setAccount_money(long account_money) {
        this.account_money = account_money;
    }
}

