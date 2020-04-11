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

    /**
     * I only included this bi-directional relationship here because I felt the brief was specified for this to be included:
     * "The manager knows which Team he/she manages." ~ The project specification
     */
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
