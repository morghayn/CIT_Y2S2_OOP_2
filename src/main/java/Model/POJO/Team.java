package Model.POJO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "team")
public @Data class Team implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamID;

    @Column(name = "name")
    private String name;

    @Column(name = "jerseyColour")
    private String jerseyColour;

    @JoinColumn(name="teamID")
    @OneToMany(cascade=CascadeType.ALL)
    private List<Player> players;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managerID")
    private Manager manager;

	public Team() {}

	/*
	public Team(String name, String jerseyColour)
    {
        this.name = name;
        this.jerseyColour = jerseyColour;
    }
	 */

}
