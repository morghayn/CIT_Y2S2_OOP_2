package Model.DAO;

import Model.POJO.Player;

import javax.persistence.*;
import java.util.List;

/**
 * Class responsible for communication with Player POJO
 */
public class PlayerDAO
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public PlayerDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

    /**
     * <p>Persists a Player instance to the database.</p>
     *
     * @param player the Player object to be persisted
     */
    public void persist(Player player)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.persist(player);
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
     * <p>Removes a specified Player instance from our database.</p>
     *
     * @param player the specified Player instance to be removed
     */
    public void remove(Player player)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.remove(em.contains(player) ? player : em.merge(player));
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
     * <p>Updates a Player instance in our database.</p>
     *
     * @param player the Player instance to be updated
     */
    public void update(Player player)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.merge(player);
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
     * <p>Finds a Player of the specified ID in our database.</p>
     *
     * @param id the specified ID
     * @return the Player which matches such an ID
     */
    public Player find(long id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Player player = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            player = em.find(Player.class, id);

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

        return player;
    }

    /**
     * <p>Retrieves list of all Players in the database.</p>
     *
     * @return a list comprising of all Player instances in our database
     */
    public List<Player> getPlayers()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        List<Player> players = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            TypedQuery<Player> query = em.createNamedQuery("Player.Retrieve", Player.class);
            players = query.getResultList();

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

        return players;
    }

    /**
     * <p>Retrieves a list of all Player instances in the database that match the search phrase entered through the
     * parameters.</p>
     *
     * @param search the search phrase to be used as a wild card search via JPQL
     * @return the list of players that match the search phrase
     */
    public List<Player> searchName(String search)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        List<Player> players = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            String queryStr = "SELECT p FROM Player p WHERE concat(firstName, ' ', middleName, ' ', lastName) like :search";
            TypedQuery<Player> query = em.createQuery(queryStr, Player.class);
            query.setParameter("search", "%" + search + "%");
            players = query.getResultList();

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

        return players;
    }

}