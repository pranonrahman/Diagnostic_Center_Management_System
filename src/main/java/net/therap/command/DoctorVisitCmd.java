package net.therap.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.therap.entity.Doctor;
import net.therap.entity.Patient;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<Doctor> doctors;

    public DoctorVisitCmd() {
        doctors = new ArrayList<>();
    }
}
