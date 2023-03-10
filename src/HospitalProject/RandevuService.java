package HospitalProject;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RandevuService {
    public TreeMap<String, LinkedList<LocalTime>> haftalikRandevuTable() {

        HashMap<String, LinkedList<LocalTime>> hashHaftalikRandevuTable = new HashMap<>();

        LinkedList<LocalTime> saatler = new LinkedList<>();

        saatler.add(LocalTime.of(9, 00));
        saatler.add(LocalTime.of(10, 00));
        saatler.add(LocalTime.of(11, 00));
        saatler.add(LocalTime.of(12, 00));
        saatler.add(LocalTime.of(14, 00));
        saatler.add(LocalTime.of(15, 00));
        saatler.add(LocalTime.of(16, 00));

        hashHaftalikRandevuTable.put("1-Pazartesi", saatler);
        hashHaftalikRandevuTable.put("2-Sali", saatler);
        hashHaftalikRandevuTable.put("3-Carsamba", saatler);
        hashHaftalikRandevuTable.put("4-Persembe", saatler);
        hashHaftalikRandevuTable.put("5-Cuma", saatler);

        TreeMap<String, LinkedList<LocalTime>> treeHaftalikRandevuTable = new TreeMap<>(hashHaftalikRandevuTable);
        return treeHaftalikRandevuTable;
    }

    public void haftalikRandevuTableList(TreeMap<String, LinkedList<LocalTime>> treeMap) {
        Scanner inp = new Scanner(System.in);
        System.out.println("Lutfen Randevu Almak Istediginiz Bolumun Ismini Giriniz: \n\t=> Alerji\n\t=> Noroloji\n\t=> Genel Cerrahi\n\t=> Cocuk Hastaliklari\n\t=> Dahiliye\n\t=> Kardioloji");
        String select = inp.nextLine().toLowerCase();
        Set<Map.Entry<String, LinkedList<LocalTime>>> myEntrys = treeMap.entrySet();

        switch (select) {
            case "alerji": case "noroloji": case "genel cerrahi": case "cocuk hastaliklari": case "dahiliye": case "kardioloji":
                System.out.println("=====================================================================");
                System.out.println("===SECMIS OLDUGUNUZ BOLUMDEN RANDEVU ALABILECEGINIZ GUN VE SAATLER===");
                for (Map.Entry<String, LinkedList<LocalTime>> w : myEntrys) {
                    System.out.printf("%-12s | %-100s\n", w.getKey(), saatListele(w.getValue()));
                }
                System.out.println("=====================================================================");
                break;
            default:
                System.out.println("ARADI??INIZ B??L??M BULUNMAMAKTADIR!");
                haftalikRandevuTableList(treeMap);
        }

        boolean devamMi = true;
        String gun = "";
        LocalTime saat = null;
        while (devamMi) {
            devamMi = false;
            System.out.println("L??tfen randevu almak istedi??iniz g??n?? giriniz:");
            gun = inp.next();
            inp.nextLine();
            System.out.println("L??tfen randevu almak istedi??iniz saati giriniz:\n\t=> DIKKAT! Saati \"hh:mm\" format??nda giriniz!");
            try {
                saat = LocalTime.parse(inp.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception e) {
                System.err.print("Ge??ersiz saat format??!\n\t=> Saati \"hh:mm\" format??nda giriniz!");
                System.out.println();
                devamMi = true;
                continue;
            }
            for (Map.Entry<String, LinkedList<LocalTime>> w : myEntrys) {

                if (w.getKey().replaceAll("[\\d\\p{Punct}]", "").equalsIgnoreCase(gun) && w.getValue().contains(saat)) {
                    devamMi = false;
                    break;
                }
                if (!(w.getKey().equalsIgnoreCase(gun) && w.getValue().contains(saat))) {

                    devamMi = true;
                }
            }
            if (devamMi) {
                System.out.println("RANDEVU ALMAK ISTEDIGINIZ GUN VEYA SAAT RANDEVU LISTESINDE BULUNMAMAKTADIR!\n" +
                        "L??TFEN RANDEVU L??STES??NDE BEL??RT??LEN G??N VE SAATLERE G??RE SEC??M YAPINIZ!");
                System.out.println();
            }

        }

        System.out.println("------------------------------------------");
        System.out.println("-- RANDEVUNUZ BA??ARIYLA OLU??TURULMU??TUR --");
        System.out.println("--------- RANDEVU BILGILERINIZ -----------");

        for (Map.Entry<String, LinkedList<LocalTime>> w : myEntrys) {
            if (w.getKey().replaceAll("[\\d\\p{Punct}]","").equalsIgnoreCase(gun) && w.getValue().contains(saat)) {
                System.out.printf("%-10s | %-10s | %-10s\n","GUN", "SAAT", "BOLUM");
                System.out.printf("%-10s | %-10s | %-10s\n",w.getKey().replaceAll("[\\d\\p{Punct}]",""), w.getValue().get(w.getValue().indexOf(saat)), select.toUpperCase());
            }
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("DIKKAT! RANDEVU SAATINDEN 30 DAKIKA ONCE HASTANEDE BULUNMANIZ GEREKMEKTEDIR!");
        System.out.println("----------------------------------------------------------------------------");
        HastaneService.slowPrint("\033[33mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
    }

    public String saatListele(LinkedList<LocalTime> list){

        String saat = "";
        for (LocalTime w : list){
            saat += w+ " ";
        }
        return saat;
    }
}