package sistem.Smarta.grandcikarangcity2.model;

public class kelurahan {
    String kelurid,kelurname;

    public String getKelurid() {
        return kelurid;
    }

    public void setKelurid(String kelurid) {
        this.kelurid = kelurid;
    }

    public String getKelurname() {
        return kelurname;
    }

    public void setKelurname(String kelurname) {
        this.kelurname = kelurname;
    }

    public kelurahan(String kelurid, String kelurname) {
        this.kelurid = kelurid;
        this.kelurname = kelurname;
    }
}
