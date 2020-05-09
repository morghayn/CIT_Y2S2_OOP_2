package View;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Player;
import Model.POJO.Team;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class Teams
{

    private Controller controller;
    private TableView<Team> tableView;
    private VBox mainColumn;

    public Teams(Controller controller) // TODO pass controller instance through
    {
        this.controller = controller;
    }

    public StackPane setup()
    {
        Button create = new Button("Create");
        Button list = new Button("List");
        Button update = new Button("Update");
        Button delete = new Button("Delete");

        AppTheme.set(create);
        AppTheme.set(list);
        AppTheme.set(update);
        AppTheme.set(delete);

        create.setOnAction(this::create);
        list.setOnAction(e -> list());
        update.setOnAction(e -> update());
        delete.setOnAction(e -> delete());
        HBox buttons = new HBox(50, create, list, update, delete);

        tableView = new TableView<>();
        TableColumn<Team, String> column1, column2;
        column1 = new TableColumn<>("name");
        column2 = new TableColumn<>("jerseyColour");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column2.setCellValueFactory(new PropertyValueFactory<>("jerseyColour"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        column1.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        column2.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        mainColumn = new VBox(25, buttons, tableView);
        mainColumn.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(mainColumn);
    }

    public void create(ActionEvent e)
    {

    }

    public void list()
    {
        List<Team> teams = controller.getTeams();
        tableView.getItems().clear();
        for (Team team : teams)
        {
            tableView.getItems().add(team);
        }
    }

    public void update()
    {

    }

    public void delete()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            Team team = tableView.getSelectionModel().getSelectedItem();
            controller.deleteTeam(team);
            list();
        }
        else
        {
            new PopupWindow("Removal Error", "No valid table selection.");
        }
    }

}
