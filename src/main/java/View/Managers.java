package View;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Team;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class Managers
{

    private FormPopup form;
    private Controller controller;
    private TableView<Manager> tableView;

    public Managers(Controller controller)
    {
        this.controller = controller;
        form = new FormPopup(controller);
    }

    public StackPane setup()
    {
        String[] columns = {"firstName", "middleName", "lastName", "phone", "email", "starRating", "dateOfBirth"};
        prepareTableView(columns);

        VBox mainColumn = new VBox(25, prepareButtonBar(), tableView);
        mainColumn.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(mainColumn);
    }

    private HBox prepareButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "List", "Update", "Delete"});
        buttons.get("Create").setOnAction(e -> form.pop(e, managerLayout()));
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
            TableColumn<Manager, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tableView.getColumns().add(column);
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.length));
        }
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void list()
    {
        List<Manager> managers = controller.getManagers();
        tableView.getItems().clear();
        for (Manager manager : managers)
        {
            tableView.getItems().add(manager);
        }
    }

    public void update()
    {

    }

    public void delete()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            Manager manager = tableView.getSelectionModel().getSelectedItem();
            Team team = manager.getTeam();
            if (team != null)
            {
                team.removeManager(manager);
                controller.updateTeam(team);
            }
            controller.deleteManager(manager);
            list();
        }
        else
        {
            new PopupWindow("Removal Error", "No valid table selection.");
        }
    }

    public StackPane managerLayout()
    {
        String[] names = {"First Name", "Middle Name", "Last Name", "Phone", "Email", "Star Rating", "Date of Birth"};
        boolean[] fieldConstraints = {false, false, false, true, false, true, false};
        Map<String, Label> labels = form.createLabelMap(names);
        Map<String, TextField> fields = form.createFieldMap(names, fieldConstraints);
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "Cancel"});
        ComboBox<String> comboTeams = new ComboBox<>();
        Label labelTeams = new Label("Select Team");
        List<Team> teams = controller.getTeams();
        AppTheme.set(labelTeams);

        for (Team team : teams)
        {
            comboTeams.getItems().add(team.getName());
        }

        buttons.get("Cancel").setOnAction(e -> form.closeThis(e));
        buttons.get("Create").setOnAction
                (e -> {
                    submitForm(fields,
                               (
                                       comboTeams.getSelectionModel().getSelectedIndex() != -1 ?
                                       teams.get(comboTeams.getSelectionModel().getSelectedIndex()) : null
                               )
                    );
                    form.closeThis(e);
                });


        HBox[] rows = {new HBox(25), new HBox(25), new HBox(25), new HBox(25)};
        for (int i = 0; i < names.length; i++)
        {
            rows[i / 3].getChildren().addAll(labels.get(names[i]), fields.get(names[i]));
        }

        rows[2].getChildren().addAll(labelTeams, comboTeams);
        rows[3].getChildren().addAll(buttons.get("Create"), buttons.get("Cancel"));
        VBox temp = new VBox(25, rows[0], rows[1], rows[2], rows[3]);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
    }

    public void submitForm(Map<String, TextField> fields, Team team)
    {
        Manager manager =
                form.createManager(fields,
                                   form.createPerson(fields,
                                                     form.createName(fields)));

        controller.persistManager(manager);
        /*
        if (team != null) // TODO handling bi-direction is currently bad
        {
            manager.setTeam(team);
            team.setManager(manager);
            controller.persistManager(manager);
            controller.updateTeam(team);
        }
        else
        {
            controller.persistManager(manager);
        }
        */
    }

}
// 222 -> 175 -> 166