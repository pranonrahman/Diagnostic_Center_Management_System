package net.therap.validator;

import net.therap.command.DoctorVisitCmd;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author khandaker.maruf
 * @since 8/13/22
 */
public class DoctorVisitCmdValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(DoctorVisitCmd.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorVisitCmd doctorVisit= (DoctorVisitCmd) target;

        boolean selfAppointment = doctorVisit.getDoctors()
                .stream()
                .anyMatch(doctor -> doctor.getId() == doctorVisit.getPatient().getId());

        if(selfAppointment) {
            errors.rejectValue("doctors", "", "Can't set appointment with thyself");
        }
    }
}
