package Model.POJO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    public Person()
    {

    }

    public Person(Name name, String phone, String email)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
