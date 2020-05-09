package View;

import Controller.Controller;
import Model.POJO.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class Retrieve
{

    private Controller controller;
    private TableView<Player> tableView;
    private VBox mainColumn;

    public Retrieve(Controller controller) // TODO pass controller instance through
    {
        this.controller = controller;
    }

    public StackPane setup()
    {
        Button displayManager = new Button("Display Managers");
        Button displayPlayer = new Button("Display Players");
        Button displayTeam = new Button("Display Teams");

        AppTheme.set(displayManager);
        AppTheme.set(displayPlayer);
        AppTheme.set(displayTeam);

        displayManager.setOnAction(e -> managerTable());
        displayPlayer.setOnAction(e -> playerTable());
        displayTeam.setOnAction(e -> teamTable());

        HBox buttons = new HBox(50, displayManager, displayPlayer, displayTeam);

        mainColumn = new VBox(25, buttons);
        mainColumn.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(mainColumn);
    }

    public void managerTable()
    {

    }

    public void playerTable()
    {
        TableColumn<Player, String> column1, column2, column3, column4, column5, column6, column7;
        tableView = new TableView<>();

        column1 = new TableColumn<>("firstName");
        column2 = new TableColumn<>("middleName");
        column3 = new TableColumn<>("lastName");
        column4 = new TableColumn<>("phone");
        column5 = new TableColumn<>("email");
        column6 = new TableColumn<>("numGoals");
        column7 = new TableColumn<>("isGoalie");

        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("phone"));
        column5.setCellValueFactory(new PropertyValueFactory<>("email"));
        column6.setCellValueFactory(new PropertyValueFactory<>("numGoals"));
        column7.setCellValueFactory(new PropertyValueFactory<>("goalie"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);

        mainColumn.getChildren().add(tableView);

        List<Player> players = controller.getPlayers();

        for(Player player : players)
        {
            tableView.getItems().add(player);
        }
    }

    public void teamTable()
    {

    }

}
