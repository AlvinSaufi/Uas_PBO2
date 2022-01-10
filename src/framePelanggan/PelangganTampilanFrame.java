package framePelanggan;

import db.koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.management.Query;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Pelanggan;

public class PelangganTampilanFrame extends JFrame{
    
    JLabel jLabel = new JLabel("Cari");
    JTextField eCari = new JTextField();
    JButton bCari = new JButton("Cari");
    
    String header [] = {"id","Pelanggan"};
    TableModel tableModel = new DefaultTableModel(header, 0);
    JTable tPelanggan = new JTable(tableModel);
    JScrollPane jScrollPane = new JScrollPane(tPelanggan);
    
    JButton bTambah = new JButton("Tambah");
    JButton bUbah = new JButton("Ubah");
    JButton bHapus = new JButton("Hapus");
    JButton bBatal = new JButton("Batal");
    JButton bTutup = new JButton("Tutup");
    
    Pelanggan pelanggan;
    

public void setKomponen(){
    getContentPane().setLayout(null);
    getContentPane().add(jLabel);
    getContentPane().add(eCari);
    getContentPane().add(jScrollPane);
    getContentPane().add(bCari);
    getContentPane().add(bTambah);
    getContentPane().add(bUbah);
    getContentPane().add(bHapus);
    getContentPane().add(bBatal);
    getContentPane().add(bTutup);
    
    jLabel.setBounds(10,10,50,25);
    eCari.setBounds(60,10,330,25);
    bCari.setBounds(400,10,70,25);
    bTutup.setBounds(400,220,70,25);
    bTambah.setBounds(10,220,80,25);
    bUbah.setBounds(95,220,70,25);
    bHapus.setBounds(170,220,70,25);
    bBatal.setBounds(245,220,70,25);
    jScrollPane.setBounds(10,45,460,160);
    
    resetTable("");
    setListener();
    setVisible(true);
       
}

public PelangganTampilanFrame(){
    setSize(500,300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setKomponen();
}

    public static void main(String[] args) {
        PelangganTampilanFrame pelangganTampilanFrame = new PelangganTampilanFrame();
    }
    
public ArrayList<Pelanggan> getPelangganList(String keyword){
    ArrayList<Pelanggan> pelangganList = new ArrayList<Pelanggan>();
    koneksi Koneksi = new koneksi();
    Connection connetion = Koneksi.getConnection();
    
    String query = "SELECT * FROM pelanggan "+keyword;
    Statement statement;
    ResultSet resultSet;
    
    try {
        statement = connetion.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            pelanggan = new Pelanggan(resultSet.getInt("id"),
                    resultSet.getString("pelanggan"));
            pelangganList.add(pelanggan);
        }
    } catch (SQLException | NullPointerException ex){
        System.err.println("Koneksi Null Gagal");
    }
    return pelangganList;
} 

public final void selectPelanggan(String keyword){
    ArrayList<Pelanggan> list = getPelangganList(keyword);
    DefaultTableModel model = (DefaultTableModel)tPelanggan.getModel();
    Object[] row = new Object[2];
    
    for (int i = 0; i < list.size(); i++){
        row[0] = list.get(i).getId();
        row[1] = list.get(i).getPelanggan();
        
        model.addRow(row);
    }
}

public final void resetTable(String keyword){
    DefaultTableModel model = (DefaultTableModel)tPelanggan.getModel();
    model.setRowCount(0);
    selectPelanggan(keyword);
}

public void setListener(){
    bTutup.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();        }
    });
    
    bCari.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            resetTable(" WHERE pelanggan like '%"+eCari.getText()+"%'");
        }   
    });

    bBatal.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            resetTable("");
        }
    });
    
    bHapus.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        int i = tPelanggan.getSelectedRow();
        int pilihan = JOptionPane.showConfirmDialog(null,
                       "yakin mau hapus ?",
                       "konfirmasi hapus",
                       JOptionPane.YES_NO_OPTION);
        if(pilihan==0){
            if(i>=0){
            try {
                TableModel model = tPelanggan.getModel();
                koneksi Koneksi = new koneksi();
                Connection con = Koneksi.getConnection();
                String executeQuery = "delete from pelanggan where id =?";
                PreparedStatement ps = con.prepareStatement(executeQuery);
                ps.setString(1, model.getValueAt(i,0).toString());
                ps.executeUpdate();
            } catch (SQLException ex){
                System.err.println(ex);
            }
            }else{
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus");      
            }
        }
        resetTable("");
        }
    });
    
    bUbah.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = tPelanggan.getSelectedRow();
            if (i>=0){
                TableModel model = tPelanggan.getModel();
                pelanggan = new Pelanggan();
                pelanggan.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                pelanggan.setPelanggan(model.getValueAt(i, 1).toString());
                PelangganTambahFrame pelangganTambahFrame = new PelangganTambahFrame(pelanggan);
            }else{
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah");
            }
        }
    });
    
    bTambah.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            PelangganTambahFrame pelangganTambahFrame= new PelangganTambahFrame();
        }
    });
    
    addWindowListener(new java.awt.event.WindowAdapter(){
        public void windowActivated(java.awt.event.WindowEvent evt){
            resetTable("");
        }
    });
}

}