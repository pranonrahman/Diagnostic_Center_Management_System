package net.therap.command;

import lombok.Getter;
import net.therap.model.Patient;

import java.util.Date;

/**
 * @author amimul.ehsan
 * @since 08/08/2022
 */
@Getter
public class PatientCmd {

    private Patient patient;

    public PatientCmd(Patient patient) {
        this.patient = patient;
    }

    public long getAge() {
        Date date = new Date();

        return (date.getTime() - patient.getUser().getDateOfBirth().getTime()) / 31536000000L;
    }
}
