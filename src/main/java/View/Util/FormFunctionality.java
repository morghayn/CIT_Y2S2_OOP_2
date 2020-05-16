package View.Util;

import Controller.Controller;
import Model.POJO.*;
import View.Util.AppTheme;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static java.lang.Integer.parseInt;

public class FormFunctionality
{

    private final Controller controller;

    public FormFunctionality(Controller controller)
    {
        this.controller = controller;
    }

    public void popup(ActionEvent e, StackPane form, int width, int height)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        Group root = new Group();
        Stage dialog = new Stage();
        Scene temp = new Scene(root, width, height);
        dialog.setScene(temp);
        root.getChildren().add(form);

        dialog.centerOnScreen();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    public Map<String, TextField> createFieldMap(String[] names, boolean[] fieldConstraints)
    {
        Map<String, TextField> fields = new HashMap<>();
        for(int i = 0; i < names.length; i++)
        {
            TextField temp = new TextField();
            if (fieldConstraints[i])
            {
                temp.setTextFormatter(new TextFormatter<>(allowNumbers()));
            }
            fields.put(names[i], temp);
        }
        return fields;
    }

    public Map<String, Label> createLabelMap(String[] names)
    {
        Map<String, Label> labels = new HashMap<>();
        for (String name : names)
        {
            Label temp = new Label(name);
            AppTheme.set(temp);
            labels.put(name, temp);
        }
        return labels;
    }

    public Map<String, Button> createButtonMap(String[] names)
    {
        Map<String, Button> buttons = new HashMap<>();
        for (String name : names)
        {
            Button temp = new Button(name);
            AppTheme.set(temp);
            buttons.put(name, temp);
        }
        return buttons;
    }

    public Name createName(Map<String, TextField> fields)
    {
        return controller
                .createName
                        (fields.get("First Name").getText(), fields.get("Middle Name").getText(), fields.get("Last Name").getText());
    }

    public Person createPerson(Map<String, TextField> fields, Name name)
    {
        return controller
                .createPerson
                        (name, fields.get("Phone").getText(), fields.get("Email").getText());
    }

    public Manager createManager(Map<String, TextField> fields, LocalDate dateOfBirth, Person person)
    {
        String temp = fields.get("Star Rating").getText();
        int starRating = (temp.equals("") ? 0 : parseInt(temp));
        return controller
                .createManager
                        (person, dateOfBirth, starRating);
    }

    public Player createPlayer(Map<String, TextField> fields, boolean goalie, Person person)
    {
        String temp = fields.get("Goal Number").getText();
        int goalNum = (temp.equals("") ? 0 : parseInt(temp));
        return controller
                .createPlayer
                        (person, goalNum, goalie);
    }

    /**
     * <p>Returns a unary operator to format text boxes to only allow number input.</p>
     *
     * @return the unary operator that is generated, will either allow all whole integers, or just positive integers
     */
    private static UnaryOperator<TextFormatter.Change> allowNumbers()
    {
        return change -> (change.getControlNewText().matches("([0-9]*)?") ? change : null);
    }

    public void closeThis(ActionEvent e)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public HBox[] createHBoxes(int amount)
    {
        HBox[] temp = new HBox[amount];

        for (int i = 0; i < amount; i++)
        {
            temp[i] = new HBox(25);
        }

        return temp;
    }

    public void binGUIComponents(String[] names, HBox[] rows, Map<String, Label> labels, Map<String, TextField> fields)
    {
        for (int i = 0; i < names.length; i++)
        {
            rows[i / 3].getChildren().addAll(labels.get(names[i]), fields.get(names[i]));
        }
    }

}
