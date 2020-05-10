package View;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Name;
import Model.POJO.Person;
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

import static java.lang.Integer.parseInt;

public class Managers
{

    private Controller controller;
    private TableView<Manager> tableView;

    public Managers(Controller controller)
    {
        this.controller = controller;
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
        TextField fieldFirstName = new TextField();
        TextField fieldMiddleName = new TextField();
        TextField fieldLastName = new TextField();
        TextField fieldPhone = new TextField();
        TextField fieldEmail = new TextField();
        TextField fieldStarRating = new TextField();
        TextField fieldDateOfBirth = new TextField();
        ComboBox<String> comboTeams = new ComboBox<>();
        // TODO set text formatter for the fields
        //fieldWeek.setTextFormatter(new TextFormatter<>(allowNumbers(false)));
        //fieldPoints.setTextFormatter(new TextFormatter<>(allowNumbers(true)));

        Label labelFirstName = new Label("First Name");
        Label labelMiddleName = new Label("Middle Name");
        Label labelLastName = new Label("Last Name");
        Label labelPhone = new Label("Phone");
        Label labelEmail = new Label("Email");
        Label labelStarRating = new Label("Star Rating");
        Label labelDateOfBirth = new Label("Date of Birth");
        Label labelTeams = new Label("Select Team");
        AppTheme.set(labelFirstName);
        AppTheme.set(labelMiddleName);
        AppTheme.set(labelLastName);
        AppTheme.set(labelPhone);
        AppTheme.set(labelEmail);
        AppTheme.set(labelStarRating);
        AppTheme.set(labelDateOfBirth);
        AppTheme.set(labelTeams);

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
                    Name name = controller.createName(fieldFirstName.getText(), fieldMiddleName.getText(), fieldLastName.getText());
                    Person person = controller.createPerson(name, fieldEmail.getText(), fieldPhone.getText());
                    Manager manager = controller.createManager(person, fieldDateOfBirth.getText(), parseInt(fieldStarRating.getText()));

                    if (comboTeams.getSelectionModel().getSelectedIndex() != -1)
                    {
                        Team selectedTeam = teams.get(comboTeams.getSelectionModel().getSelectedIndex());
                        manager.setTeam(selectedTeam);
                        selectedTeam.setManager(manager);
                        controller.persistManager(manager);
                        controller.updateTeam(selectedTeam);
                    }
                    else
                    {
                        controller.persistManager(manager);
                    }

                    final Node source = (Node) e.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
        );

        HBox row1 = new HBox(25, labelFirstName, fieldFirstName, labelMiddleName, fieldMiddleName, labelLastName, fieldLastName);
        HBox row2 = new HBox(25, labelPhone, fieldPhone, labelEmail, fieldEmail, labelStarRating, fieldStarRating);
        HBox row3 = new HBox(25, labelDateOfBirth, fieldDateOfBirth, labelTeams, comboTeams);
        HBox row4 = new HBox(25, buttonCreate, buttonCancel);
        VBox temp = new VBox(25, row1, row2, row3, row4);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
    }

}

// 222