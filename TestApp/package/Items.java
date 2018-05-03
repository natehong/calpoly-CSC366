/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.el.ELContext;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Patrick
 */

@Named(value = "items")
@ManagedBean
@SessionScoped

public class Items implements Serializable {
    private int chargeCode;
    private String description;
    private int cost;
    private int ind;
    private boolean itemCreated = false;
    private int reservationID;

    
    private DBConnect dbConnect = new DBConnect();
    
    private List<Item> items;
    private List<Item> invoice;


    private List<Integer> listIndex;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
    
    public int getChargeCode(){
        return chargeCode;
    }

    public void setChargeCode(int chargeCode){
        this.chargeCode = chargeCode;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
    }
    
    public boolean isItemCreated() {
        return itemCreated;
    }

    public void setItemCreated(boolean itemCreated) {
        this.itemCreated = itemCreated;
    }
    
    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }
    
    public List<Item> getItems() throws SQLException {
        findItems();
        return items;
    }
    
    public List<Integer> getListIndex() {
        return listIndex;
    }

    public void setListIndex(List<Integer> listIndex) {
        this.listIndex = listIndex;
    }
    
    public List<Item> getInvoice() throws SQLException {
        billItem();
        return invoice;
    }

    public void setInvoice(List<Item> invoice) {
        this.invoice = invoice;
    }
    
    public int getResID() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Reservations res = (Reservations) elContext.getELResolver().getValue(elContext, null, "reservations");
        return res.getReservationID();
    }
    
    public void billItem() throws SQLException {
        int index = 0;
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        con.setAutoCommit(false);
        PreparedStatement findRes = con.prepareStatement(
            "SELECT *\n" +
            "FROM additional_charges_invoices\n" +
            "INNER JOIN additional_charges ON charge = charge_code\n" +
            "WHERE reservation = ?\n"
//            "AND current_date >= charge_date;"
        );    
        findRes.setInt(1, getResID());

        ResultSet rs = findRes.executeQuery();

        invoice = new ArrayList<>();
        while(rs.next())
        {
            int quantity = rs.getInt("quantity");
            double total_cost = rs.getDouble("cost");

            invoice.add(new Item(rs.getInt("charge_code"), rs.getString("description"), total_cost, quantity, index));
            index++;
        }

    }
    
    public String findItems() throws SQLException {
        int index = 0;
            
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        con.setAutoCommit(false);
        PreparedStatement findRes = con.prepareStatement(
            "SELECT *\n" +
            "FROM additional_charges\n" +
            "ORDER BY charge_code;"
            );    

        ResultSet rs = findRes.executeQuery();
        items = new ArrayList<>();
        listIndex = new ArrayList<>();
        while(rs.next())
        {
            items.add(new Item(rs.getInt("charge_code"), rs.getString("description"), rs.getDouble("cost"), 0, index));
            listIndex.add(index);
            index++;
        }
        return "newCharge";
    }
    
    public void createItem() throws SQLException {
        int ID;
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT charge_code FROM additional_charges ORDER BY charge_code DESC");
        
        ResultSet rsID = getID.executeQuery();

        ID = (rsID.next()) ? rsID.getInt("charge_code") + 1 : 1;
        
        PreparedStatement createEmp = con.prepareStatement(
            "INSERT INTO additional_charges VALUES(?,?,?)");
        
        createEmp.setInt(1, ID);
        createEmp.setString(2, description);
        createEmp.setInt(3, cost);
        
        createEmp.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        description = "";
        cost = 0;
        itemCreated = true;
    }
}
