package net.therap.dms.entity;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
