package Model.POJO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public @Data
class Person implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personID;

    @Column(name = "phone")
    protected String phone;

    @Column(name = "email")
    protected String email;

    //@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(name = "nameID")
    @Embedded
    protected Name name;

    public Person() {}

    /*
    public Person(Name name, String phone, String email)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    */

}
