/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Time;
import java.time.LocalTime;
/**
 *
 * @author conranpearce
 */
public class Bookings {
    private int id;
    private int employeeid;
    private int clientid;
    private Date date;
    private Time starttime;
    private Time endtime;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setEmployeeId(int employeeid) {
        this.employeeid = employeeid;
    }
    
    public int getEmployeeId() {
        return this.employeeid;
    }
    
    public void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setStartTime(Time starttime) {
        this.starttime = starttime;
    }
    
    public Time getStartTime() {
        return this.starttime;
    }
    
    public void setEndTime(Time endtime) {
        this.endtime = endtime;
    }
    
    public Time getEndId() {
        return this.endtime;
    }
    
    public void cancel(DBConnection dbcon, int bookingId) {
        String query = "SELECT * FROM BookingSlots WHERE id = " + bookingId;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setDate(resultSet.getDate("date"));
                this.setStartTime(resultSet.getTime("starttime"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        LocalDate date = LocalDate.parse(this.date.toString());
        LocalTime time = LocalTime.parse(this.starttime.toString());
        LocalDateTime dt = LocalDateTime.of(date, time);
        
        LocalDateTime current = LocalDateTime.now();
        
        if (current.isBefore(dt)) {
            query = "DELETE FROM BookingSlots WHERE id = " + bookingId;
            
            try (Statement stmt = dbcon.conn.createStatement()) {
                int resultSet = stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public int getIdFromDatabase(DBConnection dbcon, String user) {
        String query = "SELECT id FROM Users WHERE username = '" + user + "'";
        int userid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return userid; 
    }
    
    // Create a booking by appending data to the bookingslot table
    public void createBooking(DBConnection dbcon, String username, String employeeUsername, Date date, Time startTime, Time endTime) {
        String query = "";
        
        // Get the user's id depending on the username
        int userid = getIdFromDatabase(dbcon, username);
        
        // Get the client id from the userid in the Clients table
        query = "SELECT id FROM Clients WHERE userid = " + userid;
        int clientid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                clientid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        int employeeidbefore = getIdFromDatabase(dbcon, employeeUsername);
        
        // Get the employee id from the userid in the Employees table
        query = "SELECT id FROM Employees WHERE userid = " + employeeidbefore;
        int employeeid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                employeeid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Insert booking into BookingSlots table
        query = "INSERT INTO BookingSlots (employeeid, clientid, date, starttime, endtime) VALUES (" + employeeid + ", " + clientid + ", '" + date + "', '" + startTime + "', '" + endTime +  "')"; 

        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
       
    }
    
    public void getEmployeeName(DBConnection dbcon, String queryGetEmployeeName) {
        int idOfEmployee = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryGetEmployeeName);
            while (resultSet.next()) {
                idOfEmployee = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        System.out.println("\nid of employee " + idOfEmployee);

        String query = "SELECT firstname FROM Users WHERE id = " + idOfEmployee;
        
        String firstNameEmployee = "";
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryGetEmployeeName);
            while (resultSet.next()) {
                firstNameEmployee = resultSet.getString("firstname");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        System.out.println("Employee first name " + firstNameEmployee);
    }
    
    // Return a schedule for today of the whole
    public void getTodaysBookings(DBConnection dbcon) {        
        
        java.sql.Date todaysDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        
        String query = "SELECT * FROM BookingSlots WHERE date  = '" + todaysDate + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            
            System.out.println("Schedule for today's date: " + todaysDate);
                
            while (resultSet.next()) {
                this.setStartTime(resultSet.getTime("starttime"));
                this.setEndTime(resultSet.getTime("endtime"));
                this.setEmployeeId(resultSet.getInt("employeeid"));
                this.setClientId(resultSet.getInt("clientid"));
                
                String queryGetEmployeeName = "SELECT id FROM Employees WHERE userid = " + this.employeeid;
                getEmployeeName(dbcon, queryGetEmployeeName);
                
                ///////// PUT SCHEDULE IN ORDER - BASE THIS ON TIMES, NOT EMPLOYEES - ALLOW OVERLAP FOR WHICHEVER START TIME IS FIRST

                System.out.println("EmployeeID "+ this.employeeid);
                System.out.println("ClientID "+ this.clientid);
                System.out.println("Start time "+ this.starttime);
                System.out.println("End time "+ this.endtime);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    // Return a schedule for a doctor or a nurse
    public void getEmployeeSchedule(DBConnection dbcon, int employee, String todayOrFuture) {
        
        java.sql.Date todaysDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        
        String query = "";
                
        if (todayOrFuture == "today") {
            // Get the schedule from today
            
            query = "SELECT * FROM BookingSlots WHERE date  = '" + todaysDate + "' AND employeeid = " + employee;
            
            ArrayList<String> schedule = new ArrayList<String>();
            
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);

                System.out.println("Schedule for employee: " + employee + "\ntoday's date: " + todaysDate);

                while (resultSet.next()) {
                    this.setStartTime(resultSet.getTime("starttime"));
                    this.setEndTime(resultSet.getTime("endtime"));
//                    this.setEmployeeId(resultSet.getInt("employeeid"));
                    this.setClientId(resultSet.getInt("clientid"));

//                    String queryGetEmployeeName = "SELECT id FROM Employees WHERE userid = " + this.employeeid;
//                    getEmployeeName(dbcon, queryGetEmployeeName);

                    ///////// PUT SCHEDULE IN ORDER
                    
                    // Append to an array, print in order outside of loop 

//                    System.out.println("\nEmployeeID "+ this.employeeid);
                    System.out.println("\nClientID "+ this.clientid);
                    System.out.println("Start time "+ this.starttime);
                    System.out.println("End time "+ this.endtime);
                    
                    String appenedTogether = "ClietnID:" + this.clientid + " Start Time:" + this.starttime + " End Time:" + this.endtime; 
                    
                    schedule.add(appenedTogether);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            
            System.out.println("\nschedule " + schedule);
            
            
            for (int counter = 0; counter < schedule.size(); counter++) { 		      
                System.out.println(schedule.get(counter)); 		
            }   	
            
            // ARRAY LIST ORDERED IN DATE
            // REORDER ARRAY LIST BASED ON DATE
            
        } else if (todayOrFuture == "future") {
            // Get the schedule from today and the future
        }
        
        // Determine here which employee schedule we are getting
        
        // If this schedule is for today only
        
        // Or if this schedule is from today and in the future (including today) but not showing previous
        
        
        // Input a certain employee id/other way, a variable for today or in the future
        // If today
        //      Return the schedule for today for an employee with the date, client name, time
        
        // If future
        //      Return the schedule for today and onwards for an employee with the date, client name, time
        
        
    }
}
