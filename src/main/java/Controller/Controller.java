package Controller;

import Model.DAO.ManagerDAO;
import Model.DAO.PlayerDAO;
import Model.DAO.TeamDAO;
import Model.POJO.*;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

/**
 * Class responsible for managing communication between View and Model
 */
public class Controller
{

    private final ManagerDAO managerDAO;
    private final PlayerDAO playerDAO;
    private final TeamDAO teamDAO;

    public Controller(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        managerDAO = new ManagerDAO(ENTITY_MANAGER_FACTORY);
        playerDAO = new PlayerDAO(ENTITY_MANAGER_FACTORY);
        teamDAO = new TeamDAO(ENTITY_MANAGER_FACTORY);
    }

    /**
     * <p>Creates and returns a Name object.</p>
     *
     * @param firstName  first name
     * @param middleName middle name
     * @param lastName   last name
     * @return the Name object to be returned
     */
    public Name createName(String firstName, String middleName, String lastName)
    {
        return new Name(firstName, middleName, lastName);
    }

    /**
     * <p>Creates and returns a Person object.</p>
     *
     * @param name  Name object
     * @param phone phone number
     * @param email email address
     * @return the Person object to be returned
     */
    public Person createPerson(Name name, String phone, String email)
    {
        return new Person(name, phone, email);
    }

    /**
     * <p>Creates and returns a Manager object.</p>
     *
     * @param person      Person object
     * @param dateOfBirth date of birth
     * @param starRating  star rating
     * @return the Manager object to be returned
     */
    public Manager createManager(Person person, LocalDate dateOfBirth, int starRating)
    {
        return new Manager(person, dateOfBirth, starRating);
    }

    /**
     * <p>Creates and returns a Player object.</p>
     *
     * @param person   Person object
     * @param numGoals number of goals
     * @param isGoalie is goalie, true is a goalie, false is not a goalie
     * @return the Player object to be returned
     */
    public Player createPlayer(Person person, int numGoals, boolean isGoalie)
    {
        return new Player(person, numGoals, isGoalie);
    }

    /**
     * <p>Creates and returns a Team object.</p>
     *
     * @param name         team name
     * @param jerseyColour team jersey colour
     * @return the Team object ot be returned
     */
    public Team createTeam(String name, String jerseyColour)
    {
        Team temp = new Team();
        temp.setName(name);
        temp.setJerseyColour(jerseyColour);
        return temp;
    }

    /**
     * <p>Persists a Manager instance to the database.</p>
     *
     * @param manager the Manager object to be persisted
     */
    public void persistManager(Manager manager)
    {
        managerDAO.persist(manager);
    }

    /**
     * <p>Persists a Player instance to the database.</p>
     *
     * @param player the Player object to be persisted
     */
    public void persistPlayer(Player player)
    {
        playerDAO.persist(player);
    }

    /**
     * <p>Persists a Team instance to the database.</p>
     *
     * @param team the Team object to be persists
     */
    public void persistTeam(Team team)
    {
        teamDAO.persist(team);
    }

    /**
     * <p>Retrieves list of all Managers in the database.</p>
     *
     * @return a list comprising of all Manager instances in our database
     */
    public List<Manager> getManagers()
    {
        return managerDAO.getManagers();
    }

    /**
     * <p>Retrieves list of all Players in the database.</p>
     *
     * @return a list comprising of all Player instances in our database
     */
    public List<Player> getPlayers()
    {
        return playerDAO.getPlayers();
    }

    /**
     * <p>Retrieves list of all Teams in the database.</p>
     *
     * @return a list comprising of all Team instances in our database
     */
    public List<Team> getTeams()
    {
        return teamDAO.getTeams();
    }

    /**
     * <p>Retrieves a list of all Player instances in the database that match the search phrase entered through the
     * parameters.</p>
     *
     * @param searchPhrase the search phrase to be used as a wild card search via JPQL
     * @return the list of players that match the search phrase
     */
    public List<Player> searchName(String searchPhrase)
    {
        return playerDAO.searchName(searchPhrase);
    }

    /**
     * <p>Retrieves a list of all Player instances within a specified team.</p>
     *
     * @param team the team specified
     * @return the list of players within the team specified
     */
    public List<Player> getTeamPlayers(Team team)
    {
        return team.getPlayers();
    }

    /**
     * <p>Retrieves the Manager instance related to the team specified.</p>
     *
     * @param team the team specified
     * @return the manager instance related to the team
     */
    public Manager getTeamManager(Team team)
    {
        return team.getManager();
    }

    /**
     * <p>Retrieves Team that is attached to a specified Manager instance.</p>
     *
     * @param manager the specified Manager instance
     * @return the Team to be returned
     */
    public Team getManagerTeam(Manager manager)
    {
        return manager.getTeam();
    }

    /**
     * <p>Retrieves Team that is attached to a specified Player instance.</p>
     *
     * @param player the specified Player instance
     * @return the Team to be returned
     */
    public Team getPlayerTeam(Player player)
    {
        return player.getTeam();
    }

    /**
     * <p>Builds and returns a String comprising of extra details of a specified Player instance.</p>
     *
     * @param player the specified Player instance
     * @return the String comprising of extra details to be returned
     */
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

    /**
     * <p>Finds a Team of the specified ID in our database.</p>
     *
     * @param id the specified ID
     * @return the Team which matches such an ID
     */
    public Team findTeam(long id)
    {
        return teamDAO.find(id);
    }

    /**
     * <p>Finds a Manager of the specified ID in our database.</p>
     *
     * @param id the specified ID
     * @return the Manager which matches such an ID
     */
    public Manager findManager(long id)
    {
        return managerDAO.find(id);
    }

    /**
     * <p>Finds a Player of the specified ID in our database.</p>
     *
     * @param id the specified ID
     * @return the Player which matches such an ID
     */
    public Player findPlayer(long id)
    {
        return playerDAO.find(id);
    }

    /**
     * <p>Updates a Manager instance in our database.</p>
     *
     * @param manager the Manager instance to be updated
     */
    public void updateManager(Manager manager)
    {
        managerDAO.update(manager);
    }

    /**
     * <p>Updates a Player instance in our database.</p>
     *
     * @param player the Player instance to be updated
     */
    public void updatePlayer(Player player)
    {
        playerDAO.update(player);
    }

    /**
     * <p>Updates a Team instance in our database.</p>
     *
     * @param team the Team instance to be updated
     */
    public void updateTeam(Team team)
    {
        teamDAO.update(team);
    }

    /**
     * <p>Sets a specified Teams manager to a Manager specified.</p>
     *
     * @param team the specified Team to have it's manager set
     * @param manager the specified Manager that will be attached to the respective Team specified
     */
    public void setManagerOfTeam(Team team, Manager manager)
    {
        if (team == null)
        {
            removeManagerFromTeam(manager.getTeam(), manager);
        }
        else
        {
            Team prevTeam = manager.getTeam();
            if (prevTeam != null)
            {
                prevTeam.setManager(null);
                updateTeam(prevTeam);
            }
            manager.setTeam(team);
            team.setManager(manager);
            updateTeam(team);
        }
    }

    /**
     * <p>Sets a Player instances Team to a specified Team.</p>
     *
     * @param team the team instance specified
     * @param player the player instance whose team will be changed
     */
    public void setPlayersTeam(Team team, Player player)
    {
        if (team == null)
        {
            removePlayerFromTeam(player.getTeam(), player);
        }
        else
        {
            player.setTeam(team);
            team.addPlayer(player);
            updateTeam(team);
        }
    }

    /**
     * <p>Removes a specified Manager instance from our database.</p>
     *
     * @param manager the specified Manager instance to be removed
     */
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

    /**
     * <p>Removes a specified Player instance from our database.</p>
     *
     * @param player the specified Player instance to be removed
     */
    public void delete(Player player)
    {
        playerDAO.remove(player);
    }

    /**
     * <p>Removes a specified Team instance from our database.</p>
     *
     * @param team the specified Team instance to be removed
     */
    public void delete(Team team)
    {
        teamDAO.remove(team);
    }

    /**
     * <p>Removes Manager from Team specified.</p>
     *
     * @param team the Team specified
     * @param manager the Manager to be removed from the Team specified
     */
    public void removeManagerFromTeam(Team team, Manager manager)
    {
        team.removeManager(manager);
        teamDAO.update(team);
        managerDAO.update(manager);
    }

    /**
     * <p>Removes Player from Team specified.</p>
     *
     * @param team the Team specified
     * @param player the Player to be removed from the Team specified
     */
    public void removePlayerFromTeam(Team team, Player player)
    {
        team.removePlayer(player);
        teamDAO.update(team);
        playerDAO.update(player);
    }

}
