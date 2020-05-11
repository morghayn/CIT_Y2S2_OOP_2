package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NamedQuery(name="Team.Retrieve", query="SELECT t FROM Team t")
@Embeddable
public class Team
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamID;
    private String name;
    private String jerseyColour;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "teamID")
    private List<Player> players;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH) // TODO is this enabling us to delete part of our bidirectional relationship -- INVESTIGATE!
    @JoinColumn(name = "managerID")
    private Manager manager;

    public Team()
    {
        players = new ArrayList<Player>();
    }

    public Team(String name, String jerseyColour)
    {
        this.name = name;
        this.jerseyColour = jerseyColour;
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }

    public void removePlayer(Player player)
    {
        players.remove(player);
        player.setTeam(null);
    }

    public void removeManager(Manager manager)
    {
        this.manager = null;
        manager.setTeam(null);
    }
}
