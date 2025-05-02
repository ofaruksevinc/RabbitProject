import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Orman boyutunu girin (4, 8, veya 16):");
        int size = 0;
        try {
             size = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz giriş. Lütfen bir sayı girin.");
            scanner.close();
            return;
        }

        if (size != 4 && size != 8 && size != 16) {
            System.out.println("Geçersiz boyut. Sadece 4, 8, veya 16 olabilir.");
            scanner.close();
            return;
        }

        System.out.println(size + "x" + size + " boyutunda orman oluşturuldu.");

        Grid grid = new Grid(size);
        Rabbit rabbit = new Rabbit(size);

        System.out.println("Başlangıç durumu:");
        grid.display(rabbit.getPosition());
        System.out.println("Tavşan ('T') Başlangıç Yönü: " + rabbit.getDirection());
        System.out.println("Hedef: Tavşan Deliği ('H')");

        System.out.println("Senaryoyu girin (örn: N,N,L,J,N,N,İ,P,J):");
        String scenario = scanner.nextLine().toUpperCase(); 
        String[] moves = scenario.split(",");

        boolean alive = true;
        boolean success = false;

        for (String move : moves) {
            if (move.isEmpty()) continue; 

            char command = move.charAt(0);
            Position currentPos = rabbit.getPosition();
            Position nextPos = null;

            System.out.println("\nKomut: " + command);

            switch (command) {
                case 'N': // İleri 
                    nextPos = rabbit.getNextPositionForward();
                    break;
                case 'P': // Geri 
                    nextPos = rabbit.getNextPositionBackward();
                    break;
                case 'R': // Sağ 
                    rabbit.turnRight();
                    System.out.println("Tavşan sağa döndü. Yeni Yön: " + rabbit.getDirection());
                    grid.display(rabbit.getPosition());
                    continue; 
                case 'L': // Sol 
                    rabbit.turnLeft();
                    System.out.println("Tavşan sola döndü. Yeni Yön: " + rabbit.getDirection());
                    grid.display(rabbit.getPosition()); 
                    continue; 
                case 'J': // Zıpla 
                case 'İ': // Eğil 
                    nextPos = rabbit.getNextPositionForward();
                    break;
                default:
                    System.out.println("Geçersiz komut: " + command);
                    continue;
            }

            if (nextPos == null || !grid.isValidPosition(nextPos)) {
                System.out.println("Hareket başarısız: Ormanın dışına çıkılamaz.");
                continue; 
            }


            char targetCellContent = grid.getCell(nextPos);
            System.out.println("Hedef Hücre: (" + nextPos.x() + "," + nextPos.y() + ") İçerik: " + targetCellContent);

            boolean moveSuccessful = false;
            switch (targetCellContent) {
                case Grid.WOLF:
                case Grid.FOX:
                    System.out.println("OYUN BİTTİ! Tavşan yakalandı (" + targetCellContent + ").");
                    alive = false;
                    rabbit.move(nextPos); 
                    break;
                case Grid.WIRE:
                    if (command == 'İ') {
                        System.out.println("Tavşan telin altından eğilerek geçti.");
                        moveSuccessful = true;
                    } else {
                        System.out.println("Hareket başarısız: Telin üzerinden geçilemez (Eğilmek 'İ' gerekir).");
                    }
                    break;
                case Grid.FENCE: // Çit
                    if (command == 'J') {
                        System.out.println("Tavşan çitin üzerinden atladı.");
                        moveSuccessful = true;
                    } else {
                        System.out.println("Hareket başarısız: Çitin üzerinden geçilemez (Zıplamak 'J' gerekir).");
                    }
                    break;
                case Grid.HOLE: // Tavşan Deliği
                    if (command == 'N' || command == 'P') {
                         System.out.println("BAŞARILI! Tavşan deliğe ulaştı!");
                         moveSuccessful = true;
                         success = true;
                    } else {
                         System.out.println("Hareket başarısız: Deliğe sadece N veya P ile girilebilir.");
                    }
                    break;
                case Grid.EMPTY:
                     if (command == 'N' || command == 'P') { 
                        System.out.println("Tavşan boş bir alana ilerledi.");
                        moveSuccessful = true;
                    } else {
                         System.out.println("Hareket başarısız: Boş alana sadece N veya P ile girilebilir (J/İ özel engeller içindir).");
                    }
                    break;
                default:
                    System.out.println("Beklenmeyen hücre içeriği: " + targetCellContent);
                    break;
            }

            if (moveSuccessful) {
                rabbit.move(nextPos);
            }

            grid.display(rabbit.getPosition());

            if (!alive || success) {
                break; 
            }
        }

        System.out.println("\n--- Senaryo Sonu ---");
        if (success) {
            System.out.println("SONUÇ: BAŞARILI! Tavşan evine ulaştı.");
        } else if (!alive) {
            System.out.println("SONUÇ: BAŞARISIZ! Tavşan hayatta kalamadı.");
        } else if (rabbit.getPosition().equals(grid.getHolePosition())) {
             // This case should be covered by the loop logic, but as a safeguard:
             System.out.println("SONUÇ: BAŞARILI! Tavşan evine ulaştı (senaryo sonunda kontrol edildi).");
             success = true;
        } else {
            System.out.println("SONUÇ: BAŞARISIZ! Tavşan deliğe ulaşamadı.");
        }
        System.out.println("Tavşanın son konumu: (" + rabbit.getPosition().x() + "," + rabbit.getPosition().y() + ")");
        grid.display(rabbit.getPosition()); 

        scanner.close();
    }
}