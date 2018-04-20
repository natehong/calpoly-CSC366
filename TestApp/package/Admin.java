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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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
    private UIInput currNameUI, newNameUI, newPassUI;
    private DBConnect dbConnect = new DBConnect();
    
    public UIInput getCurrNameUI() {
        return currNameUI;
    }

    public void setCurrNameUI(UIInput currNameUI) {
        this.currNameUI = currNameUI;
    }

    public UIInput getNewNameUI() {
        return newNameUI;
    }

    public void setNewNameUI(UIInput newNameUI) {
        this.newNameUI = newNameUI;
    }

    public UIInput getNewPassUI() {
        return newPassUI;
    }

    public void setNewPassUI(UIInput newPassUI) {
        this.newPassUI = newPassUI;
    }
   
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
        
        PreparedStatement changeUsername = con.prepareStatement(
            "UPDATE employees SET login = ? WHERE login = ?");
        
        changeUsername.setString(1, newNameUI.getValue().toString());
        changeUsername.setString(2, currNameUI.getValue().toString());

        
        changeUsername.executeUpdate();
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
        
        PreparedStatement changeUsername = con.prepareStatement(
            "UPDATE employees SET password = ? WHERE login = ?");
        
        changeUsername.setString(1, newPassUI.getValue().toString());
        changeUsername.setString(2, currNameUI.getValue().toString());

        
        changeUsername.executeUpdate();
        statement.close();
        con.commit();
        con.close();
    }
    
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        // this functions validates username and login
        // does not return anything but will throw an exception if the user
        // uses an incorrect login
        // the exception will print an error message on the page definded by validator message
        String name, passLocal, pass;
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
                
        PreparedStatement validateAcc = con.prepareStatement(
            "SELECT login, password FROM employees WHERE login = ?");
        
        name = currNameUI.getValue().toString();
        passLocal = value.toString();
        
        validateAcc.setString(1, name);

        ResultSet rs = validateAcc.executeQuery();

        if(rs.next())
        {
            pass = rs.getString("password");
            if(!passLocal.equals(pass)) {     // password validates with login
                FacesMessage errorMessage = new FacesMessage("Wrong login/password");
                throw new ValidatorException(errorMessage);
            }
        }
        else {
            FacesMessage errorMessage = new FacesMessage("Wrong login/password");
            throw new ValidatorException(errorMessage);
        }
    }
}
