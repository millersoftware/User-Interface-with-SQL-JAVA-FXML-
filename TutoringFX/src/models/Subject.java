package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject extends Model {

    private int id = 0;
    private String name;

    // corresponds to a database table
    public static final String TABLE = "subject";

    // must have default constructor accessible to the package
    Subject() {
    }
    public Subject(String name){
        this.name = name;
    }

    //Getters
    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
   

    public void setName(String name) {
        this.name = name;
    }

    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) {
        try {
            id = rs.getInt("id");
            name = rs.getString("name");
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
                    "insert into %s (name) values (?)", TABLE);
            PreparedStatement st = cx.prepareStatement(sql);
            int i = 0;
            st.setString(++i, name);
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
        return String.format("(%s,%s)", id, name);
    }
}
