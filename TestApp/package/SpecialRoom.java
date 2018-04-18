/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Patrick
 */

@Named(value = "specialRoom")
@ManagedBean
@SessionScoped

public class SpecialRoom implements Serializable {
    
    private String specialRateID, roomID;
    private double rate;
    private String bookDate;
    
    private DBConnect dbConnect = new DBConnect();

    public String getSpecialRateID() {
        return specialRateID;
    }

    public void setSpecialRateID(String roomID) {
        this.specialRateID = specialRateID;
    }
    
    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
    
    public double getRate(){
        return rate;
    }
    
    public void setRate(int rate){
        this.rate = rate;
    }
 
    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
    
    public String createSpecialRate() throws SQLException {
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement createRate = con.prepareStatement(
            "INSERT INTO customers VALUES(?,?,?)");
        
        createRate.setString(1, specialRateID);
        createRate.setString(2, roomID);
        createRate.setDouble(3, rate);
        createRate.setString(4, bookDate);

        createRate.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        return "index";
    }
    
}
