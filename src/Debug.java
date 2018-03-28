import java.io.*;

/**
 * Created by Szymo on 12.11.2017.
 */
public class Debug {
    private static boolean toAppend;
    public static void debug(){



        Triangle[] tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_3.getTapePath(), Main.howManyRecordsWillBeSorted, 0);

        float sum = 0;
        for (Triangle a: tab) {
            a.printOnlyFieldToConsole();
            sum+=a.getField();
            System.out.print(" ");
        }
        System.out.print("\n");

        Triangle[] tab1 = MyFile.readTriangleArrayFromBinaryFile(Main.tape_1.getTapePath(), Main.howManyRecordsWillBeSorted, 0);

        float sum1 = 0;
        for (Triangle a: tab1) {
            a.printOnlyFieldToConsole();
            sum1+=a.getField();
            System.out.print(" ");
        }
        System.out.print("\n");

        Triangle[] tab2 = MyFile.readTriangleArrayFromBinaryFile(Main.tape_2.getTapePath(), Main.howManyRecordsWillBeSorted, 0);

        float sum2 = 0;
        for (Triangle a: tab2) {
            a.printOnlyFieldToConsole();
            sum1+=a.getField();
            System.out.print(" ");
        }
        System.out.print("\n");

        sum1+=sum2;

        sum-=sum1;

    }

    public static void debug2(){
        Triangle[] tab, tab2, tab3;
        float sum = 0, sum2 = 0;
        int index = 0;


        for(int i = 0; i<1000; i++) {
            //tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_3.getTapePath(), Main.howManyRecordsInTape, i);
            tab = MyFile.readTriangleArrayFromBinaryFile("tape_3", Main.howManyRecordsInTape, i);
            for (Triangle a : tab) {
                sum += a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }

        for(int i = 0; i<1000; i++) {
            //tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_3.getTapePath(), Main.howManyRecordsInTape, i);
            tab2 = MyFile.readTriangleArrayFromBinaryFile("tape_2", Main.howManyRecordsInTape, i);
            for (Triangle a : tab2) {
                sum2 += a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }

        for(int i = 0; i<1000; i++) {
            //tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_3.getTapePath(), Main.howManyRecordsInTape, i);
            tab3 = MyFile.readTriangleArrayFromBinaryFile("tape_1", Main.howManyRecordsInTape, i);
            for (Triangle a : tab3) {
                sum2+= a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }
        System.out.print("Suma tab 3: " + sum + "\n");
        System.out.println("Index:" + index);
        sum = 0;
        index = 0;
/*
        for(int i = 0; i<1; i++) {
            tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_1.getTapePath(), Main.howManyRecordsInTape, i);
            for (Triangle a : tab) {
                sum += a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }

        for(int i = 0; i<1; i++) {
            tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_2.getTapePath(), Main.howManyRecordsInTape, i);
            for (Triangle a : tab) {
                sum += a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }

        System.out.print("Suma tab 1+2 : " + sum + "\n");
        System.out.println("Index:" + index);
*/
    }

    public static void debug3(){

        Triangle[] tab;
        float sum = 0;
        int index = 0;


            Writer writer = null;

            tab = MyFile.readTriangleArrayFromBinaryFile("out.bin", Main.howManyRecordsInTape, 0);
            for (Triangle a : tab) {
                sum += a.getField();
                if(a.getField() != 0.0f)
                    index++;
            }

            try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("debug1.txt", false)));

            for(int i=0; i<tab.length; i++)
                writer.write(tab[i].getA() + "\n" + tab[i].getH() + "\n");


            } catch (IOException ex) {
            ex.printStackTrace();
            } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
            }




        tab = MyFile.readTriangleArrayFromBinaryFile("out.bin", Main.howManyRecordsInTape, 1);
        for (Triangle a : tab) {
            sum += a.getField();
            if(a.getField() != 0)
                index++;
        }

        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("debug1.txt", true), "utf-8"));

            for(int i=0; i<tab.length; i++)
                writer.write(tab[i].getA() + "\n" + tab[i].getH() + "\n");


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }



        System.out.print("Suma tab 3: " + sum + "\n");
        System.out.println("Index:" + index);

    }

    public static void debug4(){
        Triangle[] tab, tab2, tab3;
        float sum = 0, sum2 = 0;
        int index = 0;

        tab = MyFile.readTriangleArrayFromBinaryFile("tape_3", Main.howManyRecordsInTape, 0);
        tab2 = MyFile.readTriangleArrayFromBinaryFile("tape_2", Main.howManyRecordsInTape, 0);
        tab3 = MyFile.readTriangleArrayFromBinaryFile("tape_1", Main.howManyRecordsInTape, 0);


        System.out.print("Suma tab 3: " + sum + "\n");
        System.out.println("Index:" + index);
        sum = 0;
        index = 0;
/*
        for(int i = 0; i<1; i++) {
            tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_1.getTapePath(), Main.howManyRecordsInTape, i);
            for (Triangle a : tab) {
                sum += a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }

        for(int i = 0; i<1; i++) {
            tab = MyFile.readTriangleArrayFromBinaryFile(Main.tape_2.getTapePath(), Main.howManyRecordsInTape, i);
            for (Triangle a : tab) {
                sum += a.getField();
                if(a.getField() != 0)
                    index++;
            }
        }

        System.out.print("Suma tab 1+2 : " + sum + "\n");
        System.out.println("Index:" + index);
*/
    }
}
