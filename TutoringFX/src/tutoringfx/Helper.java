/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoringfx;

import models.Student;
import models.Tutor;

/**
 *
 * @author Carl Miller
 */
public class Helper {

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

    public static java.sql.Date currentDate() {
        long now = new java.util.Date().getTime();
        java.sql.Date date = new java.sql.Date(now);
        return date;
    }
}
