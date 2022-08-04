package net.therap.viewModel;

import net.therap.model.Prescription;

import java.util.Date;

/**
 * @author amimul.ehsan
 * @since 04/08/2022
 */
public class PrescriptionViewModel implements Comparable<PrescriptionViewModel> {

    private final Prescription prescription;

    public PrescriptionViewModel(Prescription prescription) {
        this.prescription = prescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public long getDaysElapsed() {
        Date date = new Date();

        return (date.getTime() - prescription.getDateOfVisit().getTime()) / 86400000;
    }


    @Override
    public int compareTo(PrescriptionViewModel o) {
        return (int) (this.getDaysElapsed()-o.getDaysElapsed());
    }
}
