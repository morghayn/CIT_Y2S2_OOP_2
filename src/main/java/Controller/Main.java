package Controller;

import Model.POJO.Manager;
import Model.POJO.Name;
import Model.POJO.Team;

public class Main
{

    public static void main(String[] args)
    {
        Model.POJO.Name name = new Name("Jim-", "Jim-", "Bob-");
        Model.POJO.Manager manager = new Manager();
        manager.setName(name);
        manager.setDateOfBirth("18-23-1999");
        manager.setStarRating(10);
        manager.setEmail("morgan.nolan@hotmail.com");


        Model.POJO.Team team = new Team();
        team.setJerseyColour("Red");
        team.setName("mmmmmmm");
        team.setManager(manager);

        Model.DAO.Manager.addManager(manager);
        Model.DAO.League.addTeam(team);

        System.out.println("> Before deleting");
        Model.DAO.Manager.getManagers();

        Model.DAO.League.deleteTeam((long) 1); // TODO change this here to object instance <---
        System.out.println("> After deleting");
        Model.DAO.Manager.getManagers();
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
