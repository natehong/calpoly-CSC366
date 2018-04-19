/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author stanchev
 */
@Named(value = "selector")
@ManagedBean
@SessionScoped

public class Selector implements Serializable {
    
    private Date startDate;
    private Date endDate;
    private String viewChoice;
    private String roomChoice;
    
    private String[] viewChoices = {"Pool", "Ocean"};
    private String[] roomChoices = {"Single", "Double", "Queen", "King"};
    
    private DBConnect dbConnect = new DBConnect();
    
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date choice) {
        this.startDate = choice;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date choice) {
        this.endDate = choice;
    }
    
    public String getViewChoice() {
        return viewChoice;
    }
    
    public String[] getViewChoices() {
        return viewChoices;
    }
    
    public void setViewChoice(String choice) {
        this.viewChoice = choice;
    }
    
    public String getRoomChoice() {
        return roomChoice;
    }
    public String[] getRoomChoices() {
        return roomChoices;
    }
    
    public void setRoomChoice(String choice) {
        this.roomChoice = choice;
    }
    
    public void createReservation() throws SQLException {
        int ID;
        int room = 0;
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        Statement statement = con.createStatement();
        
        con.setAutoCommit(false);
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT res_code FROM reservations ORDER BY res_code DESC");
        
        PreparedStatement createRes = con.prepareStatement(
             "INSERT INTO reservations VALUES(?,?,?,?,?)");
        
        ResultSet rsID = getID.executeQuery();
        ID = (rsID.next()) ? rsID.getInt("res_code") + 1 : 1;
        
        createRes.setInt(1, ID);
        createRes.setDate(2, new java.sql.Date(startDate.getTime()));
        createRes.setDate(3, new java.sql.Date(endDate.getTime()));
        createRes.setInt(4, room);
        createRes.setString(5, "");

        createRes.executeUpdate();

        statement.close();
        con.commit();
        con.close();

    }
}
