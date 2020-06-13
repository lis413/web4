package servlet;

import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReportService dailyReportService = DailyReportService.getInstance();
     //   Long soldCars = CarService.getSold();
     //   Long earnings = CarService.getEarnings();
        dailyReportService.addReport(0L, 0L);
    //    CarService.setEarnings(0L);
    //    CarService.setSold(0L);

    }
}
