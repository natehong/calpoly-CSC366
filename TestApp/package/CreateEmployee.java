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

@Named(value = "createEmployee")
@ManagedBean
@SessionScoped

public class CreateEmployee implements Serializable {
    
    private int emplID;
    private String login, password;
    private String firstName, lastName;
    
    private DBConnect dbConnect = new DBConnect();
    
    public int getEmplID(){
        return emplID;
    }
    
    public void setEmplID(int emplID){
        this.emplID = emplID;
    }
    
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
    
    public String createEmployee() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement createAcc = con.prepareStatement(
            "INSERT INTO customers VALUES(?,?,?,?,?)");
        
        createAcc.setInt(1, emplID);
        createAcc.setString(2, login);
        createAcc.setString(3, password);
        createAcc.setString(4, firstName);
        createAcc.setString(5, lastName);
        
        createAcc.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        return "index";
    }
}

