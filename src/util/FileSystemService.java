package util;

import data.Record;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileSystemService {

    private static final String NAME_OF_FILE = "C:\\Java47\\PasswordReminder\\passRem.txt";

    private File createFile() {
        File f = new File(NAME_OF_FILE);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    private List<Record> readFromFile() {
        List<Record> lr = new LinkedList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(createFile()))) {
            Record r;
            while ((r = (Record) ois.readObject()) != null) {
                lr.add(r);
            }
        } catch (Exception e) {
            System.out.println("read From File");
        }
        return lr;
    }

    private void writeRecordsToOutputStream(Record r) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(createFile()))) {
            List<Record> lr = readFromFile();
            for (Record rec : lr) {
                oos.writeObject(rec);
            }
            oos.writeObject(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToOutputStream(Record r) {
        createFile();
        writeRecordsToOutputStream(r);
    }

    public void showFromFile() {
        List<Record> lir = readFromFile();
        for (Record rec : lir) {
            System.out.println(rec);
        }
    }

    public void chooseAddressFromFile(String s) {
        List<Record> lir = readFromFile();
        for (Record rec : lir) {
            if (rec.getAddress().contains(s)) {
                System.out.println(rec);
            }
        }
    }
}
