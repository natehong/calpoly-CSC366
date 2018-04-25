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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author stanchev
 */
@Named(value = "reservations")
@ManagedBean
@SessionScoped

public class Reservations implements Serializable {
    
    private int reservationID;
    private Date startDate;
    private Date endDate;
    private String viewChoice;
    private String roomChoice;
    private int roomNumber;
    private int total;
    private int ind;
    
    
    private String[] viewChoices = {"Pool", "Ocean"};
    private String[] roomChoices = {"double queen", "single king"};
    
    private List<Reservation> reservations;
    private List<Integer> listIndex;

    private DBConnect dbConnect = new DBConnect();
    
    public class Reservation implements Serializable {
        private int room, index;
        private boolean ocean_view;
        private String bed_type;
        
        public Reservation(int room, boolean ocean_view, String bed_type, int index) {
            this.room = room;
            this.ocean_view = ocean_view;
            this.bed_type = bed_type;
            this.index = index;
        }
        
        public int getRoom() {
            return room;
        }

        public void setRoom(int room) {
            this.room = room;
        }

        public boolean isOcean_view() {
            return ocean_view;
        }

        public void setOcean_view(boolean ocean_view) {
            this.ocean_view = ocean_view;
        }

        public String getBed_type() {
            return bed_type;
        }

        public void setBed_type(String bed_type) {
            this.bed_type = bed_type;
        }
        
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
    
    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }
    
    public int getReservationID() {
        return reservationID;
    }
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date choice) {
        this.startDate = choice;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date choice) {
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
    
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public int getTotal(){
        return total;
    }
    
    public String getName() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Login login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
        return login.getUsername();
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }
    
    public List<Integer> getListIndex() {
        return listIndex;
    }

    public void setListIndex(List<Integer> listIndex) {
        this.listIndex = listIndex;
    }
    
    public String findReservation() throws SQLException {
        int index = 0;
        boolean view = false;
        if(viewChoice.equals("Ocean"))
            view = true;
            
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        con.setAutoCommit(false);
        PreparedStatement findRes = con.prepareStatement(
            "SELECT room_code, ocean_view, bed_type\n" +
            "FROM rooms\n" +
            "WHERE ocean_view = ? AND\n" +
            "bed_type = ? AND\n" +
            "room_code NOT IN\n" +
            "   (SELECT DISTINCT room AS reserved_rooms\n" +
            "   FROM reservations\n" +
            "   WHERE ocean_view = ?\n" +
            "   AND bed_type = ?\n" +
            "AND ((check_in BETWEEN ? AND ?)\n" +
            "OR (check_out BETWEEN ? AND ?)))\n" +
            "ORDER BY room_code;"
            );
        
        findRes.setBoolean(1, view);
        findRes.setString(2, roomChoice);
        findRes.setBoolean(3, view);
        findRes.setString(4, roomChoice);
        findRes.setDate(5, new java.sql.Date(startDate.getTime()));
        findRes.setDate(6, new java.sql.Date(endDate.getTime()));
        findRes.setDate(7, new java.sql.Date(startDate.getTime()));
        findRes.setDate(8, new java.sql.Date(endDate.getTime()));

        ResultSet rs = findRes.executeQuery();
        reservations = new ArrayList<>();
        listIndex = new ArrayList<>();
        while(rs.next())
        {
            reservations.add(new Reservation(rs.getInt("room_code"), rs.getBoolean("ocean_view"), rs.getString("bed_type"), index));
            listIndex.add(index);
            index++;
        }
        return "selectRes";
    }
    
    public void checkout(){}    
    
    public void createReservation() throws SQLException {
        int ID;
        int room = 0;
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        Statement statement = con.createStatement();
        
        con.setAutoCommit(false);
        
        PreparedStatement getID = con.prepareStatement(
            "SELECT res_code FROM reservations ORDER BY res_code DESC");
        
        PreparedStatement createRes = con.prepareStatement(
             "INSERT INTO reservations VALUES(?,?,?,?,?)");
    
        
        ResultSet rsID = getID.executeQuery();
        ID = (rsID.next()) ? rsID.getInt("res_code") + 1 : 1;
        
        createRes.setInt(1, ID);
        createRes.setDate(2, new java.sql.Date(startDate.getTime()));
        createRes.setDate(3, new java.sql.Date(endDate.getTime()));
        createRes.setInt(4, room);
        createRes.setString(5, getName());

        //createRes.executeUpdate();

        statement.close();
        con.commit();
        con.close();
        
        System.out.println("DEBUG " + ind);
    }
}
