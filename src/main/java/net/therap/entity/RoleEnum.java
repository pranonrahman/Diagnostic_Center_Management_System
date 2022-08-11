package net.therap.entity;

/**
 * @author amimul.ehsan
 * @since 04/08/2022
 */
public enum RoleEnum {

    ADMIN("admin"),
    DOCTOR("doctor"),
    PATIENT("patient"),
    RECEPTIONIST("receptionist");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
