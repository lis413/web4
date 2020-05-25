package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    static Long earnings = 0L;
    static Long sold = 0L;

    private static CarService carService;

    private  SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        Configuration configuration = getMySqlConfiguration();
                this.sessionFactory = createSessionFactory(configuration);
    }

    public static CarService getInstance() {
            if (carService == null) {
                carService = new CarService(DBHelper.getSessionFactory());
            }
            return carService;
    }


    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Car.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public long addCar(Car car) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CarDao dao = new CarDao(session);
            List<Car> listCar = dao.getAllCar();
            int sch = 0;
            for (Car carSchet: listCar){
                if (carSchet.getBrand().equals(car.getBrand())) sch ++;
            }
            long id = 0L;
            if (sch < 10) {
                id = dao.insertCar(car);
                transaction.commit();
                session.close();
                System.out.println("Car add");
            }
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public Car getCar(String brand, String model, String licensePlate){
        Session session = sessionFactory.openSession();
        CarDao dao = new CarDao(session);
        Car car = dao.getCar(brand, model, licensePlate);
        earnings = earnings + car.getPrice();
        sold++;
        session.close();
        return car;

    }
    public List<Car> getAllCars()
    {
        Session session = sessionFactory.openSession();
        CarDao dao = new CarDao(session);
        return dao.getAllCar();
    }

    public int deleteCar(String brand, String model, String licensePlate){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CarDao dao = new CarDao(session);
        int t = dao.deleteCar(brand, model, licensePlate);
        transaction.commit();
        session.close();
        return t;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static Long getEarnings() {
        return earnings;
    }

    public static void setEarnings(Long earnings) {
        CarService.earnings = earnings;
    }

    public static void setSold(Long sold) {
        CarService.sold = sold;
    }

    public static Long getSold() {
        return sold;
    }

    public int deleteAllCar(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CarDao dao = new CarDao(session);
        int t = dao.deleteAllCar();
        transaction.commit();
        session.close();
        return t;
    }

}
