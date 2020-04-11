package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name = "personID")
public class Player extends Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    private boolean goalie;
    private int numGoals;

    @ManyToOne
    @JoinColumn(name = "teamID")
    private Team teamID;

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
