package RequestProcessing;


import com.signaturit.api.java_sdk.Client;
import conf.Configuration;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.xnio.Pooled;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project CreateTablesForSys In com.nicepeopleatwork.createtablesforsystem.RequestProcessing.
 * Created by root on 1/30/17 11:02 AM.
 */
public class ProcessHandlers {
    public static Connection process_con = null;
    private static String account_code = null;
    private static final Logger logger = LogManager.getLogger();

    public static HttpServerExchange MakeInitialization(HttpServerExchange exchange) {
        exchange = LoadHeaders(exchange);

        return exchange;
    }

    public static HttpServerExchange LoadHeaders(HttpServerExchange exchange) {
        try {
            if (exchange != null) {
                logger.debug("Loaded Headers");
                String current_datetime = String.valueOf(DateTime.now());
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json; charset=latin1"); //e.g text/plain
                exchange.getResponseHeaders().put(Headers.CACHE_CONTROL, "no-cache, must-revalidate");
                exchange.getResponseHeaders().put(Headers.DATE, current_datetime);
                exchange.getResponseHeaders().put(Headers.LAST_MODIFIED, current_datetime);
                exchange.getResponseHeaders().put(Headers.CONNECTION, "close");
                exchange.getResponseHeaders().put(Headers.SERVER, "HackUpcApi/1.0 (HackUpcAPI/1.0)");
                exchange.getResponseHeaders().put(HttpString.tryFromString("Access-Control-Allow-Origin"), "http://localhost");
            }
        } catch (Exception exception) {
            logger.debug(exception);
        }
        return exchange;
    }


    public static String GetRequestBody(HttpServerExchange exchange) {
        String requestBody = null;
        try {
            if (exchange != null) {
                Pooled<ByteBuffer> pooledByteBuffer = exchange.getConnection().getBufferPool().allocate();
                ByteBuffer byteBuffer = pooledByteBuffer.getResource();
                byteBuffer.clear();
                exchange.getRequestChannel().read(byteBuffer);
                int pos = byteBuffer.position();
                byteBuffer.rewind();
                byte[] bytes = new byte[pos];
                byteBuffer.get(bytes);
                requestBody = new String(bytes, Charset.forName("UTF-8"));
                byteBuffer.clear();
                pooledByteBuffer.free();
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
        return requestBody;
    }

    public static Connection getQuery_conn() {
        Connection con=null;
        return con;
    }


    public static class CheckStatus implements HttpHandler {
        @Override
        public void handleRequest(HttpServerExchange exchange) throws Exception {
            // exchange=LoadHeaders(exchange);
            logger.debug("\"STATUS\" Handler Invoked");
            exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "http://localhost");
            exchange.getResponseSender().send("status" + GetRequestBody(exchange));
            exchange.getResponseSender().close();

        }
    }

    public static class UploadFile implements HttpHandler {
        @Override
        public void handleRequest(HttpServerExchange exchange) throws Exception {
            boolean result = false;
            exchange=LoadHeaders(exchange);
            String file_names[] = GetRequestBody(exchange).split(" ");
            logger.debug("The"+file_names.length+ " files requested are uploaded");
            Client client = new Client("SwyHjiObcYrlchRVcPNwlUlQSgklOmiifgGXVfnlitwnDBZDuDtfxmxgvSUXjDKJEpPJcbCRDUkKfAXjrDuBgx", false);
            for (String file : file_names
                    ) {
                ArrayList<File> filePath = new ArrayList<File>();
                String temp = new String("src/main/resources/"+file);
                File f = new File(temp);
                filePath.add(f);
                ArrayList<HashMap<String, Object>> recipients = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> recipient = new HashMap<String, Object>();
                recipient.put("email", "suhaas95@gmail.com");
                recipient.put("name", "Suhaas");
                recipients.add(recipient);
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("delivery_type", "url");
                Response response = null;
                try {
                    response = client.createSignature(filePath, recipients, params);

                    if(response.code()==200){result=true;}else {result=false;}
                } catch (IOException exception) {
                    logger.debug(exception);
                }

                logger.debug(response);
            }
        }
    }


}

