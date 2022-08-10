package net.therap.viewModel;

public class RoleUpdateViewModel {

    private long id;
    private Boolean doctor;
    private Boolean patient;
    private Boolean receptionist;
    private Boolean admin;
    private double fee;

    public RoleUpdateViewModel() {
    }

    public RoleUpdateViewModel(long id, Boolean doctor, Boolean patient, Boolean receptionist, Boolean admin, Double fee) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.receptionist = receptionist;
        this.admin = admin;
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getDoctor() {
        return doctor;
    }

    public void setDoctor(Boolean doctor) {
        this.doctor = doctor;
    }

    public Boolean getPatient() {
        return patient;
    }

    public void setPatient(Boolean patient) {
        this.patient = patient;
    }

    public Boolean getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Boolean receptionist) {
        this.receptionist = receptionist;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
