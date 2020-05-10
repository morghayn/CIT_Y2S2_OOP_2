package Model.DAO;

import Model.POJO.Person;
import Model.POJO.Player;
import Model.POJO.Team;

import javax.persistence.*;
import java.util.List;

public class PlayerDAO
{

    private final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public PlayerDAO(EntityManagerFactory ENTITY_MANAGER_FACTORY)
    {
        this.ENTITY_MANAGER_FACTORY = ENTITY_MANAGER_FACTORY;
    }

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

/*
    public Person getPerson(long id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Person person = null;

        try
        {
            et = em.getTransaction();
            et.begin();

            person = em.find(Person.class, id);

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

        return person;
    }

    public void remove(Person person)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();
            em.remove(em.contains(person) ? person : em.merge(person));

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
*/