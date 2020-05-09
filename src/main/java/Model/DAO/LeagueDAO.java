package Model.DAO;

import Model.POJO.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class LeagueDAO
{
    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public LeagueDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

    public void addTeam(Team team)
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

    public void removeTeam(Team team)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            em.remove(team);
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

}
