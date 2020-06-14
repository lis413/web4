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

    public DailyReportDao(){}


    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport(){
        Query query =  session.createQuery("from DailyReport where id = (select max(id)-1 from DailyReport)");
        DailyReport dailyReport = (DailyReport) query.uniqueResult();
        session.close();
        return dailyReport;
    }

    public DailyReport getReport(){
        Query query =  session.createQuery("from DailyReport where id = (select max(id) from DailyReport)");
        DailyReport dailyReport = (DailyReport) query.uniqueResult();
        session.close();
        return dailyReport;
    }

    public Long addReport(DailyReport dailyReport){

        return (long) session.save(dailyReport);
    }

    public void changeReport(Long sold, Long earlyEarnings, Long earnings, Long id){
        if (id != null) {
            String hql = "update DailyReport set soldCars = :soldCard, earnings = :earnings where id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("soldCard", sold + 1);
            query.setParameter("earnings", earlyEarnings + earnings);
            query.setParameter("id", id);
            query.executeUpdate();
        } else {
            session.save(new DailyReport(earnings,sold+1));
        }

    }

    public long lastId(){
        String hql1 = "select max(id) from DailyReport";
        Query query1 = session.createQuery(hql1);
        Long id = (Long) query1.uniqueResult();
        return id;
    }

    public Long deleteAll (){
        Query query = session.createQuery("DELETE FROM DailyReport");
        Long t = (long) query.executeUpdate();
        return t;

    }

}


