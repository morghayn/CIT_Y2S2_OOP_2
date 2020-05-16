import Controller.Controller;
import Model.DAO.PlayerDAO;
import Model.DAO.TeamDAO;
import Model.POJO.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test
{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");
    private static Controller controller;


    public static void main(String[] args)
    {
        controller = new Controller(ENTITY_MANAGER_FACTORY);

        List<Player> players = createPlayers(100);
        List<Manager> managers = createManagers(10);
        List<Team> teams = createTeams(10);

        for (int i = 0, j = 0; i < players.size(); i++, j += ((i) % 10 == 0 ? 1 : 0))
        {
            controller.setPlayersTeam(teams.get(j), players.get(i));
            controller.setManagerOfTeam(teams.get(j), managers.get(j));
        }

        System.out.println("------------------------------------------");
        System.out.println("\nDisplay Teams");
        teams.forEach(t -> System.out.println(t.getName()));

        System.out.println("\nDisplay Managers");
        managers.forEach(m -> System.out.println(m.getFirstName() + " " + m.getMiddleName() + " " + m.getLastName()));

        System.out.println("\nDisplay Players");
        players.forEach(p -> System.out.println(p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName()));

        System.out.println("------------------------------------------\n\n");


        System.out.println("Manager 4 should be managing Team 4");
        Team temp = controller.getManagerTeam(managers.get(4));
        System.out.println("The team which manager 4 manages is...\t\t" + temp.getName());

    }

    public static List<Player> createPlayers(int n)
    {
        List<Player> playersPersisted = new ArrayList<>();
        for (int i = 0; i < n; i ++)
        {
            Name name = controller.createName("Player", i + "", "Test");
            Person person = controller.createPerson(name, "0854534" + i, "testEmail" + i + "@mycit.ie");
            Player player = controller.createPlayer(person, 6, i % 12 == 0);
            playersPersisted.add(player);
            controller.persistPlayer(player);
        }
        return playersPersisted;
    }

    public static List<Manager> createManagers(int n)
    {
        List<Manager> managersPersisted = new ArrayList<>();
        for (int i = 0; i < n; i ++)
        {
            Name name = controller.createName("Manager", i + "", "Test");
            Person person = controller.createPerson(name, "0854534" + i, "testEmail" + i + "@mycit.ie");
            Manager manager = controller.createManager(person, LocalDate.now(), i);
            managersPersisted.add(manager);
            controller.persistManager(manager);
        }
        return managersPersisted;
    }

    public static List<Team> createTeams(int n)
    {
        List<Team> teamsPersisted = new ArrayList<>();
        for (int i = 0; i < n; i ++)
        {
            Team team = controller.createTeam("Team Test " + i, "Orange " + i);
            teamsPersisted.add(team);
            controller.persistTeam(team);
        }
        return teamsPersisted;
    }

}
