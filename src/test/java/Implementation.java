import Unit.createManagers;
import Unit.createPlayers;
import Unit.createTeams;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Implementation
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");

    /**
     * <p>A test that is merely adding 250 teams, players and managers to our database, then removing each that has been adding.</p>
     *
     * <p>Testing removal and persist code in the controller.</p>
     */
    @Test
    public void ImplementTests()
    {
        createTeams teamTest = new createTeams(ENTITY_MANAGER_FACTORY);

        // This test will create 250 teams
        // Then check if those 250 teams were successfully added
        // Finish up the test by removing those 250 teams
        // Then check that the teams have been removed
        teamTest.create(250);

        createPlayers playerTest = new createPlayers(ENTITY_MANAGER_FACTORY);

        // This test will create 250 players
        // Then check if those 250 players were successfully added
        // Finish up the test by removing those 250 players
        // Then check that the players have been removed
        playerTest.create(250);

        createManagers managerTest = new createManagers(ENTITY_MANAGER_FACTORY);

        // This test will create 250 managers
        // Then check if those 250 managers were successfully added
        // Finish up the test by removing those 250 managers
        // Then check that the managers have been removed
        managerTest.create(250);
    }
}
