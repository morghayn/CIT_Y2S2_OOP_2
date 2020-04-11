package Model.POJO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name="personID")
public class Manager extends Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "starRating")
    private int starRating;

	public Manager() {}

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

	public String getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public int getStarRating()
	{
		return starRating;
	}

	public void setStarRating(int starRating)
	{
		this.starRating = starRating;
	}

}
