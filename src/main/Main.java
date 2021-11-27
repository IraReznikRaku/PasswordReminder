package main;

import data.Record;
import util.Util;

import static constant.Constants.*;

public class Main {

    public static void main(String[] args) {
        while (true) {
            String action;
            System.out.println(CHOICE);
            action = Util.createScanner().nextLine();
            switch (action) {
                case ADD:
                    Record r = Util.createRecord();
                    Util.addRecord(r);
                    Util.saveToTextFile();
                case SHOW:
                    Util.printRecords();
                    break;
                case CH:
                    System.out.println(CHOOSE_BY_ADDRESS);
                    String chooseAnswer = Util.createScanner().nextLine();
                    Util.chooseRecord(chooseAnswer);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect choice. Repeat, please");
            }
        }
    }
}
