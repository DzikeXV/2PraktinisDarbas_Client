package lt.viko.eif.ddzikevic.export;

import lt.viko.eif.ddzikevic.consumingwebservice.wsdl.GetSpaceshipResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.*;

import org.apache.fop.apps.*;

public class PDFGenerator {

    public void saveXml(GetSpaceshipResponse response, File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(GetSpaceshipResponse.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(response, file);
    }

    public void generatePdf(File xmlFile, File xslFile, File pdfFile) throws Exception {
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        try (OutputStream out = new FileOutputStream(pdfFile)) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

            Source src = new StreamSource(xmlFile);
            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);
        }
    }
}
