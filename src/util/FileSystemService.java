package util;

import data.Record;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileSystemService {

    private static final String NAME_OF_FILE = "C:\\Java47\\PasswordReminder\\passRem.txt";

    private void createFile() {
        File f = new File(NAME_OF_FILE);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Record> readFromFile() {
        List<Record> lr = new LinkedList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NAME_OF_FILE))) {
            Record r;
            if(ois.available() != 0) {
                while ((r = (Record) ois.readObject()) != null) {
                    lr.add(r);
                }
            } else {}
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lr;
    }

    private void writeRecordsToOutputStream(Record r) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NAME_OF_FILE))) {
            if(readFromFile().size() != 0) {
                List<Record> lr = readFromFile();
                for (int i = 0; i < lr.size(); i++) {
                    oos.writeObject(lr.get(i));
                }
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
        for (int i = 0; i < lir.size(); i++) {
            System.out.println(lir.get(i));
        }
    }

    public void chooseAddressFromFile(String s){
        List<Record> lir = readFromFile();
        for (int i = 0; i < lir.size(); i++) {
            if (lir.get(i).getAddress().contains(s)) {
                System.out.println(lir.get(i));
            }
        }
    }
}
