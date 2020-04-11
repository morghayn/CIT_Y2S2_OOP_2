package Model.POJO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name = "personID")
public @Data
class Player extends Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(name = "goalie")
    private boolean goalie;

    @Column(name = "numGoals")
    private int numGoals;

    @ManyToOne
    @JoinColumn(name = "teamID")
    //@JoinTable(name = "teamID", joinColumns = {@JoinColumn(name = "personID")}, inverseJoinColumns = {@JoinColumn(name = "teamID")})
    private Team teamID;

    public Player()
    {
    }

    /*
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
    */

}
