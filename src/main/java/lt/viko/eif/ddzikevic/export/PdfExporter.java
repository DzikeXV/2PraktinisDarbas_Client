package lt.viko.eif.ddzikevic.export;

import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class PdfExporter {
    public static void exportToPdf(String xmlPath, String xslPath, String pdfPath) {
        try {
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            try (OutputStream out = new FileOutputStream(new File(pdfPath))) {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(new File(xslPath)));

                Source src = new StreamSource(new File(xmlPath));
                Result res = new SAXResult(fop.getDefaultHandler());

                transformer.transform(src, res);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Nepavyko eksportuoti į PDF: " + e.getMessage(), e);
        }
    }
}
