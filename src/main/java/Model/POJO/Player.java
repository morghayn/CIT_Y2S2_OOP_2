package Model.POJO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NamedQuery(name="Player.Retrieve", query="SELECT p FROM Player p")
@PrimaryKeyJoinColumn(name = "personID")
//@OnDelete(action = OnDeleteAction.CASCADE)
public class Player extends Person
{
    private boolean goalie;
    private int numGoals;

    @ManyToOne
    @JoinColumn(name = "teamID")
    private Team team;

    public Player()
    {

    }

    public Player(Person person, boolean goalie)
    {
        super(
                person.getName(),
                person.getPhone(),
                person.getEmail()
        );

        numGoals = 0;
        this.goalie = goalie;
    }
}
