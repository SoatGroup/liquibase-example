package fr.patouche.soat.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

/**
 * Abstract entity.
 *
 * @author : patouche - 10/10/15.
 */
@MappedSuperclass
public class AbstractEntity {

    /** The created date. */
    @Column(name = "date_created")
    private ZonedDateTime created = ZonedDateTime.now();

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(final ZonedDateTime created) {
        this.created = created;
    }
}
