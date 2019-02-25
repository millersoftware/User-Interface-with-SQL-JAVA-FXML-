/*
/*
 * Carl Miller
 * This class provides controll when the add button is hit for subject
 * It manipulates various elements in the fxml file
 */
package tutoringfx;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ORM;
import models.Subject;

public class AddSubjectController implements Initializable {

    private TutoringController mainController;

    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private TextArea current_subjects;

    @FXML
    private TextField new_subject;

    //Control for when add button is hit
    @FXML
    private void add(Event event) {
        try {
            //Handles name
            String subject = new_subject.getText().trim();
            if (subject.equals("")) {
                throw new ExpectedException("First Name field is empty");
            }

            //Check if unique
            Subject subjectWithName
                    = ORM.findOne(Subject.class, "where name=?", new Object[]{subject});
            if (subjectWithName != null) {
                throw new ExpectedException("Subject already exists");
            }
            // put it into the database
            Subject newSubject = new Subject(subject);
            ORM.store(newSubject);

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
        Collection<Subject> subjects = ORM.findAll(Subject.class);
        StringBuilder names = new StringBuilder();
        for (Subject subject : subjects) {
            names.append(subject.getName() + "\n");
        }
        current_subjects.setText(names.toString());
    }

}
