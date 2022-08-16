package net.therap.dms.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.therap.dms.entity.Doctor;
import net.therap.dms.entity.Patient;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Getter
@Setter
@AllArgsConstructor
public class DoctorVisitCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Patient patient;
    private Set<Doctor> doctors;

    public DoctorVisitCmd() {
        doctors = new HashSet<>();
    }
}
