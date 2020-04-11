package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "personID")
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
