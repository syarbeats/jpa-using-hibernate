package com.cdc.mitrais.jpa_hibernate_one;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdc.mitrais.jpa_hibernate_one.entity.Geek;
import com.cdc.mitrais.jpa_hibernate_one.entity.Person;


public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main( String[] args )
	{
		App app = new App();
		app.run();
	}

	private void run() {

		EntityManagerFactory factory = null;
		EntityManager entitymanager = null;
		//Statistics statistic =null;

		try {
			factory = Persistence.createEntityManagerFactory("persistenceUnit");
			
			entitymanager = factory.createEntityManager();
			
			persistPerson(entitymanager);
			persistGeek(entitymanager);
			logger.info("Entity has been stored successfully.");
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			if(entitymanager != null) {
				entitymanager.close();
			}
			if(factory != null) {
				factory.close();
			}
		}
	}

	private void persistGeek(EntityManager entitymanager) {
		EntityTransaction transaction = entitymanager.getTransaction();

		try {
			transaction.begin();
			Geek geek = new Geek();
			geek.setFirstName("Linus");
			geek.setLastName("Trovalds");
			geek.setFavouriteProgrammingLanguage("C++");
			entitymanager.persist(geek);

			Geek geek2 = new Geek();
			geek2.setFirstName("Thomas");
			geek2.setLastName("Micro");
			geek2.setFavouriteProgrammingLanguage("C#");
			entitymanager.persist(geek2);

			transaction.commit();
			
		}catch(Exception e) {

			if(transaction.isActive()) {
				transaction.rollback();
			}

			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}

	private void persistPerson(EntityManager entitymanager) {

		EntityTransaction transaction = entitymanager.getTransaction();

		try {
			transaction.begin();
			Person person = new Person();
			person.setFirstName("Abraham");
			person.setLastName("Lincoln");
			entitymanager.persist(person);
			transaction.commit();
		}catch(Exception e) {

			if(transaction.isActive()) {
				transaction.rollback();
			}

			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
}
