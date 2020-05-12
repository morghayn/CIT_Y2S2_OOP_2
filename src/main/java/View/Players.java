package View;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Player;
import Model.POJO.Team;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

import static javafx.geometry.Pos.BASELINE_CENTER;
import static javafx.geometry.Pos.BASELINE_LEFT;

public class Players
{

    private final Form form;
    private final Controller controller;
    private TableView<Player> tableView;

    private Map<String, TextField> fields;
    private Map<String, Label> labels;
    private Map<String, Button> buttons;
    RadioButton isGoalie;
    RadioButton notGoalie;
    private ToggleGroup goalie;
    private Player player;
    private Team team;

    public Players(Controller controller)
    {
        this.controller = controller;
        form = new Form(controller);
    }

    public StackPane setup()
    {
        VBox temp = new VBox(25, buildSearchBar(), buildButtonBar(), buildTableView());
        temp.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(temp);
    }

    private HBox buildSearchBar()
    {
        Label labelSearch = new Label("Search by Name");
        TextField fieldSearch = new TextField();
        Button buttonSearch = new Button("Search");
        AppTheme.set(labelSearch);
        AppTheme.set(buttonSearch);
        Button buttonExtra = new Button("Extra Details");
        AppTheme.set(buttonExtra);
        buttonExtra.setOnAction(e -> extraPlayerDetails());
        buttonSearch.setOnAction(e -> populateTableView(controller.searchName(fieldSearch.getText())));
        HBox temp = new HBox(50, labelSearch, fieldSearch, buttonSearch, buttonExtra);
        temp.setAlignment(BASELINE_LEFT);
        return temp;
    }

    public HBox buildButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "List", "Update", "Delete", "Team Filter"});

        buttons.get("Create").setOnAction(e -> form.popup(e, playerLayout(true), 920, 260));
        buttons.get("List").setOnAction(e -> populateTableView());
        buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());
        buttons.get("Team Filter").setOnAction(e -> {
            form.popup(e, selectTeam(), 375, 185); // TODO get optimal dimensions
            if (team != null)
            {
                populateTableView(controller.getTeamPlayers(team));
            }
        });

        HBox temp = new HBox(
                50,
                buttons.get("Team Filter"),
                buttons.get("Create"),
                buttons.get("List"),
                buttons.get("Update"),
                buttons.get("Delete")
        );
        temp.setAlignment(BASELINE_CENTER);
        return temp;
    }

    public void populateTableView()
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getPlayers());
    }

    public void extraPlayerDetails()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            player = tableView.getSelectionModel().getSelectedItem();
            new PopupWindow("", controller.getExtraPlayerDetails(player));
        }
        else
        {
            new PopupWindow("Update Error", "No valid table selection.");
        }
    }

    public void update(ActionEvent e)
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            player = tableView.getSelectionModel().getSelectedItem();
            StackPane temp = playerLayout(false);
            fields.get("First Name").setText(player.getFirstName());
            fields.get("Middle Name").setText(player.getMiddleName());
            fields.get("Last Name").setText(player.getLastName());
            fields.get("Phone").setText(player.getPhone());
            fields.get("Email").setText(player.getEmail());
            fields.get("Goal Number").setText(player.getNumGoals() + "");
            goalie.selectToggle(player.isGoalie() ? isGoalie : notGoalie);
            form.popup(e, temp, 920, 260);
            populateTableView();
        }
        else
        {
            new PopupWindow("Update Error", "No valid table selection.");
        }
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

    public void populateTableView(List<Player> players)
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(players);
    }

    public StackPane playerLayout(boolean isCreate)
    {
        team = null;
        String[] names = {"First Name", "Middle Name", "Last Name", "Phone", "Email", "Goal Number"};
        boolean[] fieldConstraints = {false, false, false, true, false, true, false};

        labels = form.createLabelMap(names);
        fields = form.createFieldMap(names, fieldConstraints);
        buttons = form.createButtonMap(new String[]{(isCreate ? "Create" : "Update"), "Cancel", "Select Team"});

        HBox[] rows = form.createHBoxes(3);
        form.binGUIComponents(names, rows, labels, fields);

        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get((isCreate ? "Create" : "Update")).setOnAction(isCreate ? this::submitForm : this::submitUpdate);
        buttons.get("Select Team").setOnAction(e -> form.popup(e, selectTeam(), 375, 185)); // TODO get optimal dimensions

        isGoalie = new RadioButton("Is Goalie");
        notGoalie = new RadioButton("Is Not Goalie");
        goalie = new ToggleGroup();
        isGoalie.setToggleGroup(goalie);
        notGoalie.setToggleGroup(goalie);
        VBox goalieBox = new VBox(isGoalie, notGoalie);
        goalie.selectToggle(isCreate ? notGoalie : player.isGoalie() ? isGoalie : notGoalie);

        rows[2].setAlignment(BASELINE_CENTER);
        rows[2].setSpacing(150);
        rows[2].getChildren().addAll(
                buttons.get((isCreate ? "Create" : "Update")), buttons.get("Cancel"),
                goalieBox, buttons.get("Select Team")
        );

        VBox temp = new VBox(40, rows[0], rows[1], rows[2]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public void submitForm(ActionEvent e)
    {
        boolean _isGoalie = goalie.getSelectedToggle().equals(isGoalie);
        Player player = form.createPlayer(fields, _isGoalie, form.createPerson(fields, form.createName(fields)));
        controller.persistPlayer(player);
        if (team != null)
        {
            controller.setPlayersTeam(team, player);
        }
        populateTableView();
        form.closeThis(e);
    }

    public void submitUpdate(ActionEvent e)
    {
        // creating new updated player
        boolean _isGoalie = goalie.getSelectedToggle().equals(isGoalie);
        Player player = form.createPlayer(fields, _isGoalie, form.createPerson(fields, form.createName(fields)));

        // merging old player details with new ones
        player.setPersonID(this.player.getPersonID());
        controller.updatePlayer(player);

        if (team != null)
        {
            controller.setPlayersTeam(team, this.player);
        }
        else if (player.getTeam() != null)
        {
            controller.removePlayerFromTeam(player.getTeam(), player);
        }

        populateTableView();
        form.closeThis(e);
    }

    public StackPane selectTeam()
    {
        Label labelTeams = new Label("Select Team");
        ComboBox<String> comboTeams = new ComboBox<>();
        AppTheme.set(labelTeams);

        team = (player == null ? null : player.getTeam());
        List<Team> teams = controller.getTeams();
        teams.forEach(t -> {
            comboTeams.getItems().add(t.getName());
            if (team != null && t.getTeamID().equals(team.getTeamID()))
            {
                comboTeams.getSelectionModel().select(comboTeams.getItems().size() - 1);
            }
        });

        Map<String, Button> buttons = form.createButtonMap(new String[]{"Remove Team", "Select", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get("Remove Team").setOnAction(e -> {
            team = null;
            comboTeams.getSelectionModel().select(-1);
        });
        buttons.get("Select").setOnAction(e -> {
            int index = comboTeams.getSelectionModel().getSelectedIndex();
            if (index != -1)
            {
                team = teams.get(index);
            }
            form.closeThis(e);
        });

        HBox temp1 = new HBox(50, labelTeams, comboTeams);
        HBox temp2 = new HBox(50, buttons.get("Remove Team"));
        HBox temp3 = new HBox(50, buttons.get("Select"), buttons.get("Cancel"));
        temp1.setAlignment(BASELINE_CENTER);
        temp2.setAlignment(BASELINE_CENTER);
        temp3.setAlignment(BASELINE_CENTER);

        VBox temp = new VBox(25, temp1, temp2, temp3);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

}