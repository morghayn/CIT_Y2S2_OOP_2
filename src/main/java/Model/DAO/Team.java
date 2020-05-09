package Model.DAO;

import Model.POJO.Player;

import javax.persistence.*;
import java.util.List;

public class Team
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("league");

    public static void addPlayer(Model.POJO.Player player)
    {
        // TODO
    }

    public static void addManager(Model.POJO.Manager manager)
    {
        // TODO
    }

    public static void removePlayer(Model.POJO.Player player)
    {
        // TODO
    }

    public static void removeManager(Model.POJO.Manager manager)
    {
        // TODO
    }

}
