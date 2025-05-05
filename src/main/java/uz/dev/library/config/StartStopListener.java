package uz.dev.library.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

/**
 * Created by: asrorbek
 * DateTime: 4/28/25 13:40
 **/

@WebListener
public class StartStopListener implements ServletContextListener {

    public static EntityManagerFactory sessionFactory;
    public static ValidatorFactory validatorFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {

            sessionFactory = Persistence.createEntityManagerFactory("mr");

            validatorFactory = Validation.buildDefaultValidatorFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
