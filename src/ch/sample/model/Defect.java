package ch.sample.model;

import javax.persistence.*;

@Entity
@Table(name = "defects")
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private int id;//порядковый номер

    @Column
    @Access(AccessType.PROPERTY)
    private String name;//название дефекта

    public Defect() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
