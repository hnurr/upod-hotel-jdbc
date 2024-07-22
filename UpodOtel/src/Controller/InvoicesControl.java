package Controller;

import service.InvoicesService;

import java.sql.SQLException;
import java.util.Scanner;

public class InvoicesControl {

    private final InvoicesService invoicesService;

    public InvoicesControl(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }


    Scanner scanner = new Scanner(System.in);

    public void invoicesOperations(Scanner scanner) {

        while (true) {

            printInvoicesMenu();

            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    createInvoiceFromUserInput();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Geçerli bir değer giriniz. 1 ve 4 arasında !!!");
                    break;
            }
        }

    }

    private void printInvoicesMenu() {
        System.out.println("***** Fatura İşlemleri *****");
        System.out.println("1. Fatura Oluştur");

    }

    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }


    public void createInvoiceFromUserInput() {
        try {
            System.out.print("Müşteri ID'sini girin: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            System.out.print("Rezervasyon ID'sini girin: ");
            int reservationId = Integer.parseInt(scanner.nextLine());

            System.out.print("Hizmet ID'lerini virgülle ayırarak girin: ");
            String[] serviceIdStrings = scanner.nextLine().split(",");
            int[] serviceIds = new int[serviceIdStrings.length];
            for (int i = 0; i < serviceIdStrings.length; i++) {
                serviceIds[i] = Integer.parseInt(serviceIdStrings[i].trim());
            }

            // Faturayı oluşturmak için createInvoice metodunu çağır
            try {
                invoicesService.createInvoice(customerId, reservationId, serviceIds);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (NumberFormatException e) {
            System.err.println("Geçersiz giriş. Lütfen sayısal değerler girin.");
        }
    }
}
