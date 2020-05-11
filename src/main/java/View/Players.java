package View;

import Controller.Controller;
import Model.POJO.Player;
import Model.POJO.Team;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class Players
{

    private final FormPopup form;
    private final Controller controller;
    private TableView<Player> tableView;

    public Players(Controller controller)
    {
        this.controller = controller;
        form = new FormPopup(controller);
    }

    public StackPane setup()
    {
        VBox temp = new VBox(25, buildSearchBar(), buildButtonBar(), buildTableView());
        temp.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(temp);
    }

    public TableView<Player> buildTableView()
    {
        tableView = new TableView<>();
        String[] columns = new String[] {"firstName", "middleName", "lastName", "phone", "email", "numGoals", "goalie"};

        for (String columnName : columns)
        {
            TableColumn<Player, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tableView.getColumns().add(column);
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.length));
        }

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tableView;
    }

    public void populateTableView()
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getPlayers());
    }

    public HBox buildButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "List", "Update", "Delete"});

        buttons.get("Create").setOnAction(e -> form.popup(e, playerLayout()));
        buttons.get("List").setOnAction(e -> populateTableView());
        // buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());

        return new HBox(100, buttons.get("Create"), buttons.get("List"), buttons.get("Update"), buttons.get("Delete"));
    }

    private HBox buildSearchBar()
    {
        Label labelSearch = new Label("Search by Name");
        TextField fieldSearch = new TextField();
        Button buttonSearch = new Button("Search");
        AppTheme.set(labelSearch);
        AppTheme.set(buttonSearch);
        buttonSearch.setOnAction(e -> {
            tableView.getItems().clear();
            tableView.getItems().addAll(controller.searchName(fieldSearch.getText()));
        });

        // Todo Team Select
        //Button viewTeamPlayers = new Button("Team Players");
        //viewTeamPlayers.setOnAction(e -> form.pop(e, teamSelectLayout()));
        return new HBox(50, labelSearch, fieldSearch, buttonSearch);//, viewTeamPlayers);
    }

    public void delete()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            controller.delete(tableView.getSelectionModel().getSelectedItem());
            populateTableView();
        }
        else
        {
            new PopupWindow("Removal Error", "No valid table selection.");
        }
    }



    public StackPane playerLayout()
    {
        String[] names = {"First Name", "Middle Name", "Last Name", "Phone", "Email", "Goal Number", "Goalie"};
        boolean[] fieldConstraints = {false, false, false, true, false, true, false};
        Map<String, Label> labels = form.createLabelMap(names);
        Map<String, TextField> fields = form.createFieldMap(names, fieldConstraints);
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        HBox[] rows = {new HBox(25), new HBox(25), new HBox(25), new HBox(25)};
        for (int i = 0; i < names.length; i++)
        {
            rows[i / 3].getChildren().addAll(labels.get(names[i]), fields.get(names[i]));
        }

        ComboBox<String> comboTeams = new ComboBox<>();
        Label labelTeams = new Label("Select Team");
        AppTheme.set(labelTeams);
        controller.getTeams().forEach(t -> comboTeams.getItems().add(t.getName()));

        /*
        buttons.get("Create").setOnAction(e -> {
            int index = comboTeams.getSelectionModel().getSelectedIndex();
            submitForm(fields, (index != -1 ? teams.get(index) : null));
            form.closeThis(e);
        });
         */

        rows[2].getChildren().addAll(labelTeams, comboTeams);
        rows[3].getChildren().addAll(buttons.get("Create"), buttons.get("Cancel"));

        VBox temp = new VBox(25, rows[0], rows[1], rows[2], rows[3]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public StackPane managerLayout()
    {
        String[] names = {"First Name", "Middle Name", "Last Name", "Phone", "Email", "Star Rating", "Date of Birth"};
        boolean[] fieldConstraints = {false, false, false, true, false, true, false};


        Map<String, Label> labels = form.createLabelMap(names);
        Map<String, TextField> fields = form.createFieldMap(names, fieldConstraints);
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        HBox[] rows = {new HBox(25), new HBox(25), new HBox(25), new HBox(25)};
        for (int i = 0; i < names.length; i++)
        {
            rows[i / 3].getChildren().addAll(labels.get(names[i]), fields.get(names[i]));
        }

        ComboBox<String> comboTeams = new ComboBox<>();
        Label labelTeams = new Label("Select Team");
        AppTheme.set(labelTeams);
        controller.getTeams().forEach(t -> comboTeams.getItems().add(t.getName()));

        /*
        buttons.get("Create").setOnAction(e -> {
            int index = comboTeams.getSelectionModel().getSelectedIndex();
            submitForm(fields, (index != -1 ? teams.get(index) : null));
            form.closeThis(e);
        });
        */

        rows[2].getChildren().addAll(labelTeams, comboTeams);
        rows[3].getChildren().addAll(buttons.get("Create"), buttons.get("Cancel"));

        VBox temp = new VBox(25, rows[0], rows[1], rows[2], rows[3]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public StackPane teamLayout()
    {
        String[] names = {"Team Name", "Jersey Colour", "Select Manager"};
        boolean[] fieldConstraints = {false, false, false};


        Map<String, TextField> fields = form.createFieldMap(names, fieldConstraints);
        Map<String, Label> labels = form.createLabelMap(names);
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        HBox[] rows = {new HBox(25), new HBox(25)};
        rows[0].getChildren().addAll(labels.get(names[0]), fields.get(names[0]));
        rows[0].getChildren().addAll(labels.get(names[1]), fields.get(names[1]));

        ComboBox<String> comboManagers = new ComboBox<>();
        controller.getManagers().forEach(m -> comboManagers.getItems().add(m.getFirstName() + " " + m.getLastName()));

        /*
        buttons.get("Create").setOnAction(e -> {
            int index = comboManagers.getSelectionModel().getSelectedIndex();
            submitForm(fields, (index != -1 ? managers.get(index) : null));
            form.closeThis(e);
        });
        */

        rows[0].getChildren().addAll(labels.get("Select Manager"), comboManagers);
        rows[1] = new HBox(25, buttons.get("Create"), buttons.get("Cancel"));

        VBox temp = new VBox(25, rows[0], rows[1]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

}
// 73 -> 86 (228 unofficial)