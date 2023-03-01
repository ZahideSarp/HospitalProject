package HospitalProject;
import java.util.LinkedList;

public class DoktorService implements Service {
    private static HastaneService hastaneService = new HastaneService();
    LinkedList<Doktor> doktorList = new LinkedList<>();

    @Override
    public void add() {
        System.out.println("Eklemek istediginiz doktor ismini giriniz");
        String doktorAdi = HastaneService.scanner.next();
        System.out.println("Eklemek istediginiz doktor soyadini giriniz");
        String doktorSoyadi = HastaneService.scanner.next();
        System.out.println("Eklemek İstediginiz Doktor Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t=> Genel Cerrah\n\t" +
                "=> Cocuk Doktoru\n\t=> Dahiliye\n\t=> Kardiolog");
        String doktorUnvan = HastaneService.scanner.next();
        Doktor doktor = new Doktor(doktorAdi, doktorSoyadi, doktorUnvan);
        doktorList.add(doktor);
        list();
    }

    @Override
    public void remove() {
        list();
        System.out.println("Silmek Istediginiz Doktor ismi giriniz.");
        String doktorIsim = HastaneService.scanner.next();
        System.out.println("Silmek istediginiz Doktor soyismi giriniz");
        String doktorSoyisim = HastaneService.scanner.next();
        boolean sildiMi = false;
        for (Doktor w : doktorList) {

            if (w.getIsim().equalsIgnoreCase(doktorIsim) && w.getSoyIsim().equalsIgnoreCase(doktorSoyisim)) {
                sildiMi = true;
                doktorList.remove(w);
                break;
            }
        }
        if (!sildiMi) {
            System.out.println("SİLMEK İSTEDİGİNİZ DOKTOR LİSTEMİZDE BULUNMAMAKTADIR");
        }
        list();
    }

    @Override
    public void search() {
        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");
        HastaneService.scanner.nextLine();
        String unvan = HastaneService.scanner.nextLine();

        System.out.println("------------------------------------------------------");
        System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
        System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
        System.out.println("------------------------------------------------------");
        boolean varMi = false;

        for (Doktor w : doktorList) {
            if (w.getUnvan().equalsIgnoreCase(unvan)) {
                System.out.printf("%-13s | %-15s | %-15s\n", w.getIsim(), w.getSoyIsim(), w.getUnvan());
                varMi = true;
            }
        }
        if (!varMi) {
            System.out.println("BU UNVANA AİT DOKTOR BULUNMAMAKTADIR");
            HastaneService.slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
        }
        System.out.println("------------------------------------------------------");
    }

    public void firstList() {
        for (String w : hastaneService.unvanlar) {
            doktorList.add(doktorBul(w));
        }
    }

    @Override
    public void list() {
        System.out.println("------------------------------------------------------");
        System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
        System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
        System.out.println("------------------------------------------------------");
        for (Doktor w : doktorList) {
            System.out.printf("%-13s | %-15s | %-15s\n", w.getIsim(), w.getSoyIsim(), w.getUnvan());
        }
        System.out.println("------------------------------------------------------");
    }

    @Override
    public void menu() {
        int secim = -1;
        while (secim != 0) {
            System.out.println("=========================================");
            System.out.println("LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:\n\t=> 1-DOKTOR EKLE\n\t=> 2-DOKTORLARI LISTELE\n\t" +
                    "=> 3-UNVANDAN DOKTOR BULMA\n\t=> 4-DOKTOR SIL\n\t=> 0-ANAMENU");
            System.out.println("=========================================");
            try {
                secim = HastaneService.scanner.nextInt();
            } catch (Exception e) {
                HastaneService.scanner.nextLine();
                System.out.println("LUTFEN SIZE SUNULAN SECENEKLERIN DISINDA VERI GIRISI YAPMAYINIZ!");
                continue;
            }
            switch (secim) {
                case 1:
                    add();
                    break;
                case 2:
                    list();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    remove();
                    break;
                case 0:
                    HastaneService.slowPrint("ANA MENUYE YONLENİDİRİLİYORSUNUZ....\n", 20);
                    break;
                default:
                    System.out.println("HATALI GIRIS, TEKRAR DENEYINIZ!");

            }

        }

    }

    public static Doktor doktorBul(String unvan) {

        Doktor doktor = new Doktor();

        for (int i = 0; i < hastaneService.unvanlar.length; i++) {

            if (hastaneService.unvanlar[i].equalsIgnoreCase(unvan)) {

                doktor.setIsim(hastaneService.doctorIsimleri[i]);

                doktor.setSoyIsim(hastaneService.doctorSoyIsimleri[i]);

                doktor.setUnvan(hastaneService.unvanlar[i]);
            }
        }
        return doktor;
    }
}




