package Controller;

import service.CustomerServiceModel;
import service.ReservationService;
import service.ReservationServiceModel;
import service.ReservationServiceRequest;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ReservationControl {

    private final ReservationService reservationService;


    public ReservationControl(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    public void reservationOperations(Scanner scanner) {

        while (true) {

            printReservationMenu();

            int choice = getUserChoice(scanner);

            switch (choice) {

                case 1:
                    addCReservation(scanner);
                    break;
                case 2:
                    getReservationByCustomerId(scanner);
                    break;
                case 3:
                    updateReservation(scanner);
                    break;
                case 4:
                    checkInDate(scanner);
                    break;
                case  5:
                    checkOutDate(scanner);
                    break;
                case 6:
                    listReservation();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Geçerli bir değer giriniz. 1 ve 7 arasında !!!");
                    break;


            }
        }
    }


    private void printReservationMenu() {
        System.out.println("*****Rezervasyon İşlemleri*****");
        System.out.println("1.Rezervasyon Oluştur");
        System.out.println("2.Rezervasyon Sorgula");
        System.out.println("3.Rezervasyon Güncelle");
        System.out.println("4.Rezervasyon Girişi Chech Et");
        System.out.println("5.Rezervasyon Çıkış Chechk Et");
        System.out.println("6.Bütün Rezervasyonları Listele");
    }

    public int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private void addCReservation(Scanner scanner) {

        System.out.println("Rezervasyon Bilgilerini Giriniz");

        System.out.println("Müşteri ID'si: ");
        int customerID = scanner.nextInt();


        System.out.println("Oda numarası giriniz :");
        int roomNUMBER = scanner.nextInt();


        try {
            System.out.println("Giriş yapacağınız tarihi giriniz (örnek format: dd.MM.yyyy): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate inDate = LocalDate.parse(scanner.next(), formatter);

            System.out.println("Çıkış yapacağınız tarihi giriniz (örnek format: dd.MM.yyyy): ");
            LocalDate outDate = LocalDate.parse(scanner.next(), formatter);

            reservationService.addReservation(customerID, inDate, outDate);
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Hatalı tarih formatı! Lütfen dd.MM.yyyy formatında bir tarih giriniz.");
            // Hata durumunda yapılacak işlemler
        } catch (SQLException e) {
            throw new RuntimeException("Rezervasyon eklenirken bir hata oluştu: " + e.getMessage(), e);
        }


    }


    public void getReservationByCustomerId(Scanner scanner) {
        System.out.print("Müşteri ID'sini giriniz: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        try {
            List<ReservationServiceModel> reservations = reservationService.getReservationsByCustomerId(customerId);

            if (!reservations.isEmpty()) {
                System.out.println("Rezervasyon Bilgileri:");
                for (ReservationServiceModel reservation : reservations) {
                    System.out.println(reservation);
                }
            } else {
                System.out.println("Bu müşteri için rezervasyon bulunamadı!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Rezervasyon bilgileri alınırken bir hata oluştu: " + e.getMessage(), e);
        }



}
    public void updateReservation(Scanner scanner) {
        try {
            System.out.print("Güncellenecek rezervasyonun ID'sini giriniz: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Satır sonu karakterini temizle

            // Rezervasyon servisi üzerinden mevcut rezervasyon bilgisini al
            ReservationServiceModel existingReservation = reservationService.getReservation(reservationId);

            // Eğer rezervasyon bulunamazsa
            if (existingReservation == null) {
                System.out.println("Bu ID'ye ait bir rezervasyon bulunamadı.");
                return;
            }

            // Yeni oda numarasını al
            System.out.print("Yeni oda numarasını giriniz: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine(); // Satır sonu karakterini temizle

            System.out.print("Yeni check-in tarihini (gg.aa.yyyy) giriniz: ");
            String inDateStr = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate inDate = LocalDate.parse(inDateStr, formatter);

            System.out.print("Yeni check-out tarihini (gg.aa.yyyy) giriniz: ");
            String outDateStr = scanner.nextLine();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate outDate = LocalDate.parse(outDateStr, formatter1);

            // Yeni rezervasyon bilgilerini oluştur
            ReservationServiceModel newReservation = new ReservationServiceModel();
            newReservation.setReservationId(reservationId);
            newReservation.setRoomNumber(roomNumber);
            newReservation.setInDate(inDate);
            newReservation.setOutDate(outDate);

            // Rezervasyon servisindeki güncelleme metodunu çağır
            reservationService.updateReservation(newReservation);

        } catch (SQLException e) {
            throw new RuntimeException("Rezervasyon güncellenirken bir hata oluştu: " + e.getMessage(), e);
        }
    }

    private void checkInDate(Scanner scanner) {
        try {
            System.out.print("Rezervasyon ID'sini giriniz: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Rezervasyonun var olup olmadığını kontrol et
            ReservationServiceModel existingReservation = reservationService.getReservation(reservationId);
            if (existingReservation == null) {
                System.out.println("Bu rezervasyon ID'sine sahip bir rezervasyon bulunamadı.");
                return;
            }

            System.out.print("Check-in tarihini (GG.AA.YYYY) formatında giriniz: ");
            String checkInDateStr = scanner.nextLine();

            // Tarih formatını belirleyip parse işlemi yap
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate checkInDate = LocalDate.parse(checkInDateStr, formatter);

            reservationService.checkInDate(reservationId, checkInDate);
            System.out.println("Check-in işlemi başarıyla gerçekleştirildi.");
        } catch (DateTimeParseException e) {
            System.out.println("Geçersiz tarih formatı. GG.AA.YYYY (örn. 30.09.2024) formatında giriniz.");
        } catch (SQLException e) {
            throw new RuntimeException("Check-in işlemi sırasında bir hata oluştu: " + e.getMessage(), e);
        }
    }

    private void checkOutDate(Scanner scanner) {
        try {
            System.out.print("Rezervasyon ID'sini giriniz: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Rezervasyonun var olup olmadığını kontrol et
            ReservationServiceModel existingReservation = reservationService.getReservation(reservationId);
            if (existingReservation == null) {
                System.out.println("Bu rezervasyon ID'sine sahip bir rezervasyon bulunamadı.");
                return;
            }

            System.out.print("Check-out tarihini (GG.AA.YYYY) formatında giriniz: ");
            String checkOutDateStr = scanner.nextLine();

            // Tarih formatını belirleyip parse işlemi yap
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr, formatter);

            // Check-out işlemini gerçekleştir
            reservationService.checkOutDate(reservationId, checkOutDate);
            System.out.println("Check-out işlemi başarıyla gerçekleştirildi.");
        } catch (DateTimeParseException e) {
            System.out.println("Geçersiz tarih formatı. GG.AA.YYYY (örn. 30.09.2024) formatında giriniz.");
        } catch (SQLException e) {
            throw new RuntimeException("Check-out işlemi sırasında bir hata oluştu: " + e.getMessage(), e);
        }
    }

    private void listReservation() {
        List<ReservationServiceModel> reservation = null;
        try {
            reservation = reservationService.getAllReservation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("***** Odalar *****");
        for (ReservationServiceModel reservations : reservation) {
            System.out.println(reservations);
        }
        System.out.println("*************************");
    }



}


