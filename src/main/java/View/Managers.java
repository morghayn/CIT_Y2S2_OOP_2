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
import org.hibernate.sql.Select;

import javax.persistence.criteria.Selection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static javafx.geometry.Pos.BASELINE_CENTER;
import static javafx.geometry.Pos.CENTER;

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
    private SelectionPopup selectionPopup;

    public Managers(Controller controller)
    {
        this.controller = controller;
        form = new Form(controller);
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
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "List", "Update", "Delete", "Team's Manager"});

        buttons.get("Create").setOnAction(e -> form.popup(e, managerLayout(true), 920, 260));
        buttons.get("List").setOnAction(e -> populateTableView());
        buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());
        buttons.get("Team's Manager").setOnAction(e -> {
            selectTeam(e, false);
            if (team != null) // Todo do I need this?
            {
                populateTableView(controller.getTeamManager(team));
            }
        });

        HBox temp = new HBox(50, buttons.get("Create"), buttons.get("List"), buttons.get("Update"), buttons.get("Delete"), buttons.get("Team's Manager"));
        temp.setAlignment(BASELINE_CENTER);
        return temp;
    }

    /**
     * <p>Attempts to begin the update process for the currently selected domain object within the TableView of this
     * class. If such a selection is valid, this method will act as a driver method, working in conjunction with other
     * methods such as ${@link #submitUpdate(ActionEvent)}. If such a selection is not valid, an error message will be
     * delivered to the end user via a popup instance of ${@link PopupWindow}.</p>
     *
     * @param e the ActionEvent in which is driving this event. Used to close down the window opened during this
     *          process
     */
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
            new PopupWindow("Removal Error", "No valid table selection.");
        }
    }

    /**
     * <p>Builds a TableView concerning the primary domain object of this class.</p>
     *
     * @return a TableView of the same type as the primary domain object concerning this class
     */
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

    /**
     * <p>Populates the TableView with a collection comprised of object instances pertaining the primary domain object
     * of concern for this class.</p>
     */
    public void populateTableView()
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getManagers());
    }

    /**
     * <p>Populates the TableView with an object instances passed through it's parameters.</p>
     *
     * @param manager the domain specific to the TableView in question
     */
    public void populateTableView(Manager manager)
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(manager);
    }

    /**
     * <p>A multi-purpose method, which works in conjunction with {@link Form} to build forms for either updating or
     * creating information regarding the domain of concern.</p>
     *
     * @param isCreate a boolean which helps the method distinguish what type of form is currently needed
     */
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
        buttons.get((isCreate ? "Create" : "Update")).setOnAction(isCreate ? this::submitForm : this::submitUpdate);
        buttons.get("Select Team").setOnAction(e -> selectTeam(e, !isCreate));
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

    /**
     * <p>In conjunction with the create form, this method acts as closure when the create button is pressed by
     * collecting the information entered, sending it onto the form class, and then onto the controller.</p>
     *
     * @param e the event which is driving the create form. Used to close the currently opened form
     */
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

    /**
     * <p>In conjunction with the update form, this method acts as closure when the update button is pressed by
     * collecting the information entered, sending it onto the form class and forward onto the controller.</p>
     *
     * @param e the event which is driving the update form. Used to close the currently opened form
     */
    public void submitUpdate(ActionEvent e)
    {
        // creating new updated manager
        Manager manager = form.createManager(fields, dateOfBirth.getValue(), form.createPerson(fields, form.createName(fields)));

        // merging old manager details with new ones
        manager.setPersonID(this.manager.getPersonID());
        manager.setTeam(this.manager.getTeam());
        controller.updateManager(manager);

        if (manager != team.getManager())
        {
            controller.setManagerOfTeam(this.team, manager);
        }

        populateTableView();
        form.closeThis(e);
    }

    /**
     * <p>A GUI component used to select a Team instance from all Team instances available.</p>
     *
     * @param e the event driving this method call
     * @param isUpdate informs the method whether this selection is for updating, or merely just selecting
     */
    public void selectTeam(ActionEvent e, boolean isUpdate)
    {
        // Getting most up to date Player instance
        if (manager != null)
        {
            manager = controller.findManager(manager.getPersonID());
        }

        Team temp = (manager == null ? null : manager.getTeam());

        StackPane popup = selectionPopup.selectTeam(temp, isUpdate);
        form.popup(e, popup, 315, 140);
        Object object = selectionPopup.getValue();

        if (object instanceof Team)
        {
            team = (object != temp ? (Team) object : team);
        }
    }

}