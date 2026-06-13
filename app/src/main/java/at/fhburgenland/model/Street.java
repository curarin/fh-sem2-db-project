package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity
@Table(name = "street")
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "street", nullable = false, length = 255)
    private String street;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setName(String streetString) {
        this.street = streetString;
    }
}