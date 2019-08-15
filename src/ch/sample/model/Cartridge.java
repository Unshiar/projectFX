package ch.sample.model;

import javax.persistence.*;

@Entity
@Table(name = "Cartridges")
public class Cartridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private int id;//порядковый номер

    @Column
    @Access(AccessType.PROPERTY)
    private String number;//инв. номер картриджа

    @Column
    @Access(AccessType.PROPERTY)
    private String model;//модель картриджа

    public Cartridge() {
    }

    public Cartridge(String number, String model) {
        this.number = number;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
