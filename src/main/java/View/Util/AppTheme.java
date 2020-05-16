package View.Util;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AppTheme
{
    public static void set(Label label)
    {
        label.setStyle(
                "-fx-min-width: 100;" + /*"-fx-background-color: #F8B195;" +*/
                "-fx-padding: 5 5 5 5;" +
                "-fx-font-size: 12px;"
        );
    }

    public static void set(Button button)
    {
        button.setStyle(
                "-fx-min-width: 100;" + /*"-fx-background-color: #dc143c;" +*/
                "-fx-padding: 5 5 5 5;" +
                "-fx-font-size: 13px;" /*"-fx-text-fill: #ffffff;"*/
        );
    }

    public static void setBig(Button button)
    {
        button.setStyle(
                "-fx-min-width: 200;" + /*"-fx-background-color: #dc143c;" +*/
                "-fx-min-height: 50;" +
                "-fx-padding: 5 5 5 5;" +
                "-fx-font-size: 21px;" /*"-fx-text-fill: #ffffff;"*/
        );
    }

}
