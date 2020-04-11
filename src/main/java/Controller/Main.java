package Controller;

import Model.POJO.Name;
import Model.POJO.Team;

public class Main
{

    public static void main(String[] args)
    {
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

        Model.POJO.Team team = new Team();
        team.setJerseyColour("Red");
        team.setName("Lebby Debby");

        Model.DAO.League.addTeam(team);
        team.setName("Famble Damble");
        // TODO WIP Model.DAO.Team.updateTeam(team);
    }

}
