package Model.DAO;

import Model.POJO.Team;

import javax.persistence.*;
import java.util.List;

public class TeamDAO
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public TeamDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

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

    public void remove(Team team)
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

    public void update(long id)
    {
        update(getTeam(id));
    }

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
