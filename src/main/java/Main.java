import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application
{
    private TabPane tabPane;

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            BorderPane mainPane = new BorderPane();
            Group root = new Group();
            primaryStage.setTitle("My Carbon Awareness Effort");
            Scene scene = new Scene(root, 700, 410);

            setupTabPane();

            mainPane.setCenter(tabPane);
            mainPane.prefHeightProperty().bind(scene.heightProperty());
            mainPane.prefWidthProperty().bind(scene.widthProperty());

            root.getChildren().add(mainPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setupTabPane()
    {
        tabPane = new TabPane();
        Tab introduction = new Tab();
        Tab activityManagement = new Tab();
        Tab results = new Tab();

        introduction.setText("Introduction");
        activityManagement.setText("Activity Management");
        results.setText("Results");

        tabPane.getTabs().addAll(introduction, activityManagement, results);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
