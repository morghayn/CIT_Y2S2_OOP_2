package View;

/*
public class SeperatingTemp
{

    private Controller controller = new Controller(null);
    private Form form = new Form(null);

    public StackPane selectTeam(Team currentTeam)
    {
        Label labelTeams = new Label("Select Team");
        ComboBox<Team> comboTeams = new ComboBox<>();
        AppTheme.set(labelTeams);

        List<Team> teams = controller.getTeams();
        teams.forEach(t -> comboTeams.getItems().add(t));

        Team selectedTeam = currentTeam;
        Map<String, Button> buttons = form.createButtonMap(new String[]{"Remove Team", "Select", "Cancel"});
        buttons.get("Cancel").setOnAction(form::closeThis);
        buttons.get("Remove Team").setOnAction(() -> {
            currentTeam = null;
            comboTeams.getSelectionModel().select(-1);
        });
        buttons.get("Select").setOnAction(e -> {
            int index = comboTeams.getSelectionModel().getSelectedIndex();
            if (index != -1)
            {
                selectedTeam = teams.get(index);
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
*/