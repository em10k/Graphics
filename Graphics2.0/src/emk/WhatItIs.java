package emk;

import java.util.ArrayList;

//Класс, помогающий определить к какому множеству относится содержимое в строке
class WhatItIs {

    //Множество из чисел
    static boolean isNumber(String number){
        ArrayList<String> numCol = new ArrayList<> ();
        numCol.add("0");
        numCol.add("1");
        numCol.add("2");
        numCol.add("3");
        numCol.add("4");
        numCol.add("5");
        numCol.add("6");
        numCol.add("7");
        numCol.add("8");
        numCol.add("9");
        return numCol.contains(number);
    }

    //Множество из операций
    static boolean isOperation(String operation){
        ArrayList<String> operCol = new ArrayList<> ();
        operCol.add("+");
        operCol.add("-");
        operCol.add("*");
        operCol.add("÷");
        operCol.add("|");
        operCol.add("<");
        operCol.add("≤");
        operCol.add(">");
        operCol.add("≥");

        return operCol.contains(operation);
    }

    //Множество из переменных
    static boolean isVal(String operation){
        ArrayList<String> valCol = new ArrayList<> ();
        valCol.add("x");
        valCol.add("-x");
        valCol.add("y");
        valCol.add("-y");
        return valCol.contains(operation);
    }
}
