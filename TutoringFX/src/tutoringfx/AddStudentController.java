/*
 * Carl Miller
 * This class provides controll when the add button is hit for student
 * It manipulates various elements in the fxml file
 */
package tutoringfx;

import java.net.URL;
import java.sql.Date;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ORM;
import models.Student;

public class AddStudentController implements Initializable {

    //===================== add this data member and setter
    private TutoringController mainController;

    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private TextField first_name;

    @FXML
    private TextField last_name;

    //Control for when add button is hit
    @FXML
    private void add(Event event) {
        try {
            //Get Text fields and input validation
            String firstname = first_name.getText().trim();
            if (firstname.equals("")) {
                throw new ExpectedException("First Name field is empty");
            }
            String lastname = last_name.getText().trim();
            if (lastname.equals("")) {
                throw new ExpectedException("Last Name field is empty");
            }

            String name = lastname + "," + firstname;
            Date date = Helper.currentDate();

            //Check if unique
            Student studentWithName
                    = ORM.findOne(Student.class, "where name=?", new Object[]{name});
            if (studentWithName != null) {
                throw new ExpectedException("existing student with same name");
            }
            // put it into the database
            Student newStudent = new Student(name, date.toString());
            ORM.store(newStudent);

            // access the features of LibraryController
            ListView<Student> studentList = mainController.getStudentList();
            TextArea display = mainController.getDisplay();

            // reload studentlist from database
            studentList.getItems().clear();
            Collection<Student> students = ORM.findAll(Student.class);
            for (Student student : students) {
                studentList.getItems().add(student);
            }

            // select in list and scroll to added book
            studentList.getSelectionModel().select(newStudent);
            studentList.scrollTo(newStudent);

            studentList.requestFocus();
            mainController.setLastFocused(studentList);

            // set text display to added student
            display.setText(Helper.info(newStudent));
            mainController.order();

            ((Button) event.getSource()).getScene().getWindow().hide();

        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
        }

    }

    //Control for when cancel button is hit
    @FXML
    private void cancel(Event event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
