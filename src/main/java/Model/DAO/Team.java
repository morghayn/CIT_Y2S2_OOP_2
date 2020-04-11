package Model.DAO;

import Model.POJO.Player;

import javax.persistence.*;
import java.util.List;

public class Team
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");

    // TODO Add, remove players and managers
    public static void addPlayer(Team team, Player player)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<Player> query = em.createQuery("FROM Player p", Model.POJO.Player.class);
        List<Player> resultList = query.getResultList();

        //resultList.forEach(System.out::println);
        for (Model.POJO.Player p : resultList)
        {
            Model.POJO.Name n = p.getName();
            System.out.println("\t" + n.getFirstName() + "\t" + n.getMiddleName() + "\t" + n.getLastName());
        }
    }

/*
    public static void updateTeam(Model.POJO.Team team)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            et = em.getTransaction();
            et.begin();
            Model.POJO.Team teamOld = em.find(Model.POJO.Team.class, team.getTeamID());
            teamOld.setName(team.getName());

            em.persist(teamOld);
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

}
