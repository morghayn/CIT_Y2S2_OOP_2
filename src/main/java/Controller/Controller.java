package Controller;

import Model.DAO.ManagerDAO;
import Model.DAO.PlayerDAO;
import Model.DAO.TeamDAO;
import Model.POJO.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Controller
{

    private ManagerDAO managerDAO;
    private PlayerDAO playerDAO;
    private TeamDAO teamDAO;

    public Controller(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        managerDAO = new ManagerDAO(ENTITY_MANAGER_FACTORY);
        playerDAO = new PlayerDAO(ENTITY_MANAGER_FACTORY);
        teamDAO = new TeamDAO(ENTITY_MANAGER_FACTORY);
    }

    // Create
    public Name createName(String firstName, String middleName, String lastName)
    {
        return new Name(firstName, middleName, lastName);
    }

    public Person createPerson(Name name, String phone, String email)
    {
        return new Person(name, phone, email);
    }

    public Manager createManager(Person person, String dateOfBirth, int starRating)
    {
        return new Manager(person, dateOfBirth, starRating);
    }

    public Player createPlayer(Person person, int numGoals, boolean isGoalie)
    {
        return new Player(person, numGoals, isGoalie);
    }

    public Team createTeam(String name, String jerseyColour)
    {
        return new Team(name, jerseyColour);
    }

    // Persist
    public void persistManager(Manager manager)
    {
        managerDAO.persist(manager);
    }

    public void persistPlayer(Player player)
    {
        playerDAO.persist(player);
    }

    public void persistTeam(Team team)
    {
        teamDAO.persist(team);
    }

    // Read
    public List<Manager> getManagers()
    {
        return managerDAO.getManagers();
    }

    public List<Player> getPlayers()
    {
        return playerDAO.getPlayers();
    }

    public List<Team> getTeams()
    {
        return teamDAO.getTeams();
    }

    public List<Player> searchName(String searchPhrase)
    {
        return playerDAO.searchName(searchPhrase);
    }

    // Update
    public void updateManager(Manager manager)
    {
        managerDAO.update(manager);
    }

    public void updatePlayer(Player player)
    {
        playerDAO.update(player);
    }

    public void updateTeam(Team team)
    {
        teamDAO.update(team);
    }

    // DELETE

    public void deleteManager(Manager manager)
    {
        managerDAO.remove(manager);
    }

    public void deletePlayer(Player player)
    {
        playerDAO.remove(player);
    }

    public void deleteTeam(Team team)
    {
        teamDAO.remove(team);
    }

    public void removeManagerFromTeam(Team team, Manager manager)
    {
        team.removeManager(manager);
        teamDAO.update(team);
        managerDAO.update(manager);
    }

    public void removePlayerFromTeam(Team team, Player player)
    {
        team.removePlayer(player);
        teamDAO.update(team);
        playerDAO.update(player);
    }

}
