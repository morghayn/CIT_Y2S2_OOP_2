package Controller;

import Model.DAO.ManagerDAO;
import Model.DAO.PlayerDAO;
import Model.DAO.TeamDAO;
import Model.POJO.*;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
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

    public Manager createManager(Person person, LocalDate dateOfBirth, int starRating)
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

    public List<Player> getTeamPlayers(Team team)
    {
        return team.getPlayers();
    }

    public String getExtraPlayerDetails(Player player)
    {
        Team team = player.getTeam();
        Manager manager = team != null ? team.getManager() : null;
        return (
                "\nTeam Details: " +
                (team != null ?
                 "\n- Team Name: " + team.getName() +
                 "\n- Jersey Colour: " + team.getJerseyColour()
                              : "\nNo team details, team is null!") +

                "\n\nManager Details: " +
                (manager != null ?
                 "\n- Name: " + manager.getFirstName() + " " + manager.getMiddleName() + " " + manager.getLastName() +
                 "\n- Date of Birth: " + manager.getDateOfBirth().toString() +
                 "\n- Phone: " + manager.getPhone() +
                 "\n- Email: " + manager.getEmail()
                                 : "\nNo Manager, so no details available!")
        );
    }

    public String getManagerTeam(Manager manager)
    {
        Team team = manager.getTeam();
        String str = "Manager is not assigned a team currently!";

        if(team != null)
        {
            str = "Team name: \n\n- " + team.getName();
        }

        return str;
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

    public void setManagerOfTeam(Team team, Manager manager)
    {
        Team prevTeam = manager.getTeam();
        if (prevTeam != null)
        {
            prevTeam.setManager(null);
            updateTeam(prevTeam);
        }
        team.setManager(manager);
        updateTeam(team);
    }

    public void setPlayersTeam(Team team, Player player)
    {
        team.addPlayer(player);
        updateTeam(team);
    }

    // DELETE

    public void delete(Manager manager)
    {
        Team managerTeam = manager.getTeam();
        if (managerTeam != null)
        {
            managerTeam.setManager(null);
            updateTeam(managerTeam);
        }
        manager.setTeam(null);
        managerDAO.remove(manager);
    }

    public void delete(Player player)
    {
        playerDAO.remove(player);
    }

    public void delete(Team team)
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
