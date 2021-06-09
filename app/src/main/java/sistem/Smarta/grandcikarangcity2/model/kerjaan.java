package sistem.Smarta.grandcikarangcity2.model;

public class kerjaan {
    String id,nama;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public kerjaan(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
}
