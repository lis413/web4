package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class CarDao {

    private Session session;



    public CarDao(Session session) {
        this.session = session;
    }

    public long insertCar(Car car){

        return (long) session.save(car);
    }

    public Car getCar(String brand, String model, String licensePlate){
        //Criteria criteria = session.createCriteria(Car.class);

        String hql = "from Car where brand = '" + brand + "'  and model = '" + model + "' and licensePlate = '" + licensePlate + "'";
       // String hql = "from Car ";
        Query query = session.createQuery(hql);
        List<Car> list= query.list();
        Car car = new Car();
        for (Car c: list) {
            car = c;
        }
        return car;
    }

    public List<Car> getAllCar(){
        String hql = "from Car";
        Query query = session.createQuery(hql);
        List<Car> list = query.list();
        return list;
    }

    public int deleteCar(String brand, String model, String licensePlate){
        String hql = "delete from Car where brand = '" + brand + "'  and model = '" + model + "' and licensePlate = '" + licensePlate + "'";
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }

    public int deleteAllCar()
    {
        Query query = session.createQuery("DELETE FROM Car");
         return query.executeUpdate();
    }

}
