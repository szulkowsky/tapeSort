/**
 * ADD THE COMMENTS, MAN!
 */
public class Tape {
    private int howManyRecordsInTape;
    private int howManyRecordsOverall;
    private int howManySeries = 1;
    private float ratio;

    private Triangle[] recordsBuffer;
    private String tapePath;
    private int offsetForReading = 0;

    private int recordsRead = 0;
    private int indexForReading = 0;

    private int indexForSaving = 0;
    private int indexForSavingOverall = 0;
    private int timesSaved = 0;
    private boolean toAppend = false;

    public Tape(){
    }

    public Tape(String filename, int howManyRecordsTape, int howManyRecordsOvrl){
        this.tapePath = filename;
        this.howManyRecordsInTape = howManyRecordsTape;
        this.howManyRecordsOverall = howManyRecordsOvrl;
        this.ratio = (howManyRecordsOverall -(timesSaved*howManyRecordsInTape))/howManyRecordsInTape;

        this.recordsBuffer = new Triangle[howManyRecordsInTape];
    }

    public void updateRatio(){
        this.ratio = (float)(howManyRecordsOverall -(timesSaved*howManyRecordsInTape))/howManyRecordsInTape;
    }

    public void readBuffer(){
        this.recordsBuffer = MyFile.readTriangleArrayFromBinaryFile(this.tapePath, this.howManyRecordsInTape, offsetForReading);
    }

    public void setEmptyBuffer(){
        for(int i = 0; i<this.howManyRecordsInTape; i++)
            recordsBuffer[i] = new Triangle(0,0);
    }

    public void offsetPlusOne(){
        this.offsetForReading++;
    }

    public void seriesPlusOne(){
        this.howManySeries++;
    }

    public Triangle getNextRecordFromBuffer(){
        Triangle ret = this.recordsBuffer[indexForReading];
        indexForReading++;
        recordsRead++;

        if(indexForReading >= MyFile.getConvertingBufferSize()/8) {
            this.offsetPlusOne();
            this.readBuffer();
            indexForReading = 0;
        }

        return ret;
    }

    public void addRecord(Triangle record){
        this.recordsBuffer[indexForSaving].setTriangleAutoField(record.getA(), record.getH());
        indexForSaving++;
        indexForSavingOverall++;

        if(indexForSaving >= MyFile.getConvertingBufferSize()/8 || indexForSavingOverall >= howManyRecordsOverall){
            MyFile.saveTriangleArrayToBinaryFile(this.recordsBuffer, this.tapePath, this.toAppend);
            this.toAppend = true;
            indexForSaving = 0;
            this.updateRatio();
            if(this.ratio >= 1){
                this.recordsBuffer = new Triangle[recordsBuffer.length];
                this.setEmptyBuffer();
            }
            else {
                this.howManyRecordsInTape = howManyRecordsOverall -(timesSaved*howManyRecordsInTape);
                this.recordsBuffer = new Triangle[howManyRecordsInTape];
                this.setEmptyBuffer();
            }
        }

    }

    public void flush(){
        MyFile.saveTriangleArrayToBinaryFile(this.recordsBuffer, this.tapePath, this.toAppend);
    }

    // -------------------------- GETTERS ------------------------------------------------

    public Triangle getRecordFromBuffer(int index){
        return this.recordsBuffer[index];
    }

    public int getIndexForSavingOverall(){
        return this.indexForSavingOverall;
    }

    public String getTapePath(){
        return this.tapePath;
    }

    public int getHowManySeries(){ return this.howManySeries;}
}
