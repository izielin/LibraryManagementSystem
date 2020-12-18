package assistant.settings;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Preferences {
    public static final String CONFIG_FILE = "config.txt";
    int daysWithoutFee;
    double feePerDay;
    String username;
    String password;

    public Preferences() {
        daysWithoutFee = 14;
        feePerDay = 1.2;
        username = "admin";
        password = "admin";
    }

    public int getDaysWithoutFee() {
        return daysWithoutFee;
    }

    public void setDaysWithoutFee(int nDaysWithoutFee) {
        this.daysWithoutFee = nDaysWithoutFee;
    }

    public double getFeePerDay() {
        return feePerDay;
    }

    public void setFeePerDay(double feePerDay) {
        this.feePerDay = feePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            // creating default config & saving it to file
            Preferences preferences = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
        } catch (IOException e) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public static Preferences getPreferences() {
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            // reading default preferences from file
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (IOException e) {
            initConfig();
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, e);
        }
        return preferences;
    }
}
