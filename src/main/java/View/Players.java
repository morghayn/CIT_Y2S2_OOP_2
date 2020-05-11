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

    private Team team;
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
        String[] columns = new String[]{"firstName", "middleName", "lastName", "phone", "email", "numGoals", "goalie"};

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
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "List", "Update", "Delete"});

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
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "Cancel", "Select Team"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        HBox[] rows = form.createHBoxes(4);
        form.binGUIComponents(names, rows, labels, fields);

        buttons.get("Create").setOnAction(e -> {
            // todo Create / Submit
            form.closeThis(e);
        });

        buttons.get("Select Team").setOnAction(e -> form.popup(e, selectTeam()));
        rows[2].getChildren().addAll(buttons.get("Select Team"));
        rows[3].getChildren().addAll(buttons.get("Create"), buttons.get("Cancel"));

        VBox temp = new VBox(25, rows[0], rows[1], rows[2], rows[3]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public StackPane selectTeam()
    {
        team = null;
        Label labelTeams = new Label("Select Team");
        ComboBox<String> comboTeams = new ComboBox<>();
        AppTheme.set(labelTeams);

        List<Team> teams = controller.getTeams();
        teams.forEach(t -> comboTeams.getItems().add(t.getName()));

        Map<String, Button> buttons = form.createButtonMap(new String[]{"Select", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get("Select").setOnAction(e -> {
            int index = comboTeams.getSelectionModel().getSelectedIndex();
            if (index != -1)
            {
                team = teams.get(index);
            }
            form.closeThis(e);
        });

        VBox temp = new VBox(25, new HBox(50, labelTeams, comboTeams), new HBox(50, buttons.get("Select"), buttons.get("Cancel")));
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

}
// 73 -> 86 (228 unofficial)