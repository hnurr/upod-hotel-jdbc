package Controller;

import service.CustomerService;
import service.CustomerServiceModel;
import service.CustomerServiceRequest;
import service.RoomServiceModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerControl {

    private final CustomerService customerService;


    public CustomerControl(CustomerService customerService) {
        this.customerService = customerService;
    }


    public void customerOperations(Scanner scanner) {

        while (true) {

            printCustomerMenu();

            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    getCustomerById(scanner);
                    break;
                case 3:
                    listCustomer();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Geçerli bir değer giriniz. 1 ve 4 arasında !!!");
                    break;
            }
        }

    }

    private void printCustomerMenu() {
        System.out.println("***** Müşteri İşlemleri *****");
        System.out.println("1. Müşteri Kaydı Oluştur");
        System.out.println("2. Müşteri Sorgula");
        System.out.println("3.Müşterileri Listele");
        System.out.println("3. Müşteri İşlemlerinden İşlemlerinden Çıkış Yap");
        System.out.print("Yapmak istediğiniz işlemi seçiniz : ");
    }

    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private void addCustomer(Scanner scanner) throws RuntimeException {
        System.out.println("Müşteri Bilgilerini Giriniz:");


        System.out.print("İsim: ");
        String costumerFirstName = scanner.nextLine();
        scanner.nextLine(); // Consume newline character

        System.out.print("Soyisim: ");
        String costumerLastName = scanner.nextLine();
        scanner.nextLine(); // Consume newline character

        System.out.print("Email: ");
        String costumerEmail = scanner.nextLine();

        System.out.print("Adres: ");
        String costumerAddress = scanner.nextLine();

        System.out.println("Telefon Numarası: ");
        String costumerPhoneNumber = scanner.nextLine();

        // Create Customer object
        CustomerServiceModel customer = new CustomerServiceModel(costumerFirstName, costumerLastName, costumerAddress, costumerEmail, costumerPhoneNumber);

        // Call RoomService to add the room
        try {
            customerService.addCustomer(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Müşteri Kaydı başarıyla oluşturulmuştur.");
    }

    public void getCustomerById(Scanner scanner) {


        System.out.print("Müşteri ID'sini giriniz: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        try {
            CustomerServiceModel customer = customerService.getCustomer(customerId);
            if (customer != null) {
                System.out.println("Müşteri Bilgileri: " + customer);
            } else {
                System.out.println("Müşteri bulunamadı!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Müşteri bilgileri alınırken bir hata oluştu: " + e.getMessage(), e);
        }
    }

    private void listCustomer() {
        List<CustomerServiceModel> customer = null;
        try {
            customer = customerService.getAllCustomer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("***** Odalar *****");
        for (CustomerServiceModel customers : customer) {
            System.out.println(customers);
        }
        System.out.println("*************************");
    }


}