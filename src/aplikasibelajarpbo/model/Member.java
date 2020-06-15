/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Temkookokkkipkpkllkplkikiopkoplates
 * and open the template in the editor.
 */
package aplikasibelajarpbo.model;

/**
 *
 * @author SWIFT 3
 */
public class Member extends Users{

    
    private String email;
    private String noTelp;
    private String kota;
    private String ttl;
    private String gender;
    private byte[] photo;
    private int id;
    private int nilai;

   

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    @Override
    public String getUsername() {
        return super.username;
    }

    @Override
    public void setUsername(String username) {
        super.username  = username;
    }

    @Override
    public String getPassword() {
        return super.password;
    }

    @Override
    public void setPassword(String password) {
        super.password  = password;
    }
    
    
}
