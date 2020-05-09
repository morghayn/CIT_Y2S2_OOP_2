package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "personID")
public class Manager extends Person
{
    private String dateOfBirth;
    private int starRating;

    @OneToOne(mappedBy = "manager")
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
}
