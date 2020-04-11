package Model.DAO;

import javax.persistence.*;
import java.util.List;

public class Player
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");

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

    public static void getPlayers()
    {
        System.out.println("-- all Players--");

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<Model.POJO.Player> query = em.createQuery("FROM Player p", Model.POJO.Player.class);
        List<Model.POJO.Player> resultList = query.getResultList();

        //resultList.forEach(System.out::println);
        for (Model.POJO.Player p : resultList)
        {
            Model.POJO.Name n = p.getName();
            System.out.println(p.getPersonID() + "\t" + n.getFirstName() + "\t" + n.getMiddleName() + "\t" + n.getLastName());
        }
    }

    // TODO update, change, add?
    public static void alterNumGoals()
    {

    }

}
