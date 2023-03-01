package HospitalProject;
import java.util.LinkedList;

public class HastaService implements Service {
    static LinkedList<Hasta> hastaListesi = new LinkedList<>();
    static LinkedList<Durum> hastaDurumListesi = new LinkedList<>();

    private static HastaneService hastaneService = new HastaneService();


    @Override
    public void add() {

        System.out.println("Eklemek istediginiz hastanin ADINI giriniz");
        String hastaAdi = HastaneService.scanner.next();
        System.out.println("Eklemek istediginiz hastanin SOYADINI giriniz");
        String hastaSoyadi = HastaneService.scanner.next();
        HastaneService.scanner.nextLine();

        int hastaId = hastaListesi.getLast().getHastaID() + 111;

        String durum;
        String aciliyet;

        do {
            System.out.println("Hastanin Durumu:\n\t=> Allerji\n\t=> Bas agrisi\n\t=> Diabet\n\t=> Soguk alginligi\n\t=> Migren\n\t" +
                    "=> Kalp hastaliklari");
            durum = HastaneService.scanner.nextLine().toLowerCase();


        }while (hastaDurumuBul(durum).getAciliyet().equalsIgnoreCase("eslesmedi"));
        aciliyet = hastaDurumuBul(durum).getAciliyet();

        Durum hastaDurum = new Durum(hastaAdi, hastaSoyadi, hastaId, durum, aciliyet);
        Hasta hasta = new Hasta(hastaAdi, hastaSoyadi, hastaId, durum);
        hastaListesi.add(hasta);
        hastaDurumListesi.add(hastaDurum);
        list();
    }

    @Override
    public void remove() {
        list();
        boolean sildiMi = false;
        System.out.println("Silmek Istediginiz Hastanin Id sini giriniz.");
        int hastaId = 0;
        try {
            HastaneService.scanner.nextLine();
            hastaId = HastaneService.scanner.nextInt();

        } catch (Exception e) {
            System.out.println("GECERSİZ ID");
            remove();
        }
        for (Hasta w : hastaListesi) {
            if (w.getHastaID() == hastaId) {
                sildiMi = true;
                hastaListesi.remove(w);
                break;
            }
        }
        if (!sildiMi) {
            System.out.println("SİLMEK İSTEDİGİNİZ HASTA LİSTEMİZDE BULUNMAMAKTADIR!");
        }
        list();
    }

    @Override
    public void search() {
        System.out.println("Bilgilerini Gormek Istediginiz Hastanin ADINI Giriniz");
        String ad = HastaneService.scanner.next();
        System.out.println("Bilgilerini Gormek Istediginiz Hastanin SOYADINI Giriniz");
        String soyAd = HastaneService.scanner.next();
        boolean varMi= false;
        for (Durum w : hastaDurumListesi) {
            if (w.getIsim().equalsIgnoreCase(ad) && w.getSoyIsim().equalsIgnoreCase(soyAd)) {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println("------------------------------------- ARAMIS OLDUGUNUZ HASTA ---------------------------------");
                System.out.printf("%-10s | %-10s | %-15s | %-20s | %-15s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "AKTUEL DURUM", "ACİLİYET DURUMU");
                System.out.printf("%-10s | %-10s | %-15s | %-20s | %-15s\n", w.getHastaID(), w.getIsim(), w.getSoyIsim(), w.getAktuelDurum(), w.getAciliyet());
                System.out.println("----------------------------------------------------------------------------------------------");
                varMi=true;
                break;
            }
        }
        if (!varMi){
            System.out.println("BU İSİME AİT HASTA BULUNMAMAKTADIR!");
        }
    }

    public void firstList() {
        for (String w : hastaneService.durumlar) {
            hastaListesi.add(hastaBul(w));
            hastaDurumListesi.add(hastaDurumuBul(w.toLowerCase()));
        }
    }

    @Override
    public void list() {

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("----------------------- HASTANEDE BULUNAN HASTALARIMIZ --------------------");
        System.out.printf("%-10s | %-10s | %-15s | %-20s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "HASTA DURUM");
        System.out.println("---------------------------------------------------------------------------");
        for (Hasta w : hastaListesi) {
            System.out.printf("%-10s | %-10s | %-15s | %-20s\n", w.getHastaID(), w.getIsim(), w.getSoyIsim(), w.getHastaDurumu());
        }
        System.out.println("---------------------------------------------------------------------------");
    }

    public void durumList() {
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("---------------------------- HASTA DURUM LİSTESİ -----------------------------------");
        System.out.printf("%-10s | %-10s | %-15s | %-20s | %-15s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "AKTUEL DURUM", "ACİLİYET DURUMU");
        for (Durum w : hastaDurumListesi) {

            System.out.printf("%-10s | %-10s | %-15s | %-20s | %-15s\n", w.getHastaID(), w.getIsim(), w.getSoyIsim(), w.getAktuelDurum(), w.getAciliyet());
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }

    @Override
    public void menu() {

        int secim = -1;
        while (secim != 0) {
            System.out.println("===========================================");
            System.out.println("LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:\n\t=> 1-HASTA EKLE\n\t=> 2-HASTALARI LISTELE\n\t" +
                    "=> 3-HASTA BUL\n\t=> 4-HASTA SIL\n\t=> 5-HASTA DURUM LISTELE\n\t=> 0-ANAMENU");
            System.out.println("===========================================");
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
                case 5:
                    durumList();
                    break;
                case 0:
                    HastaneService.slowPrint("ANA MENUYE YONLENİDİRİLİYORSUNUZ....\n", 25);
                    break;
                default:
                    System.out.println("HATALI GIRIS, TEKRAR DENEYINIZ!");
            }
        }
    }

    public static Hasta hastaBul(String aktuelDurum) {
        Hasta hasta = new Hasta();

        for (int i = 0; i < hastaneService.durumlar.length; i++) {
            if (hastaneService.durumlar[i].equalsIgnoreCase(aktuelDurum)) {
                hasta.setHastaDurumu(hastaneService.durumlar[i]);
                hasta.setHastaID(hastaneService.hastaIDleri[i]);
                hasta.setIsim(hastaneService.hastaIsimleri[i]);
                hasta.setSoyIsim(hastaneService.hastaSoyIsimleri[i]);
            }
        }
        return hasta;
    }

    public static Durum hastaDurumuBul(String aktuelDurum) {


        Durum hastaDurum = new Durum();
        for (int i = 0; i < hastaneService.durumlar.length; i++) {
            if (hastaneService.durumlar[i].equalsIgnoreCase(aktuelDurum)) {
                hastaDurum.setAktuelDurum(hastaneService.durumlar[i]);
                hastaDurum.setHastaID(hastaneService.hastaIDleri[i]);
                hastaDurum.setIsim(hastaneService.hastaIsimleri[i]);
                hastaDurum.setSoyIsim(hastaneService.hastaSoyIsimleri[i]);
            }
        }
        switch (aktuelDurum) {
            case "allerji":
                hastaDurum.setAciliyet("Kirmizi Alan");
                break;
            case "bas agrisi":
                hastaDurum.setAciliyet("Yesil Alan");
                break;
            case "diabet":
                hastaDurum.setAciliyet("Yesil Alan");
                break;
            case "soguk alginligi":
                hastaDurum.setAciliyet("Sari Alan");
                break;
            case "migren":
                hastaDurum.setAciliyet("Sari Alan");
                break;
            case "kalp hastaliklari":
                hastaDurum.setAciliyet("Kirmizi Alan");
                break;
            default:
                System.out.println("Gecerli bir durum degil!");
                hastaDurum.setAciliyet("eslesmedi");
        }
        return hastaDurum;
    }
}