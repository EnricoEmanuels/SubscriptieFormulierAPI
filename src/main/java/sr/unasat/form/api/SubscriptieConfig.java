package sr.unasat.form.api;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;


import sr.unasat.form.api.config.JPAConfig;
import sr.unasat.form.api.controllers.SubscriptieController;
import sr.unasat.form.api.mappers.SubscriptieMapper;
import sr.unasat.form.api.repositories.SubscriptieRepository;
import sr.unasat.form.api.services.SubscriptieService;

/**
 * Hello world!
 *
 */
public class SubscriptieConfig {
    public static void main( String[] args ) throws Exception {

        // Disable JAXB optimization
        System.setProperty("org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl.fastBoot", "true");
        // Create a Jetty server
        Server server = new Server(8080);

        // Create a ServletContextHandler  ( dit is voor je default patch)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Configure Jersey
        ResourceConfig config = new ResourceConfig();
        config.register(JacksonFeature.class); // Register Jackson for JSON support
        config.packages("sr.unasat.form.api.controllers"); // Replace with your package name (hij zal alleen kijken in de packages die jij hebt aangegeven waarin die resources zijn als zijn er andere resources/controllers die je niet hebt aangegeven hij gaat niet erin kijken )

        // Add the Jersey ServletContainer to the context
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
        context.addServlet(jerseyServlet, "/api/*"); // Map to /api/*
        context.addServlet(jerseyServlet, "/*");

        // Register CORSFilter
        FilterHolder corsFilter = new FilterHolder(new CORSFilter());
        context.addFilter(corsFilter, "/*", null);

        /* toen kreeg ik deze error : Exception in thread "main" java.lang.NullPointerException: Cannot invoke
         "jakarta.persistence.EntityManagerFactory.createEntityManager()" because "sr.unasat.form.api.config.JPAConfig.emf"
         is null
         */
        // dus ik ga die entitymanager factory ook aanmaken

        EntityManagerFactory entityManagerFactory = JPAConfig.getEntityMangerFactory();

        // ik heb mijn entity manager aangemaakt en constructor injectie gedaan
        EntityManager entityManager = JPAConfig.getEntityManger();
        SubscriptieRepository subscriptieRepository = new SubscriptieRepository(entityManager); // dependecy injectie

        SubscriptieService subscriptieService = new SubscriptieService(subscriptieRepository); // dependency injection



        /*
        // Manueel instantie maken en service injecteren het gaat wel met getter injectie

        SubscriptieController controller = new SubscriptieController();
        controller.setSubscriptieService(subscriptieService);
        config.register(controller);  //Registreer de manueel aangemaakte instantie
        */

        SubscriptieMapper subscriptieMapper = new SubscriptieMapper(); // misschien moet ik iets zetten in die contructor || heb nog niet gemaakt

        SubscriptieController subscriptieController = new SubscriptieController(subscriptieService, subscriptieMapper); // dependency injection
        config.register(subscriptieController); // zonder deze regel werkt die contructor injectie niet


//        JPAConfig.getEntityMangerFactory();
//        JPAConfig.getEntityManger();

        // Set the handler and start the server
        server.setHandler(context);
        server.start();
        server.join();

    }
}
