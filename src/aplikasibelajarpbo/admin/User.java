package aplikasibelajarpbo.admin;


class User {

    private String Nama;
    private String pass;
    private String username;
    private String alamat;
    private String no;

    public User(String Nama, String pass, String username, String alamat, String no) {
        this.Nama = Nama;
        this.pass = pass;
        this.username = username;
        this.alamat = alamat;
        this.no = no;
    }

    public String getNama() {
        return Nama;
    }

    public String getPass() {
        return pass;
    }

    public String getUsername() {
        return username;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo() {
        return no;
    }

    
}
