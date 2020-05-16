package View.Popup;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Team;
import View.Util.AppTheme;
import View.Util.FormFunctionality;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SelectionPopup
{

    private final FormFunctionality form;
    private final Controller controller;
    private Object selection;

    public SelectionPopup(FormFunctionality form, Controller controller)
    {
        this.form = form;
        this.controller = controller;
    }

    public StackPane selectTeam(Team team, boolean isUpdate)
    {
        ComboBox<Team> combo = new ComboBox<>();
        controller.getTeams().forEach(t -> combo.getItems().add(t));
        combo.getSelectionModel().select(team);

        return selectionPopup(combo, isUpdate);
    }

    public StackPane selectManager(Manager manager, boolean isUpdate)
    {
        ComboBox<Manager> combo = new ComboBox<>();
        controller.getManagers().forEach(m -> combo.getItems().add(m));
        combo.getSelectionModel().select(manager);

        return selectionPopup(combo, isUpdate);
    }

    private StackPane selectionPopup(ComboBox<?> combo, boolean isUpdate)
    {
        Label label = new Label("Select");
        AppTheme.set(label);
        return new StackPane(buildPopup(label, combo, getStringButtonMap(combo), isUpdate));
    }

    private VBox buildPopup(Label label, ComboBox<?> combo, Map<String, Button> buttons, boolean isUpdate)
    {
        label.setAlignment(Pos.CENTER);

        // VBox
        VBox temp = new VBox(30);
        temp.setPadding(new Insets(30, 20, 20, 20));

        // HBoxes
        HBox[] set = {
                new HBox(50, isUpdate ? buttons.get("Remove") : label, combo),
                new HBox(50, buttons.get("Select"), buttons.get("Cancel"))
        };
        for (HBox row : set)
        {
            row.setAlignment(Pos.CENTER);
            temp.getChildren().add(row);
        }

        // Return
        return temp;
    }

    private Map<String, Button> getStringButtonMap(ComboBox<?> combo)
    {
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Remove", "Select", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get("Remove").setOnAction(e -> {
            setValue(null);
            combo.getSelectionModel().select(null);
        });
        buttons.get("Select").setOnAction(e -> {
            setValue(combo.getSelectionModel().getSelectedIndex() == -1 ? null : combo.getValue());
            form.closeThis(e);
        });
        return buttons;
    }

    private void setValue(Object object)
    {
        selection = object;
    }

    public Object getValue()
    {
        return selection;
    }

}
