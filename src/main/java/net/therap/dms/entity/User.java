package net.therap.dms.entity;

import lombok.Getter;
import lombok.Setter;
import net.therap.dms.validation.UserAuthenticationGroup;
import net.therap.dms.validation.UserValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "FROM User ORDER BY id DESC"),
        @NamedQuery(name = "User.findByUserName", query = "FROM User WHERE userName = :userName ORDER BY id DESC")
})
public class User extends Persistent {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{name.notNull}", groups = {UserValidationGroup.class})
    @Size(min = 3, max = 30, message = "{name.size}", groups = {UserValidationGroup.class})
    private String name;

    @NotNull(message = "{phone.notNull}", groups = {UserValidationGroup.class})
    @Size(min = 8, max = 36, message = "{phone.size}", groups = {UserValidationGroup.class})
    private String phone;

    @Email(groups = {UserValidationGroup.class})
    private String email;

    @NotNull(message = "{gender.notNull}", groups = {UserValidationGroup.class})
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "{dateOfBirth.notNull}", groups = {UserValidationGroup.class})
    @PastOrPresent(message = "{dateOfBirth.past}", groups = {UserValidationGroup.class})
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "{userName.notNull}", groups = {UserAuthenticationGroup.class, UserValidationGroup.class})
    @Size(min = 3, max = 15, message = "{userName.size}", groups = {UserAuthenticationGroup.class, UserValidationGroup.class})
    @Column(name = "user_name")
    private String userName;

    @NotNull(message = "{password.notNull}", groups = {UserAuthenticationGroup.class, UserValidationGroup.class})
    @Size(min = 3, max = 255, message = "{password.size}", groups = {UserAuthenticationGroup.class, UserValidationGroup.class})
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Admin admin;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Receptionist receptionist;

    public User() {
        roles = new ArrayList<>();
    }

    public int getAge() {
        Date now = new Date();

        return (int) ((now.getTime() - this.getDateOfBirth().getTime()) / 31536000000L);
    }

    public boolean hasDoctorRole() {
        return nonNull(doctor);
    }

    public boolean hasPatientRole() {
        return nonNull(patient);
    }

    public boolean hasReceptionistRole() {
        return nonNull(receptionist);
    }

    public boolean hasAdminRole() {
        return nonNull(admin);
    }
}
