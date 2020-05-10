package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NamedQuery(name="Manager.Retrieve", query="SELECT m FROM Manager m")
@PrimaryKeyJoinColumn(name = "personID")
public class Manager extends Person
{
    private String dateOfBirth;
    private int starRating;

    @OneToOne(mappedBy = "manager")//, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Team team;

    public Manager()
    {

    }

    public Manager(Person person, String dateOfBirth, int starRating)
    {
        super(
                person.getName(),
                person.getPhone(),
                person.getEmail()
        );

        setDateOfBirth(dateOfBirth);
        setStarRating(starRating);
    }

    public void setTeam(Team team)
    {
        if (this.team != null)
        {
            this.team = team;
            team.setManager(this);
            this.team.setManager(null);
        }
    }
}
