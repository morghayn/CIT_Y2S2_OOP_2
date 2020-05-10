package View;

import Controller.Controller;
import Model.POJO.*;
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

import static java.lang.Integer.parseInt;

public class Teams
{

    private Controller controller;
    private TableView<Team> tableView;

    public Teams(Controller controller) // TODO pass controller instance through
    {
        this.controller = controller;
    }

    public StackPane setup()
    {
        String[] columns = {"name", "jerseyColour"};
        prepareTableView(columns);

        VBox mainColumn = new VBox(25, prepareButtonBar(), tableView);
        mainColumn.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(mainColumn);
    }

    private HBox prepareButtonBar()
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
        return new HBox(50, create, list, update, delete);
    }

    public void prepareTableView(String[] columns)
    {
        tableView = new TableView<>();
        for (String columnName : columns)
        {
            TableColumn<Team, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tableView.getColumns().add(column);
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.length));
        }
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

    public void create(ActionEvent e)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        Group root = new Group();
        Stage dialog = new Stage();
        Scene temp = new Scene(root, 920, 280);
        dialog.setScene(temp);
        root.getChildren().add(createPane());

        dialog.centerOnScreen();

        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    public StackPane createPane()
    {
        TextField fieldTeamName = new TextField();
        TextField fieldJerseyColour = new TextField();
        ComboBox<String> comboManagers = new ComboBox<>();
        // TODO set text formatter for the fields
        //fieldWeek.setTextFormatter(new TextFormatter<>(allowNumbers(false)));
        //fieldPoints.setTextFormatter(new TextFormatter<>(allowNumbers(true)));

        Label labelTeamName = new Label("Team Name");
        Label labelJerseyColour = new Label("Jersey Colour");
        Label labelManager = new Label("Select Manager");
        AppTheme.set(labelTeamName);
        AppTheme.set(labelJerseyColour);
        AppTheme.set(labelManager);

        List<Manager> managers = controller.getManagers();
        for (Manager manager : managers)
        {
            comboManagers.getItems().add(manager.getFirstName() + " " + manager.getLastName());
        }

        // :: instantiating and styling new buttons
        Button buttonCreate = new Button("Create");
        Button buttonCancel = new Button("Cancel");
        AppTheme.set(buttonCreate);
        AppTheme.set(buttonCancel);

        buttonCreate.setOnAction(
                e ->
                {
                    Team team = controller.createTeam(fieldTeamName.getText(), fieldJerseyColour.getText());

                    if (comboManagers.getSelectionModel().getSelectedIndex() != -1)
                    {
                        Manager manager = managers.get(comboManagers.getSelectionModel().getSelectedIndex());
                        team.setManager(manager);
                        manager.setTeam(team);
                        controller.persistTeam(team);
                        controller.updateManager(manager);
                    }
                    else
                    {
                        controller.persistTeam(team);
                    }

                    final Node source = (Node) e.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
        );

        HBox row1 = new HBox(25, labelTeamName, fieldTeamName, labelJerseyColour, fieldJerseyColour, labelManager, comboManagers);
        HBox row2 = new HBox(25, buttonCreate, buttonCancel);
        VBox temp = new VBox(25, row1, row2);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
    }

}
