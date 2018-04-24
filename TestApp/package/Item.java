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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Patrick
 */

@Named(value = "item")
@ManagedBean
@SessionScoped

public class Item implements Serializable {
    private int chargeCode;
    private String description;
    private int cost;
    
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
    
    public void createItem() /*throws SQLException*/ {
        
    }
}
