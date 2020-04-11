package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "team")
public class Team implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamID;
    private String name;
    private String jerseyColour;

    @JoinColumn(name = "teamID")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;

    @OneToOne(fetch = FetchType.LAZY)
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
