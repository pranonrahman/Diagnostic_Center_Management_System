package net.therap.model;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Speciality extends BaseEntity {

    private String name;

    public Speciality() {
    }

    public Speciality(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
