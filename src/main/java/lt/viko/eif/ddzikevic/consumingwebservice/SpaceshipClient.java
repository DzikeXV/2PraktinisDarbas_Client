package lt.viko.eif.ddzikevic.consumingwebservice;

import lt.viko.eif.ddzikevic.consumingwebservice.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

public class SpaceshipClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(SpaceshipClient.class);

    public GetSpaceshipResponse getSpaceship(String name) {
        GetSpaceshipRequest request = new GetSpaceshipRequest();
        request.setName(name);

        log.info("Requesting spaceship with name: " + name);

        return (GetSpaceshipResponse) getWebServiceTemplate().marshalSendAndReceive(
                "http://localhost:8080/ws", request,
                new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetSpaceshipRequest"));
    }

    public GetAllSpaceshipsResponse getAllSpaceships() {
        GetAllSpaceshipsRequest request = new GetAllSpaceshipsRequest();

        return (GetAllSpaceshipsResponse) getWebServiceTemplate().marshalSendAndReceive(
                "http://localhost:8080/ws", request,
                new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetAllSpaceshipsRequest"));
    }

    public AddSpaceshipResponse addSpaceship(Spaceship spaceship) {
        AddSpaceshipRequest request = new AddSpaceshipRequest();
        request.setSpaceship(spaceship);

        return (AddSpaceshipResponse) getWebServiceTemplate().marshalSendAndReceive(
                "http://localhost:8080/ws", request,
                new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/AddSpaceshipRequest"));
    }

    public DeleteSpaceshipResponse deleteSpaceship(String name) {
        DeleteSpaceshipRequest request = new DeleteSpaceshipRequest();
        request.setName(name);

        return (DeleteSpaceshipResponse) getWebServiceTemplate().marshalSendAndReceive(
                "http://localhost:8080/ws", request,
                new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/DeleteSpaceshipRequest"));
    }
}
