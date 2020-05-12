package Model.POJO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedQuery(name="Player.Retrieve", query="SELECT p FROM Player p")
@PrimaryKeyJoinColumn(name = "personID")
public class Player extends Person
{
    private boolean goalie;
    private int numGoals;

    @ManyToOne
    @JoinColumn(name = "teamID")
    private Team team;

    public Player(Person person, int numGoals, boolean goalie)
    {
        super(
                person.getName(),
                person.getPhone(),
                person.getEmail()
        );

        this.numGoals = numGoals;
        this.goalie = goalie;
    }
}
