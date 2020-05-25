package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        for (DailyReport d: dailyReports) {
            System.out.println(d);
        }
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport(){
        Transaction transaction = session.beginTransaction();
        //Query query = session.createQuery("from DailyReport ");
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        //query.setMaxResults(1);
        //DailyReport last = (DailyReport) query.uniqueResult();
        DailyReport last = dailyReports.get(0);
        for (DailyReport d: dailyReports) {
            if (last.getId() < d.getId()){
                last = d;
            }
        }
        return last;
    }

    public Long addReport(DailyReport dailyReport){

        return (long) session.save(dailyReport);
    }

    public Long deleteAll (){
        Query query = session.createQuery("DELETE FROM DailyReport");
        return Long.valueOf(query.executeUpdate());

    }






}
