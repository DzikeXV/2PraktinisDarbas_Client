package lt.viko.eif.ddzikevic.export;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class HtmlExporter {
    public static void exportToHtml(String xmlPath, String xslPath, String htmlPath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xslPath)));

            Source xmlSource = new StreamSource(new File(xmlPath));
            Result result = new StreamResult(new File(htmlPath));

            transformer.transform(xmlSource, result);
        } catch (Exception e) {
            throw new RuntimeException("❌ Nepavyko eksportuoti į HTML: " + e.getMessage(), e);
        }
    }
}
