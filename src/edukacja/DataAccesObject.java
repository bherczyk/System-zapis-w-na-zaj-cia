
package edukacja;

import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataAccesObject {

    private final static String DATABASEURL = "jdbc:mysql://xxxx/";
    private final static String DATABASEUSER = "xxxx";
    private final static String DATABASEPASSWORD = "xxxx";

    
    private Connection connection;
    private Statement statement;
    private String query;
    
    public DataAccesObject()
    {
    }
    
    public void connect()
    {
        try {
            
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DATABASEURL,DATABASEUSER,DATABASEPASSWORD);
                   
        }catch ( SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public String[][] displayClasses()
    {
       
        String[][] result = null;
        
        try {
         
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM 14651951_001.Zajecia");
            
            int rowCount = 0;
            if(rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            result = new String[rowCount][11];

            for(int i = 0; rs.next(); i++) {
                result[i][0] = rs.getString("Kod zajęć");
                result[i][1] = rs.getString("Nazwa kursu");
                result[i][2] = rs.getString("Rodzaj");
                result[i][3] = rs.getString("ECTS");
                result[i][4] = rs.getString("Dzień");
                result[i][5] = rs.getString("Parzystość");
                result[i][6] = rs.getString("Godzina");
                result[i][7] = rs.getString("Budynek");
                result[i][8] = rs.getString("Sala");
                result[i][9] = rs.getString("Prowadzący");
                result[i][10] = rs.getString("Liczba miejsc");
            }
            rs.close();
            statement.close();
         } catch (SQLException ex) {
            
            ex.printStackTrace();
        }    
        return result;
    }

    public String[][] displayLecturers()
    {
        String[][] result = null;
        
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM 14651951_001.Lecturers");
            
            int rowCount = 0;
            if(rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            result = new String[rowCount][5];
            
            for(int i = 0; rs.next(); i++) {
                result[i][0] = rs.getString("idLecturer");
                result[i][1] = rs.getString("title");
                result[i][2] = rs.getString("firstName");
                result[i][3] = rs.getString("lastName");
                result[i][4] = rs.getString("pesel");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return result;
    }
   
    
    public String[][] displayLocations() {
        
        String[][] result = null;
        
        try {
            
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM 14651951_001.Locations");
            
            int rowCount = 0;
            if(rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            result = new String[rowCount][4];            
            
            for(int i = 0; rs.next(); i++) {
                result[i][0] = rs.getString("idLocation");
                result[i][1] = rs.getString("buildingName");
                result[i][2] = rs.getString("hallName");
                result[i][3] = rs.getString("size");
            }
            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public String[][] displayCourses() {
        
        String[][] result = null;
        
        try {
            
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM 14651951_001.Courses");
            
            int rowCount = 0;
            if(rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            result = new String[rowCount][2];            
            
            for(int i = 0; rs.next(); i++) {
                result[i][0] = rs.getString("idCourse");
                result[i][1] = rs.getString("Name");
            }
            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }   
    
    public String[][] displayRecords() {
        
        String[][] result = null;
        
        try {
            
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM 14651951_001.Record");
            
            int rowCount = 0;
            if(rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            result = new String[rowCount][2];

            for(int i = 0; rs.next(); i++) {
                result[i][0] = rs.getString("idStudent");
                result[i][1] = rs.getString("idClasses");
            }
            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }    
    
    public String[][] displayStudents() {
        
        String[][] result = null;
        
        try {
            
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM 14651951_001.Students");
            
            int rowCount = 0;
            if(rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            result = new String[rowCount][6];           
            
            for(int i = 0; rs.next(); i++) {
                result[i][0] = rs.getString("idStudent");
                result[i][1] = rs.getString("lastName");
                result[i][2] = rs.getString("firstName");
                result[i][3] = rs.getString("ECTS");
                result[i][4] = rs.getString("pesel");
                result[i][5] = rs.getString("Password");
            }
            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }    
    
    public void addCourse(String name) throws SQLException
    {
 
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO 14651951_001.Courses (Name) VALUES('" + name +"')");
            statement.close();

    }
    
    public void removeCourse(String id) throws SQLException {
    
            statement = connection.createStatement();
            removeClassesWithCourse(id);
            
            statement.executeUpdate("DELETE FROM 14651951_001.Courses WHERE idCourse ='" + id + "'");
            statement.close();
    }
    
    public void removeClassesWithCourse(String idCourse) throws SQLException {
        
        Statement statementClasses = connection.createStatement();
        ResultSet rs = statementClasses.executeQuery("SELECT idClasses FROM 14651951_001.Classes WHERE idCourse = '" + idCourse + "'");

        while(rs.next()) {
            removeRecord(rs.getString("idClasses"));
        }
        rs.close();
            
        statementClasses.executeUpdate("DELETE FROM 14651951_001.Classes WHERE idCourse = '" + idCourse + "'");
        statementClasses.close();

    }
    
    public void removeRecord(String id) throws SQLException {
        Statement statementRecord = connection.createStatement();
        statementRecord.executeUpdate("DELETE FROM 14651951_001.Record WHERE idClasses = '" + id +"'");
        statementRecord.close();

    }
    
    public void addLecturer(String [] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO 14651951_001.Lecturers (title,firstName,lastName,pesel) VALUES('"
                + dane[0] + "','" + dane[1] + "','" + dane[2] + "','" + dane[3] + "')");
        statement.close();
    }
    
    public void removeLecturer(String idLecturer) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM 14651951_001.Lecturers WHERE idLecturer = '" + idLecturer + "'");
        statement.close();
    }
    
    public void modifyLecturer(String idLecturer, String[] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("UPDATE 14651951_001.Lecturers SET title = '" + dane[0] + "', firstName = '" +
                dane[1] + "', lastName = '" + dane[2] + "', pesel = '" + dane[3] + "' WHERE idLecturer='" + idLecturer + "'");
        statement.close();
    }
    
    
    public void addLocation(String [] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO 14651951_001.Locations (buildingName, hallName, size) VALUES ('" + dane[0] + "','" + 
        dane[1] + "','" + dane[2] +"')");
        statement.close();
    }
    
    public void removeLocation(String idLocation) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM 14651951_001.Locations WHERE idLocation = '" + idLocation + "'");
        statement.close();
    }
    
    public void modifyLocation(String idLocation, String[] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("UPDATE 14651951_001.Locations SET buildingName= '" + dane[0] + "', hallName = '" + 
                dane[1] + "', size = '" + dane[2] + "' WHERE idLocation = '" + idLocation +"'");
        statement.close();
    }
    
    public void addStudent(String[] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO 14651951_001.Students (lastName, firstName, Pesel, Password) VALUES ('" + 
                dane[0] + "', '" + dane[1] + "' ,'" + dane[2] + "', MD5('" + dane[3] + "'))");
        statement.close();
    }
    
    public void removeStudent(String idStudent) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM 14651951_001.Students WHERE idStudent = '" + idStudent + "'");
        statement.close();
        
    }
    
    public void modifyStudentData(String idStudent, String[] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("UPDATE 14651951_001.Students SET lastName='" + dane[0] +"', firstName='" + dane[1] + "', Pesel='" + dane[2] + "' WHERE idStudent='" + idStudent +"'");
        statement.close();
    }
    
    public void modifyStudentPassword(String idStudent, String password) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("UPDATE 14651951_001.Students SET Password=MD5('" + password + "') WHERE idStudent ='" + idStudent + "'");
        statement.close();
        
    }
    
    public void addRecord(String idClasses, String idStudent) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO 14651951_001.Record (idStudent, idClasses) VALUES ('" + idStudent + "', '" + idClasses + "')");
        statement.close();
    }
    
    public void removeRecord(String idClasses, String idStudent) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM 14651951_001.Record WHERE idStudent = '" + idStudent + "' AND idClasses = '" + idClasses + "'");
        statement.close();
    }
    
    public void addClasses(String [] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO 14651951_001.Classes (ECTS, Type, Day, Hour, idCourse, idLocation, idLecturer, Week) VALUES ('" + 
                dane[0] + "', '" + dane[1] + "', '" + dane[2] + "', '" + dane[3] + "', '" + dane[4] + "', '" + dane[5] + "', '" + dane[6] + "', '" + dane[7] + "')");
        statement.close();
    }
    
    public void removeClasses(String idClasses) throws SQLException {
        statement = connection.createStatement();
        removeRecord(idClasses);
        statement.executeUpdate("DELETE FROM 14651951_001.Classes WHERE idClasses ='" + idClasses + "'");
        statement.close();
    }
    
    public void modifyClasses(String idClasses, String[] dane) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("UPDATE 14651951_001.Classes SET ECTS='" + dane[0] + "', Type='" + dane[1] + "', Day='" + 
                dane[2] + "', Week='" + dane[7] + "', Hour='" + dane[3] + "', idCourse='" + dane[4] + "', idLocation='" + 
                dane[5] + "', idLecturer='" + dane[6] + "' WHERE idClasses='" + idClasses + "'");
        statement.close();
    }
    
    public String findIdCourse(String name) {
        String idCourse = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT idCourse FROM 14651951_001.Courses WHERE Name like '" + name + "'");
            rs.next();
            idCourse = rs.getString("idCourse");
            
           // rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccesObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idCourse;
    }
    
    public String findIdLocation(String buildingName, String hallName) {
        String idLocation = null;
        

        try {
            statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("SELECT idLocation FROM 14651951_001.Locations WHERE buildingName = '" + buildingName + "' AND hallName = '" + hallName + "'");
            rs.next();
            idLocation = rs.getString("idLocation");
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccesObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idLocation;
    }
}
