package assistant.Utils;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;

public class FillDatabase {
    public static  void fillDatabase(){
        Country country1 = new Country();
        country1.setName("Anglia");
        Country country2 = new Country();
        country2.setName("Polska");

        City city1 = new City();
        city1.setName("Londyn");
        city1.setCountry(country1);
        City city2 = new City();
        city2.setName("Warszawa");
        city2.setCountry(country2);

        Library library1 = new Library();
        library1.setName("Biblioteka Uniwersytecka w Warszawie");
        library1.setStreet("Dobra 56/66");
        library1.setZipCode("00-312");
        library1.setCity(city2);

        Library library2 = new Library();
        library2.setName("Biblioteka Publiczna im. Zygmunta Łazarskiego");
        library2.setStreet("Ludowa 4");
        library2.setZipCode("00-780");
        library2.setCity(city2);

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(DigestUtils.sha1Hex("user1"));
        user1.setEmail("agrosicka@bibl.com");
        user1.setFirstName("Agnieszka");
        user1.setLastName("Grosicka");
        user1.setMobile("986-312-112");
        user1.setStreet("Główna 11");
        user1.setZipCode("00-780");
        user1.setUserType("EMPLOYEE");
        user1.setCity(city2);
        user1.setLibrary(library1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(DigestUtils.sha1Hex("user2"));
        user2.setEmail("dnowak@bibl.com");
        user2.setFirstName("Dawid");
        user2.setLastName("Nowak");
        user2.setMobile("026-333-912");
        user2.setStreet("Ludowa 51");
        user2.setZipCode("00-780");
        user2.setUserType("EMPLOYEE");
        user2.setCity(city2);
        user2.setLibrary(library2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword(DigestUtils.sha1Hex("user3"));
        user3.setEmail("kZuber@bibl.com");
        user3.setFirstName("Konrad");
        user3.setLastName("Żuber");
        user3.setMobile("998-398-902");
        user3.setStreet("Wysocka 51");
        user3.setZipCode("00-780");
        user3.setCity(city2);
        user3.setLibrary(library1);

        User user4 = new User();
        user4.setUsername("user4");
        user4.setPassword(DigestUtils.sha1Hex("user4"));
        user4.setEmail("izabelaK@home.pl");
        user4.setFirstName("Iza");
        user4.setLastName("Klusek");
        user4.setMobile("486-332-832");
        user4.setStreet("Ludowa 11");
        user4.setZipCode("00-780");
        user4.setCity(city2);
        user4.setLibrary(library1);

        Author author = new Author();
        author.setFistName("Joanne");
        author.setMiddleName("Kathleen");
        author.setLastName("Rowling");
        author.setCountry(country1);

        PublishingCompany publishingCompany = new PublishingCompany();
        publishingCompany.setName("Bloomsbury Publishing");

        Category category1 = new Category();
        category1.setName("Powieść detektywistyczna");
        Category category2 = new Category();
        category2.setName("Fantastyka");
        Category category3 = new Category();
        category3.setName("Kryminał");

        Book book1 = new Book();
        book1.setIsbn10("83-7278-167-2");
        book1.setIsbn13("978-83-7278-167-3");
        book1.setTitle("Harry Potter i Książę Półkrwi");
        book1.setAddedDate(new Timestamp(System.currentTimeMillis()));
        book1.setPublicationDate("2005");
        book1.setAuthor(author);
        book1.setCategory(category2);
        book1.setPublishingCompany(publishingCompany);
        book1.setLibrary(library1);

        Book book2 = new Book();
        book2.setIsbn10("83-7128-167-2");
        book2.setIsbn13("978-83-7128-167-3");
        book2.setTitle("Harry Potter i Książę Półkrwi");
        book2.setAddedDate(new Timestamp(System.currentTimeMillis()));
        book2.setPublicationDate("2005");
        book2.setAuthor(author);
        book2.setCategory(category2);
        book2.setPublishingCompany(publishingCompany);
        book2.setLibrary(library2);

        Book book3 = new Book();
        book3.setIsbn10("32-7128-127-2");
        book3.setIsbn13("978-32-7128-127-3");
        book3.setTitle("Wołanie kukułki");
        book3.setAddedDate(new Timestamp(System.currentTimeMillis()));
        book3.setPublicationDate("2013");
        book3.setAuthor(author);
        book3.setCategory(category1);
        book3.setPublishingCompany(publishingCompany);
        book3.setLibrary(library1);

        Book book4 = new Book();
        book4.setIsbn10("13-4432-167-2");
        book4.setIsbn13("978-13-4432-167-3");
        book4.setTitle("Troubled Blood");
        book4.setAddedDate(new Timestamp(System.currentTimeMillis()));
        book4.setPublicationDate("2020");
        book4.setAuthor(author);
        book4.setCategory(category3);
        book4.setPublishingCompany(publishingCompany);
        book4.setLibrary(library1);

        CommonDao commonDao = new CommonDao();
        try{
            commonDao.createOrUpdate(country1);
            commonDao.createOrUpdate(country2);
            commonDao.createOrUpdate(city1);
            commonDao.createOrUpdate(city2);
            commonDao.createOrUpdate(library1);
            commonDao.createOrUpdate(library2);
            commonDao.createOrUpdate(user1);
            commonDao.createOrUpdate(user2);
            commonDao.createOrUpdate(user3);
            commonDao.createOrUpdate(user4);
            commonDao.createOrUpdate(author);
            commonDao.createOrUpdate(publishingCompany);
            commonDao.createOrUpdate(category1);
            commonDao.createOrUpdate(category2);
            commonDao.createOrUpdate(category3);
            commonDao.createOrUpdate(book1);
            commonDao.createOrUpdate(book2);
            commonDao.createOrUpdate(book3);
            commonDao.createOrUpdate(book4);
        }catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
