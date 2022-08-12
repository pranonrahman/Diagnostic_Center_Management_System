package net.therap.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "doctor")
@Getter
@Setter
@NamedQuery(name = "Doctor.findAll", query = "FROM Doctor ORDER BY id DESC")
public class Doctor extends Persistent {

    private static final long serialVersionUID = 1L;

    private double fee;

    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor", orphanRemoval = true)
    private List<Prescription> prescriptions;

    public Doctor() {
        prescriptions = new ArrayList<>();
    }

    public Doctor(double fee, User user) {
        this();

        this.fee = fee;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
