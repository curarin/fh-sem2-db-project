package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity
@Table(name = "zip")
public class Zip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "zip_code", nullable = false, length = 255)
    private String zipCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setZip(String zip) {
        this.zipCode = zip;
    }
}