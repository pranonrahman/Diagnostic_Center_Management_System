package net.therap.model;

import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
public class Role extends BaseEntity{

    private String name;
    private List<Person> personList;

    public Role() {
    }

    public Role(Long id, String name, List<Person> personList) {
        super(id);
        this.name = name;
        this.personList = personList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
