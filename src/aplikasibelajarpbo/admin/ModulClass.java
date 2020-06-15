package aplikasibelajarpbo.admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author unbroken
 */
class ModulClass {
    private String id, nama, tanggal, path;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getPath() {
        return path;
    }

    public ModulClass(String id, String nama, String tanggal, String path) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.path = path;
    }
}
