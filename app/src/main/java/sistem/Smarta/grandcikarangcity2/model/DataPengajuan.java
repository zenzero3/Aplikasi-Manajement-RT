package sistem.Smarta.grandcikarangcity2.model;

public class DataPengajuan {
    private String namasurat,namawarga,status,id;

    public void setId(String id) {
        this.id = id;
    }
    public void setNamasurat(String namasurat){
        this.namasurat=namasurat;
    }
    public void setNamawarga(String namawarga){
        this.namawarga=namawarga;
    }
    public void setStatus(String status){
        this.status=status;
    }
public String getNamasurat(){
        return namasurat;
}
    public String getId() {
        return id;
    }

    public String getNamawarga() {
        return namawarga;
    }

    public String getStatus() {
        return status;
    }
}

