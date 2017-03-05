package RequestProcessing;



import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.error.SimpleErrorPageHandler;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


/**
 * Project CreateTablesForSys In com.nicepeopleatwork.createtablesforsystem.RequestProcessing.
 * Created by root on 1/30/17 11:01 AM.
 */
public class InitialiseUndertowServer {
    public static Logger logger = LogManager.getLogger();
    public static void main(String args[]) {
        System.out.println("LOGGER LEVEL " + logger.getLevel());
        logger.debug("Change log level setting at : " +  new File("log4j2.xml").getAbsolutePath());
        Undertow.builder().addHttpListener(8090, "localhost")
                .setHandler(Handlers.path()
                        // API Path
                        .addPrefixPath("/api/", Handlers.routing()
                                .post("/status", new ProcessHandlers.CheckStatus())
                                .post("/uploadfile", new ProcessHandlers.UploadFile())
                                .setFallbackHandler(new SimpleErrorPageHandler()))

                        // Redirect root path to /static to serve the index.html by default
                        .addExactPath("/", Handlers.redirect("http://www.hackupc.com"))
                ).build().start();
    }
}
