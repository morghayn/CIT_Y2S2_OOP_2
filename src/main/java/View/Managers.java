package View;

import Controller.Controller;
import Model.POJO.Manager;
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
    private VBox mainColumn;

    public Managers(Controller controller)
    {
        this.controller = controller;
    }

    public StackPane setup()
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
        HBox buttons = new HBox(50, create, list, update, delete);


        tableView = new TableView<>();
        TableColumn<Manager, String> column1, column2, column3, column4, column5, column6, column7;
        column1 = new TableColumn<>("firstName");
        column2 = new TableColumn<>("middleName");
        column3 = new TableColumn<>("lastName");
        column4 = new TableColumn<>("phone");
        column5 = new TableColumn<>("email");
        column6 = new TableColumn<>("starRating");
        column7 = new TableColumn<>("dateOfBirth");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("phone"));
        column5.setCellValueFactory(new PropertyValueFactory<>("email"));
        column6.setCellValueFactory(new PropertyValueFactory<>("starRating"));
        column7.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        column1.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        column2.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        column3.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        column4.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        column5.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        column6.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        column7.prefWidthProperty().bind(tableView.widthProperty().divide(7));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        VBox mainColumn = new VBox(50, buttons, tableView);
        mainColumn.setPadding(new Insets(50, 20, 20, 20));
        return new StackPane(mainColumn);
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

        buttonCreate.setOnAction(e ->
                                 {
                                     controller.persistManager
                                             (
                                                     controller.createManager
                                                             (
                                                                     controller.createPerson
                                                                             (
                                                                                     controller.createName
                                                                                             (
                                                                                                     fieldFirstName.getText(),
                                                                                                     fieldMiddleName.getText(),
                                                                                                     fieldLastName.getText()
                                                                                             ),
                                                                                     fieldEmail.getText(),
                                                                                     fieldPhone.getText()
                                                                             ),
                                                                     fieldDateOfBirth.getText(),
                                                                     parseInt(fieldStarRating.getText())
                                                             )
                                             );
                                     final Node source = (Node) e.getSource();
                                     final Stage stage = (Stage) source.getScene().getWindow();
                                     stage.close();
                                 }
            );

        HBox row1 = new HBox(25, labelFirstName, fieldFirstName, labelMiddleName, fieldMiddleName, labelLastName, fieldLastName);
        HBox row2 = new HBox(25, labelPhone, fieldPhone, labelEmail, fieldEmail);
        HBox row3 = new HBox(25, labelDateOfBirth, fieldDateOfBirth, labelStarRating, fieldStarRating);
        HBox row4 = new HBox(25, buttonCreate, buttonCancel);
        VBox temp = new VBox(25, row1, row2, row3, row4);
        temp.setPadding(new Insets(50, 20, 20, 20));

        return new StackPane(temp);
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
            controller.deleteManager(manager);
            list();
        }
        else
        {
            new PopupWindow("Removal Error", "No valid table selection.");
        }
    }

}
