package sistem.Smarta.grandcikarangcity2.model;

public class postal {
String postalid,postalname;

    public String getPostalid() {
        return postalid;
    }

    public void setPostalid(String postalid) {
        this.postalid = postalid;
    }

    public String getPostalname() {
        return postalname;
    }

    public void setPostalname(String postalname) {
        this.postalname = postalname;
    }

    public postal(String postalid, String postalname) {
        this.postalid = postalid;
        this.postalname = postalname;
    }
}
