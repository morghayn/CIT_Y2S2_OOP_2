package Model.DAO;

import Model.POJO.Manager;
import Model.POJO.Player;
import Model.POJO.Team;

import javax.persistence.*;
import java.util.List;

public class ManagerDAO
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public ManagerDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

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
