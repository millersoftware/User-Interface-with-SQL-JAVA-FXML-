/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Carl Miller
 */
public class Student extends Model {

    private int id = 0;
    private String name;
    private String enrolled;

    // corresponds to a database table
    public static final String TABLE = "student";

    // must have default constructor accessible to the package
    Student() {
    }

    public Student(String name, String enrolled) {
        this.name = name;
        this.enrolled = enrolled;
    }

    //Getters
    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnrolled() {
        return enrolled;
    }

    //Complex helper(Needs to go through all 3 classes to get info it needs)
    public Collection<String> getSubjects() {

        Collection<Interaction> interactions = ORM.findAll(Interaction.class,
                "where student_id=?", new Object[]{this.getId()});

        Collection<Tutor> tutors = new ArrayList<Tutor>();
        Collection<Subject> subjects = new ArrayList<Subject>();
        Collection<String> name = new ArrayList<String>();

        for (Interaction interaction : interactions) {
            tutors.add(ORM.load(Tutor.class, interaction.getTutor_id()));
        }
        for (Tutor tutoring : tutors) {
            subjects.add(ORM.load(Subject.class, tutoring.getSubject_id()));
        }
        for (Subject subject : subjects) {
            name.add(subject.getName());
        }

        return name;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            name = rs.getString("name");
            enrolled = rs.getString("enrolled");
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
                    "insert into %s (name, enrolled) values (?,?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
            st.setString(++i, enrolled);
            st.executeUpdate();
            id = ORM.getMaxId(TABLE);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getClass() + ":" + ex.getMessage());
        }
    }

    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() {
        // use this to make you aware that it is being called
        // discard throw statement once you start coding here
        throw new UnsupportedOperationException("update in " + this.getClass());
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", id, name, enrolled);
    }
}
