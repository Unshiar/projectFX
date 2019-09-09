package ch.sample.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private int id;

    @Column
    @Access(AccessType.PROPERTY)
    private LocalDate date;

    @Column
    @Access(AccessType.PROPERTY)
    private String userName;

    @Column
    @Access(AccessType.PROPERTY)
    private String cartridgeIn;

    @Column
    @Access(AccessType.PROPERTY)
    private String cartridgeOut;

    @Column
    @Access(AccessType.PROPERTY)
    private String printerNum;

    @Column
    @Access(AccessType.PROPERTY)
    private String defectName;

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCartridgeIn() {
        return cartridgeIn;
    }

    public void setCartridgeIn(String cartridgeIn) {
        this.cartridgeIn = cartridgeIn;
    }

    public String getCartridgeOut() {
        return cartridgeOut;
    }

    public void setCartridgeOut(String cartridgeOut) {
        this.cartridgeOut = cartridgeOut;
    }

    public String getPrinterNum() {
        return printerNum;
    }

    public void setPrinterNum(String printerNum) {
        this.printerNum = printerNum;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }
}
