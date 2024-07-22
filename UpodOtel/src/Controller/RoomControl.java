package Controller;

import service.RoomService;
import service.RoomServiceModel;
import service.RoomServiceRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RoomControl {

    private final RoomService roomService;

    public RoomControl(RoomService roomService) {
        this.roomService = roomService;
    }

    public void roomOperations(Scanner scanner) {
        while (true) {

            printRoomMenu();

            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addRoom(scanner);
                    break;
                case 2:
                    listRooms();
                    break;
                case 3:
                    System.out.println("Oda işlemlerinden çıkış yapıldı.");
                    return; // Programın burada sonlanmasını sağlar
                default:
                    System.out.println("Geçerli bir değer giriniz.1 ve 3 arasında !!!");
                    break;
            }
        }
    }

    private void printRoomMenu() {
        System.out.println("***** Oda İşlemleri *****");
        System.out.println("1. Oda Oluştur");
        System.out.println("2. Odaları Listele");
        System.out.println("3. Oda İşlemlerinden Çıkış Yap");
        System.out.print("Yapmak istediğiniz işlemi seçiniz : ");
    }

    private int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private void addRoom(Scanner scanner) throws RuntimeException {
        System.out.println("Oda Bilgilerini Giriniz:");

        System.out.print("Oda Numarası: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        try {
            if (roomService.isRoomNumberExists(roomNumber)) {
                System.out.println("Bu oda numarası zaten mevcut. Lütfen farklı bir oda numarası seçin.");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Kapasite: ");
        int capacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Özellikler: ");
        String features = scanner.nextLine();

        System.out.print("Müsait mi (true/false): ");
        boolean isAvailable = scanner.nextBoolean();

        // Create Room object
        RoomServiceModel room = new RoomServiceModel(roomNumber, capacity, features, isAvailable);

        // Call RoomService to add the room
        try {
            roomService.RoomAdd(room);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Oda başarıyla oluşturulmuştur.");
    }

    private void listRooms() {
        List<RoomServiceModel> rooms = null;
        try {
            rooms = roomService.getAllRooms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("***** Odalar *****");
        for (RoomServiceModel room : rooms) {
            System.out.println(room);
        }
        System.out.println("*************************");
    }


}


