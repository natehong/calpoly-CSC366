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
        int ID;
        Connection con = dbConnect.getConnection();

        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT emp_id FROM employees ORDER BY emp_id DESC");
        
        ResultSet rsID = getID.executeQuery();

        ID = (rsID.next()) ? rsID.getInt("emp_id") + 1 : 1;
        
        PreparedStatement createEmp = con.prepareStatement(
            "INSERT INTO employees VALUES(?,?,?,?,?)");
        
        createEmp.setInt(1, ID);
        createEmp.setString(2, login);
        createEmp.setString(3, password);
        createEmp.setString(4, firstName);
        createEmp.setString(5, lastName);
        
        createEmp.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        
        return "AdminHomePage";
    }
}

