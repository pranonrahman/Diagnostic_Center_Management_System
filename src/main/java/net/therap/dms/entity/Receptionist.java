package net.therap.dms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "receptionist")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Receptionist.findAll", query = "FROM Receptionist ORDER BY id DESC")
public class Receptionist extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receptionist", orphanRemoval = true)
    private List<Invoice> invoices;

    public Receptionist(User user) {
        this.user = user;
    }
}
