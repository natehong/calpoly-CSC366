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
import java.util.List;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Patrick
 */

@Named(value = "receipt")
@ManagedBean
@SessionScoped
public class Receipts implements Serializable  {
        private int reservationID;
        private double specialRate;
        private Date startDate;
        private Date endDate;
        private int roomNumber;
        private int quantity;
        private int itemCost;
        private double total;
        
        private List<Receipt> receipt;
        
        public class Receipt implements Serializable {
        
        private int resID;
        private Date chargeDate;
        private int itemPrice, orderCount;
        
        public Receipt(Date cDate, int ip, int oc) {
            chargeDate = cDate;
            itemPrice = ip;
            orderCount = oc;
        }  
        
        public int getResID() {
            return resID;
        }

        public void setResID(int resID) {
            this.resID = resID;
        }
            
        public Date getChargeDate() {
            return chargeDate;
        }

        public void setChargeDate(Date chargeDate) {
            this.chargeDate = chargeDate;
        }
        
        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public int getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(int itemPrice) {
            this.itemPrice = itemPrice;
        }

    }
        
    public int getReservationID() {
        return reservationID;
    }

    public void getReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
    
    public double getSpecialRate() {
        return specialRate;
    }

    public void setSpecialRate(double specialRate) {
        this.specialRate = specialRate;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
        
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
        
    public int getItemCost() {
        return itemCost;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }  
    
    public void reservationReceipt(){
   
    }
}
