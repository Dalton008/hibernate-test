import com.mysql.cj.util.DnsSrv;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //sql request
//        String url = "jdbc:mysql://localhost:3306/skillbox";
//        String user = "root";
//        String pass = "testtest";
//
//        try
//        {
//            Connection connection = DriverManager.getConnection(url, user, pass);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from Courses");
//            while (resultSet.next())
//            {
//                System.out.println(resultSet.getString("courses_name"));
//            }
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
        //-----------------

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        //simple request
//        Transaction transaction = session.beginTransaction();
//
//        Student student = session.get(Student.class, 1);
//        Course course = session.get(Course.class, 1);
//        Subscription subscription = session.get(Subscription.class, new SubscriptionPK(student.getId(), course.getId()));
//        System.out.println(subscription.getSubscriptionsDate());
//        transaction.commit();
        //-----------------

        //request
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        query.select(root).where(builder.greaterThan(root.<Integer>get("price"), 100000))
            .orderBy(builder.desc(root.get("price")));
        List<Course> courseList = session.createQuery(query).setMaxResults(5).getResultList();

        for (Course course : courseList)
        {
            System.out.println(course.getName() + " - " + course.getPrice());
        }
        //-----------------

        //create new table
        //createLinkedPurchaseList(session);

        session.close();
        sessionFactory.close();
    }

    private static void createLinkedPurchaseList(Session session)
    {
        String hql = "select s.id, c.id from Purchase p " +
                "join Student s in p.studentName = s.name " +
                "join Course c on p.courseName = c.name";
        List<Object[]> list = session.createQuery(hql).getResultList();
        for (Object[] object : list)
        {
            Transaction transaction = session.beginTransaction();
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            LinkedPurchaseList.Key key = new LinkedPurchaseList.Key();
            key.setStudentId((int)object[0]);
            key.setCourseId((int)object[1]);
            linkedPurchaseList.setLinkedPurchaseKey(key);
            System.out.println(key);
            session.saveOrUpdate(linkedPurchaseList);
            transaction.commit();
        }
        System.out.println("Table 'LinkedPurchaseList' created");
        System.out.println(list.size() + " entries added successfully");
    }
}
