/*
 * Carl Miller
 * This class provides a link between the scene builder and the code.
 * Functions include interactions between the database classes and the scenebuilder.
 */
package tutoringfx;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.DBProps;
import models.Interaction;
import models.ORM;
import models.Student;
import models.Tutor;

public class TutoringController implements Initializable {

    //A variable to keep track of current desired ordering
    private String order = "name";

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    //Control for adding a student(Menu Event)
    @FXML
    private void addStudent(Event event) {
        try {

            // get fxmlLoader
            URL fxml = getClass().getResource("AddStudent.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Add a Student");

            // make it block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();

            // get AddStudent dialog controller from fxmlLoader
            AddStudentController dialogController = fxmlLoader.getController();

            // pass the TutoringController to the dialog controller
            dialogController.setMainController(this);

            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    //Control for adding a tutor(Menu Item)
    @FXML
    private void addTutor(Event event) {
        try {

            // get fxmlLoader
            URL fxml = getClass().getResource("AddTutor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Add a Tutor");

            // make it block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();

            // get AddTutor dialog controller from fxmlLoader
            AddTutorController dialogController = fxmlLoader.getController();

            // pass the TutoringController to the dialog controller
            dialogController.setMainController(this);

            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    //Control for adding a subject(Menu Item)
    @FXML
    private void addSubject(Event event) {
        try {

            // get fxmlLoader
            URL fxml = getClass().getResource("AddSubject.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Add a Subject");

            // make it block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();

            // get AddSubject dialog controller from fxmlLoader
            AddSubjectController dialogController = fxmlLoader.getController();

            // pass the SubjectController to the dialog controller
            dialogController.setMainController(this);

            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    //Menu disabling code for Edit
    @FXML
    private MenuItem modifyItem;

    //Activating code for menu item
    @FXML
    private void activateModifyItem(Event event) {
        //Check if student and tutor are selected
        Student student = studentList.getSelectionModel().getSelectedItem();
        Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
        modifyItem.setDisable(tutor == null || student == null);

        //Checks if its a linked pair
        if (tutor != null && student != null) {
            Interaction interact = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[]{tutor.getId(), student.getId()}
            );
            modifyItem.setDisable(interact == null);
        }

    }

    //Control for updating a report(Menu Item)
    @FXML
    private void modifyReport(Event event) {
        try {
            Student student = studentList.getSelectionModel().getSelectedItem();
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();

            Interaction interact = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[]{tutor.getId(), student.getId()}
            );

            // get fxmlLoader
            URL fxml = getClass().getResource("ModifyReport.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Modify a Report");

            // make dialog block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();
            //====================================================

            // get controller from fxmlLoader (must be defined in FXML file)
            // you will need this if you want to set fields before invocation
            ModifyReportController dialogController = fxmlLoader.getController();

            // pass the TutoringController to the dialog controller
            // so that the dialog can affect changes in the main window 
            dialogController.setMainController(this);

            //============ additional features
            // query window closing
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to exit this dialog?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        event.consume();
                    }
                }
            });

            // set book to be modified in Modify dialog
            dialogController.setStudentToModify(student);
            dialogController.setTutorToModify(tutor);
            dialogController.setInteractionToModify(interact);

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    //Getters
    ListView<Student> getStudentList() {
        return studentList;
    }

    ListView<Tutor> getTutorList() {
        return tutorList;
    }

    TextArea getDisplay() {
        return display;
    }

    //Control for displaying Report(Button)
    @FXML
    private void interactionReport(Event event) {
        try {
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
            Student student = studentList.getSelectionModel().getSelectedItem();

            //Error Checking
            if (tutor == null || student == null) {
                throw new ExpectedException("Must select student and tutor");
            }
            Interaction interact = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[]{tutor.getId(), student.getId()}
            );

            if (interact == null) {
                throw new ExpectedException("This tutor and student are not together");
            }
            if (interact.getReport() == "") {
                display.setText("--- EMPTY ---");
            } else {
                display.setText(interact.getReport());
            }

        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            refocus(event);
        }
    }

    //Control for linking a tutor and a student (Button)
    @FXML
    private void link(Event event) {
        try {
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
            Student student = studentList.getSelectionModel().getSelectedItem();

            //Error Checking
            if (tutor == null || student == null) {
                throw new ExpectedException("Must select student and tutor");
            }
            Interaction interact = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[]{tutor.getId(), student.getId()}
            );
            if (interact != null) {
                throw new ExpectedException("Link exits already");
            }
            if (student.getSubjects().contains(tutor.getSubject().getName())) {
                throw new ExpectedException("Can't have two of the same subject being taught");
            }

            //Store Change
            interact = new Interaction(student, tutor);
            ORM.store(interact);

            Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                    "where student_id=?", new Object[]{student.getId()});

            // clear tutor and student then re add list based on interaction
            tutorIds.clear();
            for (Interaction interaction : interactions) {
                tutorIds.add(interaction.getTutor_id());

            }
            studentIds.clear();
            for (Interaction interaction : interactions) {
                studentIds.add(interaction.getStudent_id());
            }

            studentList.refresh();
            tutorList.refresh();

            //Resets Focus
            lastFocused.requestFocus();
            if (lastFocused == studentList) {
                display.setText(Helper.info(student));
            } else if (lastFocused == tutorList) {
                display.setText(Helper.info(tutor));
            }

        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            refocus(event);
        }
    }

    //Control for removing a tutor and a student link(Button)
    @FXML
    private void removeLink(Event event) {
        try {
            Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
            Student student = studentList.getSelectionModel().getSelectedItem();
            if (tutor == null || student == null) {
                throw new ExpectedException("Must select student and tutor");
            }
            Interaction interact = ORM.findOne(Interaction.class,
                    "where tutor_id=? and student_id=?",
                    new Object[]{tutor.getId(), student.getId()}
            );

            if (interact == null) {
                throw new ExpectedException("Link does not exists");
            }
            ORM.remove(interact);
            clear();

        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            refocus(event);
        }
    }

    //Control for removing a student(Button)
    @FXML
    private void removeStudent(Event event) {
        try {
            Student student = studentList.getSelectionModel().getSelectedItem();

            //Error Checking
            if (student == null) {
                throw new ExpectedException("No student selected");
            }
            Collection<Interaction> interact = ORM.findAll(Interaction.class,
                    "where student_id=?", new Object[]{student.getId()});

            if (interact != null) {
                //Goes through collection for each element
                Iterator<Interaction> iterator = interact.iterator();
                while (iterator.hasNext()) {
                    ORM.remove(iterator.next());
                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            }
            ORM.remove(student);
            studentList.getItems().remove(student);
            clear();

        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            refocus(event);
        }
    }
    //Menu disabling code for Order
    @FXML
    private MenuItem nameItem;
    @FXML
    private MenuItem enrolledItem;

    //Activate menu item when its one or the other
    @FXML
    private void activateOrderItem(Event event) {
        nameItem.setDisable(this.getOrder().equals("name"));
        enrolledItem.setDisable(this.getOrder().equals("enrolled"));
    }

    //Menu Item
    @FXML
    private void orderName(Event event) {
        this.setOrder("name");
        order();

    }

    //Menu Item
    @FXML
    private void orderEnroll(Event event) {
        this.setOrder("enrolled");
        order();

    }

    //Function to order lists based on current selection
    @FXML
    public void order() {

        //Prepare List
        studentList.getItems().clear();
        tutorList.getItems().clear();
        ORM.init(DBProps.getProps());

        //Order Students based on selection
        Collection<Student> students = ORM.findAll(Student.class, "order by " + this.getOrder());
        for (Student student : students) { //Gets info from Student class
            studentList.getItems().add(student);
        }
        //Order Tutors
        Collection<Tutor> tutors = ORM.findAll(Tutor.class, "order by name");
        for (Tutor tutor : tutors) { //Gets info from Tutor class
            tutorList.getItems().add(tutor);
        }

    }

    //Clear Lists(Button)
    @FXML
    private void clear(Event event) {
        tutorList.getSelectionModel().clearSelection();
        studentList.getSelectionModel().clearSelection();
        studentIds.clear();
        tutorIds.clear();
        studentList.refresh();
        tutorList.refresh();
        display.setText("");
        lastFocused = null;
    }

    //Overloaded (used for non events)
    @FXML
    private void clear() {
        tutorList.getSelectionModel().clearSelection();
        studentList.getSelectionModel().clearSelection();
        studentIds.clear();
        tutorIds.clear();
        studentList.refresh();
        tutorList.refresh();
        display.setText("");
        lastFocused = null;
    }
    //Very important information storage of lists
    private final Collection<Integer> studentIds = new HashSet<>();
    private final Collection<Integer> tutorIds = new HashSet<>();

    @FXML
    private ListView<Student> studentList;

    @FXML
    private ListView<Tutor> tutorList;

    @FXML
    private TextArea display;

    private Node lastFocused = null;

    //Gets focus
    void setLastFocused(Node lastFocused) {
        this.lastFocused = lastFocused;
    }

    //requests focus
    @FXML
    private void refocus(Event event) {
        if (lastFocused != null) {
            lastFocused.requestFocus();
        }
    }

    //Student Information(Mouse Click)
    @FXML
    private void studentSelect(Event event) {
        Student student = studentList.getSelectionModel().getSelectedItem();
        if (student == null) {
            refocus(event);
            return;
        }

        lastFocused = studentList;

        // get all studnets of this tutor
        Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                "where student_id=?", new Object[]{student.getId()});

        // set interactions between tutor and students
        tutorIds.clear();
        for (Interaction interaction : interactions) {
            tutorIds.add(interaction.getTutor_id());

        }

        // pick up highlight changes in studnetList
        tutorList.refresh();

        display.setText(Helper.info(student));

    }

    //Tutor Information(Mouse Click)
    @FXML
    private void tutorSelect(Event event) {
        Tutor tutor = tutorList.getSelectionModel().getSelectedItem();
        if (tutor == null) {
            refocus(event);
            return;
        }
        lastFocused = tutorList;

        // get all studnets of this tutor
        Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                "where tutor_id=?", new Object[]{tutor.getId()});

        // set interactions between tutor and students
        studentIds.clear();
        for (Interaction interaction : interactions) {
            studentIds.add(interaction.getStudent_id());
        }

        // pick up highlight changes in studnetList
        studentList.refresh();
        display.setText(Helper.info(tutor));
    }

    //Inital Setup Information
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ORM.init(DBProps.getProps());
        //Fills list from sql database
        Collection<Student> students = ORM.findAll(Student.class, "order by name");
        for (Student student : students) { //Gets info from Student class
            studentList.getItems().add(student);
        }
        //Fills list from sql database
        Collection<Tutor> tutors = ORM.findAll(Tutor.class, "order by name");
        for (Tutor tutor : tutors) { //Gets info from Tutor class
            tutorList.getItems().add(tutor);
        }
        //Gets access to call back class to make format nice
        StudentCellCallBack studentCellCallBack = new StudentCellCallBack();
        studentList.setCellFactory(studentCellCallBack);

        TutorCellCallBack tutorCellCallBack = new TutorCellCallBack();
        tutorList.setCellFactory(tutorCellCallBack);

        //Makes things red when linked by interaction
        studentCellCallBack.setHightlightedIds(studentIds);
        tutorCellCallBack.setHightlightedIds(tutorIds);
    }

}
