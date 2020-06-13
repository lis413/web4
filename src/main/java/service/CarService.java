package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import DataBases.DBCreate;
import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;
import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.List;

public class CarService {
 //   private static Long earnings = 0L;
 //   private static Long sold = 0L; // убрать
    private static CarService carService;
    private final SessionFactory sessionFactory;

    private CarService() {
        Configuration configuration = DBCreate.getMySqlConfiguration();
                this.sessionFactory = createSessionFactory(configuration);
    }

    public static CarService getInstance() {
            if (carService == null) {
                carService = new CarService();
            }
            return carService;
    }

    public long addCar(Car car) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try  {
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
            transaction.rollback();
            throw new DBException(e);
        } finally {
            session.close();
        }
    }

    public Car getCar(String brand, String model, String licensePlate){
        Session session = sessionFactory.openSession();
        try {
            CarDao dao = new CarDao(session);
            Car car = dao.getCar(brand, model, licensePlate);
            DailyReportService dailyReportService = DailyReportService.getInstance();
            DailyReport dailyReport = dailyReportService.getLastReport();
            Long sold = 0L;
            Long earnings = 0L;
            if (dailyReport != null)
            {
                sold = dailyReport.getSoldCars();
                earnings = dailyReport.getEarnings();
            }

            dailyReportService.changeReport(sold, earnings, car.getPrice(),dailyReportService.lastId());
            return car;
        } catch (HibernateException e){

        }
        finally {
            session.close();
        }
        return null;

    }//
    public List<Car> getAllCars()
    {
        Session session = sessionFactory.openSession();
        try {
            CarDao dao = new CarDao(session);
            List<Car> carList = dao.getAllCar();
            return carList;
        } catch (HibernateException e){

        }
        finally {
            session.close();
        }
        return null;
    }

    public void deleteCar(String brand, String model, String licensePlate){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            CarDao dao = new CarDao(session);
            dao.setSession(session);
            dao.deleteCar(brand, model, licensePlate);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }


    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

//    public static Long getEarnings() {
 //       return earnings;
//    }

//    public static void setEarnings(Long earnings) {
//       CarService.earnings = earnings;
 //   }

 //   public static void setSold(Long sold) {
//        CarService.sold = sold;
//    }

 //   public static Long getSold() {
 //       return sold;
 //   }

    public void deleteAllCar(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {

            CarDao dao = new CarDao(session);
            dao.deleteAllCar();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }
    }

}
