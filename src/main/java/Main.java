import Controller.Controller;
import View.Players;
import View.Managers;
import View.Home;
import View.Teams;

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
            Scene scene = new Scene(root, 748, 420);

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
        Tab managers = new Tab();
        Tab players = new Tab();
        Tab teams = new Tab();

        home.setText("Home");
        managers.setText("Managers");
        players.setText("Players");
        teams.setText("Teams");

        home.setContent(new Home().setup());
        managers.setContent(new Managers(controller).setup());
        players.setContent(new Players(controller).setup());
        teams.setContent(new Teams(controller).setup());

        tabPane.getTabs().addAll(home, managers, players, teams);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
