package Model.DAO;

import Model.POJO.Manager;
import Model.POJO.Player;
import Model.POJO.Team;

import javax.persistence.*;
import java.util.List;

public class ManagerDAO
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");

    public static void persist(Manager manager)
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

    public static void update(long id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Manager manager;

        try
        {
            et = em.getTransaction();
            et.begin();

            manager = em.find(Manager.class, id);
            em.remove(manager);
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
