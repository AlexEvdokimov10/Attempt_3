package dao;
import com.sun.istack.NotNull;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.Autopark;
import ru.Cars;

public class CarsDAO implements DAO<Cars, Integer> {
    private final SessionFactory factory;

    public CarsDAO(@NotNull final SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Create new car in engines table.
     *
     * @param car for add.
     */
    @Override
    public void create(@NotNull final Cars car) {
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            session.save(car);

            session.getTransaction().commit();
        }
    }

    @Override
    public Cars read(@NotNull final Integer id) {

        try (final Session session = factory.openSession()) {

            final Cars result = session.get(Cars.class, id);

            if (result != null) {
                Hibernate.initialize(result.getCodeAutopark());
                Hibernate.initialize(result.getCodeOwner());
            }

            return result;
        }
    }

    /**
     * Get engine by model.
     *
     * @param model for select.
     * @return engine with param model.
     */


    /**
     * Update autopark state.
     *
     * @param car new state.
     */
    @Override
    public void update(@NotNull final Cars car) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(car);

            session.getTransaction().commit();
        }
    }

    /**
     * Delete car.
     *
     * @param car for delete.
     */
    @Override
    public void delete(@NotNull final Cars car) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(car);

            session.getTransaction().commit();
        }
    }
}
