package conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {
    private Properties properties = null;
    private final static String CONFIG_FILE_NAME = "src/main/resources/configuration.properties";
    @Deprecated
    public final static String FILEPATH = "src/main/resources/CreateTables";
    @Deprecated
    public final static String BUFFILEPATH = "src/main/resources/";

    public static String USER;
    public static String PASSWORD;
    public static String DBNAME;
    public static String LOCALHOSTURL;
    public static String LOCALHOSTURL1;
    public static Logger logger = LogManager.getLogger();

    private static class InstanceHolder {
        public static Configuration instance = new Configuration();
    }

    public static Configuration getInstance() {
        return InstanceHolder.instance;
    }

    private Configuration() {

        this.properties = new Properties();

        try {
            File file = new File(CONFIG_FILE_NAME);
            if (!file.exists()) {
                logger.info("Configuration File Not Found");
                return;
            } else {
                logger.debug("Configuration file path  : "  + file.getAbsolutePath());
                FileInputStream in = new FileInputStream(CONFIG_FILE_NAME);
                properties.load(in);
                in.close();
                loadProperties();
            }

        } catch (Exception ex) {
            logger.error(ex);
        }

    }

    private void loadProperties() {

        String user = properties.getProperty("USER");
        Configuration.USER = user;

        String pass = properties.getProperty("PASSWORD");
        Configuration.PASSWORD = pass;

        String localhosturl = properties.getProperty("LOCALHOSTURL");
        Configuration.LOCALHOSTURL = localhosturl;

        String localhosturl1 = properties.getProperty("LOCALHOSTURL1");
        Configuration.LOCALHOSTURL1 = localhosturl1;

        String dbname = properties.getProperty("DBNAME");
        Configuration.DBNAME = dbname;

        logger.debug(Configuration.print());
    }

    public static String print() {
        String s = "+------------------------------------------------------------------------------------+\n";
        s +=  "Config Values\n";
        s += "| User : " + Configuration.USER + "| Password : " + Configuration.PASSWORD + "\n";
        s += "| URL : " + Configuration.LOCALHOSTURL1 + "| DB_NAME : " + Configuration.DBNAME + "\n";
        s += "+------------------------------------------------------------------------------------+";
        return s;
    }
}
