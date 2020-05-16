package Model.DAO;

import Model.POJO.Team;

import javax.persistence.*;
import java.util.List;

/**
 * Class responsible for communication with Team POJO
 */
public class TeamDAO
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public TeamDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

    /**
     * <p>Persists a Team instance to the database.</p>
     *
     * @param team the Team object to be persists
     */
    public void persist(Team team)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.persist(team);
            et.commit();
        }
        catch (Exception ex)
        {
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }

    /**
     * <p>Removes a specified Team instance from our database.</p>
     *
     * @param team the specified Team instance to be removed
     */
    public void remove(Team team)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.remove(em.contains(team) ? team : em.merge(team));
            et.commit();
        }
        catch (Exception ex)
        {
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }

    /**
     * <p>Updates a Team instance in our database.</p>
     *
     * @param team the Team instance to be updated
     */
    public void update(Team team)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.merge(team);
            et.commit();
        }
        catch (Exception ex)
        {
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }

    // TODO this is duplication? of find()?
    /**
     * <p>Finds a Team of the specified ID in our database.</p>
     *
     * @param teamID the specified ID
     * @return the Team which matches such an ID
     */
    public Team getTeam(long teamID)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Team team = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            team = em.find(Model.POJO.Team.class, teamID);

            et.commit();
        }
        catch (Exception ex)
        {
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }

        return team;
    }

    /**
     * <p>Finds a Team of the specified ID in our database.</p>
     *
     * @param id the specified ID
     * @return the Team which matches such an ID
     */
    public Team find(long id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Team team = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            team = em.find(Team.class, id);

            et.commit();
        }
        catch (Exception ex)
        {
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }

        return team;
    }

    /**
     * <p>Retrieves list of all Teams in the database.</p>
     *
     * @return a list comprising of all Team instances in our database
     */
    public List<Team> getTeams()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        List<Team> teams = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            TypedQuery<Team> query = em.createNamedQuery("Team.Retrieve", Team.class);
            teams = query.getResultList();

            et.commit();
        }
        catch (Exception ex)
        {
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }

        return teams;
    }

}
