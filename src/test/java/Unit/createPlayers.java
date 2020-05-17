package Unit;

import Controller.Controller;
import Model.POJO.Name;
import Model.POJO.Person;
import Model.POJO.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class createPlayers
{
    private Controller controller;

    public createPlayers(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        controller = new Controller(ENTITY_MANAGER_FACTORY);
    }

    public void create(int amountToCreateForTest)
    {
        // Counting current quantity of players in our database
        int originalPlayerCount = controller.getPlayers().size();

        // Adding new players of a quantity specified in our parameters,
        // Also keeping track of the instances created for removal at end of test
        List<Player> playersPersisted = new ArrayList<>();
        for (int i = 0; i < amountToCreateForTest; i ++)
        {
            Name name = controller.createName("Test", i + "", "Player");
            Person person = controller.createPerson(name, "0854534" + i, "testEmail" + i + "@mycit.ie");
            Player player = controller.createPlayer(person, 6, i % 12 == 0);
            playersPersisted.add(player);
            controller.persistPlayer(player);
        }

        // Counting current quantity of players in our database
        int updatedPlayerCount = controller.getPlayers().size();
        Assertions.assertEquals(amountToCreateForTest, updatedPlayerCount - originalPlayerCount);

        // Removal of players added during test
        for (Player player : playersPersisted)
        {
            controller.delete(player);
        }

        // Confirming removal was successful
        updatedPlayerCount = controller.getPlayers().size();
        Assertions.assertEquals(updatedPlayerCount, originalPlayerCount);
    }
}
