package Unit;

import Controller.Controller;
import Model.POJO.Manager;
import Model.POJO.Name;
import Model.POJO.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class createManagers
{
    private Controller controller;

    public createManagers(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        controller = new Controller(ENTITY_MANAGER_FACTORY);
    }

    @Test
    public void create(int amountToCreateForTest)
    {
        // Counting current quantity of managers in our database
        int originalManagerCount = controller.getManagers().size();

        // Adding new managers of a quantity specified in our parameters,
        // Also keeping track of the instances created for removal at end of test
        List<Manager> managersPersisted = new ArrayList<>();
        for (int i = 0; i < amountToCreateForTest; i ++)
        {
            Name name = controller.createName("Test", i + "", "Player");
            Person person = controller.createPerson(name, "0854534" + i, "testEmail" + i + "@mycit.ie");
            Manager manager = controller.createManager(person, LocalDate.now(), i);
            managersPersisted.add(manager);
            controller.persistManager(manager);
        }

        // Counting current quantity of managers in our database
        int updatedManagerCount = controller.getManagers().size();
        Assertions.assertEquals(amountToCreateForTest, updatedManagerCount - originalManagerCount);

        // Removal of managers added during test
        for (Manager manager : managersPersisted)
        {
            controller.delete(manager);
        }

        // Confirming removal was successful
        updatedManagerCount = controller.getManagers().size();
        Assertions.assertEquals(updatedManagerCount, originalManagerCount);
    }
}
