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
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Teams
{

    private FormPopup form;
    private Controller controller;
    private TableView<Team> tableView;

    public Teams(Controller controller) // TODO pass controller instance through
    {
        this.controller = controller;
        form = new FormPopup(controller);
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
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "List", "Update", "Delete"});
        buttons.get("Create").setOnAction(e -> form.pop(e, teamLayout()));
        buttons.get("List").setOnAction(e -> list());
        buttons.get("Update").setOnAction(e -> update());
        buttons.get("Delete").setOnAction(e -> delete());
        return new HBox(50, buttons.get("Create"), buttons.get("List"), buttons.get("Update"), buttons.get("Delete"));
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

    public StackPane teamLayout()
    {
        String[] names = {"Team Name", "Jersey Colour", "Select Manager"};
        boolean[] fieldConstraints = {false, false, false};
        ComboBox<String> comboManagers = new ComboBox<>();
        Map<String, TextField> fields = form.createFieldMap(names, fieldConstraints);
        Map<String, Label> labels = form.createLabelMap(names);
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "Cancel"});
        List<Manager> managers = controller.getManagers();
        for (Manager manager : managers)
        {
            comboManagers.getItems().add(manager.getFirstName() + " " + manager.getLastName());
        }

        buttons.get("Cancel").setOnAction(e -> form.closeThis(e));
        buttons.get("Create").setOnAction(
                e ->
                {
                    submitForm(fields, (comboManagers.getSelectionModel().getSelectedIndex() != -1 ? managers.get(comboManagers.getSelectionModel().getSelectedIndex()) : null));
                    form.closeThis(e);
                }
        );

        HBox[] rows = {new HBox(25), new HBox(25)};
        for (int i = 0; i < 2; i++)
        {
            rows[0].getChildren().addAll(labels.get(names[i]), fields.get(names[i]));
        }
        rows[0].getChildren().addAll(labels.get("Select Manager"), comboManagers);
        rows[1] = new HBox(25, buttons.get("Create"), buttons.get("Cancel"));
        VBox temp = new VBox(25, rows[0], rows[1]);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
    }

    private void submitForm(Map<String, TextField> fields, Manager manager)
    {
        Team team = controller.createTeam(fields.get("Team Name").getText(), fields.get("Jersey Colour").getText());

        /*
        if (manager != null)
        {
            team.setManager(manager);
            manager.setTeam(team);
            controller.persistTeam(team);
            controller.updateManager(manager);
        }
        else
        {
            controller.persistTeam(team);
        }
         */
    }

}
// 184