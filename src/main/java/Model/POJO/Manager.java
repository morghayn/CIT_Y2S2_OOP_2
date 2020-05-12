package Model.POJO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedQuery(name = "Manager.Retrieve", query = "SELECT m FROM Manager m")
@PrimaryKeyJoinColumn(name = "personID")
public class Manager extends Person
{
    private LocalDate dateOfBirth;
    private int starRating;

    @OneToOne(mappedBy = "manager")//, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Team team;

    public Manager(Person person, LocalDate dateOfBirth, int starRating)
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
