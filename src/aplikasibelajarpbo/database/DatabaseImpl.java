/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibelajarpbo.database;

import aplikasibelajarpbo.loginRegister.Login;
import aplikasibelajarpbo.loginRegister.Registrasi;
import aplikasibelajarpbo.model.Materi;
import aplikasibelajarpbo.model.Member;
import aplikasibelajarpbo.model.Ujian;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseImpl extends Database {
    private PreparedStatement st;
    private ResultSet rs;
   
   

    @Override
    public String insert(String username, String pass, String email, String nohp, String date, String kota, String gender, String imagePath) {
         
           String registrasiQuery = "INSERT INTO `user`(`username`, `email`, `no_hp`, `tgl_lahir`, `password`, `kota`, `gender`, `photo`, `nilai`) VALUES (?,?,?,?,?,?,?,?,?)";

            try {
                st = (PreparedStatement) MyConnection.getConnection().prepareStatement(registrasiQuery);
                st.setString(1,username);
                st.setString(2,email);
                st.setString(3,nohp);
                st.setString(4,date);
                st.setString(5,pass);
                st.setString(6,kota);
                st.setString(7,gender);
                st.setString(9,String.valueOf(0));

                try {
                    if(imagePath != null){
                        InputStream image = new FileInputStream(new File(imagePath));
                        st.setBlob(8, image);
                    }else{
                        st.setNull(8, java.sql.Types.NULL);
                    }
                    if(st.executeUpdate()!= 0){
                        return "1";
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    private Member select(String id){
        String query = "SELECT * FROM `user` WHERE `id`= ?";
        try {
            st = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
            st.setString(1, id);
            
            rs = st.executeQuery();
            
            if (rs.next()){
                Member member = new Member();
                
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setEmail(rs.getString("email"));
                member.setNoTelp(rs.getString("no_hp"));
                member.setTtl(rs.getString("tgl_lahir"));
                member.setKota(rs.getString("kota"));
                member.setGender(rs.getString("gender"));
                member.setId(rs.getInt("id"));
                member.setPhoto(rs.getBytes("photo"));
                member.setNilai(rs.getInt("nilai"));
                return member;
                
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    @Override
    public Member select(String username, String password) {
        
        String query = "SELECT * FROM `user` WHERE `username`= ? AND `password`= ?";;
        try {
            st = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            rs = st.executeQuery();
            
            if (rs.next()){
                Member member = new Member();
                
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setEmail(rs.getString("email"));
                member.setNoTelp(rs.getString("no_hp"));
                member.setTtl(rs.getString("tgl_lahir"));
                member.setKota(rs.getString("kota"));
                member.setGender(rs.getString("gender"));
                member.setId(rs.getInt("id"));
                member.setPhoto(rs.getBytes("photo"));
                member.setNilai(rs.getInt("nilai"));
                return member;
                
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
//    @Override
//    public Materi select() {
//         String query = "SELECT * FROM `materi`";
//        try {
//            st = MyConnection.getConnection().prepareStatement(query);
//            rs = st.executeQuery();
//            
//            if (rs.next()){
//                Materi materi = new Materi();
//                
//                materi.setJudul(rs.getString("judul"));
//                materi.setPdf(rs.getBytes("pdf"));
//                return materi;
//                
//            }else{
//                return null;
//            }
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    

    @Override
    public Member update(String username, String pass, String email, String nohp, String date, String kota, String gender, String imagePath, String id) {
         
         String query = "UPDATE `user` SET `username`=?,`email`=?,`no_hp`=?,`tgl_lahir`=?,`password`=?,`kota`=?,`gender`=?,`photo`=? WHERE `id`=?";

            try {
                st = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
                st.setString(1,username);
                st.setString(2,email);
                st.setString(3,nohp);
                st.setString(4,date);
                st.setString(5,pass);
                st.setString(6,kota);
                st.setString(7,gender);
                st.setString(9, id);

                try {
                    if(imagePath != null){
                        InputStream image = new FileInputStream(new File(imagePath));
                        st.setBlob(8, image);
                    }else{
                        st.setNull(8, java.sql.Types.NULL);
                    }
                    if(st.executeUpdate()!= 0){
                        return select(id);
                    }else{
                        return null;
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
     public Member update(int id, int nilai){
         String query = "UPDATE `user` SET `nilai`=? WHERE `id`=?";

            try {
                st = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
                st.setString(1, String.valueOf(nilai));
                st.setString(2, String.valueOf(id));

                    if(st.executeUpdate()!= 0){
                        return select(String.valueOf(id));
                    }else{
                        return null;
                    }     

            } catch (SQLException ex) {
                Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
     }
    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Materi> select() {
        String query = "SELECT * FROM `materi`";
        ArrayList<Materi> listMateri = new ArrayList<Materi>();
        try {
            st = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
            rs = st.executeQuery();
            Materi materi;
            while(rs.next()){
                materi = new Materi();
                materi.setJudul(rs.getString("judul"));
                materi.setPath(rs.getString("path"));
                materi.setId(rs.getInt("id"));
                listMateri.add(materi);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        
        return listMateri;
    }

    @Override
    public ArrayList<Ujian> selectUjian() {
    String query = "SELECT * FROM `ujian`";
        ArrayList<Ujian> listUjian = new ArrayList<Ujian>();
        try {
            st = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
            rs = st.executeQuery();
            Ujian materi;
            while(rs.next()){
                materi = new Ujian();
                materi.setJudul(rs.getString("judul"));
                materi.setSoal(rs.getString("soal"));
                materi.setJ1(rs.getString("jwb1"));
                materi.setJ2(rs.getString("jwb2"));
                materi.setJ3(rs.getString("jwb3"));
                materi.setJ4(rs.getString("jwb4"));
                materi.setBenar(rs.getString("benar"));
                materi.setId(rs.getInt("id"));
                listUjian.add(materi);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        
        return listUjian;    }

    
}
