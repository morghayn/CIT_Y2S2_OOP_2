package Model.DAO;

import Model.POJO.Player;

import javax.persistence.*;
import java.util.List;

public class Manager
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");
    // TODO we already can get / set rating straight from the POJO?

    public static void addManager(Model.POJO.Manager manager)
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

    public static List<Model.POJO.Manager> getManagers()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        TypedQuery<Model.POJO.Manager> query = em.createQuery("FROM Manager p", Model.POJO.Manager.class);
        List<Model.POJO.Manager> resultList = query.getResultList();

        for (Model.POJO.Manager manager : resultList)
        {
            System.out.println("PersonID:\t" + manager.getPersonID());
            Model.POJO.Team team = manager.getTeam();
            System.out.println("\tTeam Name:\t" + (team == null ? "Team is NULL" : manager.getTeam().getName()));
        }
        em.close();
        return resultList;
    }

}
