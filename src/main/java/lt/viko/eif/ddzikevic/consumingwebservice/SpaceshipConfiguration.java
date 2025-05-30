package lt.viko.eif.ddzikevic.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SpaceshipConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("lt.viko.eif.ddzikevic.consumingwebservice.wsdl");
        return marshaller;
    }

    @Bean
    public SpaceshipClient spaceshipClient(Jaxb2Marshaller marshaller) {
        SpaceshipClient client = new SpaceshipClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
