
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author derek
 */
@Named(value = "item")
@ManagedBean
@SessionScoped

public class Item implements Serializable {
    private int code;
    private String desc;
    private double price;
    private int index;
    private int quantity;
    private double total_cost;

    public Item(){}
    
    public Item(int code, String desc, double price, int quantity, int index) {
        this.code = code;
        this.desc = desc;
        this.price = price;
        this.index = index;
        this.quantity = quantity;
    }

    public double getTotal_cost() {
        return quantity * price;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc (String desc){
        this.desc = desc;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    @Override
    public String toString() {
        return "Item[id=" + code + "]";
    }
}
