/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author stanchev
 */
@Named(value = "selector")
@ManagedBean
@SessionScoped

public class Selector implements Serializable {
    
    private String startDate;
    private String endDate;
    private String viewChoice;
    private String roomChoice;
    
    private String[] viewChoices = {"Pool", "Ocean"};
    private String[] roomChoices = {"Single", "Double", "Queen", "King"};
    
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String choice) {
        this.startDate = choice;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String choice) {
        this.endDate = choice;
    }
    
    public String getViewChoice() {
        return viewChoice;
    }
    
    public String[] getViewChoices() {
        return viewChoices;
    }
    
    public void setViewChoice(String choice) {
        this.viewChoice = choice;
    }
    
    public String getRoomChoice() {
        return roomChoice;
    }
    public String[] getRoomChoices() {
        return roomChoices;
    }
    
    public void setRoomChoice(String choice) {
        this.roomChoice = choice;
    }
    
    public void transition() {
        System.out.println(startDate);
    }
}
