import Model.DAO.PlayerDAO;
import Model.DAO.TeamDAO;
import Model.POJO.Name;
import Model.POJO.Player;
import Model.POJO.Team;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Yooo
{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");


    public static void main(String[] args)
    {
        //PlayerDAO playerDAO = new PlayerDAO(ENTITY_MANAGER_FACTORY);

       // Player player = playerDAO.find(2);
        //System.out.println(person.getPersonID());

        //playerDAO.remove(player);

        //playerDAO.removePlayer(playerDAO.getPlayer(1));

        // addPlayersToTeamDemo();
        // getTeamPlayersDemo();
        // addPlayerDemo();
    }

    public static void addPlayersToTeamDemo()
    {
        PlayerDAO playerDAO = new PlayerDAO(ENTITY_MANAGER_FACTORY);
        //getTeamPlayersDemo();

        Model.POJO.Team team = new Team();
        team.setJerseyColour("Red");
        team.setName("Peppers");

        for(int i = 0; i < 15; i++)
        {
            Name name = new Name("Jim" + i, "Jim" + i, "Bob" + i);
            Player player = new Player();
            player.setName(name);
            player.setNumGoals(5);
            player.setGoalie(false);
            player.setEmail("morgan.nolan@hotmail.com + i");

            // adding player to database
            playerDAO.persist(player);

            // adding player to team's player list
            team.getPlayers().add(player);
        }

        // adding team to database
        // *JPA SHOULD UPDATE ALL THE PLAYER's TEAM COLUMN HERE*
    }

    public static void getTeamPlayersDemo()
    {
        TeamDAO teamDAO = new TeamDAO(ENTITY_MANAGER_FACTORY);

        Team team = teamDAO.getTeam((long) 1);
        List<Player> players = team.getPlayers();

        for(Player player : players)
        {
            System.out.println(player.getName().getFirstName());
        }
    }

    public static void addPlayerDemo()
    {
        PlayerDAO playerDAO = new PlayerDAO(ENTITY_MANAGER_FACTORY);
        TeamDAO teamDAO = new TeamDAO(ENTITY_MANAGER_FACTORY);
        Team team = teamDAO.getTeam((long) 1);

        Name name = new Name("I have no team :(", "Jim", "Bob");
        Player player = new Player();
        player.setName(name);
        player.setNumGoals(5);
        player.setGoalie(false);
        player.setEmail("morgan.nolan@hotmail.com");

        playerDAO.persist(player);

        team.addPlayer(player);
        teamDAO.update(team);

        //team = teamDAO.getTeam((long) 1);
        team.removePlayer(player);
        teamDAO.update(team);
        playerDAO.update(player);
    }

}
