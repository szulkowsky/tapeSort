/**
 * Tape merge sorting
 * Natural merging
 * 2+1 Scheme
 * Triangle Class file
 * Szymon Szulkowski 160683
 */
public class Triangle {
    private float a;
    private float h;
    private float field;


    // ------------------------------- CONSTRUCTORS -----------------------------------
    public Triangle(){
        this.a=0;
        this.h=0;
        this.field= 0;
    }

    public Triangle(float aValue, float hValue){
        this.a = aValue;
        this.h = hValue;
        this.field = a*h/2;
    }

    public Triangle(float aValue, float hValue, float fieldvalue){
        this.a = aValue;
        this.h = hValue;
        this.field = fieldvalue;
    }

    // --------------------------- GETTERS -----------------------------------------------
    public float getA(){
        return this.a;
    }

    public float getH(){
        return this.h;
    }

    public float getField(){
        return this.field;
    }

    // ---------------------------- SETTERS -----------------------------------------------

    public void setTriangleAutoField(float aValue, float hValue) {
        this.a = aValue;
        this.h = hValue;
        this.field = a * h / 2;
    }

    public void setTriangleManualField(float aValue, float hValue, float fieldValue) {
        this.a = aValue;
        this.h = hValue;
        this.field = fieldValue;
    }

// ---------------------------------- ? ---------------------------------------

    public void printTriangleToConsoleNicely(){
        System.out.println("[ "+ this.a + " , " + this.h + " ] {" + this.field + " }");
    }

    public void printOnlyFieldToConsole(){
        System.out.print(this.field);
    }

    public float[] getRecordInTwoElementArray(){
        float[] ret = {this.getA(), this.getH()};
        return ret;
    }

    public int compare(Triangle second, Triangle previous){
        // f_1 < f_2 && (f_1 > prev && f_2 > prev) -> 1
        // f_1 > f_2 && (f_1 > prev && f_2 < prev) -> 1
        // f_1 < f_2 && (f_1 < prev && f_2 < prev) -> 1 + series++

        // f_2 < f_1 && (f_2 > prev && f1 > prev) -> 2
        // f_2 > f_1 && (f_2 > prev && f1 < prev) -> 2
        // f_2 < f_1 && (f_2 < prev && f1 < prev) -> 2 + series

        // NOTE: można by było zrobić inny typ zwracany i nie zwracać tak różnych wartości, coś w stylu [1, true]

        if((this.getField() <= second.getField() && this.getField() >= previous.getField() && second.getField() >= previous.getField() && this.getField() != 0) ||
                (this.getField() > second.getField() && this.getField() > previous.getField() && second.getField() < previous.getField() && this.getField() != 0)){
            return 1;
        }

        else if(this.getField() <= second.getField() && this.getField() <= previous.getField() && second.getField() <= previous.getField() && this.getField() != 0){
            return 2; //adding series to tape, needs to go from there
        }

        else if ((second.getField() <= this.getField() && second.getField() >= previous.getField() && this.getField() >= previous.getField() && second.getField() != 0) ||
                (second.getField() > this.getField() && second.getField() > previous.getField() && this.getField() < previous.getField() && second.getField() != 0)){
            return 3;
        }
        else if (second.getField() <= this.getField() && second.getField() <= previous.getField() && this.getField() <= previous.getField() && second.getField() != 0){
            return 4; //adding series to tape, needs to go from there
        }
        else if(this.getField() == 0 && second.getField() != 0){
            return 5;
        }
        else if(second.getField() == 0 && this.getField() != 0 ){
            return 6;
        }
        else
            return 7;




    }

}
