package assistant.Utils;

import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDate;
import java.util.Date;

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
        user1.setRegistrationDate(LocalDate.now().toString());
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
        user2.setRegistrationDate(LocalDate.now().toString());
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
        user3.setRegistrationDate(LocalDate.now().toString());
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
        user4.setRegistrationDate(LocalDate.now().minusDays(3).toString());
        user4.setZipCode("00-780");
        user4.setCity(city2);
        user4.setLibrary(library1);

        User user5 = new User();
        user5.setUsername("user5");
        user5.setPassword(DigestUtils.sha1Hex("user5"));
        user5.setEmail("marii@home.pl");
        user5.setFirstName("Maria");
        user5.setLastName("Klusek");
        user5.setMobile("649-111-239");
        user5.setStreet("Ludowa 11");
        user5.setRegistrationDate(LocalDate.now().minusDays(1).toString());
        user5.setZipCode("00-780");
        user5.setCity(city2);
        user5.setLibrary(library1);

        User user6 = new User();
        user6.setUsername("user6");
        user6.setPassword(DigestUtils.sha1Hex("user6"));
        user6.setEmail("nowak@home.pl");
        user6.setFirstName("Magda");
        user6.setLastName("Nowak");
        user6.setMobile("199-129-885");
        user6.setStreet("Nowa 41");
        user6.setRegistrationDate(LocalDate.now().minusDays(1).toString());
        user6.setZipCode("00-780");
        user6.setCity(city2);
        user6.setLibrary(library1);

        Author author = new Author();
        author.setFistName("Joanne");
        author.setMiddleName("Kathleen");
        author.setLastName("Rowling");
        author.setCountry(country1);

        Author author2 = new Author();
        author2.setFistName("sir Arthur");
        author2.setMiddleName("Conan");
        author2.setLastName("Doyle");
        author2.setCountry(country1);

        PublishingCompany publishingCompany = new PublishingCompany();
        publishingCompany.setName("Media Rodzina");

        PublishingCompany publishingCompany2 = new PublishingCompany();
        publishingCompany2.setName("Siedmioróg");

        Category category1 = new Category();
        category1.setName("Powieść detektywistyczna");
        Category category2 = new Category();
        category2.setName("Fantastyka");
        Category category3 = new Category();
        category3.setName("Kryminał");
        Category category4 = new Category();
        category4.setName("Powieść Przygodowa");

        Book book1 = new Book();
        book1.setIsbn10("83-7278-167-2");
        book1.setIsbn13("978-83-7278-167-3");
        book1.setTitle("Harry Potter i Książę Półkrwi");
        book1.setAddedDate(LocalDate.now().minusDays(1).toString());
        book1.setPublicationDate("2005");
        book1.setAuthor(author);
        book1.setCategory(category2);
        book1.setPublishingCompany(publishingCompany);
        book1.setLibrary(library1);

        Book book2 = new Book();
        book2.setIsbn10("83-7128-167-2");
        book2.setIsbn13("978-83-7128-167-3");
        book2.setTitle("Harry Potter i Książę Półkrwi");
        book2.setAddedDate(LocalDate.now().minusDays(1).toString());
        book2.setPublicationDate("2005");
        book2.setAuthor(author);
        book2.setCategory(category2);
        book2.setPublishingCompany(publishingCompany);
        book2.setLibrary(library1);

        Book book3 = new Book();
        book3.setIsbn10("25-7128-111-2");
        book3.setIsbn13("978-25-7128-111-3");
        book3.setTitle("Harry Potter i Kamień Filozoficzny");
        book3.setAddedDate(LocalDate.now().minusDays(1).toString());
        book3.setPublicationDate("1997");
        book3.setAuthor(author);
        book3.setCategory(category2);
        book3.setPublishingCompany(publishingCompany);
        book3.setLibrary(library1);

        Book book4 = new Book();
        book4.setIsbn10("44-1128-167-2");
        book4.setIsbn13("978-44-1128-167-3");
        book4.setTitle("Harry Potter i Komnata Tajemnic");
        book4.setAddedDate(LocalDate.now().toString());
        book4.setPublicationDate("1998");
        book4.setAuthor(author);
        book4.setCategory(category2);
        book4.setPublishingCompany(publishingCompany);
        book4.setLibrary(library1);

        Book book5 = new Book();
        book5.setIsbn10("83-7908-167-2");
        book5.setIsbn13("978-83-7908-167-1");
        book5.setTitle("Harry Potter i Więzień Azkabanu");
        book5.setAddedDate(LocalDate.now().minusDays(8).toString());
        book5.setPublicationDate("1999");
        book5.setAuthor(author);
        book5.setCategory(category2);
        book5.setPublishingCompany(publishingCompany);
        book5.setLibrary(library1);

        Book book6 = new Book();
        book6.setIsbn10("83-1122-167-2");
        book6.setIsbn13("978-83-1122-167-3");
        book6.setTitle("Harry Potter i Zakon Feniksa ");
        book6.setAddedDate(LocalDate.now().minusDays(5).toString());
        book6.setPublicationDate("2003");
        book6.setAuthor(author);
        book6.setCategory(category2);
        book6.setPublishingCompany(publishingCompany);
        book6.setLibrary(library1);

        Book book7 = new Book();
        book7.setIsbn10("32-7128-127-2");
        book7.setIsbn13("978-32-7128-127-3");
        book7.setTitle("Wołanie kukułki");
        book7.setAddedDate(LocalDate.now().toString());
        book7.setPublicationDate("2013");
        book7.setAuthor(author);
        book7.setCategory(category1);
        book7.setPublishingCompany(publishingCompany);
        book7.setLibrary(library1);

        Book book8 = new Book();
        book8.setIsbn10("13-4432-167-2");
        book8.setIsbn13("978-13-4432-167-3");
        book8.setTitle("Troubled Blood");
        book8.setAddedDate(LocalDate.now().toString());
        book8.setPublicationDate("2020");
        book8.setAuthor(author);
        book8.setCategory(category3);
        book8.setPublishingCompany(publishingCompany);
        book8.setLibrary(library1);

        Book book9 = new Book();
        book9.setIsbn10("83-7791-993-2");
        book9.setIsbn13("978-83-7791-993-4");
        book9.setTitle("Pies Baskerville'ów");
        book9.setAddedDate(LocalDate.now().toString());
        book9.setPublicationDate("1902");
        book9.setAuthor(author2);
        book9.setCategory(category3);
        book9.setPublishingCompany(publishingCompany2);
        book9.setLibrary(library1);

        Book book10 = new Book();
        book10.setIsbn10("13-1123-167-7");
        book10.setIsbn13("978-13-1123-167-7");
        book10.setTitle("Studium w szkarłacie");
        book10.setAddedDate(LocalDate.now().toString());
        book10.setPublicationDate("1886");
        book10.setAuthor(author2);
        book10.setCategory(category3);
        book10.setPublishingCompany(publishingCompany2);
        book10.setLibrary(library1);

        Book book11 = new Book();
        book11.setIsbn10("13-1231-167-8");
        book11.setIsbn13("978-13-1231-167-8");
        book11.setTitle("Przygody Sherlocka Holmesa");
        book11.setAddedDate(LocalDate.now().toString());
        book11.setPublicationDate("2020");
        book11.setAuthor(author2);
        book11.setCategory(category3);
        book11.setPublishingCompany(publishingCompany2);
        book11.setLibrary(library1);

        Book book12 = new Book();
        book12.setIsbn10("13-6546-167-1");
        book12.setIsbn13("978-13-6546-167-1");
        book12.setTitle("Zaginiony świat");
        book12.setAddedDate(LocalDate.now().toString());
        book12.setPublicationDate("1912");
        book12.setAuthor(author2);
        book12.setCategory(category4);
        book12.setPublishingCompany(publishingCompany2);
        book12.setLibrary(library1);

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
            commonDao.createOrUpdate(user5);
            commonDao.createOrUpdate(user6);
            commonDao.createOrUpdate(author);
            commonDao.createOrUpdate(author2);
            commonDao.createOrUpdate(publishingCompany);
            commonDao.createOrUpdate(publishingCompany2);
            commonDao.createOrUpdate(category1);
            commonDao.createOrUpdate(category2);
            commonDao.createOrUpdate(category3);
            commonDao.createOrUpdate(category4);
            commonDao.createOrUpdate(book1);
            commonDao.createOrUpdate(book2);
            commonDao.createOrUpdate(book3);
            commonDao.createOrUpdate(book4);
            commonDao.createOrUpdate(book5);
            commonDao.createOrUpdate(book6);
            commonDao.createOrUpdate(book7);
            commonDao.createOrUpdate(book8);
            commonDao.createOrUpdate(book9);
            commonDao.createOrUpdate(book10);
            commonDao.createOrUpdate(book11);
            commonDao.createOrUpdate(book12);
        }catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
