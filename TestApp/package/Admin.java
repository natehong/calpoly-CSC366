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

@Named(value = "admin")
@ManagedBean
@SessionScoped

public class Admin implements Serializable {
    
    private String currLogin, currPassword;
    private String newLogin, newPassword;
    
    private DBConnect dbConnect = new DBConnect();
    
    public String getCurrLogin(){
        return currLogin;
    }
    
    public void setCurrLogin(String currLogin){
        this.currLogin = currLogin;
    }
    
    public String getCurrPassword(){
        return currPassword;
    }
    
    public void setCurrPassword(String currPassword){
        this.currPassword = currPassword;
    }
    
    public String getNewLogin(){
        return newLogin;
    }
    
    public void setNewLogin(String newLogin){
        this.newLogin = newLogin;
    }
    
    public String getNewPassword(){
        return newPassword;
    }
    
    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
    
    public void changeUsername() throws SQLException {
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        /*PreparedStatement createAcc = con.prepareStatement(
            "INSERT INTO customers VALUES(?,?,?,?,?)");
        
        createAcc.setInt(1, emplID);
        createAcc.setString(2, login);
        createAcc.setString(3, password);
        createAcc.setString(4, firstName);
        createAcc.setString(5, lastName);
        
        createAcc.executeUpdate();*/
        statement.close();
        con.commit();
        con.close();
    }
    public void changePassword() throws SQLException {
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        /*PreparedStatement createAcc = con.prepareStatement(
            "INSERT INTO customers VALUES(?,?,?,?,?)");
        
        createAcc.setInt(1, emplID);
        createAcc.setString(2, login);
        createAcc.setString(3, password);
        createAcc.setString(4, firstName);
        createAcc.setString(5, lastName);
        
        createAcc.executeUpdate();*/
        statement.close();
        con.commit();
        con.close();
    }
}
