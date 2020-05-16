package Model.DAO;

import Model.POJO.Manager;
import Model.POJO.Player;

import javax.persistence.*;
import java.util.List;

/**
 * Class responsible for communication with Manager POJO
 */
public class ManagerDAO
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public ManagerDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

    /**
     * <p>Persists a Manager instance to the database.</p>
     *
     * @param manager the Manager object to be persisted
     */
    public void persist(Manager manager)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.persist(manager);
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
     * <p>Removes a specified Manager instance from our database.</p>
     *
     * @param manager the specified Manager instance to be removed
     */
    public void remove(Manager manager)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.remove(em.contains(manager) ? manager : em.merge(manager));
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
     * <p>Updates a Manager instance in our database.</p>
     *
     * @param manager the Manager instance to be updated
     */
    public void update(Manager manager)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.merge(manager);
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
     * <p>Finds a Manager of the specified ID in our database.</p>
     *
     * @param id the specified ID
     * @return the Manager which matches such an ID
     */
    public Manager find(long id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Manager manager = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            manager = em.find(Manager.class, id);

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

        return manager;
    }

    /**
     * <p>Retrieves list of all Managers in the database.</p>
     *
     * @return a list comprising of all Manager instances in our database
     */
    public List<Manager> getManagers()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        List<Manager> managers = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            TypedQuery<Manager> query = em.createNamedQuery("Manager.Retrieve", Manager.class);
            managers = query.getResultList();

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

        return managers;
    }

}
