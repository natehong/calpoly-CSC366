/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    private int roomID;
    private double rate;
    private Date bookDate;
    
    private DBConnect dbConnect = new DBConnect();
    
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    
    public double getRate(){
        return rate;
    }
    
    public void setRate(double rate){
        this.rate = rate;
    }
 
    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }
    
    public String createSpecialRate() throws SQLException {
        int ID;
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT special_rate_code FROM special_room_rates ORDER BY special_rate_code DESC");
            
        ResultSet rsID = getID.executeQuery();
        ID = (rsID.next()) ? rsID.getInt("special_rate_code") + 1 : 1;
        
        PreparedStatement createRate = con.prepareStatement(
            "INSERT INTO special_room_rates VALUES(?,?,?,?)");
        
        createRate.setInt(1, ID);
        createRate.setInt(2, roomID);
        createRate.setDate(3, new java.sql.Date(bookDate.getTime()));
        createRate.setDouble(4, rate);

        createRate.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        return "index";
    }
    
}
