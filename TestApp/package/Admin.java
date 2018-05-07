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
    
    private int emp_id;
    private String currPassword, newPassword;
    private UIInput emp_idUI, newPassUI;
    
    private DBConnect dbConnect = new DBConnect();

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public UIInput getEmp_idUI() {
        return emp_idUI;
    }

    public void setEmp_idUI(UIInput emp_idUI) {
        this.emp_idUI = emp_idUI;
    }

    public UIInput getNewPassUI() {
        return newPassUI;
    }

    public void setNewPassUI(UIInput newPassUI) {
        this.newPassUI = newPassUI;
    }

    public String getCurrPassword(){
        return currPassword;
    }
    
    public void setCurrPassword(String currPassword){
        this.currPassword = currPassword;
    }
        
    public String getNewPassword(){
        return newPassword;
    }
    
    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
    
    public void changePassword() throws SQLException {
        Connection con = dbConnect.getConnection();
        
        if (con == null) {
         throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        
        PreparedStatement changeUsername = con.prepareStatement(
            "UPDATE employees SET password = ? WHERE emp_id = ?");
        
        changeUsername.setString(1, newPassUI.getValue().toString());
        changeUsername.setInt(2, (Integer) emp_idUI.getValue());

        
        changeUsername.executeUpdate();
        statement.close();
        con.commit();
        con.close();
    }
    
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        // this functions validates emp_id and password
        // does not return anything but will throw an exception if the user
        // uses an incorrect login
        // the exception will print an error message on the page definded by validator message
        Integer id;
        String passLocal, pass;
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
                
        PreparedStatement validateAcc = con.prepareStatement(
            "SELECT emp_id, password FROM employees WHERE emp_id = ?");
        
        id = (Integer) emp_idUI.getValue();
        passLocal = value.toString();
        
        validateAcc.setInt(1, id);

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
    
    public String logout() {
        emp_id = 0;
        currPassword = "";
        newPassword = "";
        return "EmployeeLogin";
    }
}
