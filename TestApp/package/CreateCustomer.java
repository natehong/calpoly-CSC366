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
 * @author derek
 */

@Named(value = "createCustomer")
@ManagedBean
@SessionScoped

public class CreateCustomer implements Serializable {
    
    private String login, password;
    private String firstName, lastName;
    private String email, street, city;
    private int streetNum, zipcode;

    private int creditCard, crc;
    private Date expDate;
    
    private DBConnect dbConnect = new DBConnect();
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
  
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
    
    public int getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(int creditCard) {
        this.creditCard = creditCard;
    }
    
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
    
    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }
 
    public String createAccount() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement createAcc = con.prepareStatement(
            "INSERT INTO customers VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
        
        createAcc.setString(1, login);
        createAcc.setString(2, password);
        createAcc.setString(3, firstName);
        createAcc.setString(4, lastName);
        createAcc.setString(5, email);
        createAcc.setInt(6, streetNum);
        createAcc.setString(7, street);
        createAcc.setString(8, city);
        createAcc.setInt(9, zipcode);
        createAcc.setInt(10, creditCard);
        createAcc.setDate(11, new java.sql.Date(expDate.getTime()));
        createAcc.setInt(12, crc);
        
        createAcc.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        return "index";
    }
    
}
