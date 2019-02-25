/*
 * Carl Miller
 * This class handles data from the interacion table.
 * It allows for load, insert and update
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interaction extends Model {

    private int id = 0;
    private int tutor_id;
    private int student_id;
    private String report;

    // corresponds to a database table
    public static final String TABLE = "interaction";

    // must have default constructor accessible to the package
    Interaction() {
    }

    public Interaction(Tutor tutor, Student student) {
        this.tutor_id = tutor.getId();
        this.student_id = student.getId();
    }

    public Interaction(Tutor tutor, Student student, String report) {
        this.tutor_id = tutor.getId();
        this.student_id = student.getId();
        this.report = report;
    }

    public Interaction(Student student, Tutor tutor) {
        this.tutor_id = tutor.getId();
        this.student_id = student.getId();
    }

    public Interaction(Student student, Tutor tutor, String report) {
        this.tutor_id = tutor.getId();
        this.student_id = student.getId();
        this.report = report;
    }

    //Getters
    @Override
    public int getId() {
        return id;
    }

    public int getTutor_id() {
        return tutor_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getReport() {
        return report;
    }

    //Helpers
    public Student getStudent() {
        return ORM.load(Student.class, student_id);
    }

    public Tutor getTutor() {
        return ORM.load(Tutor.class, tutor_id);
    }

    //Setters
    public void setReport(String report) {
        this.report = report;
    }

    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            student_id = rs.getInt("student_id");
            tutor_id = rs.getInt("tutor_id");
            report = rs.getString("report");
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }

    // user for INSERT operations in ORM.store (for new record)
    @Override
    void insert() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                    "insert into %s (student_id, tutor_id, report) values (?,?,?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setInt(++i, student_id);
            st.setInt(++i, tutor_id);
            st.setString(++i, report);
            st.executeUpdate();
            id = ORM.getMaxId(TABLE);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }

    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() {
        Connection cx = ORM.connection();
        try {
            String sql = String.format(
                    "update %s set report=? where id=?", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, report);
            st.setInt(++i, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }

    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", id, report);
    }
}
