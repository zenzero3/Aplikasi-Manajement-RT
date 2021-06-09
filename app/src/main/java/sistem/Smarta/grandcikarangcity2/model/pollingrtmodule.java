package sistem.Smarta.grandcikarangcity2.model;

public class pollingrtmodule {
    private String namapolling,deskripsipolling,image;

    public void setNamapolling(String namapolling){
        this.namapolling=namapolling;
    }
    public void setDeskripsipolling(String deskripsi){
        this.deskripsipolling=deskripsi;
    }
    public void setImage(String image){
        this.image=image;
    }
    public String getNamapolling(){
        return namapolling;
    }
    public String getDeskripsipolling(){
        return deskripsipolling;
    }
    public String getImage(){
        return image;
    }
}