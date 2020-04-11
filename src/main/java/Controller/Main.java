package Controller;

import Model.POJO.Name;

public class Main
{

    public static void main(String[] args)
    {
        Model.POJO.Name name = new Name("JimmdyJim", "Jim", "Bob");
        Model.DAO.Player.addPlayer(name,"083433", "morgan.nolan@hotmail.com", true);

        Model.POJO.Name name2 = new Name("Booby", "Frank", "Bob");

      //  Model.DAO.Player.getPlayers();

    }

}
