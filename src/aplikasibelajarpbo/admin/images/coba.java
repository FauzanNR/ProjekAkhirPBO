/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibelajarpbo.admin.images;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SWIFT 3
 */
public class coba {
    public static void main(String[] args) {
        
        ClassLoader loader = coba.class.getClassLoader();
        System.out.println(loader.getResource("images/"));
        String oo = loader.getResource("images/").getPath();
        System.out.println(oo);
        String path = "/media/unbroken/A84E423F4E420712/KULIAH/SMESTER_4/PBO/tugas/SUBMISSION1.pdf";
        Path p = Paths.get(path);
        String fileName = p.getFileName().toString();
        System.out.println(fileName);
        String loc = oo+fileName;
        System.out.println(loc);
        File i = new File(path);
        File o = new File(loc);
        try {
            Files.copy(i.toPath(), o.toPath());
        } catch (IOException ex) {
            Logger.getLogger(coba.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
