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
    
    private List<Reservation> allReservations;
    private List<Reservation> reservations;
    private List<Integer> listIndex;
    private List<UserReservations> userReservations;

    private DBConnect dbConnect = new DBConnect();
    
    
    public class UserReservations implements Serializable {
        
        private int resCode;
        private Date checkIn, checkOut;
        private String view, roomType;
        
        public UserReservations(int rc, Date ci, Date co, String v, String rt) {
            resCode = rc;
            checkIn = ci;
            checkOut = co;
            view = v;
            roomType = rt;
        }

        public int getResCode() {
            return resCode;
        }

        public void setResCode(int resCode) {
            this.resCode = resCode;
        }

        public Date getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(Date checkIn) {
            this.checkIn = checkIn;
        }

        public Date getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(Date checkOut) {
            this.checkOut = checkOut;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }
        
    }
    
    public class Reservation implements Serializable {
        private int floor;
        private double price, sp;
        private int room, index;
        private boolean ocean_view;
        private String bed_type;
        
        public Reservation(int floor, double price, double sp,
                int room, boolean ocean_view, 
                String bed_type, int index) {
            this.floor = floor;
            this.price = price;
            this.room = room;
            this.ocean_view = ocean_view;
            this.bed_type = bed_type;
            this.index = index;
        }
        
        public double getSp() {
            return sp;
        }

        public void setSp(double sp) {
            this.sp = sp;
        }
        
        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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
    
    public List<UserReservations> getUserReservations() throws SQLException {
        showCustomerReservations();
        return userReservations;
    }

    public void setUserReservations(List<UserReservations> userReservations) {
        this.userReservations = userReservations;
    }
    
    public List<Reservation> getAllReservations() throws SQLException {
        findAllReservations();
        return allReservations;
    }

    public void setAllReservations(List<Reservation> allReservations) {
        this.allReservations = allReservations;
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
            reservations.add(new Reservation(0, 0, 0, rs.getInt("room_code"), rs.getBoolean("ocean_view"), rs.getString("bed_type"), index));
            listIndex.add(index);
            index++;
        }
        return "selectRes";
    }
    
    public void checkout(){}    
    
    public void createReservation() throws SQLException {
        int ID;
        
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
        createRes.setInt(4, reservations.get(ind).getRoom());
        createRes.setString(5, getName());

        createRes.executeUpdate();

        statement.close();
        con.commit();
        con.close();
    }
    
    public String showCustomerReservations() throws SQLException {
        int room_code; 
        String bed_type;
        String view;
        Date c_in, c_out;
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        Statement statement = con.createStatement();
        
        con.setAutoCommit(false);
        
  
        PreparedStatement showRes = con.prepareStatement(
            "SELECT *\n" +
            "FROM reservations\n" +
            "WHERE customer = ?\n" +
            "ORDER BY check_in;");
        
        showRes.setString(1, getName());

    
        ResultSet rs = showRes.executeQuery();
        userReservations = new ArrayList<>();
        while(rs.next())
        {
            c_in = rs.getDate("check_in");
            c_out = rs.getDate("check_out");
            
            if((room_code = rs.getInt("res_code")) % 2 == 0) // even then single king
                bed_type = "Single King";
            else
                bed_type = "Double Queen";
            if((room_code = rs.getInt("res_code")) % 100 > 6)
                view = "Pool";
            else
                view = "Ocean";
            
            userReservations.add(new UserReservations(
                    room_code, c_in, c_out, view, bed_type));
        }
        statement.close();
        con.commit();
        con.close();
        return "checkRsvr";
    }
    
    public void findAllReservations() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        Statement statement = con.createStatement();
        
        con.setAutoCommit(false);
        
        PreparedStatement findRes = con.prepareStatement(
            "SELECT (room_code / 100) AS floor, (room_code - ((room_code/100) * 100)) AS room_number, ocean_view, bed_type, base_price, rate AS special_rate\n" +
            "FROM rooms\n" +
            "LEFT JOIN special_room_rates ON (room = room_code) AND (current_date = book_date)\n" +
            "WHERE room_code NOT IN\n" +
            "(SELECT room\n" +
            "FROM reservations\n" +
            "WHERE current_date BETWEEN check_in AND check_out)\n" +
            "ORDER BY room_code;");
        
        ResultSet rs = findRes.executeQuery();
        
        allReservations = new ArrayList<>();
        while(rs.next()) {
            allReservations.add(new Reservation(rs.getInt("floor"), rs.getDouble("base_price"),
                    rs.getDouble("special_rate"), rs.getInt("room_number"), rs.getBoolean("ocean_view"), 
                    rs.getString("bed_type"), 0));
        }
    }
    
    public void cancelReservation() throws SQLException {
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        Statement statement = con.createStatement();
        
        con.setAutoCommit(false);
        
        PreparedStatement deleteRes = con.prepareStatement(
            "DELETE FROM reservations\n" +
            "WHERE res_code = ?\n" +
            "AND current_date > check_in;");
        
        PreparedStatement deleteCharges = con.prepareStatement(
            "DELETE FROM additional_charges_invoices\n" +
            "WHERE reservation = ?\n" +
            "AND current_date > charge_date;");
        
        PreparedStatement deleteHistory = con.prepareStatement(
            "DELETE FROM room_rate_history\n" +
            "WHERE reservation = ?\n" +
            "AND current_date > charge_date;");
        
        deleteRes.setInt(1, reservationID);
        deleteCharges.setInt(1, reservationID);
        deleteHistory.setInt(1, reservationID);

        deleteRes.executeUpdate();
        deleteCharges.executeUpdate();
        deleteHistory.executeUpdate();

        statement.close();
        con.commit();
        con.close();
    }
}
