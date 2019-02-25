/*
/*
 * Carl Miller
 * This class provides controll when the add button is hit for tutor
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ORM;
import models.Subject;
import models.Tutor;
import org.apache.commons.validator.EmailValidator;

public class AddTutorController implements Initializable {

    //===================== add this data member and setter
    private TutoringController mainController;

    void setMainController(TutoringController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private TextField first_name;

    @FXML
    private TextField last_name;

    @FXML
    private TextField tutor_email;

    @FXML
    private ComboBox<String> tutor_subject;

    //Control for when add button is hit
    @FXML
    private void add(Event event) {
        try {
            //Handles name
            String firstname = first_name.getText().trim();
            if (firstname.equals("")) {
                throw new ExpectedException("First Name field is empty");
            }
            String lastname = last_name.getText().trim();
            if (lastname.equals("")) {
                throw new ExpectedException("Last Name field is empty");
            }
            String name = lastname + "," + firstname;

            //Handles email
            String temail = tutor_email.getText().trim();
            EmailValidator validator = EmailValidator.getInstance();
            String email = temail;
            boolean isValid = validator.isValid(email);
            if (isValid == false) {
                throw new ExpectedException("Invalid email");
            }

            //Handles subject
            String tsubject = tutor_subject.getSelectionModel().getSelectedItem();
            Subject subjectWithName
                    = ORM.findOne(Subject.class, "where name=?", new Object[]{tsubject});

            //Check if unique
            Tutor tutorWithName
                    = ORM.findOne(Tutor.class, "where name=?", new Object[]{name});
            if (tutorWithName != null) {
                throw new ExpectedException("Existing tutor with same name");
            }
            // put it into the database
            Tutor newTutor = new Tutor(name, email, subjectWithName.getId());
            ORM.store(newTutor);

            // access the features of LibraryController
            ListView<Tutor> tutorList = mainController.getTutorList();
            TextArea display = mainController.getDisplay();

            // reload tutorlist from database
            tutorList.getItems().clear();
            Collection<Tutor> tutors = ORM.findAll(Tutor.class);
            for (Tutor tutor : tutors) {
                tutorList.getItems().add(tutor);
            }

            // select in list and scroll to added book
            tutorList.getSelectionModel().select(newTutor);
            tutorList.scrollTo(newTutor);

            tutorList.requestFocus();
            mainController.setLastFocused(tutorList);

            // set text display to added tutor
            display.setText(Helper.info(newTutor));
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
        Collection<Subject> subjects = ORM.findAll(Subject.class);
        for (Subject subject : subjects) {
            tutor_subject.getItems().add(subject.getName());
        }
    }

}
