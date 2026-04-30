package at.fhburgenland;

import jakarta.persistence.*;
import java.util.List;

public class Main {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("person");

    public static void main(String[] args) {
        System.out.println("Test");

        addPerson("josef", "mustersuppe", 3000);

        readAll();
        readPerson("muster");

        /* TO DO
            -) Connect Database
            -) Klasse zur Tabelle erstellen!
            -) Create Methods for
                -) addPerson
                -) readPerson
                -) readAllPersons --> Ausgabe ganze Tabelle
                -) update Person
                -) delete Person
         */

        EMF.close();
    }
    public static void addPerson(String vorname, String nachname, Integer gehalt) {
        Person newPerson = new Person(vorname, nachname, gehalt);
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(newPerson);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception);
            if(entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public static void readAll() {
        EntityManager entityManager = EMF.createEntityManager();

        String query = """
                select
                    person
                from
                    Person as person
                """;
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);

        List<Person> personList = null;
        try {
            personList = typedQuery.getResultList();
            System.out.println(personList);

            personList.forEach(person -> System.out.println(
                    "ID: " + person.getPnr() + " " +
                            "Vorname: " + person.getVorname() + " " + person.getNachname() + " " +
                            "Gehalt: " + person.getGehalt()
            ));
        } catch (Exception exception) {

        } finally {
            entityManager.close();
        }
    }

    public static void readPerson(String nachname) {
        EntityManager entityManager = EMF.createEntityManager();

        String query = """
                select
                    person
                from
                    Person as person
                where
                    person.nachname = :nname
                """;
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);
        typedQuery.setParameter("nname", nachname);

        List<Person> personList = null;

        try {
            personList = typedQuery.getResultList();
            System.out.println(personList);

            personList.forEach(person -> System.out.println(
                    "ID: " + person.getPnr() + " " +
                            "Vorname: " + person.getVorname() + " " + person.getNachname() + " " +
                            "Gehalt: " + person.getGehalt()
            ));
        } catch (Exception exception) {

        } finally {
            entityManager.close();
        }
    }
    public static void readPerson(int pnr) {
        EntityManager entityManager = EMF.createEntityManager();
        Person person;

        try {
            person = entityManager.find(Person.class, pnr);
            entityManager.persist(person);
        } catch (Exception exception) {

        } finally {
            entityManager.close();
        }
    }

    public static void updatePerson(int pnr, String vorname, String nachname, int gehalt) {
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction entityTransaction = null;

        Person personToBeUpdated = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            personToBeUpdated = entityManager.find(Person.class, pnr);

            personToBeUpdated.setVorname(vorname);
            personToBeUpdated.setNachname(nachname);
            personToBeUpdated.setGehalt(gehalt);

            entityManager.persist(personToBeUpdated);
            entityTransaction.commit();

        } catch (Exception exception) {

        } finally {
            entityManager.close();
        }
    }
}

