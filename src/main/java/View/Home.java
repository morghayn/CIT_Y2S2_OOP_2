package View;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Home
{
    // TODO controller here

    public Home() // TODO pass controller instance through
    {
        //this.controller = controller;
    }

    public StackPane setup()
    {
        //ImageView imageView = new ImageView();
        //imageView.setImage(new Image(getClass().getResourceAsStream("/balls.jpg")));

        Button quit = new Button("Quit");
        quit.setOnAction(this::quitProgram);
        AppTheme.set(quit);

        VBox mainColumn = new VBox(50, quit);
        mainColumn.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(mainColumn); // imgview should go in here eventually pls
    }

    private void quitProgram(ActionEvent e)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        // TODO close EMF here
    }

}
