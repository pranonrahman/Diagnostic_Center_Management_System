package net.therap.validator;

import net.therap.command.DoctorVisitCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author khandaker.maruf
 * @since 8/13/22
 */
public class DoctorVisitCmdValidator implements Validator {

    @Autowired
    MessageSourceAccessor msa;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(DoctorVisitCmd.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorVisitCmd doctorVisit = (DoctorVisitCmd) target;

        boolean selfAppointment = doctorVisit.getDoctors()
                .stream()
                .anyMatch(doctor -> doctor.getId() == doctorVisit.getPatient().getId());

        if (selfAppointment) {
            errors.rejectValue("doctors", "", msa.getMessage("error.selfAppointment"));
        }
    }
}
