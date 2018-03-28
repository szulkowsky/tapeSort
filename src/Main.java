/**
 * Tape merge sorting
 * Natural merging
 * 2+1 Scheme
 * Main file
 * Szymon Szulkowski 160683
 */

public class Main {

    public static Tape tape_1;
    public static Tape tape_2;
    public static Tape tape_3;
    public static int howManyRecordsWillBeSorted = 100;
    public static int howManyRecordsInTape;
    public static int saves = 0;
    public static int reads = 0;
    public static boolean showPhases = false;
    public static boolean random = false;
    public static boolean keyboard = false;
    public static String fileName = "input.txt";


    public static void main(String[] args) {

        // ---------------------------------- MANUAL --------------------------------------------------------

        if(args.length == 0){
            System.out.println("Argument 1: Enter mode you want to go for:");
            System.out.println(" -- To go random: type 'random'. \n -- To go from keyboard, type 'keyboard'. \n -- To go from the file: type filename");
            System.out.println("Argument 2: Enter how many records you want to sort");
            System.out.println("Argument 3: Type true to write phases to file or false to not to");
            System.exit(0);
        }

        // ------------------------ CONFIGURING SORTER BY ARGUMENTS FROM COMMAND LINE ------------------------
        howManyRecordsWillBeSorted = Integer.parseInt(args[1]);

        if(args[0].equals("random")) {
            random = true;
        }
        else if (args[0].equals("keyboard")) {
            keyboard = true;
        }
        else
            fileName = args[0];

        showPhases = Boolean.parseBoolean(args[2]);

        // ------------------------ SETTING PARAMETERS TO RUN FINE --------------------------------------------
        if(howManyRecordsWillBeSorted < MyFile.getConvertingBufferSize()/8)
            howManyRecordsInTape = howManyRecordsWillBeSorted;
        else
            howManyRecordsInTape = MyFile.getConvertingBufferSize()/8;

        tape_1 = new Tape("tape_1", howManyRecordsInTape, howManyRecordsWillBeSorted);
        tape_2 = new Tape("tape_2", howManyRecordsInTape, howManyRecordsWillBeSorted);
        tape_3 = new Tape("tape_3", howManyRecordsInTape, howManyRecordsWillBeSorted);

        // ------------------------- MAKING BINARY FILES FROM INPUT -------------------------------------------
            // Input needs to be saved for better looking at the results - you would like to see values from the beginning, don't you?

        if(random && !keyboard){
            MyFile.convertRandomnessToTextFile(howManyRecordsWillBeSorted);
            MyFile.convertTextFileToBinaryFile("input" + howManyRecordsWillBeSorted + ".txt", tape_3.getTapePath(), howManyRecordsWillBeSorted);
        }
        else if(keyboard &&  !random){
            MyFile.convertKeyboardInputToTextFile(howManyRecordsWillBeSorted);
            MyFile.convertTextFileToBinaryFile("input" + howManyRecordsWillBeSorted + ".txt", tape_3.getTapePath(), howManyRecordsWillBeSorted);
        }
        else
            MyFile.convertTextFileToBinaryFile(fileName, tape_3.getTapePath(), howManyRecordsWillBeSorted);

        // ------------------------- ALGORITHM WORKING --------------------------------------------------------
        int phases = 0;
        while (true){
            Algorithm.distribute();
            Algorithm.merge();
            phases++;
            if(tape_3.getHowManySeries() == 1)
                break;
        }
        // ------------------------- SHOWING BASIC NUMBERS, HOW SORTING WAS -----------------------------------
        System.out.println("Phases: " + phases);
        System.out.println("Reads: " + reads);
        System.out.println("Saves: " + saves);

        // ------------------------- PREPARING TEXT FILE RESULTS ----------------------------------------------
            // It is easier to compare data when it is saved to .txt file than looking at all million+ numbers in a console

        MyFile.convertBinaryFileToTextFile(tape_3.getTapePath(), "output" + howManyRecordsWillBeSorted + ".txt", howManyRecordsWillBeSorted);
        MyFile.prepareResultsForShowing("input" + howManyRecordsWillBeSorted + ".txt", "beginning" + howManyRecordsWillBeSorted + ".txt");
        MyFile.prepareResultsForShowing("output" + howManyRecordsWillBeSorted + ".txt", "results" + howManyRecordsWillBeSorted + ".txt");
    }
}
