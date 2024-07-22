package Controller;

import service.CustomerServiceModel;
import service.ModelServices;
import service.ServicesService;
import service.ServicesServiceRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ServiceControl {


    private ServicesService servicesService;

    public ServiceControl(ServicesService servicesService) {
        this.servicesService = servicesService;
    }


    Scanner scanner = new Scanner(System.in);

    public void serviceOperations(Scanner scanner) {


        while (true) {

            printServiceMenu();

            int choice = getUserChoice(scanner);

            switch (choice) {

                case 1:
                    addService();
                    break;

                case 2:
                    listService();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Lütfen geçerli bir değer giriniz!!");

            }


        }
    }


    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private void printServiceMenu() {

        System.out.println("*******HİZMET İŞLEMLERİ*******");
        System.out.println("1.Hizmet Ekle");
        System.out.println("2.Mevcut Hizmetleri Listele");

    }


    private void addService() {
        try {
            // Kullanıcıdan hizmet adı ve maliyetini al
            System.out.print("Hizmet adını girin: ");
            String serviceName = scanner.nextLine();


            System.out.print("Hizmet maliyetini girin: ");
            double serviceCost = Double.parseDouble(scanner.nextLine());

            // Hizmeti oluştur ve addService metodunu çağır
            ModelServices serviceToAdd = new ModelServices(serviceName, serviceCost);
            servicesService.addService(serviceToAdd);

            System.out.println("Hizmet başarıyla eklendi.");
        } catch (NumberFormatException e) {
            System.err.println("Geçersiz maliyet formatı. Lütfen sayısal bir değer girin.");
        } catch (SQLException e) {
            System.err.println("Hizmet eklenirken bir hata oluştu: " + e.getMessage());
        }
    }


    private void listService() {
        List<ModelServices> service = null;
        try {
            service = servicesService.getAllServices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("***** Odalar *****");
        for (ModelServices services : service) {
            System.out.println(services);
        }
        System.out.println("*************************");
    }
}
