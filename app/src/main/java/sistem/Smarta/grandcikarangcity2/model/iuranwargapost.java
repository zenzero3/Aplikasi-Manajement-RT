package sistem.Smarta.grandcikarangcity2.model;

public class iuranwargapost {
    private String id,namawarga,namaiuran,status,nominal;

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void  setNamawarga(String namewarga){
        this.namawarga=namewarga;
    }

    public void  setNamaiuran(String naiuran){
        this.namaiuran=naiuran;
    }

    public void  setStatus(String status){
        this.status=status;
    }

    public String getNominal() {
        return nominal;
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

    public String getId() {
        return id;
    }
}
