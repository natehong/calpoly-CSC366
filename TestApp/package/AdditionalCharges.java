/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrick
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Patrick
 */

@Named(value = "additionalCharges")
@ManagedBean
@SessionScoped

public class AdditionalCharges implements Serializable {
    private DBConnect dbConnect = new DBConnect();
    private int invoiceCode, reservationCode;
    private String chargeDate;
    private int chargeCode, quantity;
    
    private Item item;
    
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    public int getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(int invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
    public int getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }
    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }
    public int getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(int chargeCode) {
        this.chargeCode = chargeCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void addCharge() throws SQLException {
        int ID = 1;
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT invoice_code FROM additional_charges_invoices ORDER BY invoice_code DESC");
        
        ResultSet rsID = getID.executeQuery();

        ID = (rsID.next()) ? rsID.getInt("invoice_code") + 1 : 1;
        
        PreparedStatement addCharge = con.prepareStatement(
            "INSERT INTO additional_charges_invoices VALUES (?,?,?,?,?)");
        
        
        addCharge.setInt(1, ID);
        addCharge.setInt(2, reservationCode);
        addCharge.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        addCharge.setInt(4, chargeCode);
        addCharge.setInt(5, quantity);
        
        addCharge.executeUpdate();
        statement.close();
        con.commit();
        con.close();
    }
    
    public void createCharge() throws SQLException {
        int ID = 1;
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT invoice_code FROM additional_charges_invoices ORDER BY invoice_code DESC");
        
        ResultSet rsID = getID.executeQuery();

        ID = (rsID.next()) ? rsID.getInt("invoice_code") + 1 : 1;
        
        PreparedStatement addCharge = con.prepareStatement(
            "INSERT INTO additional_charges_invoices VALUES (?,?,?,?,?)");
        
        
        addCharge.setInt(1, ID);
        addCharge.setInt(2, reservationCode);
        addCharge.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        addCharge.setInt(4, chargeCode);
        addCharge.setInt(5, quantity);
        
        addCharge.executeUpdate();
        statement.close();
        con.commit();
        con.close();
    }
    
}
