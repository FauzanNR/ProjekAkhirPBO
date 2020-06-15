/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibelajarpbo.database;

/**
 *
 * @author SWIFT 3
 */

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SWIFT 3
 */
public class MyConnection {
    
    private static final String serverName = "localhost";
    private static final String user = "root";
    private static final String dbName = "belajar_db";
    private static final Integer port = 3306;
    private static final String password = "";
     
    public static Connection getConnection(){
        Connection conn = null;
        
        
        try {
           Class.forName("com.mysql.jdbc.Driver");
            conn = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost/belajar_db", "root","");
        } catch (SQLException ex) {
            Logger.getLogger("get Connection"+MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }
    
    
    
}

