package net.therap.model;

import java.util.Date;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Person extends BaseEntity {

    private String name;
    private String phone;
    private String email;
    private Gender gender;
    private Date dateOfBirth;
    private String userName;
    private String password;
    private List<Role> roleList;

    public Person() {
    }

    public Person(Long id, String name, String phone, String email, Gender gender, Date dateOfBirth,
                  List<Role> roleList, String userName, String password) {

        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.roleList = roleList;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
