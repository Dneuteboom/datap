package Classes;

public class Adres {
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    public Reiziger reiziger;
    private int reiziger_id;


    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    public int getAdres_id() {
        return adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public Reiziger getReiziger(){
        return reiziger;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }
    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    @Override
    public String toString() {
        return "Adres{" +
                "adres_id=" + adres_id +
                ", huisnummer='" + huisnummer + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", reiziger_id=" + reiziger_id +
                ", reiziger=" + reiziger +
                '}';
    }
}
