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

public class Players
{

    private Controller controller;
    private TableView<Player> tableView;

    public Players(Controller controller)
    {
        this.controller = controller;
    }

    public StackPane setup()
    {
        String[] columns = {"firstName", "middleName", "lastName", "phone", "email", "numGoals", "goalie"};
        prepareTableView(columns);

        Button viewTeamPlayers = new Button("Team Players");
        viewTeamPlayers.setOnAction(this::teamPlayers);

        VBox mainColumn = new VBox(25, prepareButtonBar(), prepareSearchBar(), viewTeamPlayers, tableView);
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
        return new HBox(50, labelSearch, fieldSearch, buttonSearch);
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

    public void create(ActionEvent e)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        Group root = new Group();
        Stage dialog = new Stage();
        Scene temp = new Scene(root, 920, 300);
        dialog.setScene(temp);
        root.getChildren().add(createPane());

        dialog.centerOnScreen();

        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    public StackPane createPane()
    {
        TextField fieldFirstName = new TextField();
        TextField fieldMiddleName = new TextField();
        TextField fieldLastName = new TextField();
        TextField fieldPhone = new TextField();
        TextField fieldEmail = new TextField();
        TextField fieldDateOfBirth = new TextField();
        TextField fieldStarRating = new TextField();
        // TODO set text formatter for the fields
        //fieldWeek.setTextFormatter(new TextFormatter<>(allowNumbers(false)));
        //fieldPoints.setTextFormatter(new TextFormatter<>(allowNumbers(true)));

        Label labelFirstName = new Label("First Name");
        Label labelMiddleName = new Label("Middle Name");
        Label labelLastName = new Label("Last Name");
        Label labelPhone = new Label("Phone");
        Label labelEmail = new Label("Email");
        Label labelDateOfBirth = new Label("Date of Birth");
        Label labelStarRating = new Label("Star Rating");
        AppTheme.set(labelFirstName);
        AppTheme.set(labelMiddleName);
        AppTheme.set(labelLastName);
        AppTheme.set(labelPhone);
        AppTheme.set(labelEmail);
        AppTheme.set(labelDateOfBirth);
        AppTheme.set(labelStarRating);

        // :: instantiating and styling new buttons
        Button buttonCreate = new Button("Create");
        Button buttonCancel = new Button("Cancel");
        AppTheme.set(buttonCreate);
        AppTheme.set(buttonCancel);

        HBox row1 = new HBox(25, labelFirstName, fieldFirstName, labelMiddleName, fieldMiddleName, labelLastName, fieldLastName);
        HBox row2 = new HBox(25, labelPhone, fieldPhone, labelEmail, fieldEmail);
        HBox row3 = new HBox(25, labelDateOfBirth, fieldDateOfBirth, labelStarRating, fieldStarRating);
        VBox temp = new VBox(25, row1, row2, row3);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
    }

    public void teamPlayers(ActionEvent e)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        Group root = new Group();
        Stage dialog = new Stage();
        Scene temp = new Scene(root, 920, 300);
        dialog.setScene(temp);
        root.getChildren().add(teamPlayersPane());

        dialog.centerOnScreen();

        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    public StackPane teamPlayersPane()
    {
        Label labelTeam  = new Label("Select Team");
        ComboBox<String> comboTeams = new ComboBox<>();
        List<Team> teams = controller.getTeams();

        for (Team team : teams)
        {
            comboTeams.getItems().add(team.getName());
        }

        // :: instantiating and styling new buttons
        Button buttonCreate = new Button("Create");
        Button buttonCancel = new Button("Cancel");
        AppTheme.set(buttonCreate);
        AppTheme.set(buttonCancel);

        buttonCreate.setOnAction(
                e ->
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
                    else
                    {
                        // TODO something here, error?
                    }

                    final Node source = (Node) e.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
        );

        HBox row1 = new HBox(25, labelTeam, comboTeams);
        HBox row2 = new HBox(25, buttonCreate, buttonCancel);
        VBox temp = new VBox(25, row1, row2);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
    }

}

// 183