package Classes;

import java.sql.Date;
import java.util.ArrayList;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private Adres reizigerAdres;
    private ArrayList<OVChipkaart> ovKaarten = new ArrayList<>();


    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Adres getReizigerAdres() {
        return reizigerAdres;
    }

    public void setReizigerAdres(Adres reizigerAdres) {
        this.reizigerAdres = reizigerAdres;
    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }


    public void setOvchipkaarten(ArrayList<OVChipkaart> ovchipkaarten) {
        this.ovKaarten = ovchipkaarten;
    }

    public ArrayList<OVChipkaart> getOvchipkaarten() {
        return ovKaarten;
    }

    public void addOVChipkaart(OVChipkaart ovchipkaart) {
        if (!ovKaarten.contains(ovchipkaart)) ovKaarten.add(ovchipkaart);
    }


    @Override
    public String toString() {
        return "Reiziger{" +
                "id=" + id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", reizigerAdres=" + reizigerAdres +
                ", ovKaarten=" + ovKaarten +
                '}';
    }
}

