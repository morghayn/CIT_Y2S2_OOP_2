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

    private FormPopup form;
    private Controller controller;
    private TableView<Player> tableView;

    public Players(Controller controller)
    {
        this.controller = controller;
        form = new FormPopup(controller);
    }

    public StackPane setup()
    {
        String[] columns = {"firstName", "middleName", "lastName", "phone", "email", "numGoals", "goalie"};
        prepareTableView(columns);

        VBox mainColumn = new VBox(25, prepareButtonBar(), prepareSearchBar(), tableView);
        mainColumn.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(mainColumn);
    }

    private HBox prepareSearchBar()
    {
        Label labelSearch = new Label("Search by Name");
        TextField fieldSearch = new TextField();
        Button buttonSearch = new Button("Search");
        AppTheme.set(labelSearch);
        AppTheme.set(buttonSearch);
        buttonSearch.setOnAction
                (e ->
                 {
                     List<Player> players = controller.searchName(fieldSearch.getText());
                     tableView.getItems().clear();
                     for (Player player : players)
                     {
                         tableView.getItems().add(player);
                     }
                 });

        Button viewTeamPlayers = new Button("Team Players");
        viewTeamPlayers.setOnAction(e -> form.pop(e, teamSelectLayout()));
        return new HBox(50, labelSearch, fieldSearch, buttonSearch, viewTeamPlayers);
    }

    private HBox prepareButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "List", "Update", "Delete"});
        buttons.get("Create").setOnAction(e -> form.pop(e, playerLayout()));
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
            TableColumn<Player, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tableView.getColumns().add(column);
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.length));
        }
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void list()
    {
        List<Player> players = controller.getPlayers();
        tableView.getItems().clear();
        for (Player player : players)
        {
            tableView.getItems().add(player);
        }
    }

    public void update()
    {

    }

    public void delete()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            Player player = tableView.getSelectionModel().getSelectedItem();
            controller.deletePlayer(player);
            list();
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
                (e ->
                 {
                     submitForm(
                             fields,
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

    private StackPane teamSelectLayout()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Select", "Cancel"});
        ComboBox<String> comboTeams = new ComboBox<>();
        Label labelTeams = new Label("Select Team");
        List<Team> teams = controller.getTeams();
        AppTheme.set(labelTeams);

        for (Team team : teams)
        {
            comboTeams.getItems().add(team.getName());
        }

        buttons.get("Cancel").setOnAction(e -> form.closeThis(e));
        buttons.get("Select").setOnAction
                (e ->
                 {
                     if (comboTeams.getSelectionModel().getSelectedIndex() != -1)
                     {
                         Team team = teams.get(comboTeams.getSelectionModel().getSelectedIndex());
                         tableView.getItems().clear();
                         for (Player player : team.getPlayers())
                         {
                             tableView.getItems().add(player);
                         }
                     }
                     // else TODO something here, error?
                     form.closeThis(e);
                 });

        HBox[] rows = {new HBox(25), new HBox(25)};
        rows[0].getChildren().addAll(labelTeams, comboTeams);
        rows[1].getChildren().addAll(buttons.get("Select"), buttons.get("Cancel"));

        VBox temp = new VBox(25, rows[0], rows[1]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public void submitForm(Map<String, TextField> fields, Team team)
    {
        Player player =
                form.createPlayer(
                        fields,
                        form.createPerson(
                                fields,
                                form.createName(fields)
                        )
                );

        controller.persistPlayer(player);
    }

}
// 259 -> 223 -> 214