package net.therap.viewModel;

public class RoleUpdateViewModel {

    private Long id;
    private Boolean doctor;
    private Boolean patient;
    private Boolean receptionist;
    private Boolean admin;
    private Double fee;

    public RoleUpdateViewModel() {
    }

    public RoleUpdateViewModel(Long id, Boolean doctor, Boolean patient, Boolean receptionist, Boolean admin, Double fee) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.receptionist = receptionist;
        this.admin = admin;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
