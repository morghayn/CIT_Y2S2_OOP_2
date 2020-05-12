package Model.POJO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long personID;
    protected String phone;
    protected String email;

    @Embedded
    protected Name name;

    public Person(Name name, String phone, String email)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName()
    {
        return name.getFirstName();
    }

    public String getMiddleName()
    {
        return name.getMiddleName();
    }

    public String getLastName()
    {
        return name.getLastName();
    }
}
