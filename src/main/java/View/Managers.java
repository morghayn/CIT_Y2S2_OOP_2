package View;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Team;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static javafx.geometry.Pos.BASELINE_CENTER;

public class Managers
{
    private final Form form;
    private final Controller controller;
    private TableView<Manager> tableView;

    private Map<String, TextField> fields;
    private Map<String, Label> labels;
    private Map<String, Button> buttons;
    private DatePicker dateOfBirth;
    private Manager manager;
    private Team team;

    public Managers(Controller controller)
    {
        this.controller = controller;
        form = new Form(controller);
    }

    public StackPane setup()
    {
        VBox temp = new VBox(25, buildButtonBar(), buildTableView());
        temp.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(temp);
    }

    public HBox buildButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "List", "Update", "Delete", "Show Team"});
        buttons.get("Show Team").setOnAction(e -> showTeam());
        buttons.get("Create").setOnAction(e -> form.popup(e, managerLayout(true), 920, 260));
        buttons.get("List").setOnAction(e -> populateTableView());
        buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());

        HBox temp = new HBox(50, buttons.get("Create"), buttons.get("List"), buttons.get("Update"), buttons.get("Delete"), buttons.get("Show Team"));
        temp.setAlignment(BASELINE_CENTER);
        return temp;
    }

    public void showTeam()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            manager = tableView.getSelectionModel().getSelectedItem();
            new PopupWindow("", controller.getManagerTeam(manager));
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
            manager = tableView.getSelectionModel().getSelectedItem();
            StackPane temp = managerLayout(false);
            fields.get("First Name").setText(manager.getFirstName());
            fields.get("Middle Name").setText(manager.getMiddleName());
            fields.get("Last Name").setText(manager.getLastName());
            fields.get("Phone").setText(manager.getPhone());
            fields.get("Email").setText(manager.getEmail());
            fields.get("Star Rating").setText(manager.getStarRating() + "");
            LocalDate _dateOfBirth = manager.getDateOfBirth();
            dateOfBirth.setValue(_dateOfBirth);
            form.popup(e, temp, 920, 260);
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

    public TableView<Manager> buildTableView()
    {
        tableView = new TableView<>();
        String[] columns = new String[]{"firstName", "middleName", "lastName", "phone", "email", "starRating", "dateOfBirth"};

        for (String columnName : columns)
        {
            TableColumn<Manager, String> column = new TableColumn<>(columnName);
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
        tableView.getItems().addAll(controller.getManagers());
    }

    public StackPane managerLayout(boolean isCreate)
    {
        team = null;
        String[] names = {"First Name", "Middle Name", "Last Name", "Phone", "Email", "Star Rating"};
        boolean[] fieldConstraints = {false, false, false, true, false, true, false};

        labels = form.createLabelMap(names);
        fields = form.createFieldMap(names, fieldConstraints);
        HBox[] rows = form.createHBoxes(3);
        form.binGUIComponents(names, rows, labels, fields);

        buttons = form.createButtonMap(new String[]{(isCreate ? "Create" : "Update"), "Cancel", "Select Team"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get((isCreate ? "Create" : "Update")).setOnAction(isCreate? this::submitForm : this::submitUpdate);
        buttons.get("Select Team").setOnAction(e -> form.popup(e, selectTeam(), 375, 185));
        Label labelDateOfBirth = new Label("Date of Birth");
        dateOfBirth = new DatePicker();
        dateOfBirth.setValue(LocalDate.now());

        rows[2].setAlignment(BASELINE_CENTER);
        rows[2].setSpacing(50);
        rows[2].getChildren().addAll(
                buttons.get((isCreate ? "Create" : "Update")), buttons.get("Cancel"),
                labelDateOfBirth, dateOfBirth, buttons.get("Select Team")
        );

        VBox temp = new VBox(40, rows[0], rows[1], rows[2]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public void submitForm(ActionEvent e)
    {
        Manager manager = form.createManager(fields, dateOfBirth.getValue(), form.createPerson(fields, form.createName(fields)));
        controller.persistManager(manager);
        if (team != null)
        {
            controller.setManagerOfTeam(team, manager);
        }
        populateTableView();
        form.closeThis(e);
    }

    public void submitUpdate(ActionEvent e)
    {
        // creating new updated manager
        Manager manager = form.createManager(fields, dateOfBirth.getValue(), form.createPerson(fields, form.createName(fields)));

        // merging old manager details with new ones
        manager.setPersonID(this.manager.getPersonID());
        manager.setTeam(this.manager.getTeam());
        controller.updateManager(manager);

        if (team != null)
        {
            controller.setManagerOfTeam(team, manager);
        }
        else if (manager.getTeam() != null)
        {
            controller.removeManagerFromTeam(manager.getTeam(), manager);
        }

        populateTableView();
        form.closeThis(e);
    }

    public StackPane selectTeam()
    {
        Label labelTeams = new Label("Select Team");
        ComboBox<String> comboTeams = new ComboBox<>();
        AppTheme.set(labelTeams);

        team = (manager == null ? null : manager.getTeam());
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
        buttons.get("Remove Team").setOnAction(e-> {
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