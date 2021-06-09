package sistem.Smarta.grandcikarangcity2.model;

public class kecamatan {
    String kecid,kecname;

    public String getKecid() {
        return kecid;
    }

    public void setKecid(String kecid) {
        this.kecid = kecid;
    }

    public String getKecname() {
        return kecname;
    }

    public void setKecname(String kecname) {
        this.kecname = kecname;
    }

    public kecamatan(String kecid, String kecname) {
        this.kecid = kecid;
        this.kecname = kecname;
    }
}
