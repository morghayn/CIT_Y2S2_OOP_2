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

public class Teams
{

    private final FormPopup form;
    private final Controller controller;
    private TableView<Team> tableView;
    private Manager manager;

    public Teams(Controller controller)
    {
        this.controller = controller;
        form = new FormPopup(controller);
    }

    public StackPane setup()
    {
        VBox temp = new VBox(25, buildButtonBar(), buildTableView());
        temp.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(temp);
    }

    public TableView<Team> buildTableView()
    {
        tableView = new TableView<>();
        String[] columns = new String[] {"name", "jerseyColour"};

        for (String columnName : columns)
        {
            TableColumn<Team, String> column = new TableColumn<>(columnName);
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
        tableView.getItems().addAll(controller.getTeams());
    }

    public HBox buildButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "List", "Update", "Delete"});

        buttons.get("Create").setOnAction(e -> form.popup(e, teamLayout()));
        buttons.get("List").setOnAction(e -> populateTableView());
        // buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());

        return new HBox(100, buttons.get("Create"), buttons.get("List"), buttons.get("Update"), buttons.get("Delete"));
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

    public StackPane teamLayout()
    {
        String[] names = {"Team Name", "Jersey Colour"};
        boolean[] fieldConstraints = {false, false};

        Map<String, TextField> fields = form.createFieldMap(names, fieldConstraints);
        Map<String, Label> labels = form.createLabelMap(names);
        Map<String, Button> buttons = form.createButtonMap(new String[] {"Create", "Cancel", "Select Manager"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        HBox[] rows = {new HBox(25), new HBox(25)};
        rows[0].getChildren().addAll(labels.get(names[0]), fields.get(names[0]));
        rows[0].getChildren().addAll(labels.get(names[1]), fields.get(names[1]));


        buttons.get("Select Manager").setOnAction(e -> form.popup(e, selectManager()));
        rows[0].getChildren().addAll(buttons.get("Select Manager"));
        rows[1] = new HBox(25, buttons.get("Create"), buttons.get("Cancel"));

        VBox temp = new VBox(25, rows[0], rows[1]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    public StackPane selectManager()
    {
        manager = null;
        Label labelManagers = new Label("Select Manager");
        ComboBox<String> comboManagers = new ComboBox<>();
        AppTheme.set(labelManagers);

        List<Manager> managers = controller.getManagers();
        managers.forEach(m -> comboManagers.getItems().add(m.getFirstName() + " " + m.getLastName()));

        Map<String, Button> buttons = form.createButtonMap(new String[]{"Select", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get("Select").setOnAction(e -> {
            int index = comboManagers.getSelectionModel().getSelectedIndex();
            if (index != -1)
            {
                manager = managers.get(index);
            }
            form.closeThis(e);
        });

        VBox temp = new VBox(25, new HBox(50, labelManagers, comboManagers), new HBox(50, buttons.get("Select"), buttons.get("Cancel")));
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

}
// 74 ->