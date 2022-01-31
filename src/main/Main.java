package main;

import data.Record;
import databaseService.DatabaseService;
import util.FileSystemService;
import util.Util;

import static constant.Constants.*;

public class Main {
    public static void main(String[] args) {

        int i = 0;
        while (true) {
            String action;
            System.out.println(CHOICE);
            action = Util.createScanner().nextLine();

//            FileSystemService fss = new FileSystemService();
            switch (action) {
                case ADD:
                    Record r = Util.createRecord();
                    DatabaseService.addRecord(r);

//                    fss.writeLinesToFile(r);
//                    fss.writeRecordsToOutputStream(r);
//                    Util.addRecord(r);
//                    Util.saveToTextFileStreams();
                case SHOW:
                    DatabaseService.showRecordFromTable();
//                    Util.readAllRecordsFromTextFileProperties();
//                    fss.showFromFile();
//                    fss.showFromStringFile();
                    break;
                case CH:
                    System.out.println(CHOOSE_BY_ADDRESS);
                    String chooseByAddress = Util.createScanner().nextLine();
                    StringBuffer sb = new StringBuffer("%").append(chooseByAddress).append("%");
                    DatabaseService.chooseRecordFromTableByAddress(sb.toString());
//                    Util.chooseRecordFromTextFileProperties(chooseByAddress);
//                    fss.chooseAddressFromFile(chooseByAddress);
//                    fss.chooseFromStringFile(chooseByAddress);
                    break;
                case EXIT:
//                    FileSystemService.createKeyFile().delete();
                    System.exit(0);
                    break;
                default:
                    Util.incorrectAnswer(i);
                    i++;
            }
        }
    }
}
