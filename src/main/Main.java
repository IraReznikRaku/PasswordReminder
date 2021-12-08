package main;

import data.Record;
import util.FileSystemService;
import util.Util;

import java.io.IOException;

import static constant.Constants.*;

public class Main {

    public static void main(String[] args) {
        int i = 0;
        while (true) {
            String action;
            System.out.println(CHOICE);
            action = Util.createScanner().nextLine();
            FileSystemService fss = new FileSystemService();
            switch (action) {
                case ADD:
                    Record r = Util.createRecord();
                    fss.writeToOutputStream(r);
//                    Util.addRecord(r);
//                    Util.saveToTextFileStreams();
                case SHOW:
//                    Util.readAllRecordsFromTextFileProperties();
                    fss.showFromFile();
                    break;
                case CH:
                    System.out.println(CHOOSE_BY_ADDRESS);
                    String chooseByAddress = Util.createScanner().nextLine();
//                    Util.chooseRecordFromTextFileProperties(chooseByAddress);
                    fss.chooseAddressFromFile(chooseByAddress);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    Util.incorrectAnswer(i);
                    i++;
            }
        }
    }
}
