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

        // Initialize Grid and Rabbit
        Grid grid = new Grid(size);
        Rabbit rabbit = new Rabbit(size);

        // Display initial state
        System.out.println("Başlangıç durumu:");
        grid.display(rabbit.getPosition());
        System.out.println("Tavşan ('T') Başlangıç Yönü: " + rabbit.getDirection());
        System.out.println("Hedef: Tavşan Deliği ('H')");

        // Get scenario input
        System.out.println("Senaryoyu girin (örn: N,N,L,J,N,N,İ,P,J):");
        String scenario = scanner.nextLine().toUpperCase(); 
        String[] moves = scenario.split(",");

        // Process scenario
        boolean alive = true;
        boolean success = false;

        for (String move : moves) {
            if (move.isEmpty()) continue; // Skip empty parts if input is like N,,L

            char command = move.charAt(0);
            Position currentPos = rabbit.getPosition();
            Position nextPos = null;

            System.out.println("\nKomut: " + command);

            switch (command) {
                case 'N': // İleri (Forward)
                    nextPos = rabbit.getNextPositionForward();
                    break;
                case 'P': // Geri (Backward)
                    nextPos = rabbit.getNextPositionBackward();
                    break;
                case 'R': // Sağ (Right)
                    rabbit.turnRight();
                    System.out.println("Tavşan sağa döndü. Yeni Yön: " + rabbit.getDirection());
                    grid.display(rabbit.getPosition()); // Show grid after turn
                    continue; // Turn doesn't involve moving to a new cell
                case 'L': // Sol (Left)
                    rabbit.turnLeft();
                    System.out.println("Tavşan sola döndü. Yeni Yön: " + rabbit.getDirection());
                    grid.display(rabbit.getPosition()); // Show grid after turn
                    continue; // Turn doesn't involve moving to a new cell
                case 'J': // Zıpla (Jump)
                case 'İ': // Eğil (Duck - Using 'I' for ASCII compatibility)
                    nextPos = rabbit.getNextPositionForward(); // Jump/Duck is always forward
                    break;
                default:
                    System.out.println("Geçersiz komut: " + command);
                    continue;
            }

            // Check if the calculated next position is valid
            if (nextPos == null || !grid.isValidPosition(nextPos)) {
                System.out.println("Hareket başarısız: Ormanın dışına çıkılamaz.");
                continue; // Skip to the next move
            }

            // Check the content of the target cell
            char targetCellContent = grid.getCell(nextPos);
            System.out.println("Hedef Hücre: (" + nextPos.x() + "," + nextPos.y() + ") İçerik: " + targetCellContent);

            boolean moveSuccessful = false;
            switch (targetCellContent) {
                case Grid.WOLF:
                case Grid.FOX:
                    System.out.println("OYUN BİTTİ! Tavşan yakalandı (" + targetCellContent + ").");
                    alive = false;
                    rabbit.move(nextPos); // Move rabbit to the death spot for visualization
                    break;
                case Grid.WIRE: // Dikenli Tel
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
                    if (command == 'N' || command == 'P') { // Can only enter hole by moving forward/backward
                         System.out.println("BAŞARILI! Tavşan deliğe ulaştı!");
                         moveSuccessful = true;
                         success = true;
                    } else {
                         System.out.println("Hareket başarısız: Deliğe sadece N veya P ile girilebilir.");
                    }
                    break;
                case Grid.EMPTY:
                     if (command == 'N' || command == 'P') { // Can only move to empty with N/P
                        System.out.println("Tavşan boş bir alana ilerledi.");
                        moveSuccessful = true;
                    } else {
                         System.out.println("Hareket başarısız: Boş alana sadece N veya P ile girilebilir (J/İ özel engeller içindir).");
                    }
                    break;
                default:
                    System.out.println("Beklenmeyen hücre içeriği: " + targetCellContent);
                    // Treat as blocked for safety?
                    break;
            }

            if (moveSuccessful) {
                rabbit.move(nextPos);
            }

            // Display grid after attempting the move
            grid.display(rabbit.getPosition());

            if (!alive || success) {
                break; // End simulation if rabbit died or reached the hole
            }
        }

        // Final result check (in case the scenario ends without reaching the hole or dying)
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
        grid.display(rabbit.getPosition()); // Show final grid state

        scanner.close();
    }
}