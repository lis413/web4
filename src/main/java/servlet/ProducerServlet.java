package servlet;

import model.Car;
import service.CarService;
import service.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService carService = CarService.getInstance();
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Long price = Long.valueOf(req.getParameter("price"));
        try {
           Long sh =  carService.addCar(new Car(brand, model, licensePlate, price));
           if (sh > 0) {
               resp.setStatus(HttpServletResponse.SC_OK);
           } else {
               resp.setStatus(403);
           }

        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
