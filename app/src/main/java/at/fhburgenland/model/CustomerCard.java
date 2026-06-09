package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity
@Table
public class CustomerCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_card_id", nullable = false)
    private Integer customerCardId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Integer getCustomerCardId() {
        return customerCardId;
    }

    public void setCustomerCardId(Integer customerCardId) {
        this.customerCardId = customerCardId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}