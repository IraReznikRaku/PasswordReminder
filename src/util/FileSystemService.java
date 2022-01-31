package util;

import data.Record;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class FileSystemService {

    private static final String NAME_OF_FILE = "C:\\Java47\\PasswordReminder\\passRem.txt";

    private static final String SECRET_KEY = "C:\\Java47\\PasswordReminder\\secretKey.txt";
    public static SecretKey secretKey;

//    static {
//        File secretFile = new File(SECRET_KEY);
//        if (!secretFile.exists()) {
//            try {
//                secretFile.createNewFile();
//                try {
//                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//                    keyGenerator.init(128);
//                    secretKey = keyGenerator.generateKey();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(secretFile))){
//                    oos.writeObject(secretKey);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static File createKeyFile() {
        File f = new File(SECRET_KEY);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static void createKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void writeKeyToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(createKeyFile()))) {
            createKey();
            oos.writeObject(secretKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey readKeyFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(createKeyFile()))) {
            secretKey = (SecretKey) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error: read from file");;
        }
        return secretKey;
    }

    public static String encryptionPass(String pass)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] data = pass.getBytes();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, readKeyFromFile());
        byte[] encryptedData = cipher.doFinal(data);
        return new String(encryptedData);
    }

    public static String decryptionPass(String pass)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedData = pass.getBytes();
        Cipher cipher2 = Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE, readKeyFromFile());
        byte[] decryptedData = cipher2.doFinal(encryptedData);
        return new String(decryptedData);
    }

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
            System.out.println("error: read From File");
        }
        System.out.println("rrr " + lr);
        return lr;
    }

    public void writeRecordsToOutputStream(Record r) {
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

    private List<String> readLineFromFile() {
        List<String> ls = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(createFile()))) {
            String s = br.readLine();
            while (s != null) {
                ls.add(s);
                s = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ls;
    }

    public void writeLinesToFile(Record r) {
        List<String> lis = readLineFromFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(createFile()))) {
            for (int i = 0; i < lis.size(); i++) {
                bw.write(lis.get(i));
                bw.newLine();
            }
            bw.write(r.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFromStringFile() {
        List<String> ls = readLineFromFile();
        for (String s : ls) {
            System.out.println(s);
        }
    }

    public void chooseFromStringFile(String s) {
        List<String> ls = readLineFromFile();
        int i = 0;
        for (String str : ls) {
            if (str.substring(str.indexOf("Address is:") + "Address is:".length(), str.indexOf("Login is")).contains(s)) {
                System.out.println(str);
                i++;
            }
        }
        if (i == 0) {
            System.out.println("Records no chosen");
        }
    }
}
