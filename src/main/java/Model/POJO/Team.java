package Model.POJO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "team")
public class Team implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamID;

    @OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="teamID")
    private List<Player> players;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "managerID")
    private Manager manager;

    @Column(name = "name")
    private String name;

    @Column(name = "jerseyColour")
    private String jerseyColour;

	public Team() {}

	public Team(String name, String jerseyColour)
    {
        this.name = name;
        this.jerseyColour = jerseyColour;
    }

	public int getTeamID()
	{
		return teamID;
	}

	public void setTeamID(int teamID)
	{
		this.teamID = teamID;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public void setPlayers(List<Player> players)
	{
		this.players = players;
	}

	public Manager getManager()
	{
		return manager;
	}

	public void setManager(Manager manager)
	{
		this.manager = manager;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getJerseyColour()
	{
		return jerseyColour;
	}

	public void setJerseyColour(String jerseyColour)
	{
		this.jerseyColour = jerseyColour;
	}

}
