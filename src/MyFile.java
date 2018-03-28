/**
 * Tape merge sorting
 * Natural merging
 * 2+1 Scheme
 * Input Output and Converting
 * Szymon Szulkowski 160683
 */

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.Scanner;


public class MyFile {

    // ---------------------- CONSTANTS -------------------------------------

    private static final int convertingBufferSize = 8192;

    // ---------------------- GETTERS ---------------------------------------

    public static int getConvertingBufferSize(){return convertingBufferSize;}

    // ---------------------------- SAVING ----------------------------------

    public static void saveFloatArrayToTextFile(float[] arrayToSave, String fileName, boolean toAppend){
        Writer writer = null;

        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(fileName, toAppend), "utf-8"));

            for(int i=0; i<arrayToSave.length; i++)
                writer.write(arrayToSave[i] + "\n");


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    public static void saveFloatArrayToBinaryFile(float[] arrayToSave, String fileName, boolean toAppend){
        try (
                FileOutputStream outputStreamF = new FileOutputStream(fileName, toAppend);
                BufferedOutputStream outputStream = new BufferedOutputStream(outputStreamF, 8192);
                //FileOutputStream outputStream = new FileOutputStream(fileName, toAppend);
        ) {
            byte[] buffer = new byte[convertingBufferSize];
            Main.saves++;
            int j=0;
            for (int i = 0; i<arrayToSave.length; i++) {
                byte[] floatInBytes = float2ByteArray(arrayToSave[i]);
                for(int k = 0; k<4; j++, k++){
                    buffer[j] = floatInBytes[k];
                }
            }

            //outputStream.write(buffer);

            outputStream.write(buffer, 0, convertingBufferSize);
            outputStream.flush();
            outputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void saveTriangleArrayToTextFile(Triangle[] arrayToSave, String fileName, boolean toAppend){
        Writer writer = null;

        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(fileName, toAppend), "utf-8"));
            float[] record;
            for(int i=0; i<arrayToSave.length; i++) {
                record = arrayToSave[i].getRecordInTwoElementArray();
                writer.write(record[0] + "\n" + record[1] + "\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    public static void saveTriangleArrayToBinaryFile(Triangle[] arrayToSave, String fileName, boolean toAppend){
        float[] array = new float[arrayToSave.length*2];
        int i = 0;
        for (Triangle a: arrayToSave) {
            array[i] = a.getA();
            array[i+1] = a.getH();
            i+=2;
        }
        saveFloatArrayToBinaryFile(array, fileName, toAppend);
    }

    // ------------------------------ READING ----------------------------------

    public static float[] readFloatArrayFromTextFile(String fileName, int length){
        float[] readArray = null;


        return readArray;
    }

    public static float[] readFloatArrayFromBinaryFile(String fileName, int length){
        float[] readArray = null;


        return readArray;
    }

    public static Triangle[] readTriangleArrayFromTextFile(String fileName, int length){
        Triangle[] readArray = new Triangle[length];
        int readRecords = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line, line2;
            Float data1, data2;
            while ((((line = br.readLine()) != null) && ((line2 = br.readLine()) != null)) && readRecords < length) {
                line = line.replaceAll("\\s+","");
                line2 = line.replaceAll("\\s+","");

                data1 = Float.parseFloat(line);
                data2 = Float.parseFloat(line2);

                readArray[readRecords].setTriangleAutoField(data1, data2);

                readRecords++;
            }
            if (line == null){

            }
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return readArray;
    }

    public static Triangle[] readTriangleArrayFromBinaryFile(String fileName, int length, int offsetMultiplier){
        Triangle[] readArray = new Triangle[length];
        for (int i = 0; i<length; i++) {
            readArray[i] = new Triangle();
        }

        try (InputStream inputStream = new FileInputStream(fileName)) {

            byte[] buffer = new byte[convertingBufferSize];
            Main.reads++;
            if(offsetMultiplier != 0){
                long numberOfBytes = 0;
                numberOfBytes = inputStream.skip(offsetMultiplier*convertingBufferSize - numberOfBytes);
                //System.out.println(numberOfBytes);
            }
            /*
            while (numberOfBytes != (long)offsetMultiplier*convertingBufferSize){
                numberOfBytes = inputStream.skip(offsetMultiplier*convertingBufferSize - numberOfBytes); // bcs may be buggy and smtms dont skip all bytes :V
            }
*/
            int x = 0;
            int j = 0;

            inputStream.read(buffer, 0, convertingBufferSize);
            while (j<Main.howManyRecordsInTape*8) {
                byte[] bytes = new byte[4];

                float[] data = new float[2];


                for (int k = 0; k < length; k++){
                    for(int i = 0; i<4; i++, j++){
                        bytes[i] = buffer[j];
                    }
                    data[0] = byteArray2Float(bytes);
                    for(int i = 0; i<4; i++, j++){
                        bytes[i] = buffer[j];
                    }
                    data[1] = byteArray2Float(bytes);

                    readArray[k].setTriangleAutoField(data[0], data[1]);
                    x=k;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return readArray;
    }

    // --------------------------- CONVERTING -----------------------------------

    private static byte [] float2ByteArray (float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    private static float byteArray2Float (byte[] bytes){
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static void convertBinaryFileToTextFile(String binFileName, String txtFileName, int recordNumber){
        try (InputStream inputStream = new FileInputStream(binFileName)) {

            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(txtFileName, false)));


            byte[] buffer = new byte[convertingBufferSize];
            int index = 0;

            while (index < recordNumber*2) {
                int j = 0;
                inputStream.read(buffer, 0, convertingBufferSize);
                byte[] bytes = new byte[4];
                for (int k = 0; k < convertingBufferSize / 4; k++) {
                    for (int i = 0; i < 4; i++, j++) {
                        bytes[i] = buffer[j];
                    }
                    float f = byteArray2Float(bytes);
                    if(f != 0){
                        String s = Float.toString(f);
                        writer.write(s + "\n");
                        index++;
                    }
                }
                writer.flush();
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void convertTextFileToBinaryFile(String txtFileName, String binFileName, int recordNumber){
        float[] readArray;
        if(recordNumber < convertingBufferSize/8)
            readArray = new float[recordNumber*2];
        else
            readArray = new float[convertingBufferSize/4];

        boolean toAppend = false;

        int readRecords = 0;
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(txtFileName))) {
            Main.reads++;
            String line, line2;
            Float data1, data2;
            while ((((line = br.readLine()) != null) && ((line2 = br.readLine()) != null)) && readRecords < recordNumber) {
                line = line.replaceAll("\\s+","");
                line2 = line2.replaceAll("\\s+","");

                readArray[index] = Float.parseFloat(line);
                readArray[index+1] = Float.parseFloat(line2);
                readRecords++;
                index+=2;

                if(index == convertingBufferSize/4 || readRecords == recordNumber){
                    saveFloatArrayToBinaryFile(readArray, binFileName, toAppend);
                    Main.saves--;
                    toAppend = true;
                    readArray = new float[readArray.length];
                    index = 0;
                }
            }
            if (line == null){

            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void convertRandomnessToTextFile(int numberOfRecords){
        float minX = 0.0f;
        float maxX = 100.0f;

        Random rand = new Random();
        int index = 0;

        try ( Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("input"+ Main.howManyRecordsWillBeSorted + ".txt", false)))) {

            while(index < numberOfRecords*2){
                writer.write(Float.toString( rand.nextFloat() * (maxX - minX) + minX) + "\n");
                index++;
            }

            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void convertKeyboardInputToTextFile(int numberOfRecords){
        int index = 0;

        try ( Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("input"+ Main.howManyRecordsWillBeSorted + ".txt", false)))) {
            Scanner reader = new Scanner(System.in);

            while(index < numberOfRecords){


                System.out.println("Record: " + (index+1) + " Records left: " + (numberOfRecords - (index + 1)));

                System.out.println("Enter base:");
                writer.write(Float.toString( reader.nextFloat()) + "\n");

                System.out.println("Enter height:");
                writer.write(Float.toString( reader.nextFloat()) + "\n");
                index++;

            }
            reader.close();
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // -------------------------- JUST FOR BETTER SHOWING ------------------------

    public static void prepareResultsForShowing(String input, String output){
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(output, false)));

            String line, line2;
            Float data1, data2;
            while ((((line = br.readLine()) != null) && ((line2 = br.readLine()) != null))) {
                line = line.replaceAll("\\s+","");
                line2 = line2.replaceAll("\\s+","");
                float field = (Float.parseFloat(line) * Float.parseFloat(line2)/2);

                writer.write("(\t" + line + "\t,\t" + line2 + "\t)\t->\t" + field + " \n");
            }
            writer.flush();
            writer.close();
            if (line == null){

            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
