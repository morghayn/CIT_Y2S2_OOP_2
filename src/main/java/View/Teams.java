package View;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Team;
import View.Popup.PopupNotify;
import View.Popup.SelectionPopup;
import View.Util.FormFunctionality;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;

import static javafx.geometry.Pos.BASELINE_CENTER;

public class Teams
{

    private final FormFunctionality form;
    private final Controller controller;
    private TableView<Team> tableView;

    private Map<String, TextField> fields;
    private Map<String, Label> labels;
    private Map<String, Button> buttons;
    private Manager manager;
    private Team team;
    private SelectionPopup selectionPopup;

    public Teams(Controller controller)
    {
        this.controller = controller;
        form = new FormFunctionality(controller);
        selectionPopup = new SelectionPopup(form, controller);
    }

    /**
     * <p>Driver GUI component in which numerous GUI components are assembled into a StackPane, returned to the main
     * class and added to the tabbed layout.</p>
     *
     * @return a StackPane containing all the GUI components pertaining to this particular form
     */
    public StackPane setup()
    {
        VBox temp = new VBox(25, buildButtonBar(), buildTableView());
        temp.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(temp);
    }

    /**
     * <p>Driver method which builds a HBox comprising of buttons that will emit actions for functionality pertaining
     * this class.</p>
     *
     * @return the HBox comprising of functionality buttons
     */
    public HBox buildButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "List", "Update", "Delete", "Manager's Team"});

        buttons.get("Create").setOnAction(e -> form.popup(e, teamLayout(true), 750, 200));
        buttons.get("List").setOnAction(e -> populateTableView());
        buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());
        buttons.get("Manager's Team").setOnAction(e -> {
            selectManager(e, false);
            if (manager != null) // TODO do I need this?
            {
                populateTableView(controller.getManagerTeam(manager));
            }
        });

        HBox temp = new HBox(50, buttons.get("Create"), buttons.get("List"), buttons.get("Update"), buttons.get("Delete"), buttons.get("Manager's Team"));
        temp.setAlignment(BASELINE_CENTER);
        return temp;
    }

    /**
     * <p>Attempts to begin the update process for the currently selected domain object within the TableView of this
     * class. If such a selection is valid, this method will act as a driver method, working in conjunction with other
     * methods such as ${@link #submitUpdate(ActionEvent)}. If such a selection is not valid, an error message will be
     * delivered to the end user via a popup instance of ${@link PopupNotify}.</p>
     *
     * @param e the ActionEvent in which is driving this event. Used to close down the window opened during this
     *          process
     */
    public void update(ActionEvent e)
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            team = tableView.getSelectionModel().getSelectedItem();
            StackPane temp = teamLayout(false);
            fields.get("Team Name").setText(team.getName());
            fields.get("Jersey Colour").setText(team.getJerseyColour());
            form.popup(e, temp, 750, 200);
        }
        else
        {
            new PopupNotify("Update Error", "No valid table selection.");
        }
    }

    /**
     * <p>Attempts to remove the currently selected domain object within the TableView in this class. If no object is
     * selected within the TableView an error popup is displayed to notify the end user.</p>
     */
    public void delete()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            controller.delete(tableView.getSelectionModel().getSelectedItem());
            populateTableView();
        }
        else
        {
            new PopupNotify("Removal Error", "No valid table selection.");
        }
    }

    /**
     * <p>Builds a TableView concerning the primary domain object of this class.</p>
     *
     * @return a TableView of the same type as the primary domain object concerning this class
     */
    public TableView<Team> buildTableView()
    {
        tableView = new TableView<>();
        String[] columns = new String[]{"name", "jerseyColour"};

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

    /**
     * <p>Populates the TableView with a collection comprised of object instances pertaining the primary domain object
     * of concern for this class.</p>
     */
    public void populateTableView()
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getTeams());
    }

    /**
     * <p>Populates the TableView with an object instances passed through it's parameters.</p>
     *
     * @param team the domain specific to the TableView in question
     */
    public void populateTableView(Team team)
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(team);
    }

    /**
     * <p>A multi-purpose method, which works in conjunction with {@link FormFunctionality} to build forms for either updating or
     * creating information regarding the domain of concern.</p>
     *
     * @param isCreate a boolean which helps the method distinguish what type of form is currently needed
     * @return the StackPane for the playerLayout
     */
    public StackPane teamLayout(boolean isCreate)
    {
        manager = null;
        String[] names = {"Team Name", "Jersey Colour"};
        boolean[] fieldConstraints = {false, false};

        fields = form.createFieldMap(names, fieldConstraints);
        labels = form.createLabelMap(names);
        buttons = form.createButtonMap(new String[]{(isCreate ? "Create" : "Update"), "Cancel", "Select Manager"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        HBox[] rows = {new HBox(25), new HBox(25)};
        rows[0].getChildren().addAll(labels.get(names[0]), fields.get(names[0]));
        rows[0].getChildren().addAll(labels.get(names[1]), fields.get(names[1]));

        buttons.get(isCreate ? "Create" : "Update").setOnAction(isCreate ? this::submitForm : this::submitUpdate);
        buttons.get("Select Manager").setOnAction(e -> selectManager(e, !isCreate));
        rows[0].getChildren().addAll(buttons.get("Select Manager"));
        rows[1] = new HBox(50, buttons.get(isCreate ? "Create" : "Update"), buttons.get("Cancel"));
        rows[1].setAlignment(BASELINE_CENTER);

        VBox temp = new VBox(40, rows[0], rows[1]);
        temp.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(temp);
    }

    /**
     * <p>In conjunction with the create form, this method acts as closure when the create button is pressed by
     * collecting the information entered, sending it onto the form class, and then onto the controller.</p>
     *
     * @param e the event which is driving the create form. Used to close the currently opened form
     */
    public void submitForm(ActionEvent e)
    {
        Team team = controller.createTeam(fields.get("Team Name").getText(), fields.get("Jersey Colour").getText());
        controller.persistTeam(team);
        if (manager != null)
        {
            controller.setManagerOfTeam(team, manager);
        }
        populateTableView();
        form.closeThis(e);
    }

    /**
     * <p>In conjunction with the update form, this method acts as closure when the update button is pressed by
     * collecting the information entered, sending it onto the form class, and then onto the controller.</p>
     *
     * @param e the event which is driving the update form. Used to close the currently opened form
     */
    public void submitUpdate(ActionEvent e)
    {
        // creating new updated team
        Team team = controller.createTeam(fields.get("Team Name").getText(), fields.get("Jersey Colour").getText());

        // merging old manager details with new ones
        team.setTeamID(this.team.getTeamID());
        controller.updateTeam(team);

        if (manager != team.getManager())
        {
            controller.setManagerOfTeam(this.team, manager);
        }

        populateTableView();
        form.closeThis(e);
    }

    /**
     * <p>A GUI component used to select a Manager instance from all Manager instances available.</p>
     *
     * @param e the event driving this method call
     * @param isUpdate informs the method whether this selection is for updating, or merely just selecting
     */
    public void selectManager(ActionEvent e, boolean isUpdate)
    {
        // Getting most up to date Team instance
        if (team != null)
        {
            team = controller.findTeam(team.getTeamID());
        }

        Manager temp = (team == null ? null : controller.getTeamManager(team));

        StackPane popup = selectionPopup.selectManager(temp, isUpdate);
        form.popup(e, popup, 315, 140);
        Object object = selectionPopup.getValue();

        if (object instanceof Manager)
        {
            manager = (object != temp ? (Manager) object : manager);
        }
    }

}