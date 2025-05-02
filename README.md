# Tavşan Ormanda Simülasyonu

Bu proje, Java ile yazılmış basit bir konsol tabanlı simülasyon oyunudur. Oyunda amaç, bir tavşanı rastgele engellerle dolu bir orman (ızgara) üzerinde hareket ettirerek güvenli bir şekilde tavşan deliğine ulaştırmaktır.

## Projenin Amacı

Yıldız adındaki tavşan ormanda kaybolmuştur. Amacımız, Yıldız'ı kullanıcının verdiği komutlarla yönlendirerek ormandaki tehlikelerden (Kurt, Tilki) kaçmasını sağlamak, engelleri (Tel, Çit) aşmasını sağlamak ve onu ormanın diğer ucundaki tavşan deliğine sokmaktır.

## Özellikler

*   **Dinamik Orman Boyutu:** Oyun alanı 4x4, 8x8 veya 16x16 boyutlarında bir ızgara olabilir (kullanıcı tarafından belirlenir).
*   **Rastgele Engeller:** Her oyun başlangıcında ormana rastgele olarak en fazla 4'er adet Kurt (K), Tilki (X), Dikenli Tel (W) ve Çit (F) yerleştirilir. Engeller aynı hücreye denk gelmez.
*   **Tavşan Hareketi:** Tavşan (T) başlangıçta sol üst köşede (A8 gibi) Güney yönüne bakacak şekilde başlar.
*   **Komutlar:** Kullanıcı, tavşanın hareketlerini belirleyen bir senaryo girer. Geçerli komutlar:
    *   `N`: İleri (Mevcut yöne doğru bir adım)
    *   `P`: Geri (Mevcut yönün tersine doğru bir adım)
    *   `R`: Sağa Dön (Yönü saat yönünde 90 derece değiştirir)
    *   `L`: Sola Dön (Yönü saat yönünün tersine 90 derece değiştirir)
    *   `J`: Zıpla (Çit 'F' engeli olan hücreye ilerlemek için kullanılır)
    *   `İ`: Eğil (Dikenli Tel 'W' engeli olan hücreye ilerlemek için kullanılır)
*   **Çarpışma Kuralları:**
    *   Kurt (K) veya Tilki (X) olan bir hücreye girilirse tavşan ölür ve oyun biter.
    *   Dikenli Tel (W) olan bir hücreye sadece 'İ' komutu ile girilebilir.
    *   Çit (F) olan bir hücreye sadece 'J' komutu ile girilebilir.
    *   Diğer komutlarla Tel veya Çit'e çarpmak hareketi başarısız yapar ancak tavşan ölmez.
*   **Hedef:** Tavşan Deliği (H) sağ alt köşededir. Deliğe 'N' veya 'P' komutuyla girildiğinde oyun başarıyla tamamlanır.

## Nasıl Derlenir ve Çalıştırılır

Projeyi çalıştırmak için sisteminizde Java Development Kit (JDK) kurulu olmalıdır.

1.  **Derleme:**
    Projenin ana dizinindeyken (bu README dosyasının bulunduğu yer) terminal veya komut istemcisini açın ve aşağıdaki komutu çalıştırın:
    ```bash
    javac src/*.java -d out
    ```
    Bu komut, `src` klasöründeki tüm Java dosyalarını derleyecek ve derlenmiş `.class` dosyalarını `out` adında yeni bir klasöre yerleştirecektir.

2.  **Çalıştırma:**
    Yine projenin ana dizinindeyken aşağıdaki komutu çalıştırın:
    ```bash
    java -cp out Main
    ```
    Bu komut, `out` klasörünü sınıf yolu (classpath) olarak kullanarak `Main` sınıfını çalıştıracaktır.

3.  **Kullanım:**
    *   Program başladığında sizden orman boyutunu (4, 8 veya 16) girmeniz istenir. Sayıyı girip Enter'a basın.
    *   Ardından, tavşanın hareket senaryosunu girmeniz istenir. Komutları büyük harfle ve aralarına virgül koyarak yazın (örneğin: `N,N,L,J,N,N,İ,P,J`). Senaryoyu girip Enter'a basın.
    *   Program, senaryodaki her adımı işleyecek, ızgaranın durumunu gösterecek ve sonunda oyunun sonucunu (başarılı, başarısız/öldü, başarısız/ulaşamadı) bildirecektir.

## Proje Yapısı

*   `src/`: Kaynak kod dosyalarının bulunduğu klasör.
    *   `Main.java`: Oyunun ana giriş noktası, kullanıcı girdilerini alır ve oyun döngüsünü yönetir.
    *   `Grid.java`: Oyun alanını (ızgara), engelleri ve hücre yönetimini temsil eder.
    *   `Rabbit.java`: Tavşanın konumunu, yönünü ve hareket mantığını içerir.
    *   `Position.java`: Izgara üzerindeki (x, y) koordinatlarını temsil eden bir Java Record'udur.
    *   `Direction.java`: NORTH, SOUTH, EAST, WEST yönlerini temsil eden bir Enum'dur.
*   `out/`: Derlenmiş `.class` dosyalarının yer aldığı klasör (derleme sonrası oluşur).
*   `README.md`: Bu dosya. 