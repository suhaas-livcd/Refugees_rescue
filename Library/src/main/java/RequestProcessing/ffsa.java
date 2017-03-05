package RequestProcessing;

import com.google.gson.Gson;
import com.signaturit.api.java_sdk.Client;
import io.undertow.util.FileUtils;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

/**
 * Created by root on 5/03/17.
 */
public class ffsa {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws IOException {
GetusingCurl();
        //GetSignature();
        }


        public static void GetusingCurl() throws IOException {


            String[] command = {"curl",
                    "-H", "\"Authorization:Bearer","SwyHjiObcYrlchRVcPNwlUlQSgklOmiifgGXVfnlitwnDBZDuDtfxmxgvSUXjDKJEpPJcbCRDUkKfAXjrDuBgx\"","https://api.sandbox.signaturit.com/v3/signatures.json"};
            Process p = Runtime.getRuntime().exec(command);
            InputStream is = p.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String theString = writer.toString();
            System.out.println(theString.length());
        }


        public static void GetSignature() throws IOException {
            Client client = new Client("SwyHjiObcYrlchRVcPNwlUlQSgklOmiifgGXVfnlitwnDBZDuDtfxmxgvSUXjDKJEpPJcbCRDUkKfAXjrDuBgx", false);
            Response obj = client.getSignatures();
            Object someObject = obj;
            for (Field field : someObject.getClass().getDeclaredFields()) {
                field.setAccessible(true); // You might want to set modifier to public first.
                Object value = null;
                try {
                    value = field.get(someObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    System.out.println(field.getName() + "=" + value);
                }
            }
            System.out.println(client.getTemplates());
            System.out.println(obj);
        }

        public static void RequestSenderFun(){
            Client client = new Client("SwyHjiObcYrlchRVcPNwlUlQSgklOmiifgGXVfnlitwnDBZDuDtfxmxgvSUXjDKJEpPJcbCRDUkKfAXjrDuBgx", false);

            ArrayList<File> filePath = new ArrayList<File>();
            String temp = new String("src/main/resources/"+"Downloads.pdf");
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

                System.out.println(response.body());
            } catch (IOException exception) {
                logger.debug(exception);
            }

            logger.debug(response);
        }
    }
