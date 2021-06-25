
import dao.CarsDAO;
import dao.DAO;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.Autopark;
import ru.Cars;
import ru.Owners;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Excellent conection");
            DAO<Cars, Integer> carsDAO = new CarsDAO(sessionFactory);
            read(carsDAO);
            update(carsDAO);
            delete(carsDAO);






            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        SessionFactory factory = null;


        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);

                }

            }
        } finally {
            session.close();
        }
    }
    private static void read(DAO<Cars, Integer> carDao) {
        final Cars result = carDao.read(1);
        System.out.println("Read: " + result);
    }
    private static void update(DAO<Cars, Integer> carDao){
        final Cars car= carDao.read(9);
        car.setPlaces(4);
        car.getCodeAutopark().setCount_places(30);
        carDao.update(car);
        System.out.println("Updated: " + carDao.read(1));
    }
    private static void create(DAO<Cars,Integer>carDao){
        Cars car = new Cars();
        car.setCode(1);
        car.setNumber(102);
        car.setPlaces(100);
        Autopark autopark=new Autopark();
        autopark.setCode(1);
        autopark.setPrice(100);
        autopark.setName("K21");
        autopark.setCount_places(30);
        car.setCodeAutopark(autopark);
        Owners owners=new Owners();
        owners.setCode(5);
        owners.setName("Slavik");
        car.setCodeOwner(owners);
        carDao.create(car);

    }
    private static void delete(DAO<Cars,Integer>carDao){
        Cars car = new Cars();
        car.setCode(5);
        car.setNumber(106);
        car.setPlaces(101);
        Autopark autopark=new Autopark();
        autopark.setCode(1);
        autopark.setPrice(130);
        autopark.setName("K21");
        autopark.setCount_places(32);
        car.setCodeAutopark(autopark);
        Owners owners=new Owners();
        owners.setCode(6);
        owners.setName("Alex");
        car.setCodeOwner(owners);
        carDao.delete(car);
    }
}