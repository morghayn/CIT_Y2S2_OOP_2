import Controller.Controller;
import View.Create;
import View.Delete;
import View.Home;
import View.Retrieve;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");
    private Controller controller;
    private TabPane tabPane;

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            BorderPane mainPane = new BorderPane();
            Group root = new Group();
            primaryStage.setTitle("League Management System");
            Scene scene = new Scene(root, 700, 410);

            setup();

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

    private void setup()
    {
        controller = new Controller(ENTITY_MANAGER_FACTORY);

        tabPane = new TabPane();
        Tab home = new Tab();
        Tab create = new Tab();
        Tab retrieve = new Tab();
        Tab delete = new Tab();

        home.setText("Home");
        create.setText("Create");
        retrieve.setText("Retrieve");
        delete.setText("Delete");

        home.setContent(new Home().setup());
        create.setContent(new Create().setup());
        retrieve.setContent(new Retrieve(controller).setup());
        delete.setContent(new Delete().setup());

        tabPane.getTabs().addAll(home, create, retrieve, delete);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
