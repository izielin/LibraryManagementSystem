package assistant.settings;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static assistant.alert.AlertMaker.showSimpleAlert;

public class Settings {
    public static final String CONFIG_FILE = "config.txt";
    int daysWithoutFee;
    float feePerDay;
    String username;
    String password;

    public Settings() {
        daysWithoutFee = 14;
        feePerDay = (float) 1.2;
        username = "admin";
        setPassword("admin");
    }

    public int getDaysWithoutFee() {
        return daysWithoutFee;
    }

    public void setDaysWithoutFee(int nDaysWithoutFee) {
        this.daysWithoutFee = nDaysWithoutFee;
    }

    public float getFeePerDay() {
        return feePerDay;
    }

    public void setFeePerDay(float feePerDay) {
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
        if (password.length() < 16)
            this.password = DigestUtils.sha1Hex(password);
        else
            this.password = password;
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            // creating default config & saving it to file
            Settings settings = new Settings();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(settings, writer);
        } catch (IOException e) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                initConfig();
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public static Settings getSettings() {
        Gson gson = new Gson();
        Settings settings = new Settings();
        try {
            // reading default settings from file
            settings = gson.fromJson(new FileReader(CONFIG_FILE), Settings.class);
        } catch (IOException e) {
            initConfig();
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, e);
        }
        return settings;
    }

    public static void overWriteSettings(Settings settings) {
        Writer writer = null;
        try {
            // creating default config & saving it to file
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(settings, writer);

            showSimpleAlert("information", "Success", "", "Setting Updated");

        } catch (IOException e) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, e);
            showSimpleAlert("error", "Error", "", "Can't save configuration file");
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
