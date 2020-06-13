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
        String hql = "from Car where brand = :brandName  and model = :modelName and licensePlate = :licensePlateName";
        Query query = session.createQuery(hql);
        query.setParameter("brandName", brand);
        query.setParameter("modelName", model);
        query.setParameter("licensePlateName", licensePlate);
        Car car =  (Car)query.uniqueResult();
        return car;

    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Car> getAllCar(){
        String hql = "from Car";
        Query query = session.createQuery(hql);
        return (List<Car>) query.list();
    }

    public int deleteCar(String brand, String model, String licensePlate){
        String hql = "delete from Car where brand = :brandName  and model = :modelName and licensePlate = :licensePlateName";
        Query query = session.createQuery(hql);
        query.setParameter("brandName", brand);
        query.setParameter("modelName", model);
        query.setParameter("licensePlateName", licensePlate);
        return query.executeUpdate();
    }

    public int deleteAllCar()
    {
        Query query = session.createQuery("DELETE FROM Car");
         return query.executeUpdate();
    }

}


