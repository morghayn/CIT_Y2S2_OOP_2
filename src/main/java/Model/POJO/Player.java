package Model.POJO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name="personID")
public class Player extends Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(name = "goalie")
    private boolean goalie;

    @Column(name = "numGoals")
    private int numGoals;

    @ManyToOne
    @JoinColumn(name = "teamID")
    private Team teamID;

    public Player() { }

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

    public void addGoals(int numGoals)
    {
        this.numGoals += numGoals;
    }

	public boolean isGoalie()
	{
		return goalie;
	}

	public void setGoalie(boolean goalie)
	{
		this.goalie = goalie;
	}

	public int getNumGoals()
	{
		return numGoals;
	}

	public void setNumGoals(int numGoals)
	{
		this.numGoals = numGoals;
	}

    public Team getTeamID()
    {
        return teamID;
    }

    public void setTeamID(Team teamID)
    {
        this.teamID = teamID;
    }

}
