package View;

import Controller.Controller;
import Model.POJO.Player;
import Model.POJO.Team;
import View.Popup.PopupNotify;
import View.Popup.SelectionPopup;
import View.Util.AppTheme;
import View.Util.FormFunctionality;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

import static javafx.geometry.Pos.*;

public class Players
{

    private final FormFunctionality form;
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
    private SelectionPopup selectionPopup;

    public Players(Controller controller)
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
        VBox temp = new VBox(25, buildSearchBar(), buildButtonBar(), buildTableView());
        temp.setPadding(new Insets(35, 20, 20, 20));

        return new StackPane(temp);
    }

    /**
     * <p>Driver method which builds a HBox comprising of search functionality pertaining
     * this class and the primary domain object of concern.</p>
     *
     * @return the HBox comprising of search functionality
     */
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
        temp.setAlignment(BASELINE_CENTER);
        return temp;
    }

    /**
     * <p>Driver method which builds a HBox comprising of buttons that will emit actions for functionality pertaining
     * this class.</p>
     *
     * @return the HBox comprising of functionality buttons
     */
    public HBox buildButtonBar()
    {
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Create", "List", "Update", "Delete", "Team Filter"});

        buttons.get("Create").setOnAction(e -> form.popup(e, playerLayout(true), 920, 260));
        buttons.get("List").setOnAction(e -> populateTableView());
        buttons.get("Update").setOnAction(this::update);
        buttons.get("Delete").setOnAction(e -> delete());
        buttons.get("Team Filter").setOnAction(e -> {
            selectTeam(e, false);
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

    /**
     * <p>If a valid selection is made within the TableView within this class, this method will attempt to retrieve
     * extra information pertaining the object selected within the TableView. If a valid selection is not found
     * within the TableView, an error will be displayed through an instance of ${@link PopupNotify}.</p>
     */
    public void extraPlayerDetails()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            player = tableView.getSelectionModel().getSelectedItem();
            new PopupNotify("", controller.getExtraPlayerDetails(player));
        }
        else
        {
            new PopupNotify("Update Error", "No valid table selection.");
        }
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

    /**
     * <p>Populates the TableView with a collection comprised of object instances pertaining the primary domain object
     * of concern for this class.</p>
     */
    public void populateTableView()
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getPlayers());
    }

    /**
     * <p>Populates the TableView with a collection comprised of object instances pertaining the primary domain object
     * of concern for this class.</p>
     *
     * @param players the domain specific to the TableView in question
     */
    public void populateTableView(List<Player> players)
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(players);
    }

    /**
     * <p>A multi-purpose method, which works in conjunction with {@link FormFunctionality} to build forms for either updating or
     * creating information regarding the domain of concern.</p>
     *
     * @param isCreate a boolean which helps the method distinguish what type of form is currently needed
     * @return the StackPane for the playerLayout
     */
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
        buttons.get("Select Team").setOnAction(e -> selectTeam(e, !isCreate));

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

    /**
     * <p>In conjunction with the create form, this method acts as closure when the create button is pressed by
     * collecting the information entered, sending it onto the form class, and then onto the controller.</p>
     *
     * @param e the event which is driving the create form. Used to close the currently opened form
     */
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

    /**
     * <p>In conjunction with the update form, this method acts as closure when the update button is pressed by
     * collecting the information entered, sending it onto the form class, and then onto the controller.</p>
     *
     * @param e the event which is driving the update form. Used to close the currently opened form
     */
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
        if (player != null)
        {
            player = controller.findPlayer(player.getPersonID());
        }

        Team temp = (player == null ? null : controller.getPlayerTeam(player));

        StackPane popup = selectionPopup.selectTeam(temp, isUpdate);
        form.popup(e, popup, 315, 140);
        Object object = selectionPopup.getValue();

        if (object instanceof Team)
        {
            team = (object != temp ? (Team) object : team);
        }
    }

}