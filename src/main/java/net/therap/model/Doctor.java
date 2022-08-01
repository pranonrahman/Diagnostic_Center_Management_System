package net.therap.model;

import java.util.Date;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Doctor extends Person {

    private Double fee;
    List<Speciality> specialityList;

    public Doctor() {
    }

    public Doctor(Long id, String name, String phone, String email, Gender gender, Date dateOfBirth,
                  List<Role> roleList, String userName, String password, Double fee, List<Speciality> specialityList) {

        super(id, name, phone, email, gender, dateOfBirth, roleList, userName, password);
        this.fee = fee;
        this.specialityList = specialityList;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public List<Speciality> getSpecialityList() {
        return specialityList;
    }

    public void setSpecialityList(List<Speciality> specialityList) {
        this.specialityList = specialityList;
    }
}
