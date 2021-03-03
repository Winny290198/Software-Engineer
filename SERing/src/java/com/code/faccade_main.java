package com.code;

import object.SessionInfor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.Speaker;
import org.json.JSONArray;

public class faccade_main {

    // Method to write a new speaker from Web App to SQL DB
    /*
    String query = "SELECT * FROM speaker where name='" + name + "' and day_of_contact ='" + doc + "' and phone_number ='" + phone + "' and email ='" + email + "'";
     */
    private DBContext database = new DBContext();
    private JSONArray json;
    private ResultSet result;

    public boolean write_speaker(String name, String phone, String email, String doc) {
        // check for duplicates
        String query = "SELECT * FROM speaker where phone_number ='" + phone + "' and email ='" + email + "'";
        String reQuery = "INSERT INTO speaker (speaker_id,name,day_of_contact,phone_number,email) VALUES((SELECT MAX( speaker_id )+1 FROM speaker cust),'" + name + "','" + doc + "','" + phone + "','" + email + "')";
        try {
            result = database.getConnectionStatement(query);
            if (result.next()) {
                /* 
                because email and phone are unique, so we only check email and phone values
                if this result check if exist phone, email exist in database, then these value user enter is already exist
                so it will return false
                 */
                return false;
            } else {
                System.out.println("Inserted");
                database.preStatement(reQuery);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(faccade_main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(faccade_main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    // Method to edit an existing Speaker in SQL DB
    public boolean edit_speaker(String name, String phone, String email, String doc, String id) {
        // check for duplicates
        String query = "UPDATE speaker set name='" + name + "' , day_of_contact ='" + doc + "' , phone_number ='" + phone + "' , email ='" + email + "' where speaker_id ='" + id + "'";
        String reQuery = "SELECT * FROM speaker where speaker_id='" + id + "'";
        //ResultSet result2;
        boolean check = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            database.preStatement(query);
            result = database.getConnectionStatement(reQuery);
            if (result.next()) {
                if (result.getString("name").equals(name) == false) { 
                    return false;
                } else if (result.getString("day_of_contact").equals(doc) == false) { 
                    return false;
                } else if (result.getString("phone_number").equals(phone) == false) { 
                    return false;
                } else if (result.getString("email").equals(email) == false) { 
                    return false;
                } 
                return true;
                 
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public JSONArray showInforspeaker(String id) {
        String query = "SELECT * FROM speaker where speaker_id ='" + id + "'";
        ArrayList<Speaker> speaker = new ArrayList<>();
        json = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            result = database.getConnectionStatement(query);
            Speaker spk = new Speaker();
            if (result.next()) {
                spk.name = result.getString("name");
                spk.doc = result.getString("day_of_contact");
                spk.phone = result.getString("phone_number");
                spk.email = result.getString("email");
                spk.spkID = result.getString("speaker_id");
            }
            speaker.add(spk);
        } catch (Exception ex) { }
        json = new JSONArray(speaker);
        
        return json;
    }

    // Method to remove an speaker from the database
    public boolean remove_speaker(String name, String phone, String email, String doc) {
        String query = "SELECT * FROM speaker where name='" + name + "' and day_of_contact ='" + doc + "' and phone_number ='" + phone + "' and email ='" + email + "'";
        try {
            result = database.getConnectionStatement(query);
            if (result.next()) {
                String queryDel = "DELETE FROM speaker where speaker_id='" + result.getInt("speaker_id") + "'";
                System.out.println("Speak ID: " + result.getInt("speaker_id"));
                System.out.println("exist in database, removed");
                database.preStatement(queryDel);
                return true;
            }
        } catch (Exception ex) {
        }

        return false;
    }

    // Method to find a speaker w/o using the speaker_id
    public boolean find_speaker(String name, String email) {
        String query = "SELECT * FROM speaker where name='" + name + "' and email ='" + email + "'";
        try {
            result = database.getConnectionStatement(query);
            if (result.next()) {
                System.out.println("Speak ID: " + result.getInt("speaker_id"));
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(faccade_main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public JSONArray getSession() {
        ResultSet result2;
        String query = "SELECT * FROM session";
        ArrayList<SessionInfor> sessionInforArr = new ArrayList<>();
        json = null;
        String sa = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            result = database.getConnectionStatement(query);

            while (result.next()) {
                SessionInfor sessionInfor = new SessionInfor();
                sessionInfor.Session = result.getString("session_name");
                sessionInfor.SessionID = result.getInt("session_id");
                sessionInfor.SpeakID = result.getInt("speaker_id");
                sessionInfor.TimeID = result.getInt("room_id");
                sessionInfor.TimeID = result.getInt("time_slot_id");

                //System.out.print(result.getString("session_name") + " | ");
                String querySPK = "SELECT * FROM speaker where speaker_id='" + result.getInt("speaker_id") + "'";
                result2 = database.getConnectionStatement(querySPK);
                while (result2.next()) {
                    sessionInfor.Name = result2.getString("name");
                    sessionInfor.Email = result2.getString("email");
                    // System.out.print("Name: " + result2.getString("name") + "| Email: " + result2.getString("email"));
                }

                String queryROOM = "SELECT * FROM room where room_id='" + result.getInt("room_id") + "'";
                result2 = database.getConnectionStatement(queryROOM);
                while (result2.next()) {
                    sessionInfor.RoomName = result2.getString("room_number");
                    sessionInfor.Seat = result2.getInt("seats");
                    sessionInfor.RoomID = result2.getInt("room_id");
                    //System.out.print("| Room Num: " + result2.getString("room_number") + "| Seat: " + result2.getInt("seats"));
                }

                String queryTIME = "SELECT * FROM time_slot where time_slot_id='" + result.getInt("time_slot_id") + "'";
                result2 = database.getConnectionStatement(queryTIME);
                while (result2.next()) {
                    sessionInfor.Time = result2.getTime("time_start") + " - " + result2.getTime("time_end");
                    //System.out.print("| Time: " + result2.getTime("time_start") + " - " + result2.getTime("time_end"));
                }

                //System.out.println();
                sessionInforArr.add(sessionInfor);
            }
            json = new JSONArray(sessionInforArr);
             
        } catch (Exception ex) {

        }

        return json;
    }

    // Method to get all speakers info to front end
    public String[][] get_all_speakers() {
        // Decision on return type between 1D or 2D array of String
        return new String[0][0];
    }

    // Method to write a new room from Web App to SQL DB
    public boolean write_room(String room_no, String seats) {
        // check for duplicates
        return true;
    }

    // Method to edit an existing Room in SQL DB
    public boolean edit_room(String room, String seats) {
        // check for duplicates
        return true;
    }

    // Method to remove a room from the database
    public boolean remove_room(String room_no, String seats) {
        return true;
    }

    // Method to find a room w/o using the room_id
    public boolean find_room(String room_no, String seats) {
        return false;
    }

    // Method to get all rooms info to front end
    public String[][] get_all_rooms() {
        // Decision on return type between 1D or 2D array of String
        return new String[0][0];
    }

    // Method to write a new time slot from Web App to SQL DB
    public boolean write_time_slot(LocalTime start, LocalTime end) {
        // check for duplicates
        return true;
    }

    // Method to edit an existing time slot in SQL DB
    public boolean edit_time_slot(LocalTime start, LocalTime end) {
        // check for duplicates
        return true;
    }

    // Method to remove a time slot from the database
    public boolean remove_time_slot(LocalTime start, LocalTime end) {
        return true;
    }

    // Method to find a time slot w/o using the time_slot_id
    public boolean find_time_slot(LocalTime start, LocalTime end) {
        return false;
    }

    // Method to get all time slots info to front end
    public String[][] get_all_time_slots() {
        // Decision on return type between 1D or 2D array of String
        return new String[0][0];
    }

    // Method to write a new session from Web App to SQL DB
    public boolean write_session(String session_name) {
        // check for duplicates
        return true;
    }

    // Method to edit an existing Session in SQL DB
    public boolean edit_session(String session_name) {
        // check for duplicates
        return true;
    }

    // Method to remove a session from the database
    public boolean remove_session(String session_name) {
        return true;
    }

    // Method to find a session w/o using the session_id
    public boolean find_session(String session_name) {
        return false;
    }

    // Method to get all sessions info to front end
    public String[][] get_all_sessions() {
        // Decision on return type between 1D or 2D array of String
        return new String[0][0];
    }

    public boolean delete_doc_info() {
        return true;
    }

    // check for malicious code?
    public boolean check_valid_entry(String s) {
        return false;
    }

    // check to make sure its a valid integer number
    public boolean check_num(String s) {
        return false;
    }

    // Maybe delete - check with Bruce, Raymond
    public boolean check_email(String s) {
        return false;
    }
}
