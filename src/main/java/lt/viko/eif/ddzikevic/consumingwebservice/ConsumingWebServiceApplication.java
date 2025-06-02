package lt.viko.eif.ddzikevic.consumingwebservice;

import lt.viko.eif.ddzikevic.consumingwebservice.wsdl.*;
import lt.viko.eif.ddzikevic.export.HtmlExporter;
import lt.viko.eif.ddzikevic.export.PdfExporter;
import lt.viko.eif.ddzikevic.export.XmlExporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Scanner;

@SpringBootApplication
public class ConsumingWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumingWebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(SpaceshipClient spaceshipClient) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n=== ERDVĖLAIVIŲ SISTEMA ===");
                System.out.println("1. Gauti erdvėlaivį pagal pavadinimą");
                System.out.println("2. Gauti visus erdvėlaivius");
                System.out.println("3. Pridėti naują erdvėlaivį");
                System.out.println("4. Ištrinti erdvėlaivį");
                System.out.println("5. Pridėti naują misiją prie erdvėlaivio");
                System.out.println("6. Pridėti naują įgulos narį prie erdvėlaivio");
                System.out.println("7. Eksportuoti erdvėlaivį į HTML");
                System.out.println("0. Išeiti");
                System.out.print("Pasirinkite: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Įveskite pavadinimą: ");
                        String name = scanner.nextLine();
                        var response = spaceshipClient.getSpaceship(name);
                        var s = response.getSpaceship();

                        if (s == null || s.getName() == null) {
                            System.out.println("⚠️ Erdvėlaivis nerastas.");
                            break;
                        }

                        System.out.println("\n🔷 Erdvėlaivio informacija:");
                        System.out.println("ID: " + s.getId());
                        System.out.println("Pavadinimas: " + s.getName());
                        System.out.println("Modelis: " + s.getModel());
                        System.out.println("Registracija: " + s.getRegistry());
                        System.out.println("Gamintojas: " + s.getManufacturer());
                        System.out.println("Pagaminimo metai: " + s.getYearBuilt());

                        System.out.println("\nĮgulos nariai (" + s.getCrew().size() + "):");
                        for (CrewMember cm : s.getCrew()) {
                            System.out.println(" - " + cm.getFirstName() + " " + cm.getLastName()
                                    + " (" + cm.getPosition() + ", " + cm.getRank() + "), Amžius: " + cm.getAge()
                                    + ", Patirtis: " + cm.getExperienceYears() + " metų");
                        }

                        System.out.println("\nMisijos (" + s.getMissions().size() + "):");
                        for (Mission m : s.getMissions()) {
                            System.out.println(" - " + m.getName() + " (" + m.getStartDate() + " → " + m.getEndDate() + ")");
                        }
                    }
                    case 2 -> {
                        var all = spaceshipClient.getAllSpaceships().getSpaceships();
                        System.out.println("Erdvėlaivių sąrašas:");
                        all.forEach(s -> System.out.println(" - " + s.getName()));
                    }
                    case 3 -> {
                        Spaceship newShip = new Spaceship();
                        System.out.print("ID: ");
                        newShip.setId(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Pavadinimas: ");
                        newShip.setName(scanner.nextLine());
                        System.out.print("Modelis: ");
                        newShip.setModel(scanner.nextLine());
                        System.out.print("Registracija: ");
                        newShip.setRegistry(scanner.nextLine());
                        System.out.print("Gamintojas: ");
                        newShip.setManufacturer(scanner.nextLine());
                        System.out.print("Metai: ");
                        newShip.setYearBuilt(Integer.parseInt(scanner.nextLine()));

                        // Paprastas įgulos narys
                        System.out.print("Įgulos nario vardas: ");
                        String fname = scanner.nextLine();
                        CrewMember cm = new CrewMember();
                        cm.setId(1);
                        cm.setFirstName(fname);
                        cm.setLastName("Default");
                        cm.setPosition("Pilot");
                        cm.setRank("Lieutenant");
                        cm.setAge(30);
                        cm.setExperienceYears(5);
                        newShip.getCrew().add(cm);

                        // Paprasta misija
                        Mission m = new Mission();
                        m.setId(1);
                        m.setName("Test Mission");
                        m.setStartDate(toXmlDate(LocalDate.of(2024, 1, 1)));
                        m.setEndDate(toXmlDate(LocalDate.of(2024, 12, 31)));
                        newShip.getMissions().add(m);

                        var addResponse = spaceshipClient.addSpaceship(newShip);
                        System.out.println(addResponse.getStatus());
                    }
                    case 4 -> {
                        System.out.print("Įveskite pavadinimą: ");
                        String toDelete = scanner.nextLine();
                        var del = spaceshipClient.deleteSpaceship(toDelete);
                        System.out.println(del.getStatus());
                    }
                    case 5 -> {
                        System.out.print("Įveskite erdvėlaivio pavadinimą: ");
                        String name = scanner.nextLine();
                        Spaceship ship = spaceshipClient.getSpaceship(name).getSpaceship();

                        if (ship == null || ship.getName() == null) {
                            System.out.println("⚠️ Erdvėlaivis nerastas.");
                            break;
                        }

                        Mission mission = new Mission();
                        System.out.print("Misijos ID: ");
                        mission.setId(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Misijos pavadinimas: ");
                        mission.setName(scanner.nextLine());
                        System.out.print("Pradžios data (YYYY-MM-DD): ");
                        LocalDate start = LocalDate.parse(scanner.nextLine());
                        System.out.print("Pabaigos data (YYYY-MM-DD): ");
                        LocalDate end = LocalDate.parse(scanner.nextLine());

                        mission.setStartDate(toXmlDate(start));
                        mission.setEndDate(toXmlDate(end));

                        ship.getMissions().add(mission);

                        var update = spaceshipClient.addSpaceship(ship);
                        System.out.println("✅ " + update.getStatus());
                    }
                    case 6 -> {
                        System.out.print("Įveskite erdvėlaivio pavadinimą: ");
                        String name = scanner.nextLine();
                        Spaceship ship = spaceshipClient.getSpaceship(name).getSpaceship();

                        if (ship == null || ship.getName() == null) {
                            System.out.println("⚠️ Erdvėlaivis nerastas.");
                            break;
                        }

                        CrewMember cm = new CrewMember();
                        System.out.print("ID: ");
                        cm.setId(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Vardas: ");
                        cm.setFirstName(scanner.nextLine());
                        System.out.print("Pavardė: ");
                        cm.setLastName(scanner.nextLine());
                        System.out.print("Pareigos: ");
                        cm.setPosition(scanner.nextLine());
                        System.out.print("Laipsnis: ");
                        cm.setRank(scanner.nextLine());
                        System.out.print("Amžius: ");
                        cm.setAge(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Patirtis (metais): ");
                        cm.setExperienceYears(Integer.parseInt(scanner.nextLine()));

                        ship.getCrew().add(cm);

                        var update = spaceshipClient.addSpaceship(ship);
                        System.out.println("✅ " + update.getStatus());
                    }
                    case 7 -> {
                        System.out.print("Įveskite erdvėlaivio pavadinimą: ");
                        String exportName = scanner.nextLine();
                        var exportResponse = spaceshipClient.getSpaceship(exportName);
                        var spaceship = exportResponse.getSpaceship();

                        if (spaceship == null || spaceship.getName() == null) {
                            System.out.println("⚠️ Erdvėlaivis nerastas.");
                            break;
                        }

                        String xmlPath = "output/Spaceship.xml";
                        String pdfPath = "output/Spaceship.pdf";
                        String pdfXsl = "src/main/resources/spaceship-to-fo.xsl";
                        String htmlPath = "output/Spaceship.html";
                        String htmlXsl = "src/main/resources/spaceship-to-html.xsl";

                        XmlExporter.exportToXml(exportResponse, xmlPath);
                        PdfExporter.exportToPdf(xmlPath, pdfXsl, pdfPath);
                        HtmlExporter.exportToHtml(xmlPath, htmlXsl, htmlPath);

                        System.out.println("✅ PDF: " + pdfPath);
                        System.out.println("✅ HTML: " + htmlPath);
                    }


                    case 0 -> {
                        System.out.println("Programa baigta.");
                        return;
                    }
                    default -> System.out.println("Neteisingas pasirinkimas.");
                }
            }
        };
    }

    private XMLGregorianCalendar toXmlDate(LocalDate localDate) {
        try {
            GregorianCalendar gcal = GregorianCalendar.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (Exception e) {
            throw new RuntimeException("Nepavyko konvertuoti datą", e);
        }
    }
}
