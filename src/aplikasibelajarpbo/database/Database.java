/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibelajarpbo.database;

import aplikasibelajarpbo.model.Materi;
import aplikasibelajarpbo.model.Member;
import aplikasibelajarpbo.model.Ujian;
import java.util.ArrayList;

/**
 *
 * @author SWIFT 3
 */
public abstract  class Database {

    public abstract String insert(String username, String pass, String email, String nohp, String date, String kota, String gender, String imagePath);
    public abstract Member select(String username, String password);
//    public abstract Materi select();
    public abstract Member update(String username, String pass, String email, String nohp, String date, String kota, String gender, String imagePath, String id);
    public abstract String delete();

    /**
     *
     * @return
     */
    public abstract ArrayList<Materi> select();
    public abstract ArrayList<Ujian> selectUjian();
    
}
