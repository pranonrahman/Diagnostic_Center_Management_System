package net.therap.dms.entity;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    Gender(String displayName) {
        this.displayName = displayName;
    }

    private final String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
