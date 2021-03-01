package assistant.Utils;

import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Random;

public class FillDatabase {
    private static final String CITIES_SOURCE = "src/main/resources/dataInputFiles/cities.csv";
    private static final String CATEGORIES_SOURCE = "src/main/resources/dataInputFiles/categories.csv";
    private static final String PUBLISHERS_SOURCE = "src/main/resources/dataInputFiles/publishing_companies.csv";
    private static final String AUTHORS_SOURCE = "src/main/resources/dataInputFiles/authors.csv";
    private static final String LIBRARIES_SOURCE = "src/main/resources/dataInputFiles/libraries.csv";
    private static final String USERS_SOURCE = "src/main/resources/dataInputFiles/users.csv";
    private static final String BOOKS_SOURCE = "src/main/resources/dataInputFiles/books.csv";
    private static final String BORROWED_BOOKS_SOURCE = "src/main/resources/dataInputFiles/borrowed_books.csv";
    private static final String REPORTS_SOURCE = "src/main/resources/dataInputFiles/reports.csv";
    private static final String IMAGES = "src/main/resources/images/default_profile_pic/";


    public static void fillDatabase() {

        readCitiesFromCSV();
        readCategoriesFromCSV();
        readPublishersFromCSV();
        readAuthorsFromCSV();
        readLibrariesFromCSV();
        readUsersFromCSV();
        readBooksFromCSV();
        readBorrowedBooksFromCSV();
        readReportsFromCSV();
    }

    private static void readCitiesFromCSV() {
        Path pathToFile = Paths.get(CITIES_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine(); // pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");
                City city = new City();
                city.setId(Integer.parseInt(attributes[0]));
                city.setName(attributes[1]);
                dao.createOrUpdate(city);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readCategoriesFromCSV() {
        Path pathToFile = Paths.get(CATEGORIES_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine(); // pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");
                Category category = new Category();
                category.setId(Integer.parseInt(attributes[0]));
                category.setName(attributes[1]);
                dao.createOrUpdate(category);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readPublishersFromCSV() {
        Path pathToFile = Paths.get(PUBLISHERS_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine(); // pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");
                PublishingCompany publishingCompany = new PublishingCompany();
                publishingCompany.setId(Integer.parseInt(attributes[0]));
                publishingCompany.setName(attributes[1]);
                dao.createOrUpdate(publishingCompany);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readAuthorsFromCSV() {
        Path pathToFile = Paths.get(AUTHORS_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine(); // pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");
                Author author = new Author();
                author.setId(Integer.parseInt(attributes[0]));
                author.setFistName(attributes[1]);
                author.setMiddleName(attributes[2]);
                author.setLastName(attributes[3]);
                dao.createOrUpdate(author);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readLibrariesFromCSV() {
        Path pathToFile = Paths.get(LIBRARIES_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine(); // pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");
                Library library = new Library();
                library.setId(Integer.parseInt(attributes[0]));
                library.setName(attributes[1]);
                library.setStreet(attributes[2]);
                library.setZipCode(attributes[3]);
                library.setCityID(Integer.parseInt(attributes[4]));
                dao.createOrUpdate(library);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readBooksFromCSV() {
        Path pathToFile = Paths.get(BOOKS_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine();// pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                System.out.println();
                System.out.println();

                String[] attributes = line.split(";");
                System.out.println(Arrays.toString(attributes));
                Book book = new Book();
                book.setId(Integer.parseInt(attributes[0]));
                book.setIsbn10(attributes[1]);
                book.setIsbn13(attributes[2]);
                book.setTitle(attributes[3]);
                book.setAddedDate(attributes[4]);
                book.setDescription(attributes[5]);
                book.setPublicationDate(attributes[6]);
                book.setAvailability(Boolean.valueOf(attributes[7]));
                book.setAuthor(Integer.parseInt(attributes[8]));
                book.setCategory(Integer.parseInt(attributes[9]));
                book.setPublishingCompany(Integer.parseInt(attributes[10]));
                book.setLibrary(Integer.parseInt(attributes[11]));
                //if (attributes.length == 13) {
                    //book.setBookCover(attributes[12].getBytes(StandardCharsets.UTF_8));
                //}
                dao.createOrUpdate(book);
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(attributes[4], df), LocalDate.now());
                if (daysBetween < 30)
                    dao.createOrUpdate(MessageMaker.bookCreationMessage(attributes[3], dao.findById(User.class, 1), attributes[4]));
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readUsersFromCSV() {
        Path pathToFile = Paths.get(USERS_SOURCE);


        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine(); // pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();
                Random rand = new Random();
                int max = 9, min = 1;
                int randomNum = rand.nextInt((max - min) + 1) + min;

                String[] attributes = line.split(";");
                User user = new User();
                user.setId(Integer.parseInt(attributes[0]));
                user.setUsername(attributes[1]);
                user.setPassword(attributes[2]);
                user.setFirstName(attributes[3]);
                user.setLastName(attributes[4]);
                user.setGender(attributes[5]);
                user.setMobile(attributes[6]);
                user.setEmail(attributes[7]);
                user.setRegistrationDate(attributes[8]);
                user.setStreet(attributes[9]);
                user.setZipCode(attributes[10]);
                user.setUserType(attributes[11]);
                user.setLibraryID(Integer.parseInt(attributes[12]));

                BufferedImage bufferedImage = ImageIO.read(new File(IMAGES + randomNum + ".png"));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", outputStream);
                byte[] data = outputStream.toByteArray();
                user.setProfilePicture(data);


                dao.createOrUpdate(user);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readBorrowedBooksFromCSV() {
        Path pathToFile = Paths.get(BORROWED_BOOKS_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine();// pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");

                BorrowedBook borrowedBook = new BorrowedBook();
                borrowedBook.setId(Integer.parseInt(attributes[0]));
                borrowedBook.setBorrowTime(attributes[1]);
                borrowedBook.setReturnTime(attributes[2]);
                borrowedBook.setReturned(Boolean.valueOf(attributes[3]));
                borrowedBook.setBookID(Integer.parseInt(attributes[4]));
                borrowedBook.setUserID(Integer.parseInt(attributes[5]));
                borrowedBook.setLibraryID(Integer.parseInt(attributes[6]));
                dao.createOrUpdate(borrowedBook);
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readReportsFromCSV() {
        Path pathToFile = Paths.get(REPORTS_SOURCE);

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            reader.readLine();// pass 1 line
            String line;
            while ((line = reader.readLine()) != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(";");

                Report report = new Report();
                report.setId(Integer.parseInt(attributes[0]));
                report.setLibraryID(Integer.parseInt(attributes[1]));
                report.setDate(attributes[2]);
                report.setBooksAmount(attributes[3].isEmpty() ? 0 : Integer.parseInt(attributes[3]));
                report.setbBooksAmount(attributes[4].isEmpty() ? 0 : Integer.parseInt(attributes[4]));
                report.setrBooksAmount(attributes[5].isEmpty() ? 0 : Integer.parseInt(attributes[5]));
                report.setUsersNumber(attributes[6].isEmpty() ? 0 : Integer.parseInt(attributes[6]));
                report.setWomanNumber(attributes[7].isEmpty() ? 0 : Integer.parseInt(attributes[7]));
                dao.createOrUpdate(report);
            }
        } catch (IOException | ApplicationException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
