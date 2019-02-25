/*
 * Carl Miller
 * This class handles data from the tutor table.
 * It allows for load and insert
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tutor extends Model {

    private int id = 0;
    private String name;
    private String email;
    private int subject_id;
    // corresponds to a database table
    public static final String TABLE = "tutor";

    // must have default constructor accessible to the package
    Tutor() {
    }
    public Tutor(String name, String email, int subject_id){
        this.name = name;
        this.email = email;
        this.subject_id = subject_id;
    }
    //Getters
    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getSubject_id() {
        return subject_id;
    }
    
    //Helper
    public Subject getSubject() {
        return ORM.load(Subject.class, subject_id);

    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            name = rs.getString("name");
            email = rs.getString("email");
            subject_id = rs.getInt("subject_id");
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
                    "insert into %s (name,email,subject_id) values (?,?,?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
            st.setString(++i, email);
            st.setInt(++i, subject_id);
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
        return String.format("(%s,%s,%s,%s)", id, name, email, subject_id);
    }
}
