package ru.kfu.itis.reenaz.app.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Reenaz on 23.07.2017.
 */
public class UserDTO implements Serializable {

    private int id;
    private String name;
    private Date birthday;

    public UserDTO() {
    }

    public UserDTO(int id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
