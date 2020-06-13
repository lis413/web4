package service;

import DAO.DailyReportDao;
import DataBases.DBCreate;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;
    private final SessionFactory sessionFactory;
    DailyReportDao dailyReportDao = new DailyReportDao();

    public DailyReportService(SessionFactory sessionFactory) {
        Configuration configuration = DBCreate.getMySqlConfiguration();
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
        DailyReport d = new DailyReportDao(sessionFactory.openSession()).getLastReport();;
        return d;
    }

    public void addReport(Long soldCars, Long earnings){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            DailyReport dailyReport = new DailyReport(earnings, soldCars);
            DailyReportDao dao = new DailyReportDao(session);
            dao.addReport(dailyReport);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public Long lastId(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            DailyReportDao dao = new DailyReportDao(session);
            Long id = dao.lastId();
            return id;
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    public void changeReport(Long sold, Long earlyEarnings, Long earnings, Long id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            DailyReportDao dao = new DailyReportDao(session);
            dao.changeReport(sold, earlyEarnings, earnings, id);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void deleteAll(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            DailyReportDao dao = new DailyReportDao(session);
            dao.deleteAll();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }
        CarService carService = CarService.getInstance();
        carService.deleteAllCar();
    }


}
