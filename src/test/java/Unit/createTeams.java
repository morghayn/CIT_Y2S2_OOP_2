package Unit;

import Controller.Controller;
import Model.POJO.Team;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class createTeams
{
    private Controller controller;

    public createTeams(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        controller = new Controller(ENTITY_MANAGER_FACTORY);
    }

    @Test
    public void create(int amountToCreateForTest)
    {
        // Counting current quantity of teams in our database
        int originalTeamSize = controller.getTeams().size();

        // Adding new teams of a quantity specified in our parameters,
        // Also keeping track of the instances created for removal at end of test
        List<Team> teamsPersisted = new ArrayList<>();
        for (int i = 0; i < amountToCreateForTest; i ++)
        {
            Team team = controller.createTeam("Team Test" + i, "Orange" + i);
            teamsPersisted.add(team);
            controller.persistTeam(team);
        }

        // Counting current quantity of teams in our database
        int updatedTeamSize = controller.getTeams().size();
        Assertions.assertEquals(amountToCreateForTest, updatedTeamSize - originalTeamSize);

        // Removal of teams added during test
        for (Team team : teamsPersisted)
        {
            controller.delete(team);
        }

        // Confirming removal was successful
        updatedTeamSize = controller.getTeams().size();
        Assertions.assertEquals(originalTeamSize, updatedTeamSize);
    }
}
