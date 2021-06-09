package sistem.Smarta.grandcikarangcity2.model;

public class suratwarga {
    public String pid,pname,niko;
    public suratwarga(String pid, String pname,String nik){
        this.pid=pid;
        this.pname=pname;
        this.niko = nik;
    }

    public String getPid() {
        return pid;
    }
    public String getNiko(){return niko;}
    public String getPname() {
        return pname;
    }
}
