package emk;

//Класс, служащий для просчета координат каждой точки графика
class Calculate extends Graphics{
    private int doMinusVal = 1;
    void calculate(StructCalc exp_struct){                                                                              //на вход получаем структуру
        exp_struct.setElement_count(0);                                                                                 //обнуляем счетчик элементов
        double iter = -graphWidth/exp_struct.getScl_width();                                                            //высчитываем левую границу формы, зависящую от масштаба
        double step = exp_struct.getStep();                                                                             //шаг - приближает к 0
        doMinusVal = 1;
        if (exp_struct.getIsMinusVar()){
            doMinusVal = -doMinusVal;
        }
        if(exp_struct.getVal().equals("x")) {
//*******************************************************************************************
            if (exp_struct.getSign().equals("+")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("x")) {
                    do {
                        exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                                  //считаем x[i]
                        if (exp_struct.getIsAbs()) {                                                                    //если имеется модуль в выражении
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                                doAbs(exp_struct);                                                                      //выполнить модуль в функции
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                doAbs(exp_struct);
                                exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getY_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                if (!onlyNumFlag) {
                                    doAbs(exp_struct);
                                    onlyNumFlag = true;
                                }
                                exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                            }
                        }
                        else{
                            exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                        }
                        exp_struct.setElement_count(exp_struct.getElement_count() + 1);                                 //увеличиваем счетчик элементов
                        iter += step;
                    }
                    while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                }            
            }
//*******************************************************************************************
            if (exp_struct.getSign().equals("-")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("x")) {
                    if (exp_struct.getSide().equals("L")) {
                        do {
                            exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getY_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                                }
                            } else {
                                exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);                             //увеличиваем счетчик элементов
                            iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                    }
                    else{
                        do {
                            exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                            }
                            else {
                                exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);                             //увеличиваем счетчик элементов
                            iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля

                    }
                }
            }
//*******************************************************************************************
            if (exp_struct.getSign().equals("*")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("x")) {
                    do {
                        exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                                  //считаем x[i]
                        if (exp_struct.getIsAbs()) {                                                                    //если имеется модуль в выражении
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                                doAbs(exp_struct);                                                                      //выполнить модуль в функции
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                doAbs(exp_struct);
                                exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getY_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                if (!onlyNumFlag) {
                                    doAbs(exp_struct);
                                    onlyNumFlag = true;
                                }
                                exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                            }
                        }
                        else{
                            exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                        }
                        exp_struct.setElement_count(exp_struct.getElement_count() + 1);                                 //увеличиваем счетчик элементов
                        iter += step;
                    }
                    while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                }
            }
//*******************************************************************************************
            if (exp_struct.getSign().equals("/")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("x")) {
                    if (exp_struct.getSide().equals("L")) {
                        do {
                            exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getY_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                                }
                            }
                            else {
                                exp_struct.getY_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);                             //увеличиваем счетчик элементов
                            iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                    }
                    else{
                        int el_count = 0;
                        double prev_x;
                        do {
                            el_count++;                                                                                 //считаем количество зайдействованных элементов
                        }
                        while (el_count < (graphWidth / exp_struct.getScl_width()) * 2 * (1/step));                     //пока счетчик не достигнет правого конца поля
                        int halfElCount = (el_count - 1) / 2;                                                           //центральное значение из всех имеющихся - середина
                        do {
                            exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                            }
                            else {
                                exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / doMinusVal*exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);
                            if (halfElCount == exp_struct.getElement_count() - 1) {                                     //если при подсчете номер элемента равен центральному
                                prev_x = exp_struct.getX_array()[exp_struct.getElement_count() - 1];                    //запоминаем его значение
                                iter = prev_x * (-1);                                                                   //умножаем на -1 (делаем зеркальным) и присваиваем текущему х
                            }
                            else iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля

                    }
                }
            }
//*******************************************************************************************
            if ((exp_struct.getSign().equals(""))||(exp_struct.getSign().equals("<"))||(exp_struct.getSign().equals(">"))||
                (exp_struct.getSign().equals("≤"))||(exp_struct.getSign().equals("≥"))){                                //в случае, если в форме просто число или знаки больше/меньше
                do {
                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber();                    //считаем x[i]
                    exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                                      //считаем y[i]
                    exp_struct.setElement_count(exp_struct.getElement_count() + 1);                                     //увеличиваем счетчик элементов
                    iter+=step;
                }
                while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1/step));        //пока счетчик не достигнет правого конца поля
            }

        }
//===============================================================================================================================================================
//===============================================================================================================================================================
//===============================================================================================================================================================
        else{
            if (exp_struct.getSign().equals("+")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("y")) {
                    do {
                        exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                                  //считаем x[i]
                        if (exp_struct.getIsAbs()) {                                                                    //если имеется модуль в выражении
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                                doAbs(exp_struct);                                                                      //выполнить модуль в функции
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                doAbs(exp_struct);
                                exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getX_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                if (!onlyNumFlag) {
                                    doAbs(exp_struct);
                                    onlyNumFlag = true;
                                }
                                exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                            }
                        }
                        else{
                            exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] + exp_struct.getNumber(); //считаем x[i]+число
                        }
                        exp_struct.setElement_count(exp_struct.getElement_count() + 1);                                 //увеличиваем счетчик элементов
                        iter += step;
                    }
                    while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                }
            }
//*******************************************************************************************
            if (exp_struct.getSign().equals("-")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("y")) {
                    if (exp_struct.getSide().equals("L")) {
                        do {
                            exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getX_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                                }
                            } else {
                                exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] - exp_struct.getNumber(); //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);                             //увеличиваем счетчик элементов
                            iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));      //пока счетчик не достигнет правого конца поля
                    }
                    else{
                        do {
                            exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                            }
                            else {
                                exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() - doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);                             //увеличиваем счетчик элементов
                            iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля

                    }
                }
                
            }
//*******************************************************************************************
            if (exp_struct.getSign().equals("*")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("y")) {
                    do {
                        exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                                  //считаем x[i]
                        if (exp_struct.getIsAbs()) {                                                                    //если имеется модуль в выражении
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                                doAbs(exp_struct);                                                                      //выполнить модуль в функции
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                doAbs(exp_struct);
                                exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getX_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                            }
                            if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                if (!onlyNumFlag) {
                                    doAbs(exp_struct);
                                    onlyNumFlag = true;
                                }
                                exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                            }
                        }
                        else{
                            exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] * exp_struct.getNumber(); //считаем x[i]+число
                        }
                        exp_struct.setElement_count(exp_struct.getElement_count() + 1);                                 //увеличиваем счетчик элементов
                        iter += step;
                    }
                    while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                }            
            }
//*******************************************************************************************
            if (exp_struct.getSign().equals("/")) {
                boolean onlyNumFlag = false;
                if (exp_struct.getVal().equals("y")) {
                    if (exp_struct.getSide().equals("L")) {
                        do {
                            exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getX_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                                }
                            }
                            else {
                                exp_struct.getX_array()[exp_struct.getElement_count()] = doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()] / exp_struct.getNumber(); //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);                             //увеличиваем счетчик элементов
                            iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля
                    }
                    else{
                        int el_count = 0;
                        double prev_x;
                        do {
                            el_count++;                                                                                 //считаем количество зайдействованных элементов
                        }
                        while (el_count < (graphWidth / exp_struct.getScl_width()) * 2 * (1/step));                     //пока счетчик не достигнет правого конца поля
                        int halfElCount = (el_count - 1) / 2;                                                           //центральное значение из всех имеющихся - середина
                        do {
                            exp_struct.getY_array()[exp_struct.getElement_count()] = iter;                              //считаем x[i]
                            if (exp_struct.getIsAbs()) {                                                                //если имеется модуль в выражении
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.allExp)) {
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                    doAbs(exp_struct);                                                                  //выполнить модуль в функции
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyVal)) {
                                    doAbs(exp_struct);
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / exp_struct.getX_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                                if (exp_struct.getAbsExp().equals(enumAbs.enumAbsExp.onlyNum)) {
                                    if (!onlyNumFlag) {
                                        doAbs(exp_struct);
                                        onlyNumFlag = true;
                                    }
                                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                                }
                            }
                            else {
                                exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getNumber() / doMinusVal*exp_struct.getY_array()[exp_struct.getElement_count()]; //считаем x[i]+число
                            }
                            exp_struct.setElement_count(exp_struct.getElement_count() + 1);
                            if (halfElCount == exp_struct.getElement_count() - 1) {                                     //если при подсчете номер элемента равен центральному
                                prev_x = exp_struct.getY_array()[exp_struct.getElement_count() - 1];                    //запоминаем его значение
                                iter = prev_x * (-1);                                                                   //умножаем на -1 (делаем зеркальным) и присваиваем текущему х
                            }
                            else iter += step;
                        }
                        while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1 / step));  //пока счетчик не достигнет правого конца поля

                    }
                }
            }
//*******************************************************************************************
            if ((exp_struct.getSign().equals(""))||(exp_struct.getSign().equals("<"))||(exp_struct.getSign().equals(">"))||
                    (exp_struct.getSign().equals("≤"))||(exp_struct.getSign().equals("≥"))){                            //в случае, если в форме просто число или знаки больше/меньше
                do {
                    exp_struct.getX_array()[exp_struct.getElement_count()] = iter;                                      //считаем x[i]
                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getNumber();                    //считаем y[i]
                    exp_struct.setElement_count(exp_struct.getElement_count() + 1);                                     //увеличиваем счетчик элементов
                    iter+=step;
                }
                while (exp_struct.getElement_count() < (graphWidth / exp_struct.getScl_width()) * 2 * (1/step));        //пока счетчик не достигнет правого конца поля
            }

        }
    }

    private void doAbs(StructCalc exp_struct){
        if (exp_struct.getVal().equals("x")) {
            switch (exp_struct.getAbsExp()){
                case allExp:
                    if (exp_struct.getY_array()[exp_struct.getElement_count()] < 0) {
                        exp_struct.getY_array()[exp_struct.getElement_count()] = -exp_struct.getY_array()[exp_struct.getElement_count()];
                    }
                    break;
                case onlyVal:
                    exp_struct.getY_array()[exp_struct.getElement_count()] = exp_struct.getX_array()[exp_struct.getElement_count()];
                    if (exp_struct.getY_array()[exp_struct.getElement_count()] < 0) {
                        exp_struct.getY_array()[exp_struct.getElement_count()] = -exp_struct.getY_array()[exp_struct.getElement_count()];
                    }
                    break;
                case onlyNum:
                    if (exp_struct.getNumber()<0) {
                        exp_struct.setNumber(-exp_struct.getNumber());
                    }
                    break;
            }
        }
        if (exp_struct.getVal().equals("y")) {
            switch (exp_struct.getAbsExp()){
                case allExp:
                    if (exp_struct.getX_array()[exp_struct.getElement_count()] < 0) {
                        exp_struct.getX_array()[exp_struct.getElement_count()] = -exp_struct.getX_array()[exp_struct.getElement_count()];
                    }
                    break;
                case onlyVal:
                    exp_struct.getX_array()[exp_struct.getElement_count()] = exp_struct.getY_array()[exp_struct.getElement_count()];
                    if (exp_struct.getX_array()[exp_struct.getElement_count()] < 0) {
                        exp_struct.getX_array()[exp_struct.getElement_count()] = -exp_struct.getX_array()[exp_struct.getElement_count()];
                    }
                    break;
                case onlyNum:
                    if (exp_struct.getNumber()<0) {
                        exp_struct.setNumber(-exp_struct.getNumber());
                    }
                    break;
            }
        }
    }
}
