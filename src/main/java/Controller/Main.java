package Controller;

import Model.POJO.Manager;
import Model.POJO.Name;
import Model.POJO.Player;
import Model.POJO.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main
{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");


    public static void main(String[] args)
    {
        Model.POJO.Name name = new Name("Jim-", "Jim-", "Bob-");
        Model.POJO.Player manager = new Player();
        manager.setName(name);
        manager.setNumGoals(5);
        manager.setGoalie(false);
        manager.setEmail("morgan.nolan@hotmail.com");

        Model.POJO.Team team = new Team();
        team.setJerseyColour("Red");
        team.setName("mmmmmmm");
      //  team.setManager(manager);


        manager.setTeam(team);

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Model.DAO.Player dm = new Model.DAO.Player(em);

        Model.DAO.League.addTeam(team);
        dm.persist(manager);

       // System.out.println(team.getManager().getName().getFirstName());


        //System.out.println("> Before deleting");
        //Model.DAO.Manager.getManagers();

        Model.DAO.League.removeTeam(team); // TODO change this here to object instance <---

        manager.getName().setFirstName("Morgan");
        dm.update(manager);
        //System.out.println("> After deleting");
        //Model.DAO.Manager.getManagers();
    }

/*
        for (int i = 0; i < 5; i++)
        {
            Model.POJO.Name name = new Name("Jim-" + i, "Jim-" + i, "Bob-" + i);

            Model.POJO.Player player = new Model.POJO.Player();
            player.setName(name);
            player.setPhone("0873539835");
            player.setEmail("morgan.nolan@hotmail.com");
            player.setGoalie(true);
            player.setNumGoals(0);

            Model.DAO.Player.addPlayer(player);
        }
*/

    // Model.DAO.Player.getPlayers();

}
