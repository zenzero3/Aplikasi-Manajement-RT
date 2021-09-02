package sistem.Smarta.grandcikarangcity2.model;

public class suratwarga {
    public String pid,pname,niko,kk;
    public suratwarga(String pid, String pname,String nik,String kk){
        this.pid=pid;
        this.kk =kk;
        this.pname=pname;
        this.niko = nik;
    }

    public String getKk() {
        return kk;
    }

    public String getPid() {
        return pid;
    }
    public String getNiko(){return niko;}
    public String getPname() {
        return pname;
    }
}
