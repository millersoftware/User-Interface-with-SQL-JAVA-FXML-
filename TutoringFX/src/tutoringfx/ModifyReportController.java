/*
 * Carl Miller
 * This class provides controll when the edit button is hit for report
 * It manipulates various elements in the fxml file
 */
package tutoringfx;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Interaction;
import models.ORM;
import models.Student;
import models.Tutor;

public class ModifyReportController implements Initializable {

    //===================== add this data member and setter
    private TutoringController mainController;
    public boolean change = false;

    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    // data member and setter for modify
    private Student studentToModify;

    //Local storing of student class/inital setup
    void setStudentToModify(Student student) {
        this.studentToModify = student;

        // initialize fields from book to modify
        student_name.setText(student.getName());

    }

    // data member and setter for modify
    private Tutor tutorToModify;

    //Local storing of tutor class/inital setup
    void setTutorToModify(Tutor tutor) {
        this.tutorToModify = tutor;

        // initialize fields from book to modify
        tutor_name.setText(tutor.getName());
        tutor_subject.setText(tutor.getSubject().getName());

    }

    // data member and setter for modify
    private Interaction interactionToModify;

    //Local storing of interaction class/inital setup
    void setInteractionToModify(Interaction interact) {
        this.interactionToModify = interact;

        // initialize fields from book to modify
        link_report.setText(interact.getReport());
        change = false; //above triggers add listener, so set it back to false

    }

    @FXML
    private TextField tutor_name;

    @FXML
    private TextField student_name;

    @FXML
    private TextField tutor_subject;

    @FXML
    private TextArea link_report;

    //Control for save changes button
    @FXML
    private void add(Event event) {
        String report = link_report.getText();

        interactionToModify.setReport(report);
        ORM.store(interactionToModify);

        TextArea display = mainController.getDisplay();
        display.setText(interactionToModify.getReport());

        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    //Cotrol for cancel with event listener check
    @FXML
    private void cancel(Event event) {

        if (change == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to exit this dialog?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                event.consume();
            } else {
                ((Button) event.getSource()).getScene().getWindow().hide();
            }
        } else {
            ((Button) event.getSource()).getScene().getWindow().hide();

        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Event listener
        link_report.textProperty().addListener((obs, old, niu) -> {
            change = true;
        });
    }

}
