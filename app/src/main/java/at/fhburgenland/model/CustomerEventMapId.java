package at.fhburgenland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerEventMapId implements Serializable {
    private static final long serialVersionUID = 2621873027484038432L;
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEventMapId entity = (CustomerEventMapId) o;
        return Objects.equals(this.customerId, entity.customerId) &&
                Objects.equals(this.eventId, entity.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, eventId);
    }
}