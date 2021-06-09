package sistem.Smarta.grandcikarangcity2.model;

public class iurannamawarga {
    private String namawarga,namaiuran,status;

    public void  setNamawarga(String namewarga){
        this.namawarga=namewarga;
    }

    public void  setNamaiuran(String naiuran){
        this.namaiuran=naiuran;
    }

    public void  setStatus(String status){
        this.status=status;
    }

    public String getNamawarga(){
        return namawarga;
    }

    public String getNamaiuran(){
        return namaiuran;
    }

    public String getStatus(){
        return status;
    }
}
