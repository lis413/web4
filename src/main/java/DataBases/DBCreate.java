package DataBases;

import model.Car;
import model.DailyReport;
import org.hibernate.cfg.Configuration;

public class DBCreate {
    public static Configuration getMySqlConfiguration() {
        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(DailyReport.class);
        configuration.addAnnotatedClass(Car.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        return configuration;
    }
}
