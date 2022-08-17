package net.therap.dms.validator;

import net.therap.dms.command.DoctorVisitCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author khandaker.maruf
 * @since 8/13/22
 */
@Component
public class DoctorVisitCmdValidator implements Validator {

    @Autowired
    private MessageSourceAccessor msa;

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
