/*
 * Carl Miller
 * Provides formatting for information
 */
package tutoringfx;

import models.Student;
import models.Tutor;

public class Helper {

    //Provides student info
    public static String info(Student student) {

        return String.format(
                "id: %s\n"
                + "name: %s\n"
                + "enrolled: %s\n"
                + "Tutored in: %s\n",
                student.getId(),
                student.getName(),
                student.getEnrolled(),
                student.getSubjects().toString()
        );
    }

    //Provides tutor info
    public static String info(Tutor tutor) {
        return String.format(
                "id: %s\n"
                + "name: %s\n"
                + "email: %s\n"
                + "subject_id: %s\n",
                tutor.getId(),
                tutor.getName(),
                tutor.getEmail(),
                tutor.getSubject().getName()
        );
    }

    //Provides current date
    public static java.sql.Date currentDate() {
        long now = new java.util.Date().getTime();
        java.sql.Date date = new java.sql.Date(now);
        return date;
    }
}
