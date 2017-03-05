package ConfigureSystem;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Project CreateTablesForSys In com.nicepeopleatwork.createtablesforsystem.ConfigureSystem.
 * Created by root on 1/18/17 3:30 PM.
 */

public class Queries {
    private static final String propFileName = "queries.properties";
    private static Properties props;

    public static Properties getQueries() throws SQLException, IOException {
        InputStream is =
                Queries.class.getResourceAsStream("/" + propFileName);
        if (is == null) {
            throw new SQLException("Unable to load property file: " + propFileName);
        }
        //singleton
        if (props == null) {
            props = new Properties();
            props.load(is);
        }
        return props;
    }

    public static String getQuery(String query) throws SQLException, IOException {
        return getQueries().getProperty(query);
    }
}
