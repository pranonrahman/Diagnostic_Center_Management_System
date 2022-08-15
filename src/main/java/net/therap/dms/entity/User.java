package net.therap.dms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @NotNull(message = "{name.notNull}")
    @Size(min = 3, max = 30, message = "{name.size}")
    private String name;

    @NotNull(message = "{phone.notNull}")
    @Size(min = 8, max = 36, message = "{phone.size}")
    private String phone;

    @Email
    private String email;

    @NotNull(message = "{gender.notNull}")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "{dateOfBirth.notNull}")
    @PastOrPresent(message = "{dateOfBirth.past}")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "{userName.notNull}")
    @Size(min = 3, max = 15, message = "{userName.size}")
    @Column(name = "user_name")
    private String userName;

    @NotNull(message = "{password.notNull}")
    @Size(min = 3, max = 255, message = "{password.size}")
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

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
        roles = new HashSet<>();
    }

    public int getAge(){
         Date now = new Date();

        return (int) ((now.getTime() - this.getDateOfBirth().getTime()) / 31536000000L);
    }
}
