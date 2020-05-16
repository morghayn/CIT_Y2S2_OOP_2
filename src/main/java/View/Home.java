package View;

import Controller.Controller;
import Model.POJO.Name;
import Model.POJO.Person;
import Model.POJO.Player;
import View.Popup.PopupNotify;
import View.Util.AppTheme;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class Home
{

    private Controller controller;
    private EntityManagerFactory ENTITY_MANAGER_FACTORY; // I CLOSE THIS WHEN I CLICK QUIT IN THE HOME TAB

    public Home(EntityManagerFactory ENTITY_MANAGER_FACTORY, Controller controller)
    {
        this.controller = controller;
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

    public StackPane setup()
    {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResourceAsStream("/balls.jpg")));

        Button memTest = new Button("Mem Test");
        memTest.setOnAction(e -> memTest(1000));
        AppTheme.setBig(memTest);

        Button quit = new Button("Quit");
        quit.setOnAction(this::quitProgram);
        AppTheme.setBig(quit);

        HBox temp = new HBox(100, memTest, quit);
        temp.setAlignment(CENTER);

        VBox mainColumn = new VBox(50, temp);
        mainColumn.setPadding(new Insets(150, 20, 20, 20));

        return new StackPane(imageView, mainColumn);
    }

    private void memTest(int n)
    {
        List<Player> players = new ArrayList<Player>();

        for(int i = 0; i < n; i++)
        {
            Name name = controller.createName("Player", i + "", "Test");
            Person person = controller.createPerson(name, "0854534" + i, "testEmail" + i + "@mycit.ie");
            Player player = controller.createPlayer(person, 6, i % 12 == 0);
            players.add(player);
            controller.persistPlayer(player);
        }

        for(int i = 0; i < n; i++)
        {
            controller.delete(players.get(i));
        }

        new PopupNotify("MemTest Complete", "MemTest Completed Successfully!");
    }

    private void quitProgram(ActionEvent e)
    {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        ENTITY_MANAGER_FACTORY.close();
    }

}
