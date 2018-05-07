/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/**
 *
 * @author derek
 */

@Named(value = "customer")
@ManagedBean
@SessionScoped
public class Customer implements Serializable {
    
    private String customer;
    
    public String getCustomer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
        return login.getUsername();
    }
    
    public String logout() {
        customer = "";
        return "index";
    }
}
