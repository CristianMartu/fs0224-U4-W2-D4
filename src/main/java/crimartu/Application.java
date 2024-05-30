package crimartu;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            System.out.println("Genre: " + faker.book().genre() + "\tauthor: " + faker.book().author() + "\tsupehero: " + faker.book().title() + "\n");
        }
        File file = new File("src/testo.text");
        try {
            FileUtils.writeStringToFile(file, "ciao", StandardCharsets.UTF_8, true);
            System.out.println("File creato correttamente!");
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
