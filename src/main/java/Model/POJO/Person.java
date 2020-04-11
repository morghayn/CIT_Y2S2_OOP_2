package Model.POJO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public class Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "nameID")
    protected Name name;

    @Column(name = "phone")
    protected String phone;

    @Column(name = "email")
    protected String email;

    public Person() {}

    public Person(Name name, String phone, String email)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getPersonID()
    {
        return personID;
    }

    public void setPersonID(int personID)
    {
        this.personID = personID;
    }

    public Name getName()
    {
        return name;
    }

    public void setName(Name name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
