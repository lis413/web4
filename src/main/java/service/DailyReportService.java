package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        Configuration configuration = getMySqlConfiguration();
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }


    public DailyReport getLastReport() {
        DailyReport d = new DailyReportDao(sessionFactory.openSession()).getLastReport();
        System.out.println("_____________" +  d);
        return d;
    }

    public Long addReport(Long soldCars, Long earnings){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DailyReport dailyReport = new DailyReport(earnings, soldCars);
        DailyReportDao dao = new DailyReportDao(session);
        long sh = dao.addReport(dailyReport);
        transaction.commit();
        session.close();
        return  sh;
    }

    private static Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(DailyReport.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        return configuration;
    }

    public Long deleteAll(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DailyReportDao dao = new DailyReportDao(session);
        Long t = dao.deleteAll();
        transaction.commit();
        session.close();
        CarService carService = CarService.getInstance();
        carService.deleteAllCar();
        return t;
    }


}
