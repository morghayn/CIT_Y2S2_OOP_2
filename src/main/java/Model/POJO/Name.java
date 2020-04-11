package Model.POJO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public @Data
class Name implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "lastName")
    private String lastName;

	public Name() {}

    public Name(String firstName, String middleName, String lastName)
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

}
