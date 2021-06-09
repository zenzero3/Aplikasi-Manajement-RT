package sistem.Smarta.grandcikarangcity2.model;

public class kabupaten {
    String cityid,cityname;

    public kabupaten(String cityid, String cityname) {
        this.cityid = cityid;
        this.cityname = cityname;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
