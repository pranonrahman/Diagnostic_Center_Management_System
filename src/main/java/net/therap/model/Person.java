package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "person", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
@Getter
@Setter
public class Person extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "user_name")
    private String userName;

    private String password;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "person_role",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @OneToOne
    private Admin admin;

    @OneToOne
    private Doctor doctor;

    @OneToOne
    private Patient patient;

    @OneToOne
    private Receptionist receptionist;

    public Person() {
        roles = new HashSet<>();
    }

    public Person(String name, String phone, String email, Gender gender, Date dateOfBirth, String userName, String password) {
        this();

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
    }
}
