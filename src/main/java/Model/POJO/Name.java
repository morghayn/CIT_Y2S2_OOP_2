package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Name implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String middleName;
    private String lastName;

    public Name()
    {

    }

    public Name(String firstName, String middleName, String lastName)
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

}
