/**
 * Tape merge sorting
 * Natural merging
 * 2+1 Scheme
 * Algorithm file
 * Szymon Szulkowski 160683
 */

import java.io.File;

public class Algorithm {

    public static int phase = 1;

    public static void distribute(){
        Triangle previous = new Triangle(0,0,1);
        int index = 0;
        int tapeToSave = 1;

        Main.tape_1.setEmptyBuffer();
        Main.tape_2.setEmptyBuffer();
        Main.tape_3.readBuffer();

        while(previous.getField() != 0 && index < Main.howManyRecordsWillBeSorted){
            if(index == 0)
                previous = new Triangle(0,0);


            Triangle record = Main.tape_3.getNextRecordFromBuffer();
            if(record.getField() >= previous.getField()){
                if(tapeToSave == 1)
                    Main.tape_1.addRecord(record);
                else
                    Main.tape_2.addRecord(record);
            }
            else {
                if(tapeToSave == 2) {
                    Main.tape_1.addRecord(record);
                    tapeToSave = 1;
                }
                else {
                    Main.tape_2.addRecord(record);
                    tapeToSave = 2;
                }
            }

            previous.setTriangleAutoField(record.getA(), record.getH());
            index++;
        }
        Main.tape_1.flush();
        Main.tape_2.flush();

        File file = new File(Main.tape_3.getTapePath());
        //if(!file.delete())
            //System.out.println("Cannot delete file");

        Main.tape_3 = new Tape("tape_3", Main.howManyRecordsInTape, Main.howManyRecordsWillBeSorted);
        Main.tape_3.setEmptyBuffer();
        Main.tape_1.setEmptyBuffer();
        Main.tape_2.setEmptyBuffer();
    }

    public static void merge(){
        Triangle previous = new Triangle(0,0);

        Main.tape_1.readBuffer();
        Main.tape_2.readBuffer();

        Triangle record_1 = Main.tape_1.getNextRecordFromBuffer();
        Triangle record_2 = Main.tape_2.getNextRecordFromBuffer();
        int indexTape1 = 0, indexTape2 = 0;

        // improvements needs to be done, illegible code
        while(indexTape1 + indexTape2 <= Main.howManyRecordsWillBeSorted) {
            int cmp = record_1.compare(record_2, previous);

            if (cmp == 1) {
                Main.tape_3.addRecord(record_1);
                previous = record_1;
                record_1 = Main.tape_1.getNextRecordFromBuffer();
                indexTape1++;
            }
            else if (cmp == 2) {
                Main.tape_3.addRecord(record_1);
                previous = record_1;
                record_1 = Main.tape_1.getNextRecordFromBuffer();
                indexTape1++;
                Main.tape_3.seriesPlusOne();
            }
            else if (cmp == 3) {
                Main.tape_3.addRecord(record_2);
                previous = record_2;
                record_2 = Main.tape_2.getNextRecordFromBuffer();
                indexTape2++;
            }
            else if (cmp == 4) {
                Main.tape_3.addRecord(record_2);
                previous = record_2;
                record_2 = Main.tape_2.getNextRecordFromBuffer();
                indexTape2++;
                Main.tape_3.seriesPlusOne();
            }
            else if (cmp == 5){
                // field 1 == 0
                Main.tape_3.addRecord(record_2);
                if(record_2.getField() != 0){
                    Main.tape_3.seriesPlusOne();
                }
                previous = record_2;
                record_2 = Main.tape_2.getNextRecordFromBuffer();
                indexTape2++;
            }
            else if (cmp == 6){
                Main.tape_3.addRecord(record_1);
                if(record_1.getField() != 0){
                    Main.tape_3.seriesPlusOne();
                }
                previous = record_1;
                record_1 = Main.tape_1.getNextRecordFromBuffer();
                indexTape1++;
            }
            else {
                break;
            }
        }

        // making phases files, could be in function, weird names
        if(Main.showPhases) {
            MyFile.convertBinaryFileToTextFile("tape_1", "tmp_tape_1_phase_" + phase + ".txt", Main.tape_1.getIndexForSavingOverall());
            MyFile.prepareResultsForShowing("tmp_tape_1_phase_" + phase + ".txt", "phases/tape_1_phase_" + phase + ".txt");
            File file = new File("tmp_tape_1_phase_" + phase + ".txt");
            file.delete();

            MyFile.convertBinaryFileToTextFile("tape_2", "tmp_tape_2_phase_" + phase + ".txt", Main.tape_2.getIndexForSavingOverall());
            MyFile.prepareResultsForShowing("tmp_tape_2_phase_" + phase + ".txt", "phases/tape_2_phase_" + phase + ".txt");
            file = new File("tmp_tape_2_phase_" + phase + ".txt");
            file.delete();

            MyFile.convertBinaryFileToTextFile("tape_3", "tmp_tape_3_phase_" + phase + ".txt", Main.howManyRecordsWillBeSorted);
            MyFile.prepareResultsForShowing("tmp_tape_3_phase_" + phase + ".txt", "phases/tape_3_phase_" + phase + ".txt");
            file = new File("tmp_tape_3_phase_" + phase + ".txt");
            file.delete();

            phase++;
        }

        // just cleaning, preparing for distribution
        Main.tape_1 = new Tape("tape_1", Main.howManyRecordsInTape, Main.howManyRecordsWillBeSorted);
        Main.tape_2 = new Tape("tape_2", Main.howManyRecordsInTape, Main.howManyRecordsWillBeSorted);
        Main.tape_1.setEmptyBuffer();
        Main.tape_2.setEmptyBuffer();
    }
}
