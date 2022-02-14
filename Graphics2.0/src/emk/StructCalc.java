package emk;

//Класс, в котором хранится структура с функциями позволяющими записать и достать значения из ее элементов
class StructCalc {                                                                                                      //класс-структура
    String getSign() {
        return sign;
    }

    void setSign(String sign) {
        this.sign = sign;
    }

    String getSide() {
        return side;
    }

    double[] getX_array() {
        return x_array;
    }

    void setX_array(double[] x_array) {
        this.x_array = x_array;
    }

    void setSide(String side) {
        this.side = side;
    }

    double getNumber() {
        return number;
    }

    void setNumber(double number) {
        this.number = number;
    }

    double[] getY_array() {
        return y_array;
    }

    void setY_array(double[] y_array) {
        this.y_array = y_array;
    }

    int getElement_count() {
        return element_count;
    }

    void setElement_count(int element_count) {
        this.element_count = element_count;
    }

    int getScl_width() {
        return scl_width;
    }

    void setScl_width(int scl_width) {
        this.scl_width = scl_width;
    }

    String getVal() {
        return val;
    }

    void setVal(String val) {
        this.val = val;
    }

    boolean getIsAbs() {
        return isAbs;
    }

    void setAbs(boolean abs) {
        isAbs = abs;
    }

    enumAbs.enumAbsExp getAbsExp() {
        return absExp;
    }

    void setAbsExp(enumAbs.enumAbsExp absExp) {
        this.absExp = absExp;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public boolean getIsMinusVar() {
        return minusVar;
    }

    public void setMinusVar(boolean minusVar) {
        this.minusVar = minusVar;
    }

    private String sign = "";                                                                                           //знак
    private String side = "";                                                                                           //сторона
    private double number = 0;                                                                                          //чило
    private double x_array[] = new double[10000];                                                                       //массив х
    private double y_array[] = new double[10000];                                                                       //массив y
    private int element_count = 0;                                                                                      //счетчик элементов, использующихся в массиве
    private int scl_width = 40;                                                                                         //масштаб
    private double step = 0.05;                                                                                         //шаг рассчета точек графика
    private String val = "x";                                                                                           //переменная
    private boolean isAbs = false;                                                                                      //имеется ли модуль в формуле
    private enumAbs.enumAbsExp absExp;                                                                                  //тип - перечисление, в котором хранится одно из значений
    private boolean minusVar = false;                                                                                   //флаг отрицательной переменной
}
