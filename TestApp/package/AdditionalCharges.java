/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrick
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

@Named(value = "additionalCharges")
@ManagedBean
@SessionScoped

public class AdditionalCharges implements Serializable {
    private int invoiceCode, reservationCode;
    private String chargeDate;
    private int chargeCode, quantity;
    
    private DBConnect dbConnect = new DBConnect();
    
    public int getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(int invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
    public int getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }
    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }
    public int getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(int chargeCode) {
        this.chargeCode = chargeCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
