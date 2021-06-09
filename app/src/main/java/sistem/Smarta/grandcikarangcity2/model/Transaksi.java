package sistem.Smarta.grandcikarangcity2.model;

public class Transaksi {
    String id,idorder,namatransaksi,nominal,status,tanggal;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdorder(String idorder) {
        this.idorder = idorder;
    }

    public void setNamatransaksi(String namatransaksi) {
        this.namatransaksi = namatransaksi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getNominal() {
        return nominal;
    }

    public String getId() {
        return this.id;
    }

    public String getIdorder() {
        return idorder;
    }

    public String getNamatransaksi() {
        return namatransaksi;
    }

    public String getTanggal() {
        return tanggal;
    }
}
