/*
 * Carl Miller
 * Makes information for tutor look pretty
 */
package tutoringfx;

import java.util.Collection;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Tutor;

/**
 *
 * @author Carl Miller
 */
public class TutorCellCallBack implements Callback<ListView<Tutor>, ListCell<Tutor>> {

    private Collection<Integer> highlightedIds = null;

    void setHightlightedIds(Collection<Integer> highlightedIds) {
        this.highlightedIds = highlightedIds;
    }

    @Override
    public ListCell<Tutor> call(ListView<Tutor> p) {
        ListCell<Tutor> cell = new ListCell<Tutor>() {
            @Override
            protected void updateItem(Tutor tutor, boolean empty) {
                super.updateItem(tutor, empty);
                if (empty) {
                    this.setText(null);
                    return;
                }

                this.setText(tutor.getName());

                //======== add this section at the end of the "updateItem" member 
                if (highlightedIds == null) {
                    return;
                }

                String css = ""
                        + "-fx-text-fill: #c00;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-style: italic;";

                if (highlightedIds.contains(tutor.getId())) {
                    this.setStyle(css);
                } else {
                    this.setStyle(null);
                }
            }
        };
        return cell;
    }
}
