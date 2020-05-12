package View;

import javafx.scene.control.Alert;

/**
 * <p>A simple helper class for displaying a generic popup window.</p>
 */
class PopupWindow
{
    /**
     * <p>The constructor instantiates a modal alert window tailored to the parameters passed to it.</p>
     *
     * @param title   the title that will be applied to the alert window
     * @param message the message that will be displayed within the alert window
     */
    PopupWindow(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
