package aplikasibelajarpbo.admin;


import aplikasibelajarpbo.database.MyConnection;
import aplikasibelajarpbo.model.Member;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author unbroken
 */
public class Admin extends javax.swing.JFrame {

    private CardLayout card;
    private final JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem  menuItem = null;
    private String filePath;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet set = null;
    private DefaultTableModel tableModel;
    private boolean edit;
    Statement st;
    
    public Admin() {
        initComponents();
        card = (CardLayout)(PanelCard.getLayout());
        showDataUser();
        showDataMOdul();
        ClickPopUpMenu(this);
        CencelBtn.setVisible(false);

    }
public ArrayList<Member> accountUser(){
        ArrayList<Member> account = new ArrayList<>();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/belajar_db", "root","");
            ps = (PreparedStatement) connection.prepareStatement("SELECT * FROM `user` ORDER BY `user`.`id` ASC");
            set = ps.executeQuery();
            
            Member mem ;            
            while(set.next()){
               
                mem = new Member();
                
                mem.setId(set.getInt("id"));
                mem.setUsername(set.getString("username"));
                mem.setPassword(set.getString("password"));
                mem.setTtl(set.getString("tgl_lahir"));
                mem.setEmail(set.getString("email"));
                mem.setGender(set.getString("gender"));
                mem.setKota(set.getString("kota"));
                mem.setNoTelp(set.getString("no_hp"));
                mem.setNilai(set.getInt("nilai"));
                mem.setPhoto(set.getBytes("photo"));
                account.add(mem);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        return account;
    }
    
    private void showDataUser(){
        ArrayList<Member> listData = accountUser();
        System.out.println("lisdata "+listData.size());
        DefaultTableModel tableModel = (DefaultTableModel) TabelUser.getModel();
        Object[] rowObjects  = new Object[10];
        
        for(int i = 0; i<listData.size();i++){
            
            rowObjects[0] = listData.get(i).getId();
            rowObjects[1] = listData.get(i).getUsername();
            rowObjects[2] = listData.get(i).getPassword();
            rowObjects[3] = listData.get(i).getKota();
            rowObjects[4] = listData.get(i).getNoTelp();
            rowObjects[5] = listData.get(i).getEmail();
            rowObjects[6] = listData.get(i).getTtl();
            rowObjects[7] = listData.get(i).getGender();
            rowObjects[8] = listData.get(i).getPhoto();
            rowObjects[9] = listData.get(i).getNilai();
            
           
            tableModel.insertRow(i, rowObjects);//.addRow(rowObjects);
        }
    }
    
    public ArrayList<ModulClass> SelectmodulClass(){
        ArrayList<ModulClass> modul = new ArrayList<>();
             
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/belajar_db", "root","");
            ps = (PreparedStatement) connection.prepareStatement("SELECT * FROM `materi` ORDER BY `materi`.`id` ASC");
            set = ps.executeQuery();
            
            ModulClass iniModul;
            while(set.next()){
                iniModul = new ModulClass(set.getString("Id"),set.getString("Judul"),set.getString("tanggal"),set.getString("path"));
                
                modul.add(iniModul);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        return modul;
    }
    
    private void showDataMOdul(){
        ArrayList<ModulClass> listModul = SelectmodulClass();
        
        tableModel = (DefaultTableModel) ModulTable.getModel();
        if (listModul.size() == 0) {
            Object[] rowObjects  = new Object[4];
            for(int i = 0; i<listModul.size();i++){
            
                rowObjects[0] = listModul.get(i).getId();
                rowObjects[1] = listModul.get(i).getNama();
                rowObjects[2] = listModul.get(i).getTanggal();
                rowObjects[3] = listModul.get(i).getPath();
                tableModel.insertRow(i, rowObjects);//.addRow(rowObjects);
            }
        }else{
            ModulTable.setModel(new DefaultTableModel(null, new String []{"Id","Nama","Tanggal","Path"}));
            tableModel = (DefaultTableModel) ModulTable.getModel();
            Object[] rowObjects  = new Object[4];
            
            for(int i = 0; i<listModul.size();i++){
                
                rowObjects[0] = listModul.get(i).getId();
                rowObjects[1] = listModul.get(i).getNama();
                rowObjects[2] = listModul.get(i).getTanggal();
                rowObjects[3] = listModul.get(i).getPath();
                tableModel.insertRow(i, rowObjects);//.addRow(rowObjects);
            }
            
        }
        
        
        
    }
    
    private void ClickPopUpMenu(JFrame jFrame){
        menuItem = new JMenuItem("Edit" ,new ImageIcon("/images/edit2.png"));
        menuItem.getAccessibleContext().setAccessibleDescription("Edit");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indek = ModulTable.getSelectedRow();
                TableModel model = ModulTable.getModel();
                String id = model.getValueAt(indek, 0).toString();
                String nama = model.getValueAt(indek, 1).toString();
                String path = model.getValueAt(indek, 3).toString();
                IdTxt.setText(id);
                InsertFile.setText(path);
                InsertNamaModul.setText(nama);
                edit = true;
                CencelBtn.setVisible(true);
            }
        });
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Hapus" ,new ImageIcon("/images/delete21.png"));
        menuItem.getAccessibleContext().setAccessibleDescription("Hapus");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 int indek = ModulTable.getSelectedRow();
                TableModel model = ModulTable.getModel();
                DeleteModul(model.getValueAt(indek, 0).toString());
            }
        });
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Refresh" ,new ImageIcon("/images/refresh2.png"));
        menuItem.getAccessibleContext().setAccessibleDescription("Refresh");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDataMOdul();
            }
        });
        popupMenu.add(menuItem);
    }
    
    private void InsertModul(boolean edit){
        
        if(edit){
            System.out.println("masuk edit"+edit);
             try{
                 st = MyConnection.getConnection().createStatement();
                  File f = new File(InsertFile.getText());
                         if(f.exists()){
                             st.executeUpdate("UPDATE `materi` SET `Judul` = '"+InsertNamaModul.getText()+"', `path` = '"+InsertFile.getText()+"' WHERE `materi`.`id` = "+IdTxt.getText());
                             JOptionPane.showMessageDialog(rootPane, "Edit Success !!!");
                         }else{
                             JOptionPane.showMessageDialog(rootPane, "You should insert file first !!!");
                         }
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e);
             }
             this.edit = false;
             CencelBtn.setVisible(this.edit);
        }else{
            System.out.println("masuk insert"+edit);
            try{
                 st = MyConnection.getConnection().createStatement();
                  File f = new File(InsertFile.getText());
                         if(f.exists()){
                             st.executeUpdate("INSERT INTO `materi` (`Judul`, `tanggal`, `path`) VALUES ( '"+InsertNamaModul.getText()+"', CURRENT_DATE(), '"+InsertFile.getText()+"');");
                             JOptionPane.showMessageDialog(rootPane, "Insert Success !!!");
                         }else{
                             JOptionPane.showMessageDialog(rootPane, "You should insert file first !!!");
                         }
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e);
             }    
        }
        showDataMOdul();
        InsertNamaModul.setText("");
        InsertFile.setText("");
        IdTxt.setText("");
    }
    
    private void DeleteModul(String a){
        
        int respone = JOptionPane.showConfirmDialog(this, 
                "Continue delete ???", 
                "Delete Modul", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
        
        if(respone == JOptionPane.YES_OPTION){
            try{
                st = MyConnection.getConnection().createStatement();
                st.executeUpdate("DELETE FROM `materi` WHERE `materi`.`id` ="+a);
                JOptionPane.showMessageDialog(rootPane, "Delete Success !!!");
                 
             }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                JOptionPane.showMessageDialog(rootPane, "Delete failed !!!");
             }    
        }
         showDataMOdul();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel1 = new javax.swing.JPanel();
        PanelSideBar = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        LabelModul = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        LabelUser = new javax.swing.JLabel();
        PanelCard = new javax.swing.JPanel();
        card1 = new javax.swing.JPanel();
        InsertNamaModul = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        InsertFile = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ButtonSave = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ModulTable = new javax.swing.JTable();
        ButtonOpen = new javax.swing.JButton();
        IdTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        CencelBtn = new javax.swing.JButton();
        card2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelUser = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin");

        Panel1.setBackground(new java.awt.Color(204, 255, 204));
        Panel1.setName("panelUtama"); // NOI18N
        Panel1.setPreferredSize(new java.awt.Dimension(2000, 1125));

        PanelSideBar.setBackground(new java.awt.Color(255, 102, 0));
        PanelSideBar.setFocusCycleRoot(true);

        jLabel3.setFont(new java.awt.Font("DejaVu Math TeX Gyre", 1, 24)); // NOI18N
        jLabel3.setText("Admin");

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));

        LabelModul.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        LabelModul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasibelajarpbo/admin/images/book2.png"))); // NOI18N
        LabelModul.setText("Modul");
        LabelModul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelModulMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelModul, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelModul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        LabelUser.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        LabelUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasibelajarpbo/admin/images/account2.png"))); // NOI18N
        LabelUser.setText("User");
        LabelUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelUser, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(LabelUser, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelSideBarLayout = new javax.swing.GroupLayout(PanelSideBar);
        PanelSideBar.setLayout(PanelSideBarLayout);
        PanelSideBarLayout.setHorizontalGroup(
            PanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSideBarLayout.createSequentialGroup()
                .addGroup(PanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSideBarLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSideBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSideBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelSideBarLayout.setVerticalGroup(
            PanelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSideBarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelCard.setName("CardPanel"); // NOI18N
        PanelCard.setLayout(new java.awt.CardLayout());

        card1.setBackground(new java.awt.Color(102, 102, 102));
        card1.setName(""); // NOI18N

        InsertNamaModul.setToolTipText("");
        InsertNamaModul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertNamaModulActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pilih FIle");

        InsertFile.setToolTipText("");
        InsertFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertFileActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Modul");

        ButtonSave.setText("Save");
        ButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSaveActionPerformed(evt);
            }
        });

        ModulTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Judul", "Tanggal", "Path"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ModulTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ModulTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(ModulTable);

        ButtonOpen.setText("open");
        ButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonOpenActionPerformed(evt);
            }
        });

        IdTxt.setEditable(false);
        IdTxt.setText("id");
        IdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdTxtActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID");

        CencelBtn.setText("cencel");
        CencelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CencelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addComponent(InsertFile, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonOpen))
                    .addComponent(InsertNamaModul, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addComponent(ButtonSave)
                        .addGap(58, 58, 58)
                        .addComponent(CencelBtn))
                    .addGroup(card1Layout.createSequentialGroup()
                        .addComponent(IdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addContainerGap(626, Short.MAX_VALUE))
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InsertNamaModul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonOpen))
                .addGap(37, 37, 37)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSave)
                    .addComponent(CencelBtn))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1125, Short.MAX_VALUE)
        );

        PanelCard.add(card1, "card1");

        card2.setBackground(new java.awt.Color(102, 102, 102));
        card2.setName(""); // NOI18N

        TabelUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Username", "Password", "Alamat", "Kontak", "email", "tgl_lahir", "gender", "poto", "nilai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelUser);

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(564, Short.MAX_VALUE))
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelCard.add(card2, "card2");

        javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
        Panel1.setLayout(Panel1Layout);
        Panel1Layout.setHorizontalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createSequentialGroup()
                .addComponent(PanelSideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PanelCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel1Layout.setVerticalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelSideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LabelModulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelModulMouseClicked
        card.show(PanelCard, "card1");
    }//GEN-LAST:event_LabelModulMouseClicked

    private void LabelUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelUserMouseClicked
        card.show(PanelCard, "card2");
    }//GEN-LAST:event_LabelUserMouseClicked

    private void InsertNamaModulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertNamaModulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InsertNamaModulActionPerformed

    private void ButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSaveActionPerformed
/*
                try{
                    File f = new File(InsertFile.getText());
                    if(f.exists()){
                        if(Desktop.isDesktopSupported()){
                            Desktop.getDesktop().open(f);
                        }else{
                            JOptionPane.showMessageDialog(rootPane, "Unsupported file");
                        }
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "You should insert file first !!!");
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(rootPane, e);
                }*/
        InsertModul(edit);
    }//GEN-LAST:event_ButtonSaveActionPerformed

    private void ModulTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ModulTableMouseClicked
        
        if (evt.getModifiers() == MouseEvent.BUTTON3_MASK){  
            int Xpos = MouseInfo.getPointerInfo().getLocation().x;
            int Ypos = MouseInfo.getPointerInfo().getLocation().y;
            popupMenu.show(this, Xpos, Ypos);
        }

    }//GEN-LAST:event_ModulTableMouseClicked

    private void InsertFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InsertFileActionPerformed

    private void ButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonOpenActionPerformed
FileNameExtensionFilter extension = new FileNameExtensionFilter("PDF", "pdf");
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        chooser.addChoosableFileFilter(extension);
        
        try{
            File f = chooser.getSelectedFile();
            if(f != null){
               filePath = f.getAbsolutePath();
               filePath = filePath.replace('\\', '/');
               InsertFile.setText(filePath);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }//GEN-LAST:event_ButtonOpenActionPerformed

    private void IdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdTxtActionPerformed

    private void CencelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CencelBtnActionPerformed
        InsertNamaModul.setText("");
        InsertFile.setText("");
        IdTxt.setText("");
        CencelBtn.setVisible(false);
        edit = false;
    }//GEN-LAST:event_CencelBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonOpen;
    private javax.swing.JButton ButtonSave;
    private javax.swing.JButton CencelBtn;
    private javax.swing.JTextField IdTxt;
    private javax.swing.JTextField InsertFile;
    private javax.swing.JTextField InsertNamaModul;
    private javax.swing.JLabel LabelModul;
    private javax.swing.JLabel LabelUser;
    private javax.swing.JTable ModulTable;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel PanelCard;
    private javax.swing.JPanel PanelSideBar;
    private javax.swing.JTable TabelUser;
    private javax.swing.JPanel card1;
    private javax.swing.JPanel card2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
