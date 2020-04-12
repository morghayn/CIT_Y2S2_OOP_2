package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Team
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamID;
    private String name;
    private String jerseyColour;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamID")
    private List<Player> players;

    @OneToOne(cascade = CascadeType.DETACH) // TODO is this enabling us to delete part of our bidirectional relationship -- INVESTIGATE!
    @JoinColumn(name = "managerID")
    private Manager manager;

    public Team()
    {

    }

    public Team(String name, String jerseyColour)
    {
        this.name = name;
        this.jerseyColour = jerseyColour;
    }
}
