package net.therap.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.therap.model.Doctor;
import net.therap.model.Patient;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khandaker.maruf
 * @since 8/7/22
 */
@Getter
@Setter
@AllArgsConstructor
public class DoctorVisit {

    @NotNull
    private Patient patient;
    private List<Doctor> doctors;

    public DoctorVisit() {
        doctors = new ArrayList<>();
    }
}
