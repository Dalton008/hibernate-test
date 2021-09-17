import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchaseList implements Serializable
{
    @EmbeddedId
    private Key linkedPurchaseKey;

    @Embeddable
    @Data
    public static class Key implements Serializable
    {
        @Column(name = "student_id")
        private int studentId;

        @Column(name = "course_id")
        private int courseId;
    }
}
