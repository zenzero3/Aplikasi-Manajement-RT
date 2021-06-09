package sistem.Smarta.grandcikarangcity2.model;

public class iurannamadesa {
    private String idiuran,nama,nominal,tanggalstart;

    public void setIdiuran(String idiuran){this.idiuran = idiuran;}

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setNama(String deskripsi) {
        this.nama = deskripsi;
    }

    public void setTanggalstart(String tanggalstart){this.tanggalstart=tanggalstart;}

    public String getIdiuran(){return idiuran;}

    public String getNama() {
        return nama;
    }

    public String getNominal() {
        return nominal;
    }

    public String getTanggalstart(){return tanggalstart;}

}
