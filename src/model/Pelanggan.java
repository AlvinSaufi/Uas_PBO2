package model;

import db.koneksi;

public class Pelanggan {
    private int id;
    private String pelanggan;
    
    public Pelanggan(){
        
    }
    
    public Pelanggan(int id, String pelanggan){
        this.id = id;
        this.pelanggan = pelanggan;
    }

    public Pelanggan(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getPelanggan(){
        return pelanggan;
    } 
    
    public void setPelanggan(String penerbit){
        this.pelanggan = pelanggan;
    }
}
