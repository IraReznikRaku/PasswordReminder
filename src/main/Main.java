package main;

import data.Record;
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
            switch (action) {
                case ADD:
                    Record r = Util.createRecord();
                    Util.addRecord(r);

                        Util.saveToTextFileStreams();

                case SHOW:
                    Util.readAllRecordsFromTextFileProperties();
                    break;
                case CH:
                    System.out.println(CHOOSE_BY_ADDRESS);
                    String chooseByAddress = Util.createScanner().nextLine();
                    Util.chooseRecordFromTextFileProperties(chooseByAddress);
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
