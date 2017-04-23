
package edukacja;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AppliactionPanel extends javax.swing.JPanel {



    private final DataAccesObject dataBase;
    private final DefaultTableModel modelClasses ;
    private final DefaultTableModel modelLecturers;
    private final DefaultTableModel modelLocations;
    private final DefaultTableModel modelCourses;
    private final DefaultTableModel modelRecord;
    private final DefaultTableModel modelStudents;
    
    private String idLecturer;
    private String idLocation;
    private String idStudent;
    
    public AppliactionPanel() {
        initComponents();
        dataBase = new DataAccesObject();
        
        dataBase.connect();
        
        modelClasses = (DefaultTableModel) classesTable.getModel();
        modelLecturers = (DefaultTableModel) lecturersTable.getModel();
        modelLocations = (DefaultTableModel) locationsTable.getModel(); 
        modelCourses = (DefaultTableModel) coursesTable.getModel();
        modelRecord = (DefaultTableModel) recordsTable.getModel();
        modelStudents = (DefaultTableModel) studentsTable.getModel();
        
        weekClassesAddBox.addItem("");
        
        classesTable.getTableHeader().setBackground(new Color(51,51,51));
        classesTable.getTableHeader().setForeground(new Color(204,204,204));
        classesScrollPane.getViewport().setBackground(new Color(51,51,51));
        
        coursesTable.getTableHeader().setBackground(new Color(51,51,51));
        coursesTable.getTableHeader().setForeground(new Color(204,204,204));
        coursesScrollPane.getViewport().setBackground(new Color(51,51,51));
        
        lecturersTable.getTableHeader().setBackground(new Color(51,51,51));
        lecturersTable.getTableHeader().setForeground(new Color(204,204,204));
        lecturersScrollPane.getViewport().setBackground(new Color(51,51,51));
        
        locationsTable.getTableHeader().setBackground(new Color(51,51,51));
        locationsTable.getTableHeader().setForeground(new Color(204,204,204));
        locationsScrollPane.getViewport().setBackground(new Color(51,51,51));
        
        studentsTable.getTableHeader().setBackground(new Color(51,51,51));
        studentsTable.getTableHeader().setForeground(new Color(204,204,204));
        studentsScrollPane.getViewport().setBackground(new Color(51,51,51));
        
        recordsTable.getTableHeader().setBackground(new Color(51,51,51));
        recordsTable.getTableHeader().setForeground(new Color(204,204,204));
        recordScrollPane.getViewport().setBackground(new Color(51,51,51));
        

        
    }


    public void displayClasses()
    {
        String[][] resultDisplayClasses = dataBase.displayClasses();
        
 
        for (String[] resultDisplayClasse : resultDisplayClasses) {
            modelClasses.addRow(new Object[]{resultDisplayClasse[0], resultDisplayClasse[1], resultDisplayClasse[2], resultDisplayClasse[3], resultDisplayClasse[4], resultDisplayClasse[5], resultDisplayClasse[6], resultDisplayClasse[7], resultDisplayClasse[8], resultDisplayClasse[9], resultDisplayClasse[10]});
        }
        
    }
    
    public void displayLecturers() {
        String[][] resultDisplayLecturers = dataBase.displayLecturers();
        
        for (String[] resultDisplayLecturer : resultDisplayLecturers) {
            modelLecturers.addRow(new Object[]{resultDisplayLecturer[0], resultDisplayLecturer[1], resultDisplayLecturer[2], resultDisplayLecturer[3], resultDisplayLecturer[4]});
        }
    }
    
    public void displayLocations() {
        String[][] resultDisplayLocations = dataBase.displayLocations();
        
        for (String[] resultDisplayLocation : resultDisplayLocations) {
            modelLocations.addRow(new Object[]{resultDisplayLocation[0], resultDisplayLocation[1], resultDisplayLocation[2], resultDisplayLocation[3]});
        }        
        
    }
    
    public void displayCourser() {
        String[][] resultDisplayCourser = dataBase.displayCourses();
        
        for (String[] resultDisplayCourser1 : resultDisplayCourser) {
            modelCourses.addRow(new Object[]{resultDisplayCourser1[0], resultDisplayCourser1[1]});
        }
    }
    
    public void displayStudents() {
        String[][] resultDisplayStudents = dataBase.displayStudents();
        
        for (String[] resultDisplayStudent : resultDisplayStudents) {
            modelStudents.addRow(new Object[]{resultDisplayStudent[0], resultDisplayStudent[1], resultDisplayStudent[2], resultDisplayStudent[3], resultDisplayStudent[4], resultDisplayStudent[5]});
        }
    }
    
    public void displayRecords(){
        String [][] resultDisplayRecords = dataBase.displayRecords();
        
        for (String[] resultDisplayRecord : resultDisplayRecords) {
            modelRecord.addRow(new Object[]{resultDisplayRecord[0], resultDisplayRecord[1]});
        }
    }
    
    public void addCourse() {
        if(!courseNameField.getText().equals("")) {
            String name = courseNameField.getText();
            try {
                dataBase.addCourse(name);

                while (modelCourses.getRowCount() > 0) {
                    modelCourses.removeRow(0);
                }
                displayCourser();

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Nie podany nazwy!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removeCourse() {
       if(!coursesTable.getSelectionModel().isSelectionEmpty()) {
           int column = 0;
           int row = coursesTable.getSelectedRow();
           String id = modelCourses.getValueAt(row, column).toString();

           try {
               dataBase.removeCourse(id);

               while (modelCourses.getRowCount() > 0) {
                   modelCourses.removeRow(0);
               }
               displayCourser();

           } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
           }
       } else {
           JOptionPane.showMessageDialog(null, "Nie wybrano żadnego kursu!", "Błąd", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    public void addLecturer() {
        if(!(titleField.getText().equals("") || firstNameLecturerField.getText().equals("") ||
            lastNameLecturerField.getText().equals("") || peselLecturerField.getText().equals(""))) {
            String[] dane = new String[4];
            dane[0] = titleField.getText();
            dane[1] = firstNameLecturerField.getText();
            dane[2] = lastNameLecturerField.getText();
            dane[3] = peselLecturerField.getText();

            try {
                dataBase.addLecturer(dane);

                while (modelLecturers.getRowCount() > 0) {
                    modelLecturers.removeRow(0);
                }
                displayLecturers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wprowadzono błędne dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public void removeLecturer() {
        
        
        if(!lecturersTable.getSelectionModel().isSelectionEmpty()) {
            int column = 0;
            int row = lecturersTable.getSelectedRow();
            idLecturer = modelLecturers.getValueAt(row, column).toString();

            try {
                dataBase.removeLecturer(idLecturer);

                while (modelLecturers.getRowCount() > 0) {
                    modelLecturers.removeRow(0);
                }
                displayLecturers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Brak prowadzącego!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niewybrany żaden prowadzący!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void modifyLecturer() {

        if(!(titleFieldModify.getText().equals("") || firstNameLecturerFieldModify.getText().equals("") ||
                lastNameLecturerFieldModify.getText().equals("") || peselLecturerFieldModify.getText().equals(""))) {
            if (!lecturersTable.getSelectionModel().isSelectionEmpty()) {
                int column = 0;
                int row = lecturersTable.getSelectedRow();
                idLecturer = modelLecturers.getValueAt(row, column).toString();
            }

            String[] dane = new String[4];
            dane[0] = titleFieldModify.getText();
            dane[1] = firstNameLecturerFieldModify.getText();
            dane[2] = lastNameLecturerFieldModify.getText();
            dane[3] = peselLecturerFieldModify.getText();

            try {
                dataBase.modifyLecturer(idLecturer, dane);

                while (modelLecturers.getRowCount() > 0) {
                    modelLecturers.removeRow(0);
                }
                displayLecturers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Błedne dane lub niewybrany żaden prowadzący!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void addLocation() {
        
        if(!(buildingAddField.getText().equals("") || hallAddField.getText().equals("") || placesAddField.getText().equals(""))) {
            String[] dane = new String[3];

            dane[0] = buildingAddField.getText();
            dane[1] = hallAddField.getText();
            dane[2] = placesAddField.getText();

            try {
                dataBase.addLocation(dane);

                while (modelLocations.getRowCount() > 0) {
                    modelLocations.removeRow(0);
                }
                displayLocations();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Błędne dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removeLocation() {
        if (!locationsTable.getSelectionModel().isSelectionEmpty()) {
            int column = 0;
            int row = locationsTable.getSelectedRow();
            String idLocation = modelLocations.getValueAt(row, column).toString();

            try {
                dataBase.removeLocation(idLocation);

                while (modelLocations.getRowCount() > 0) {
                    modelLocations.removeRow(0);
                }
                displayLocations();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Nieznany błąd!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nie wybrano żadnej sali!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void modifyLocation() {
        if(!(buildingModifyField.getText().equals("") || hallModifyField.getText().equals("") || placesModifyField.getText().equals(""))) {
            
            if (!locationsTable.getSelectionModel().isSelectionEmpty()) {
                int column = 0;
                int row = locationsTable.getSelectedRow();
                idLocation = modelLocations.getValueAt(row, column).toString();
            }
            
            String[] dane = new String[3];
            dane[0] = buildingModifyField.getText();
            dane[1] = hallModifyField.getText();
            dane[2] = placesModifyField.getText();
            
            try {
                
                dataBase.modifyLocation(idLocation, dane);
                
                while(modelLocations.getRowCount() > 0) {
                    modelLocations.removeRow(0);
                }
                displayLocations();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Błędne dane lub nie wybrano żadnej sali!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    public void addStudent() {
        if(!(firstNameStudentAddField.getText().equals("") || lastNameStudentAddField.getText().equals("") || 
                peselStudentAddField.getText().equals("") || passwordStudentAddField.getText().equals(""))) {
            
            String dane[] = new String[4];
            
            dane[0] = firstNameStudentAddField.getText();
            dane[1] = lastNameStudentAddField.getText();
            dane[2] = peselStudentAddField.getText();
            dane[3] = passwordStudentAddField.getText();
            
            try {
                
                dataBase.addStudent(dane);
                
                while(modelStudents.getRowCount() > 0) {
                    modelStudents.removeRow(0);
                }
                displayStudents();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Błędne dane !", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removeStudent() {
        if(!studentsTable.getSelectionModel().isSelectionEmpty()) {
            int column = 0;
            int row = studentsTable.getSelectedRow();
            String idStudent = modelStudents.getValueAt(row, column).toString();
            
            try {
                
                dataBase.removeStudent(idStudent);
                
                while(modelStudents.getRowCount() > 0) {
                    modelStudents.removeRow(0);
                }
                displayStudents();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Nie wybrano żadnego studenta!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void modifyStudent() {
        if(!(firstNameStudentModifyield.getText().equals("") || lastNameStudentModifyField.getText().equals("") ||
                peselStudentModifyField.getText().equals(""))) {
            
            if (!studentsTable.getSelectionModel().isSelectionEmpty()) {
                int column = 0;
                int row = studentsTable.getSelectedRow();
                idStudent = modelStudents.getValueAt(row, column).toString();
            }
            
            String dane[] = new String[3];
            dane[0] = firstNameStudentModifyield.getText();
            dane[1] = lastNameStudentModifyField.getText();
            dane[2] = peselStudentModifyField.getText();
            
        
            
            try {
                dataBase.modifyStudentData(idStudent, dane);
                
                if(!passwordStudentModifyField.getText().equals("")) {
                    String password = passwordStudentModifyField.getText();
                    dataBase.modifyStudentPassword(idStudent, password);
                }
                
                while(modelStudents.getRowCount() > 0) {
                    modelStudents.removeRow(0);
                }
                displayStudents();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Błędne dane lub nie wybrano żadnego studenta!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    public void addRecord() {
        if(!(classesCodeField.getText().equals("") || numberIndexFiels.getText().equals(""))) {
            
            String idStudent = numberIndexFiels.getText();
            String idClasses = classesCodeField.getText();
            
            try {
                dataBase.addRecord(idClasses, idStudent);
                
                while(modelRecord.getRowCount() > 0) {
                    modelRecord.removeRow(0);
                }
                displayRecords();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            
        }else {
            JOptionPane.showMessageDialog(null, "Nie podano żadnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void removeRecord() {
        if (!(classesCodeField.getText().equals("") || numberIndexFiels.getText().equals(""))) {

            String idStudent = numberIndexFiels.getText();
            String idClasses = classesCodeField.getText();

            try {
                dataBase.removeRecord(idClasses, idStudent);

                while (modelRecord.getRowCount() > 0) {
                    modelRecord.removeRow(0);
                }
                displayRecords();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nie podano żadnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addClasses() {
        if(!(ectsClassesAddBox.getText().equals("") || hourClassesAddBox.getText().equals(""))) {
            String[] dane = new String[8];
            
            dane[0] = ectsClassesAddBox.getText();
            dane[1] = typeClassesAddBox.getSelectedItem().toString();
            dane[2] = dayClassesAddBox.getSelectedItem().toString();
            dane[3] = hourClassesAddBox.getText();
            dane[4] = dataBase.findIdCourse(classesAddBox.getSelectedItem().toString());
            
            String[] locationData = locationClassesAddBox.getSelectedItem().toString().split(" / ", 2);
 
            dane[5] = dataBase.findIdLocation(locationData[0], locationData[1]);
            
            String[] lecturersID = lecturerClassesAddBox.getSelectedItem().toString().split(" ", 2);

            dane[6] = lecturersID[0];
            
            dane[7] = weekClassesAddBox.getSelectedItem().toString();
            
            
            try {
                dataBase.addClasses(dane);
                
                while(modelClasses.getRowCount() > 0) {
                    modelClasses.removeRow(0);
                }
                
                displayClasses();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Błędne dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void modifyClasses() {
        if (!classesTable.getSelectionModel().isSelectionEmpty()) {
            String[] dane = new String[8];

            dane[0] = ectsClassesAddBox.getText();
            dane[1] = typeClassesAddBox.getSelectedItem().toString();
            dane[2] = dayClassesAddBox.getSelectedItem().toString();
            dane[3] = hourClassesAddBox.getText();
            dane[4] = dataBase.findIdCourse(classesAddBox.getSelectedItem().toString());

            String[] locationData = locationClassesAddBox.getSelectedItem().toString().split(" / ", 2);

            dane[5] = dataBase.findIdLocation(locationData[0], locationData[1]);

            String[] lecturersID = lecturerClassesAddBox.getSelectedItem().toString().split(" ", 2);

            dane[6] = lecturersID[0];

            dane[7] = weekClassesAddBox.getSelectedItem().toString();

            int row = classesTable.getSelectedRow();
            String idClasses = modelClasses.getValueAt(row, 0).toString();
            
            try {
                dataBase.modifyClasses(idClasses, dane);

                while (modelClasses.getRowCount() > 0) {
                    modelClasses.removeRow(0);
                }

                displayClasses();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nie wybrano żadnych zajęć!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removeClasses() {
        if(!(classesTable.getSelectionModel().isSelectionEmpty())) {
            
            int row = classesTable.getSelectedRow();
            String idClasses = modelClasses.getValueAt(row, 0).toString();
            
            try {
                
                dataBase.removeClasses(idClasses);
                
                while(modelClasses.getRowCount() > 0) {
                    modelClasses.removeRow(0);
                }
                displayClasses();
                
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Nie wybrano żadnych zajęć!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        Zajecia = new javax.swing.JPanel();
        classesScrollPane = new javax.swing.JScrollPane();
        classesTable = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        classesAddBox = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        typeClassesAddBox = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        ectsClassesAddBox = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        dayClassesAddBox = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        weekClassesAddBox = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        hourClassesAddBox = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        locationClassesAddBox = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        lecturerClassesAddBox = new javax.swing.JComboBox<>();
        addClassesButton = new javax.swing.JButton();
        modifyClassesButton = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        removeClassesButton = new javax.swing.JButton();
        Kursy = new javax.swing.JPanel();
        coursesScrollPane = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        courseNameField = new javax.swing.JTextField();
        addCourseButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        removeCourseButton = new javax.swing.JButton();
        Prowadzacy = new javax.swing.JPanel();
        lecturersScrollPane = new javax.swing.JScrollPane();
        lecturersTable = new javax.swing.JTable();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        addLEcturerButton = new javax.swing.JButton();
        firstNameLecturerField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lastNameLecturerField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        peselLecturerField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        removeLecturerButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        titleFieldModify = new javax.swing.JTextField();
        firstNameLecturerFieldModify = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lastNameLecturerFieldModify = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        peselLecturerFieldModify = new javax.swing.JTextField();
        modifyLecturerButton = new javax.swing.JButton();
        Sale = new javax.swing.JPanel();
        locationsScrollPane = new javax.swing.JScrollPane();
        locationsTable = new javax.swing.JTable();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        buildingAddField = new javax.swing.JTextField();
        addLocationButton = new javax.swing.JButton();
        hallAddField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        placesAddField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        removeLocationButton = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        modifyLocationButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        buildingModifyField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        hallModifyField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        placesModifyField = new javax.swing.JTextField();
        Studenci = new javax.swing.JPanel();
        studentsScrollPane = new javax.swing.JScrollPane();
        studentsTable = new javax.swing.JTable();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        firstNameStudentAddField = new javax.swing.JTextField();
        addStudentButton = new javax.swing.JButton();
        lastNameStudentAddField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        peselStudentAddField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        passwordStudentAddField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        removeStudentButton = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        modifyStudentButton = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        firstNameStudentModifyield = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        lastNameStudentModifyField = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        peselStudentModifyField = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        passwordStudentModifyField = new javax.swing.JTextField();
        Zapisy = new javax.swing.JPanel();
        recordScrollPane = new javax.swing.JScrollPane();
        recordsTable = new javax.swing.JTable();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        addRecordButton = new javax.swing.JButton();
        studentBox = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        kursBox = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        classesCodeField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        numberIndexFiels = new javax.swing.JTextField();
        removeRecordButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setForeground(new java.awt.Color(51, 51, 51));
        setMaximumSize(new java.awt.Dimension(1600, 900));
        setPreferredSize(new java.awt.Dimension(1600, 900));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1600, 897));

        tabbedPane.setBackground(new java.awt.Color(51, 51, 51));
        tabbedPane.setForeground(new java.awt.Color(204, 204, 204));

        Zajecia.setBackground(new java.awt.Color(51, 51, 51));
        Zajecia.setForeground(new java.awt.Color(51, 51, 51));
        Zajecia.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ZajeciaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        classesScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        classesScrollPane.setForeground(new java.awt.Color(51, 51, 51));
        classesScrollPane.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                classesScrollPaneAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                classesScrollPaneAncestorRemoved(evt);
            }
        });

        classesTable.setBackground(new java.awt.Color(102, 102, 102));
        classesTable.setForeground(new java.awt.Color(204, 204, 204));
        classesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kod zajęć", "Nazwa kursu", "Rodzaj", "ECTS", "Dzień", "Parzystość", "Godzina", "Budynek", "Sala", "Prowadzący", "Liczba miejsc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        classesTable.setMaximumSize(new java.awt.Dimension(800, 0));
        classesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classesTableMouseClicked(evt);
            }
        });
        classesScrollPane.setViewportView(classesTable);
        if (classesTable.getColumnModel().getColumnCount() > 0) {
            classesTable.getColumnModel().getColumn(0).setMaxWidth(60);
            classesTable.getColumnModel().getColumn(1).setMinWidth(200);
            classesTable.getColumnModel().getColumn(2).setMaxWidth(200);
            classesTable.getColumnModel().getColumn(3).setMaxWidth(60);
            classesTable.getColumnModel().getColumn(4).setMaxWidth(60);
            classesTable.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        jTabbedPane3.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane3.setForeground(new java.awt.Color(204, 204, 204));

        jPanel14.setBackground(new java.awt.Color(51, 51, 51));
        jPanel14.setForeground(new java.awt.Color(255, 255, 255));

        jLabel28.setForeground(new java.awt.Color(204, 204, 204));
        jLabel28.setText("Kurs:");

        classesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        classesAddBox.setForeground(new java.awt.Color(204, 204, 204));

        jLabel29.setForeground(new java.awt.Color(204, 204, 204));
        jLabel29.setText("Typ:");

        typeClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        typeClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));
        typeClassesAddBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wykład", "Ćwiczenia", "Laboratorium", "Projekt", "Seminarium" }));

        jLabel30.setForeground(new java.awt.Color(204, 204, 204));
        jLabel30.setText("ECTS");

        ectsClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        ectsClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));

        jLabel31.setForeground(new java.awt.Color(204, 204, 204));
        jLabel31.setText("Dzień:");

        dayClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        dayClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));
        dayClassesAddBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pn", "Wt", "Sr", "Czw", "Pt" }));

        jLabel32.setForeground(new java.awt.Color(204, 204, 204));
        jLabel32.setText("Tydzień:");

        weekClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        weekClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));
        weekClassesAddBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TP", "TN" }));

        jLabel33.setForeground(new java.awt.Color(204, 204, 204));
        jLabel33.setText("Godzina:");

        hourClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        hourClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));

        jLabel34.setForeground(new java.awt.Color(204, 204, 204));
        jLabel34.setText("Sala:");

        locationClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        locationClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));

        jLabel35.setForeground(new java.awt.Color(204, 204, 204));
        jLabel35.setText("Prowadzący:");

        lecturerClassesAddBox.setBackground(new java.awt.Color(102, 102, 102));
        lecturerClassesAddBox.setForeground(new java.awt.Color(204, 204, 204));

        addClassesButton.setBackground(new java.awt.Color(102, 102, 102));
        addClassesButton.setForeground(new java.awt.Color(204, 204, 204));
        addClassesButton.setText("Dodaj");
        addClassesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClassesButtonActionPerformed(evt);
            }
        });

        modifyClassesButton.setBackground(new java.awt.Color(102, 102, 102));
        modifyClassesButton.setForeground(new java.awt.Color(204, 204, 204));
        modifyClassesButton.setText("Modyfikuj");
        modifyClassesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyClassesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(18, 18, 18)
                        .addComponent(lecturerClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(classesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(typeClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(ectsClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(dayClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(weekClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(hourClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(locationClassesAddBox, 0, 258, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addClassesButton)
                .addGap(27, 27, 27)
                .addComponent(modifyClassesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(classesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(typeClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(ectsClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(dayClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(weekClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(hourClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(locationClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(lecturerClassesAddBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addClassesButton)
                    .addComponent(modifyClassesButton))
                .addGap(43, 43, 43))
        );

        jTabbedPane3.addTab("Dodaj zajęcia", jPanel14);

        jPanel15.setBackground(new java.awt.Color(51, 51, 51));

        removeClassesButton.setBackground(new java.awt.Color(102, 102, 102));
        removeClassesButton.setForeground(new java.awt.Color(204, 204, 204));
        removeClassesButton.setText("Usuń");
        removeClassesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeClassesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeClassesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1427, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(removeClassesButton)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Usuń zajęcia", jPanel15);

        javax.swing.GroupLayout ZajeciaLayout = new javax.swing.GroupLayout(Zajecia);
        Zajecia.setLayout(ZajeciaLayout);
        ZajeciaLayout.setHorizontalGroup(
            ZajeciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(classesScrollPane)
            .addComponent(jTabbedPane3)
        );
        ZajeciaLayout.setVerticalGroup(
            ZajeciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ZajeciaLayout.createSequentialGroup()
                .addComponent(classesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPane.addTab("Zajęcia", Zajecia);

        Kursy.setBackground(new java.awt.Color(51, 51, 51));
        Kursy.setForeground(new java.awt.Color(51, 51, 51));
        Kursy.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                KursyAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                KursyAncestorRemoved(evt);
            }
        });

        coursesScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        coursesScrollPane.setForeground(new java.awt.Color(51, 51, 51));

        coursesTable.setBackground(new java.awt.Color(102, 102, 102));
        coursesTable.setForeground(new java.awt.Color(204, 204, 204));
        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Kursy", "Nazwa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        coursesScrollPane.setViewportView(coursesTable);

        jTabbedPane5.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane5.setForeground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Nazwa:");

        courseNameField.setBackground(new java.awt.Color(102, 102, 102));
        courseNameField.setForeground(new java.awt.Color(204, 204, 204));
        courseNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                courseNameFieldKeyPressed(evt);
            }
        });

        addCourseButton.setBackground(new java.awt.Color(102, 102, 102));
        addCourseButton.setForeground(new java.awt.Color(204, 204, 204));
        addCourseButton.setText("Dodaj");
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(courseNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addCourseButton))
                .addContainerGap(1157, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(courseNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(addCourseButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane5.addTab("Dodaj kurs", jPanel2);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        removeCourseButton.setBackground(new java.awt.Color(102, 102, 102));
        removeCourseButton.setForeground(new java.awt.Color(204, 204, 204));
        removeCourseButton.setText("Usuń");
        removeCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCourseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeCourseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1459, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(removeCourseButton)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Usuń kurs", jPanel3);

        javax.swing.GroupLayout KursyLayout = new javax.swing.GroupLayout(Kursy);
        Kursy.setLayout(KursyLayout);
        KursyLayout.setHorizontalGroup(
            KursyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(coursesScrollPane)
            .addComponent(jTabbedPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        KursyLayout.setVerticalGroup(
            KursyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KursyLayout.createSequentialGroup()
                .addComponent(coursesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Kursy", Kursy);

        Prowadzacy.setBackground(new java.awt.Color(51, 51, 51));
        Prowadzacy.setForeground(new java.awt.Color(51, 51, 51));

        lecturersScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        lecturersScrollPane.setForeground(new java.awt.Color(51, 51, 51));

        lecturersTable.setBackground(new java.awt.Color(102, 102, 102));
        lecturersTable.setForeground(new java.awt.Color(204, 204, 204));
        lecturersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id prowadzącego", "Tytuł", "Imię", "Nazwisko", "Pesel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lecturersTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lecturersTableAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                lecturersTableAncestorRemoved(evt);
            }
        });
        lecturersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lecturersTableMouseClicked(evt);
            }
        });
        lecturersScrollPane.setViewportView(lecturersTable);

        jTabbedPane6.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane6.setForeground(new java.awt.Color(204, 204, 204));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Tytuł");
        jLabel3.setToolTipText("");

        titleField.setBackground(new java.awt.Color(102, 102, 102));
        titleField.setForeground(new java.awt.Color(204, 204, 204));

        addLEcturerButton.setBackground(new java.awt.Color(102, 102, 102));
        addLEcturerButton.setForeground(new java.awt.Color(204, 204, 204));
        addLEcturerButton.setText("Dodaj");
        addLEcturerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLEcturerButtonActionPerformed(evt);
            }
        });

        firstNameLecturerField.setBackground(new java.awt.Color(102, 102, 102));
        firstNameLecturerField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Imię:");
        jLabel5.setToolTipText("");

        lastNameLecturerField.setBackground(new java.awt.Color(102, 102, 102));
        lastNameLecturerField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Nazwisko:");
        jLabel6.setToolTipText("");

        peselLecturerField.setBackground(new java.awt.Color(102, 102, 102));
        peselLecturerField.setForeground(new java.awt.Color(204, 204, 204));
        peselLecturerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                peselLecturerFieldKeyPressed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Pesel:");
        jLabel7.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(firstNameLecturerField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameLecturerField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(peselLecturerField, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addLEcturerButton))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(peselLecturerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(firstNameLecturerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(lastNameLecturerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(addLEcturerButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane6.addTab("Dodaj prowadzącego", jPanel4);

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        removeLecturerButton.setBackground(new java.awt.Color(102, 102, 102));
        removeLecturerButton.setForeground(new java.awt.Color(204, 204, 204));
        removeLecturerButton.setText("Usuń");
        removeLecturerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLecturerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(removeLecturerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1444, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(removeLecturerButton)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Usuń prowadzącego", jPanel5);

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setForeground(new java.awt.Color(51, 51, 51));

        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Tytuł");
        jLabel4.setToolTipText("");

        titleFieldModify.setBackground(new java.awt.Color(102, 102, 102));
        titleFieldModify.setForeground(new java.awt.Color(204, 204, 204));
        titleFieldModify.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                titleFieldModifyKeyPressed(evt);
            }
        });

        firstNameLecturerFieldModify.setBackground(new java.awt.Color(102, 102, 102));
        firstNameLecturerFieldModify.setForeground(new java.awt.Color(204, 204, 204));
        firstNameLecturerFieldModify.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                firstNameLecturerFieldModifyKeyPressed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Imię:");
        jLabel8.setToolTipText("");

        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Nazwisko:");
        jLabel9.setToolTipText("");

        lastNameLecturerFieldModify.setBackground(new java.awt.Color(102, 102, 102));
        lastNameLecturerFieldModify.setForeground(new java.awt.Color(204, 204, 204));
        lastNameLecturerFieldModify.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lastNameLecturerFieldModifyKeyPressed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("Pesel:");
        jLabel10.setToolTipText("");

        peselLecturerFieldModify.setBackground(new java.awt.Color(102, 102, 102));
        peselLecturerFieldModify.setForeground(new java.awt.Color(204, 204, 204));
        peselLecturerFieldModify.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                peselLecturerFieldModifyKeyPressed(evt);
            }
        });

        modifyLecturerButton.setBackground(new java.awt.Color(102, 102, 102));
        modifyLecturerButton.setForeground(new java.awt.Color(204, 204, 204));
        modifyLecturerButton.setText("Modyfikuj");
        modifyLecturerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyLecturerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(titleFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(firstNameLecturerFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameLecturerFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(peselLecturerFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(modifyLecturerButton))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(peselLecturerFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(titleFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(firstNameLecturerFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(lastNameLecturerFieldModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(modifyLecturerButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane6.addTab("Modyfikuj", jPanel6);

        javax.swing.GroupLayout ProwadzacyLayout = new javax.swing.GroupLayout(Prowadzacy);
        Prowadzacy.setLayout(ProwadzacyLayout);
        ProwadzacyLayout.setHorizontalGroup(
            ProwadzacyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lecturersScrollPane)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProwadzacyLayout.createSequentialGroup()
                .addComponent(jTabbedPane6)
                .addContainerGap())
        );
        ProwadzacyLayout.setVerticalGroup(
            ProwadzacyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProwadzacyLayout.createSequentialGroup()
                .addComponent(lecturersScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPane.addTab("Prowadzący", Prowadzacy);

        Sale.setBackground(new java.awt.Color(51, 51, 51));
        Sale.setForeground(new java.awt.Color(51, 51, 51));

        locationsScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        locationsScrollPane.setForeground(new java.awt.Color(51, 51, 51));

        locationsTable.setBackground(new java.awt.Color(102, 102, 102));
        locationsTable.setForeground(new java.awt.Color(204, 204, 204));
        locationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID sali", "Budynek", "Sala", "Liczba miejsc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        locationsTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                locationsTableAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                locationsTableAncestorRemoved(evt);
            }
        });
        locationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                locationsTableMouseClicked(evt);
            }
        });
        locationsScrollPane.setViewportView(locationsTable);

        jTabbedPane7.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane7.setForeground(new java.awt.Color(204, 204, 204));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));

        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("Budynek:");
        jLabel11.setToolTipText("");

        buildingAddField.setBackground(new java.awt.Color(102, 102, 102));
        buildingAddField.setForeground(new java.awt.Color(204, 204, 204));

        addLocationButton.setBackground(new java.awt.Color(102, 102, 102));
        addLocationButton.setForeground(new java.awt.Color(204, 204, 204));
        addLocationButton.setText("Dodaj");
        addLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLocationButtonActionPerformed(evt);
            }
        });

        hallAddField.setBackground(new java.awt.Color(102, 102, 102));
        hallAddField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("Sala:");
        jLabel12.setToolTipText("");

        placesAddField.setBackground(new java.awt.Color(102, 102, 102));
        placesAddField.setForeground(new java.awt.Color(204, 204, 204));
        placesAddField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placesAddFieldKeyPressed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("Liczba miejsc:");
        jLabel13.setToolTipText("");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buildingAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hallAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(placesAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addLocationButton))
                .addContainerGap(253, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(buildingAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hallAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(placesAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(addLocationButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane7.addTab("Dodaj sale", jPanel7);

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));

        removeLocationButton.setBackground(new java.awt.Color(102, 102, 102));
        removeLocationButton.setForeground(new java.awt.Color(204, 204, 204));
        removeLocationButton.setText("Usuń");
        removeLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLocationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(removeLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1450, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(removeLocationButton)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Usuń sale", jPanel8);

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));

        modifyLocationButton.setBackground(new java.awt.Color(102, 102, 102));
        modifyLocationButton.setForeground(new java.awt.Color(204, 204, 204));
        modifyLocationButton.setText("Modyfikuj");
        modifyLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyLocationButtonActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Budynek:");
        jLabel14.setToolTipText("");

        buildingModifyField.setBackground(new java.awt.Color(102, 102, 102));
        buildingModifyField.setForeground(new java.awt.Color(204, 204, 204));
        buildingModifyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buildingModifyFieldKeyPressed(evt);
            }
        });

        jLabel19.setForeground(new java.awt.Color(204, 204, 204));
        jLabel19.setText("Sala:");
        jLabel19.setToolTipText("");

        hallModifyField.setBackground(new java.awt.Color(102, 102, 102));
        hallModifyField.setForeground(new java.awt.Color(204, 204, 204));
        hallModifyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hallModifyFieldKeyPressed(evt);
            }
        });

        jLabel20.setForeground(new java.awt.Color(204, 204, 204));
        jLabel20.setText("Liczba miejsc:");
        jLabel20.setToolTipText("");

        placesModifyField.setBackground(new java.awt.Color(102, 102, 102));
        placesModifyField.setForeground(new java.awt.Color(204, 204, 204));
        placesModifyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placesModifyFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modifyLocationButton)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buildingModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hallModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(placesModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(253, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(buildingModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hallModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(placesModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(modifyLocationButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane7.addTab("Modyfikuj", jPanel9);

        javax.swing.GroupLayout SaleLayout = new javax.swing.GroupLayout(Sale);
        Sale.setLayout(SaleLayout);
        SaleLayout.setHorizontalGroup(
            SaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(SaleLayout.createSequentialGroup()
                .addComponent(locationsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        SaleLayout.setVerticalGroup(
            SaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaleLayout.createSequentialGroup()
                .addComponent(locationsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPane.addTab("Sale", Sale);

        Studenci.setBackground(new java.awt.Color(51, 51, 51));
        Studenci.setForeground(new java.awt.Color(51, 51, 51));

        studentsScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        studentsScrollPane.setForeground(new java.awt.Color(51, 51, 51));

        studentsTable.setBackground(new java.awt.Color(102, 102, 102));
        studentsTable.setForeground(new java.awt.Color(204, 204, 204));
        studentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numer indeksu", "Nazwisko", "Imie", "Ects", "Pesel", "Hasło"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentsTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                studentsTableAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                studentsTableAncestorRemoved(evt);
            }
        });
        studentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentsTableMouseClicked(evt);
            }
        });
        studentsScrollPane.setViewportView(studentsTable);

        jTabbedPane8.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane8.setForeground(new java.awt.Color(204, 204, 204));

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));

        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setText("Imię:");
        jLabel15.setToolTipText("");

        firstNameStudentAddField.setBackground(new java.awt.Color(102, 102, 102));
        firstNameStudentAddField.setForeground(new java.awt.Color(204, 204, 204));

        addStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        addStudentButton.setForeground(new java.awt.Color(204, 204, 204));
        addStudentButton.setText("Dodaj");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });

        lastNameStudentAddField.setBackground(new java.awt.Color(102, 102, 102));
        lastNameStudentAddField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("Nazwisko:");
        jLabel16.setToolTipText("");

        peselStudentAddField.setBackground(new java.awt.Color(102, 102, 102));
        peselStudentAddField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel17.setForeground(new java.awt.Color(204, 204, 204));
        jLabel17.setText("Pesel:");
        jLabel17.setToolTipText("");

        passwordStudentAddField.setBackground(new java.awt.Color(102, 102, 102));
        passwordStudentAddField.setForeground(new java.awt.Color(204, 204, 204));
        passwordStudentAddField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordStudentAddFieldKeyPressed(evt);
            }
        });

        jLabel23.setForeground(new java.awt.Color(204, 204, 204));
        jLabel23.setText("Hasło:");
        jLabel23.setToolTipText("");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addStudentButton)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordStudentAddField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(firstNameStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(peselStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(303, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(firstNameStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(peselStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(passwordStudentAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(addStudentButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane8.addTab("Dodaj studenta", jPanel11);

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));

        removeStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        removeStudentButton.setForeground(new java.awt.Color(204, 204, 204));
        removeStudentButton.setText("Usuń");
        removeStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStudentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(removeStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1459, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(removeStudentButton)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("Usuń studenta", jPanel12);

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));

        modifyStudentButton.setBackground(new java.awt.Color(102, 102, 102));
        modifyStudentButton.setForeground(new java.awt.Color(204, 204, 204));
        modifyStudentButton.setText("Modyfikuj");
        modifyStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyStudentButtonActionPerformed(evt);
            }
        });

        jLabel24.setForeground(new java.awt.Color(204, 204, 204));
        jLabel24.setText("Imię:");
        jLabel24.setToolTipText("");

        firstNameStudentModifyield.setBackground(new java.awt.Color(102, 102, 102));
        firstNameStudentModifyield.setForeground(new java.awt.Color(204, 204, 204));
        firstNameStudentModifyield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                firstNameStudentModifyieldKeyPressed(evt);
            }
        });

        jLabel25.setForeground(new java.awt.Color(204, 204, 204));
        jLabel25.setText("Nazwisko:");
        jLabel25.setToolTipText("");

        lastNameStudentModifyField.setBackground(new java.awt.Color(102, 102, 102));
        lastNameStudentModifyField.setForeground(new java.awt.Color(204, 204, 204));
        lastNameStudentModifyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lastNameStudentModifyFieldKeyPressed(evt);
            }
        });

        jLabel26.setForeground(new java.awt.Color(204, 204, 204));
        jLabel26.setText("Pesel:");
        jLabel26.setToolTipText("");

        peselStudentModifyField.setBackground(new java.awt.Color(102, 102, 102));
        peselStudentModifyField.setForeground(new java.awt.Color(204, 204, 204));
        peselStudentModifyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                peselStudentModifyFieldKeyPressed(evt);
            }
        });

        jLabel27.setForeground(new java.awt.Color(204, 204, 204));
        jLabel27.setText("Hasło:");
        jLabel27.setToolTipText("");

        passwordStudentModifyField.setBackground(new java.awt.Color(102, 102, 102));
        passwordStudentModifyField.setForeground(new java.awt.Color(204, 204, 204));
        passwordStudentModifyField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordStudentModifyFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordStudentModifyField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(firstNameStudentModifyield, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameStudentModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(peselStudentModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(modifyStudentButton))
                .addContainerGap(303, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(firstNameStudentModifyield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameStudentModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(peselStudentModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(passwordStudentModifyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(modifyStudentButton)
                .addGap(26, 26, 26))
        );

        jTabbedPane8.addTab("Modyfikuj", jPanel13);

        javax.swing.GroupLayout StudenciLayout = new javax.swing.GroupLayout(Studenci);
        Studenci.setLayout(StudenciLayout);
        StudenciLayout.setHorizontalGroup(
            StudenciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(StudenciLayout.createSequentialGroup()
                .addComponent(studentsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        StudenciLayout.setVerticalGroup(
            StudenciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudenciLayout.createSequentialGroup()
                .addComponent(studentsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPane.addTab("Studenci", Studenci);

        Zapisy.setBackground(new java.awt.Color(51, 51, 51));
        Zapisy.setForeground(new java.awt.Color(51, 51, 51));
        Zapisy.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ZapisyAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        recordScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        recordScrollPane.setForeground(new java.awt.Color(51, 51, 51));

        recordsTable.setBackground(new java.awt.Color(102, 102, 102));
        recordsTable.setForeground(new java.awt.Color(204, 204, 204));
        recordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numer indeksu", "Kod kursu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recordsTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                recordsTableAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                recordsTableAncestorRemoved(evt);
            }
        });
        recordsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordsTableMouseClicked(evt);
            }
        });
        recordScrollPane.setViewportView(recordsTable);

        jTabbedPane9.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane9.setForeground(new java.awt.Color(204, 204, 204));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Kurs");

        addRecordButton.setBackground(new java.awt.Color(102, 102, 102));
        addRecordButton.setForeground(new java.awt.Color(204, 204, 204));
        addRecordButton.setText("Zapisz");
        addRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRecordButtonActionPerformed(evt);
            }
        });

        studentBox.setBackground(new java.awt.Color(102, 102, 102));
        studentBox.setForeground(new java.awt.Color(204, 204, 204));
        studentBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentBoxActionPerformed(evt);
            }
        });

        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setText("Student:");

        kursBox.setBackground(new java.awt.Color(102, 102, 102));
        kursBox.setForeground(new java.awt.Color(204, 204, 204));
        kursBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kursBoxActionPerformed(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(204, 204, 204));
        jLabel21.setText("Kod kursu:");

        classesCodeField.setBackground(new java.awt.Color(102, 102, 102));
        classesCodeField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel22.setForeground(new java.awt.Color(204, 204, 204));
        jLabel22.setText("Numer indeksu:");

        numberIndexFiels.setBackground(new java.awt.Color(102, 102, 102));
        numberIndexFiels.setForeground(new java.awt.Color(204, 204, 204));
        numberIndexFiels.setToolTipText("");

        removeRecordButton.setBackground(new java.awt.Color(102, 102, 102));
        removeRecordButton.setForeground(new java.awt.Color(204, 204, 204));
        removeRecordButton.setText("Wypisz");
        removeRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRecordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(addRecordButton)
                        .addGap(18, 18, 18)
                        .addComponent(removeRecordButton))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel2))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kursBox, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentBox, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(46, 46, 46)
                                .addComponent(classesCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(numberIndexFiels)))))
                .addContainerGap(709, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kursBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(classesCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(studentBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)
                        .addComponent(numberIndexFiels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRecordButton)
                    .addComponent(removeRecordButton))
                .addGap(26, 26, 26))
        );

        jTabbedPane9.addTab("Opcje", jPanel10);

        javax.swing.GroupLayout ZapisyLayout = new javax.swing.GroupLayout(Zapisy);
        Zapisy.setLayout(ZapisyLayout);
        ZapisyLayout.setHorizontalGroup(
            ZapisyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(ZapisyLayout.createSequentialGroup()
                .addComponent(recordScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ZapisyLayout.setVerticalGroup(
            ZapisyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ZapisyLayout.createSequentialGroup()
                .addComponent(recordScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPane.addTab("Zapisy", Zapisy);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1592, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void classesScrollPaneAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_classesScrollPaneAncestorRemoved
        while(modelClasses.getRowCount() > 0) {
            modelClasses.removeRow(0);
        }
    }//GEN-LAST:event_classesScrollPaneAncestorRemoved

    private void classesScrollPaneAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_classesScrollPaneAncestorAdded
        displayClasses();
    }//GEN-LAST:event_classesScrollPaneAncestorAdded

    private void lecturersTableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lecturersTableAncestorAdded
        displayLecturers();
    }//GEN-LAST:event_lecturersTableAncestorAdded

    private void lecturersTableAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lecturersTableAncestorRemoved
        while(modelLecturers.getRowCount() > 0) {
            modelLecturers.removeRow(0);
        }
    }//GEN-LAST:event_lecturersTableAncestorRemoved

    private void locationsTableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_locationsTableAncestorAdded
        displayLocations();
    }//GEN-LAST:event_locationsTableAncestorAdded

    private void locationsTableAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_locationsTableAncestorRemoved
        while(modelLocations.getRowCount() > 0) {
            modelLocations.removeRow(0);
        }
    }//GEN-LAST:event_locationsTableAncestorRemoved

    private void studentsTableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_studentsTableAncestorAdded
        displayStudents();
    }//GEN-LAST:event_studentsTableAncestorAdded

    private void studentsTableAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_studentsTableAncestorRemoved
        while(modelStudents.getRowCount() > 0) {
            modelStudents.removeRow(0);
        }
    }//GEN-LAST:event_studentsTableAncestorRemoved

    private void recordsTableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_recordsTableAncestorAdded
        displayRecords();
    }//GEN-LAST:event_recordsTableAncestorAdded

    private void recordsTableAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_recordsTableAncestorRemoved
        while(modelRecord.getRowCount() > 0) {
            modelRecord.removeRow(0);
        }
    }//GEN-LAST:event_recordsTableAncestorRemoved

    private void KursyAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_KursyAncestorAdded
        displayCourser();
    }//GEN-LAST:event_KursyAncestorAdded

    private void KursyAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_KursyAncestorRemoved
        while(modelCourses.getRowCount() > 0) {
            modelCourses.removeRow(0);
        }
    }//GEN-LAST:event_KursyAncestorRemoved

    private void addCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCourseButtonActionPerformed
        addCourse();
    }//GEN-LAST:event_addCourseButtonActionPerformed

    private void removeCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCourseButtonActionPerformed
        removeCourse();
    }//GEN-LAST:event_removeCourseButtonActionPerformed

    private void addLEcturerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLEcturerButtonActionPerformed
        addLecturer();
    }//GEN-LAST:event_addLEcturerButtonActionPerformed

    private void removeLecturerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeLecturerButtonActionPerformed
        removeLecturer();         
    }//GEN-LAST:event_removeLecturerButtonActionPerformed

    private void modifyLecturerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyLecturerButtonActionPerformed
        modifyLecturer();
    }//GEN-LAST:event_modifyLecturerButtonActionPerformed

    private void lecturersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecturersTableMouseClicked
        int row = lecturersTable.getSelectedRow();
        
        titleFieldModify.setText(modelLecturers.getValueAt(row, 1).toString());
        firstNameLecturerFieldModify.setText(modelLecturers.getValueAt(row, 2).toString());
        lastNameLecturerFieldModify.setText(modelLecturers.getValueAt(row, 3).toString());
        peselLecturerFieldModify.setText(modelLecturers.getValueAt(row, 4).toString());
    }//GEN-LAST:event_lecturersTableMouseClicked

    private void courseNameFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_courseNameFieldKeyPressed
       if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
          addCourse();
       }
        
    }//GEN-LAST:event_courseNameFieldKeyPressed

    private void peselLecturerFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_peselLecturerFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addLecturer();
        }
    }//GEN-LAST:event_peselLecturerFieldKeyPressed

    private void titleFieldModifyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_titleFieldModifyKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLecturer();
        }
    }//GEN-LAST:event_titleFieldModifyKeyPressed

    private void firstNameLecturerFieldModifyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstNameLecturerFieldModifyKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLecturer();
        }
    }//GEN-LAST:event_firstNameLecturerFieldModifyKeyPressed

    private void lastNameLecturerFieldModifyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastNameLecturerFieldModifyKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLecturer();
        }
    }//GEN-LAST:event_lastNameLecturerFieldModifyKeyPressed

    private void peselLecturerFieldModifyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_peselLecturerFieldModifyKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLecturer();
        }
    }//GEN-LAST:event_peselLecturerFieldModifyKeyPressed

    private void addLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLocationButtonActionPerformed
        addLocation();
    }//GEN-LAST:event_addLocationButtonActionPerformed

    private void removeLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeLocationButtonActionPerformed
        removeLocation();
    }//GEN-LAST:event_removeLocationButtonActionPerformed

    private void modifyLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyLocationButtonActionPerformed
        modifyLocation();
    }//GEN-LAST:event_modifyLocationButtonActionPerformed

    private void locationsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_locationsTableMouseClicked
        int row = locationsTable.getSelectedRow();
        
        buildingModifyField.setText(modelLocations.getValueAt(row, 1).toString());
        hallModifyField.setText(modelLocations.getValueAt(row, 2).toString());
        placesModifyField.setText(modelLocations.getValueAt(row, 3).toString());
    }//GEN-LAST:event_locationsTableMouseClicked

    private void placesAddFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placesAddFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addLocation();
        }
    }//GEN-LAST:event_placesAddFieldKeyPressed

    private void placesModifyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placesModifyFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLocation();
        }
    }//GEN-LAST:event_placesModifyFieldKeyPressed

    private void buildingModifyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buildingModifyFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLocation();
        }
    }//GEN-LAST:event_buildingModifyFieldKeyPressed

    private void hallModifyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hallModifyFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyLocation();
        }
    }//GEN-LAST:event_hallModifyFieldKeyPressed

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed
        addStudent();
    }//GEN-LAST:event_addStudentButtonActionPerformed

    private void removeStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeStudentButtonActionPerformed
        removeStudent();
    }//GEN-LAST:event_removeStudentButtonActionPerformed

    private void passwordStudentAddFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordStudentAddFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addStudent();
        }
    }//GEN-LAST:event_passwordStudentAddFieldKeyPressed

    private void studentsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentsTableMouseClicked
        int row = studentsTable.getSelectedRow();
        
        firstNameStudentModifyield.setText(modelStudents.getValueAt(row, 1).toString());
        lastNameStudentModifyField.setText(modelStudents.getValueAt(row, 2).toString());
        peselStudentModifyField.setText(modelStudents.getValueAt(row, 4).toString());
        
    }//GEN-LAST:event_studentsTableMouseClicked

    private void modifyStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyStudentButtonActionPerformed
        modifyStudent();
    }//GEN-LAST:event_modifyStudentButtonActionPerformed

    private void peselStudentModifyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_peselStudentModifyFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyStudent();
        }
    }//GEN-LAST:event_peselStudentModifyFieldKeyPressed

    private void passwordStudentModifyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordStudentModifyFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyStudent();
        }
    }//GEN-LAST:event_passwordStudentModifyFieldKeyPressed

    private void firstNameStudentModifyieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstNameStudentModifyieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyStudent();
        }
    }//GEN-LAST:event_firstNameStudentModifyieldKeyPressed

    private void lastNameStudentModifyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastNameStudentModifyFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            modifyStudent();
        }
    }//GEN-LAST:event_lastNameStudentModifyFieldKeyPressed

    private void ZapisyAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ZapisyAncestorAdded
        kursBox.removeAllItems();
        studentBox.removeAllItems();
        String[][] courses = dataBase.displayClasses();
        String[][] students = dataBase.displayStudents();
        
        kursBox.addItem("");
        studentBox.addItem("");
        
        for(int i = 0; i < courses.length; i++){
            kursBox.addItem(courses[i][0] + " " + courses[i][1] +" " + courses[i][2] + " " + courses[i][4] + " " + courses[i][5] + " " + courses[i][6]);
        }
        
        for (int i = 0; i < students.length; i++) {
            studentBox.addItem(students[i][0] + " " + students[i][1] + " " + students[i][2]);
        }
    }//GEN-LAST:event_ZapisyAncestorAdded

    private void kursBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kursBoxActionPerformed
        try{
            String[] idKursu = kursBox.getSelectedItem().toString().split(" ",2);
            classesCodeField.setText(idKursu[0]);
        }catch(NullPointerException e) {
            classesCodeField.setText("");
        }
    }//GEN-LAST:event_kursBoxActionPerformed

    private void studentBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentBoxActionPerformed
        try {
            String[] idStudent = studentBox.getSelectedItem().toString().split(" ",2);
            numberIndexFiels.setText(idStudent[0]);
        }catch (NullPointerException e) {
            numberIndexFiels.setText("");
        }
    }//GEN-LAST:event_studentBoxActionPerformed

    private void addRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRecordButtonActionPerformed
        addRecord();
    }//GEN-LAST:event_addRecordButtonActionPerformed

    private void removeRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRecordButtonActionPerformed
        removeRecord();
    }//GEN-LAST:event_removeRecordButtonActionPerformed

    private void recordsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordsTableMouseClicked
        int row = recordsTable.getSelectedRow();
        
        classesCodeField.setText(modelRecord.getValueAt(row, 1).toString());
        numberIndexFiels.setText(modelRecord.getValueAt(row, 0).toString());
    }//GEN-LAST:event_recordsTableMouseClicked

    private void addClassesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClassesButtonActionPerformed
        addClasses();
    }//GEN-LAST:event_addClassesButtonActionPerformed

    private void removeClassesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeClassesButtonActionPerformed
        removeClasses();
    }//GEN-LAST:event_removeClassesButtonActionPerformed

    private void classesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classesTableMouseClicked
        int row = classesTable.getSelectedRow();

        classesAddBox.setSelectedItem(modelClasses.getValueAt(row, 1));
        typeClassesAddBox.setSelectedItem(modelClasses.getValueAt(row, 2));
        ectsClassesAddBox.setText(modelClasses.getValueAt(row, 3).toString());
        dayClassesAddBox.setSelectedItem(modelClasses.getValueAt(row, 4));
        weekClassesAddBox.setSelectedItem(modelClasses.getValueAt(row, 5));
        hourClassesAddBox.setText(modelClasses.getValueAt(row, 6).toString());
        
        String location = modelClasses.getValueAt(row, 7).toString() + " / " + modelClasses.getValueAt(row, 8).toString();
        locationClassesAddBox.setSelectedItem(location);
        
        lecturerClassesAddBox.setSelectedItem(modelClasses.getValueAt(row, 9));
        
    }//GEN-LAST:event_classesTableMouseClicked

    private void modifyClassesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyClassesButtonActionPerformed
        modifyClasses();
    }//GEN-LAST:event_modifyClassesButtonActionPerformed

    private void ZajeciaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ZajeciaAncestorAdded
        classesAddBox.removeAllItems();
        locationClassesAddBox.removeAllItems();
        lecturerClassesAddBox.removeAllItems();

        String[][] courses = dataBase.displayCourses();
        String[][] locations = dataBase.displayLocations();
        String[][] lecturers = dataBase.displayLecturers();

        for (int i = 0; i < courses.length; i++) {
            classesAddBox.addItem(courses[i][1]);
        }

        for (int i = 0; i < locations.length; i++) {
            locationClassesAddBox.addItem(locations[i][1] + " / " + locations[i][2]);
        }

        for (int i = 0; i < lecturers.length; i++) {
            lecturerClassesAddBox.addItem(lecturers[i][0] + " " + lecturers[i][1] + " " + lecturers[i][2] + " " + lecturers[i][3]);
        }
    }//GEN-LAST:event_ZajeciaAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Kursy;
    private javax.swing.JPanel Prowadzacy;
    private javax.swing.JPanel Sale;
    private javax.swing.JPanel Studenci;
    private javax.swing.JPanel Zajecia;
    private javax.swing.JPanel Zapisy;
    private javax.swing.JButton addClassesButton;
    private javax.swing.JButton addCourseButton;
    private javax.swing.JButton addLEcturerButton;
    private javax.swing.JButton addLocationButton;
    private javax.swing.JButton addRecordButton;
    private javax.swing.JButton addStudentButton;
    private javax.swing.JTextField buildingAddField;
    private javax.swing.JTextField buildingModifyField;
    private javax.swing.JComboBox<String> classesAddBox;
    private javax.swing.JTextField classesCodeField;
    private javax.swing.JScrollPane classesScrollPane;
    private javax.swing.JTable classesTable;
    private javax.swing.JTextField courseNameField;
    private javax.swing.JScrollPane coursesScrollPane;
    private javax.swing.JTable coursesTable;
    private javax.swing.JComboBox<String> dayClassesAddBox;
    private javax.swing.JTextField ectsClassesAddBox;
    private javax.swing.JTextField firstNameLecturerField;
    private javax.swing.JTextField firstNameLecturerFieldModify;
    private javax.swing.JTextField firstNameStudentAddField;
    private javax.swing.JTextField firstNameStudentModifyield;
    private javax.swing.JTextField hallAddField;
    private javax.swing.JTextField hallModifyField;
    private javax.swing.JTextField hourClassesAddBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JComboBox<String> kursBox;
    private javax.swing.JTextField lastNameLecturerField;
    private javax.swing.JTextField lastNameLecturerFieldModify;
    private javax.swing.JTextField lastNameStudentAddField;
    private javax.swing.JTextField lastNameStudentModifyField;
    private javax.swing.JComboBox<String> lecturerClassesAddBox;
    private javax.swing.JScrollPane lecturersScrollPane;
    private javax.swing.JTable lecturersTable;
    private javax.swing.JComboBox<String> locationClassesAddBox;
    private javax.swing.JScrollPane locationsScrollPane;
    private javax.swing.JTable locationsTable;
    private javax.swing.JButton modifyClassesButton;
    private javax.swing.JButton modifyLecturerButton;
    private javax.swing.JButton modifyLocationButton;
    private javax.swing.JButton modifyStudentButton;
    private javax.swing.JTextField numberIndexFiels;
    private javax.swing.JTextField passwordStudentAddField;
    private javax.swing.JTextField passwordStudentModifyField;
    private javax.swing.JTextField peselLecturerField;
    private javax.swing.JTextField peselLecturerFieldModify;
    private javax.swing.JTextField peselStudentAddField;
    private javax.swing.JTextField peselStudentModifyField;
    private javax.swing.JTextField placesAddField;
    private javax.swing.JTextField placesModifyField;
    private javax.swing.JScrollPane recordScrollPane;
    private javax.swing.JTable recordsTable;
    private javax.swing.JButton removeClassesButton;
    private javax.swing.JButton removeCourseButton;
    private javax.swing.JButton removeLecturerButton;
    private javax.swing.JButton removeLocationButton;
    private javax.swing.JButton removeRecordButton;
    private javax.swing.JButton removeStudentButton;
    private javax.swing.JComboBox<String> studentBox;
    private javax.swing.JScrollPane studentsScrollPane;
    private javax.swing.JTable studentsTable;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField titleFieldModify;
    private javax.swing.JComboBox<String> typeClassesAddBox;
    private javax.swing.JComboBox<String> weekClassesAddBox;
    // End of variables declaration//GEN-END:variables
}
