package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "personID")
public class Manager extends Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String dateOfBirth;
    private int starRating;

    // TODO manager knows the team they manage

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
