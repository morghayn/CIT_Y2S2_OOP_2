package View;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Create
{

    public void Create()
    {
        //this.controller = controller;
    }

    public StackPane setup()
    {
        Button createPlayer = new Button("Create Player");
        Button createManager = new Button("Create Manager");
        Button createTeam = new Button("Create Team");

        AppTheme.set(createPlayer);
        AppTheme.set(createManager);
        AppTheme.set(createTeam);

        VBox mainColumn = new VBox(50, createPlayer, createManager, createTeam);
        mainColumn.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(mainColumn);
    }

    /*
Manager
----------
firstName
middleName
lastName
phone
email
dateOfBirth
starRating
*/

    public void createManager()
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
    }

    public void createPlayer()
    {
        TextField fieldFirstName = new TextField();
        TextField fieldMiddleName = new TextField();
        TextField fieldLastName = new TextField();
        TextField fieldPhone = new TextField();
        TextField fieldEmail = new TextField();
        TextField fieldNumGoals = new TextField();
        TextField fieldIsGoalie = new TextField();
        // TODO set text formatter for the fields

        Label labelFirstName = new Label("First Name");
        Label labelMiddleName = new Label("Middle Name");
        Label labelLastName = new Label("Last Name");
        Label labelPhone = new Label("Phone");
        Label labelEmail = new Label("Email");
        Label labelNumGoals = new Label("Goal Number");
        Label labelIsGoalie = new Label("Is Goalie");
        AppTheme.set(labelFirstName);
        AppTheme.set(labelMiddleName);
        AppTheme.set(labelLastName);
        AppTheme.set(labelPhone);
        AppTheme.set(labelEmail);
        AppTheme.set(labelNumGoals);
        AppTheme.set(labelIsGoalie);

        // :: instantiating and styling new buttons
        Button buttonCreate = new Button("Create");
        Button buttonCancel = new Button("Cancel");
        AppTheme.set(buttonCreate);
        AppTheme.set(buttonCancel);
    }

    public void createTeam()
    {
        TextField fieldTeamName = new TextField();
        TextField fieldJerseyColour = new TextField();
        // TODO set text formatter for the fields

        Label labelTeamName = new Label("Team Name");
        Label labelJerseyColour = new Label("Jersey Colour");
        AppTheme.set(labelTeamName);
        AppTheme.set(labelJerseyColour);

        // :: instantiating and styling new buttons
        Button buttonCreate = new Button("Create");
        Button buttonCancel = new Button("Cancel");
        AppTheme.set(buttonCreate);
        AppTheme.set(buttonCancel);
    }

}
