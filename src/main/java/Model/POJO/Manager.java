package Model.POJO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "personID")
public @Data
class Manager extends Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "starRating")
    private int starRating;

    public Manager()
    {
    }

    /*
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
    */

}
