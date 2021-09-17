import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Teachers")
public class Teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int salary;

    private int age;
}
