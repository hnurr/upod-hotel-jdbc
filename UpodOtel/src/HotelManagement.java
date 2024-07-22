import Controller.CustomerControl;
import Controller.ReservationControl;
import Controller.RoomControl;
import Controller.ServiceControl;
import Controller.InvoicesControl;
import Controller.Start;
import service.*;

import java.util.Scanner;

public class HotelManagement {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Polimorfizme göre Bağımlılıkları azaltmak adına Interface üzerinden nesne üretiyoruz ki esneklik sağlayabilelim...


        RoomService roomService = new RoomServiceRequest();
        RoomControl roomControl = new RoomControl(roomService);


        CustomerService customerService = new CustomerServiceRequest();
        CustomerControl customerControl = new CustomerControl(customerService);

        ReservationService reservationService = new ReservationServiceRequest();
        ReservationControl reservationControl = new ReservationControl(reservationService);

        ServicesService service = new ServicesServiceRequest();
        ServiceControl serviceControl = new ServiceControl(service);


        InvoicesService invoicesService = new InvoicesServiceRequest();
        InvoicesControl invoicesControl = new InvoicesControl(invoicesService);


        Start start = new Start();

//        start.start();

//        System.out.println("=========================================");
//
//        System.out.println("Gerçekleştirmek istediğiniz işlemi seçiniz: ");
//        int choose = scanner.nextInt();

        while (true) {

            start.start();

            System.out.println("=========================================");

            System.out.println("Gerçekleştirmek istediğiniz işlemi seçiniz: ");
            int choose = scanner.nextInt();

            switch (choose) {


                case 1:
                    roomControl.roomOperations(scanner);
                    break;

                case 2:
                    customerControl.customerOperations(scanner);
                    break;
                case 3:

                    reservationControl.reservationOperations(scanner);
                    break;
                case 4:
                    serviceControl.serviceOperations(scanner);
                    break;

                case 5:
                    invoicesControl.invoicesOperations(scanner);
                    break;
                case 6:
                    System.out.println("Sistemden Çıkış Yapılıyor.....");
                    return;
                default:
                    System.out.println("Geçerli bir değer giriniz!");
            }

        }
    }
}
