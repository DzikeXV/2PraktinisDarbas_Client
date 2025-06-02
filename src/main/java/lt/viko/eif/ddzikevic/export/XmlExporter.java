package lt.viko.eif.ddzikevic.export;

import lt.viko.eif.ddzikevic.consumingwebservice.wsdl.GetSpaceshipResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.File;

public class XmlExporter {
    public static void exportToXml(GetSpaceshipResponse response, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(GetSpaceshipResponse.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(response, new File(path));
        } catch (Exception e) {
            throw new RuntimeException("❌ Nepavyko eksportuoti į XML: " + e.getMessage(), e);
        }
    }
}
