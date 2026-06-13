package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity
@Table(name = "town")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "town_name", nullable = false, length = Integer.MAX_VALUE)
    private String townName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void setName(String townString) {
        this.townName = townString;
    }
}