package Model.DAO;

import javax.persistence.*;
import java.util.List;

public class Player
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("league");

    public static void addPlayer(Model.POJO.Name name, String phone, String email, boolean goalie)
    {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try
        {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Create and set values for new customer
            Model.POJO.Player player = new Model.POJO.Player();
            player.setName(name);
            player.setPhone(phone);
            player.setEmail(email);
            player.setGoalie(goalie);
            player.setNumGoals(0);

            // Save the customer object
            em.persist(player);
            et.commit();
        }
        catch (Exception ex)
        {
            // If there is an exception rollback changes
            if (et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            // Close EntityManager
            em.close();
            //ENTITY_MANAGER_FACTORY.close();
        }
    }

    public static void getPlayers()
    {
        System.out.println("-- all Players--");
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager(); // TODO i believe more queries than is necessary is occurring for name
        //TypedQuery<Model.POJO.Player> query = em.createQuery("SELECT p FROM Player p", Model.POJO.Player.class);
        TypedQuery<Model.POJO.Player> query = em.createQuery("FROM Player p", Model.POJO.Player.class);
        List<Model.POJO.Player> resultList = query.getResultList();
        //resultList.forEach(System.out::println);
        for (Model.POJO.Player p : resultList)
        {
            Model.POJO.Name n = p.getName();
            System.out.println(p.getPersonID() + "\t" + n.getFirstName() + "\t" + n.getMiddleName() + "\t" + n.getLastName());
        }
    }

    /*
    public static void getCustomers()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching Customer
        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> custs;
        try
        {
            // Get matching customer object and output
            custs = tq.getResultList();
            custs.forEach(cust -> System.out.println(cust.getFName() + " " + cust.getLName()));
        }
        catch (NoResultException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }
*/

}
