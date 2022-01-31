package util;

import data.Record;
import main.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static constant.Constants.*;

public class Util {

    //    public static Record[] records = new Record[0];
    public static List<Record> records = new ArrayList<>();
    public static List<String> randomSymbolResults = new CopyOnWriteArrayList<>();
    public static File f = new File("passRem.txt");

    public static Scanner createScanner() {
        return new Scanner(System.in);
    }

    public static void incorrectAnswer(int i) {
        if (i == 3) {
            System.out.println("Foolish user! Train your brain!");
            System.exit(0);
        }
        System.out.println("Incorrect answer. Repeat, please");
    }

    public static Record createRecord() {
        Record r = new Record();
        r.setDate(new Date(System.currentTimeMillis()));
        System.out.println(ADDRESS);
        r.setAddress(createScanner().nextLine());
        System.out.println(LOGIN);
        r.setLogin(createScanner().nextLine());

        int i = 0;
        while (true) {
            System.out.println(PASSWORD_QUESTION);
            String answer = createScanner().nextLine();
            if (YES.equalsIgnoreCase(String.valueOf(answer.charAt(0)))) {  // ????? Why?
                System.out.println(NUMBER_OF_PASSWORD_SYMBOLS);
                r.setPassword(getRandomNumber(createScanner().nextInt()));
                break;
            } else if (NO.equalsIgnoreCase(String.valueOf(answer.charAt(0)))) {  // ????? Why?
                System.out.println(PASSWORD);
                r.setPassword(createScanner().nextLine());
                break;
            } else {
                incorrectAnswer(i);
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
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getAddress().contains(address)) {
                arrStr.add(records.get(i));
            }
        }
        if (arrStr.size() != 0) {
            System.out.println("Choosing records:");
            for (Record rec : arrStr) {
                System.out.println(rec);
            }
        } else {
            System.out.println("Records no chosen");
        }
    }

    public static void chooseRecordFromTextFileProperties(String address) {
        List<String> arrStr = new ArrayList<>();
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream("RecordsProperties.txt")) {
            property.load(fis);
            int i = 0;
            while (property.containsKey("Record" + i)) {
                i++;
            }
            for (int j = 0; j < i; j++) {
                if (property.getProperty("Record" + j).
                        substring(property.getProperty("Record" + j).indexOf("Address is") +
                                        "Address is".length(),
                                property.getProperty("Record" + j).indexOf("Login")).
                        contains(address)) {
                    arrStr.add(property.getProperty("Record" + j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (arrStr.size() != 0) {
            System.out.println("Choosing records:");
            for (String rec : arrStr) {
                System.out.println(rec);
            }
        } else {
            System.out.println("Records no chosen");
        }
    }

    public static void saveToTextFileStreams() {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Record r;
            List<Record> rc = new LinkedList<>();
            while ((r = (Record) ois.readObject()) != null) {
                rc.add(r);
            }
            if (rc.size() != 0) {
                for (Record value : rc) {
                    oos.writeObject(value);
                }
            }
            for (Record record : records) {
                oos.writeObject(record);
            }
        } catch (FileNotFoundException | EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveToTextFileProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream("RecordsProperties.txt")) {
            property.load(fis);
            int i = 0;
            while (property.containsKey("Record" + i)) {
                i++;
            }
            property.setProperty("Record" + i, records.get(records.size() - 1).toString());
            property.store(new FileOutputStream("RecordsProperties.txt"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToTextFile() {
        Map<String, String> mapRecords = new LinkedHashMap<>();
        for (int i = 0; i < records.size(); i++) {
            mapRecords.put("Record" + i, records.get(i).toString());
        }
        try (FileOutputStream fos = new FileOutputStream("RecordsProperties.txt")) {
            for (String key : mapRecords.keySet()) {
                fos.write((key + " = " + mapRecords.get(key) + "\n").getBytes(StandardCharsets.UTF_8)); // ???
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAllRecordsFromTextFileProperties() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("RecordsProperties.txt")) {
            prop.load(fis);
            int i = 0;
            while (prop.containsKey("Record" + i)) {
                System.out.println(prop.getProperty("Record" + i));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
