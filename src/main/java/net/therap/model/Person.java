package net.therap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "{name.notBlank}")
    @NotNull(message = "{name.notNull}")
    @Size(min = 1, max = 30, message = "{name.message}")
    private String name;

    @NotBlank(message = "{phone.notBlank}")
    @NotNull(message = "{phone.notNull}")
    @Size(min = 11, max = 11, message = "{phone.size}")
    private String phone;

    @Email
    private String email;

    @NotNull(message = "{gender.notNull}")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "{dateOfBirth.notNull}")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "{userName.notNull}")
    @NotBlank(message = "{userName.notBlank}")
    @Size(min = 3, max = 15, message = "{userName.size}")
    @Column(name = "user_name")
    private String userName;

    @NotNull(message = "{password.notNull}")
    @NotBlank(message = "{password.notBlank}")
    @Size(min = 5, max=255, message = "{password.size}")
    private String password;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "person_role",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Admin admin;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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
