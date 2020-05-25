import model.Car;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.CarService;
import service.DBException;
import service.DailyReportService;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import servlet.NewDayServlet;
import servlet.ProducerServlet;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        ProducerServlet producerServlet = new ProducerServlet();
        CustomerServlet customerServlet = new CustomerServlet();
        NewDayServlet newDayServlet = new NewDayServlet();
        DailyReportServlet dailyReportServlet = new DailyReportServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(producerServlet), "/producer");
        context.addServlet(new ServletHolder(customerServlet), "/customer");
        context.addServlet(new ServletHolder(newDayServlet), "/newday");
      //  context.addServlet(new ServletHolder(dailyReportServlet), "/report/last");
      //  context.addServlet(new ServletHolder(dailyReportServlet), "/report/all");
        context.addServlet(new ServletHolder(dailyReportServlet), "/report/*");





        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();

//        CarService carService = CarService.getInstance();
//

//
//       // System.out.println(carService.getCar("Nissan111", "almera1", "5552").getPrice());
//
//
//        try {
//            carService.addCar(nissan);
//            carService.addCar(nissan1);
//            carService.addCar(nissan2);
//            System.out.println(carService.getCar("Nissan111", "almera1", "5552").getPrice());
//
//            List<Car> car = carService.getAllCar();
//            for (Car c: car) {
//                System.out.println(c);
//            }
//
//            System.out.println("удаление - " + carService.deleteCar("Nissan111", "almera1", "5552"));
//
//            List<Car> car1 = carService.getAllCar();
//            for (Car c: car1) {
//                System.out.println(c);
//            }
//        } catch (DBException e) {
//            e.printStackTrace();
//        }

    }
}
