package util;

import data.Record;
import main.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static constant.Constants.*;

public class Util {

//    public static Record[] records = new Record[0];
    public static List<Record> records = new ArrayList<>();
    public static List<String> randomSymbolResults = new CopyOnWriteArrayList<>();

    public static Scanner createScanner() {
        return new Scanner(System.in);
    }

    public static Record createRecord() {
        Record r = new Record();
        System.out.println(ADDRESS);
        r.setAddress(createScanner().nextLine());
        System.out.println(LOGIN);
        r.setLogin(createScanner().nextLine());

        int i = 0;
        while (true) {
            System.out.println(PASSWORD_QUESTION);
            String answer = createScanner().nextLine();
            if(YES.equalsIgnoreCase(String.valueOf(answer.charAt(0)))) {  // ????? Why?
                System.out.println(NUMBER_OF_PASSWORD_SYMBOLS);
                r.setPassword(getRandomNumber(createScanner().nextInt()));
                break;
            } else  if (NO.equalsIgnoreCase(String.valueOf(answer.charAt(0)))) {  // ????? Why?
                System.out.println(PASSWORD);
                r.setPassword(createScanner().nextLine());
                break;
            } else {
                if(i == 3) {
                    System.out.println("Foolish user! Train your brain!");
                    System.exit(0);
                }
                System.out.println("Incorrect answer. Repeat, please");
                i++;
            }
        }
        return r;
    }

    public static void addRecord(Record rec) {
        records.add(rec);
//        Record[] tmp = new Record[records.length + 1];
//        System.arraycopy(records, 0, tmp, 0, records.length);
//        tmp[tmp.length - 1] = rec;
//        records = tmp;
    }

    public static void printRecords() {
        for (Record r : records) {
            System.out.println(r);
        }
    }

    public static String getRandomSymbol() {
        List<Thread> threads = new LinkedList<>();
        for (Integer i = 0; i < 10; i++) {
            threads.add(new MyRandomThread(i.toString()));
        }
        threads.add(new MyRandomThread("_"));
        threads.add(new MyRandomThread("$"));
        char c[] = new char[1];
        char cc[] = new char[1];
        for (c[0] = 65, cc[0] = 97; c[0] <= 90 && cc[0] <= 122; c[0]++, cc[0]++) {
            threads.add(new MyRandomThread(new String(c)));
            threads.add(new MyRandomThread(new String(cc)));
        }
        for (Thread t : threads) {
            t.start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        String result = randomSymbolResults.get(random.nextInt(63));
        randomSymbolResults.clear();
        return result;
    }

    public static String getRandomNumber(int quantity) {
        StringBuilder str = new StringBuilder(quantity);
        for (int i = 0; i < quantity; i++) {
            str.append(getRandomSymbol());
        }
        System.out.println("Your password: " + str);
        return str.toString();
    }

    public static void chooseRecord(String address) {
        List<Record> arrStr = new ArrayList<>();
        for(int i = 0; i < records.size(); i++) {
            if(records.get(i).getAddress().contains(address)) {
                arrStr.add(records.get(i));
            }
        }
        if(arrStr.size() != 0) {
            System.out.println("Choosing records:");
            for(Record rec : arrStr) {
                System.out.println(rec);
            }
        } else {
            System.out.println("Records no chosen");
        }
    }

    public static void saveToTextFile() {
        Properties property = new Properties();
        Map<String, String> mapRecords = new LinkedHashMap<>();
        try(FileOutputStream fos = new FileOutputStream("RecordsProperties.txt")) {
            for(int i = 0; i < records.size(); i++) {
                mapRecords.put("Record"+ i, records.get(i).toString());
            }
            for (Integer i = 0; i < mapRecords.size(); i++) {
                property.setProperty("Record"+i,
                       mapRecords.get("Record"+i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
