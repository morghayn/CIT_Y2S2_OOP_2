package Model.DAO;

import javax.persistence.*;
import java.util.List;

public class Player
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");
    // TODO we can already update numGoals straight from the POJO...

    public static void addPlayer(Model.POJO.Player player)
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

    public static List<Player> getPlayers()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        TypedQuery<Player> query = em.createQuery("FROM Player p", Player.class);
        List<Player> resultList = query.getResultList();

        em.close();
        return resultList;
    }

}
