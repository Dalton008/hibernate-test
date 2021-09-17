import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Embeddable
class SubscriptionPK implements Serializable
{
    @Column(name = "student_id")
   int studentId;

    @Column(name = "course_id")
   int courseId;

}

@Data
@Entity
@Table(name = "Subscriptions")
public class Subscription implements  Serializable
{
    @EmbeddedId
    private SubscriptionPK id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(insertable = false, updatable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(insertable = false, updatable = false)
    private Course course;

    @Column(name = "subscription_date")
    private Date subscriptionsDate;
}
