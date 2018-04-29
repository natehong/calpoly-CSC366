
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
    private int price;
    private int index;

    public Item(){}
    
    public Item(int code, String desc, int price, int index) {
        this.code = code;
        this.desc = desc;
        this.price = price;
        this.index = index;
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

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
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
