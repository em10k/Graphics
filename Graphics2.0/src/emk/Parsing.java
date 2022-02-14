package emk;

//Класс, служащий для парсинга формулы, заданной в тестовом поле
class Parsing {

    //Функция, выполняющая парсинг
    static StructCalc parse_formula(String expretion, StructCalc exp_struct) {                                          //на вход получаем строчку из лейбла
        StringBuilder exp_num_str;
        //if (exp_struct.getVal().equals("x")) {
            String tryStr = "";
            boolean absFlag = false;
            StringBuilder newExpretion = new StringBuilder();
            String outOfBounds = "";
            exp_struct.setMinusVar(false);
            exp_struct.setAbs(false);
            for (int i = 0; i<expretion.length(); i++){
                if (WhatItIs.isVal(String.valueOf(expretion.charAt(i)))) {
                    try {
                        if (expretion.charAt(i - 1) == '-') {
                            outOfBounds = "1";
                            if (expretion.charAt(i - 2) == '|') {
                                outOfBounds += "2";
                                exp_struct.setMinusVar(true);
                                exp_struct.setAbs(true);
                            }
                        }
                    }
                    catch (StringIndexOutOfBoundsException e) {
                        System.out.println(outOfBounds+" Вылетел за стринг");
                    }
                    if (outOfBounds.equals("12")) {                                                                     // 5+|-x|
                        for (int j = 0; j < expretion.length(); j++) {
                            if (j == i - 1) {
                                j++;
                            }
                            newExpretion.append(expretion.charAt(j));
                        }
                        expretion = String.valueOf(newExpretion);
                    }
                    else{
                        if (i-2>=0) {
                            if (WhatItIs.isOperation(String.valueOf(expretion.charAt(i - 2)))) {                        //-|x|
                                if (expretion.charAt(i-1)=='|'){
                                    if (expretion.charAt(i-2)=='-'){
                                        expretion="0"+expretion;
                                        outOfBounds+="3";
                                        break;
                                    }
                                }
                                else {
                                    for (int j = 0; j < expretion.length(); j++) {
                                        //if (i - 2 == j) {
                                        if (j+1!=expretion.length()) {
                                            if (expretion.charAt(j + 1) == '-') {
                                                j++;
                                            }
                                        }
                                        //if (expretion.charAt(j) != '-') {
                                        newExpretion.append(expretion.charAt(j));
                                        //}
                                    }
                                    expretion = String.valueOf(newExpretion);
                                }
                            }
                        }
//                        if((i-1 == 0)&&(outOfBounds.equals("1"))){                                                      //  -x
//                            exp_struct.setMinusVar(true);
//                        }
                        if((expretion.length()==2)&&(expretion.charAt(0))=='-'){                                        // -x
                            expretion = "0"+expretion;
                        }
                        else if(outOfBounds.equals("")) {                                                               //   и другие операции
                            for (int j = 0; j < expretion.length(); j++) {
                                if (expretion.charAt(j) != '-') {
                                    newExpretion.append(expretion.charAt(j));
                                }
                            }
                            expretion = String.valueOf(newExpretion);
                        }
                    }
                }
            }
//*******************************************************************************************
            for (int i = 0; i < expretion.length(); i++) {
                int l;
                if (expretion.charAt(i) == '|') {                                                                       //в выражении ищем модуль
                    if (!exp_struct.getIsAbs()) {                                                                       //если он раньше зафиксирован не был
                        exp_struct.setAbs(true);                                                                        //то зафиксировать
                    }
                    l = i + 1;
                    while (l != expretion.length()) {                                                                   //переписываем выражение,находящее в скобках в строку
                        if (expretion.charAt(l) == '|') {
                            absFlag = !absFlag;
                        }
                        if (!absFlag) {
                            tryStr = tryStr + expretion.charAt(l);
                        }
                        l++;
                    }
                    if (WhatItIs.isVal(tryStr)) {                                                                       //если строка является переменной |x|, |y|
                        exp_struct.setAbsExp(enumAbs.enumAbsExp.onlyVal);
                    } else if (isNum.isNumber(tryStr)) {                                                                //если строка является числом |5|
                        exp_struct.setAbsExp(enumAbs.enumAbsExp.onlyNum);
                    } else
                        exp_struct.setAbsExp(enumAbs.enumAbsExp.allExp);                                                //остальное - если строка является выражением |x+5|
                }
//*******************************************************************************************
                //Для 3х вариантов: если х, если |x|, если 2х или 2|x|
                if (WhatItIs.isVal(String.valueOf(expretion.charAt(i)))) {
                    if (i != 0) {
                        if (isNum.isNumber(expretion.charAt(i - 1))) {                                                  //если 2х
                            exp_struct.setSign("*");
                            exp_struct.setSide("R");
                            exp_num_str = new StringBuilder();
                            for (int k = expretion.length() - 1; k >= 0; k--) {                                         //от предпоследнего элемента идем до нулевого
                                if (WhatItIs.isOperation(String.valueOf(expretion.charAt(i))) || (k == 0)) {            //если встретилась операция или дошли до конца - тут char преобразовали в String
                                    for (int j = k; !WhatItIs.isVal(String.valueOf(expretion.charAt(j))); j++) {        //то идем от текущего элемента до переменной
                                        if (expretion.charAt(j) != '|') {                                               //если 2|x|
                                            exp_num_str.append(expretion.charAt(j));
                                            exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                                        }
                                    }
                                }
                            }
                        }
                        if (WhatItIs.isOperation(String.valueOf(expretion.charAt(i - 1))) && (expretion.length() == 3)) {   //если |x|
                            if ((expretion.length()/2 == expretion.indexOf("x"))||(expretion.length()/2 == expretion.indexOf("y"))){
                                if (WhatItIs.isOperation(String.valueOf(expretion.charAt(i+1)))){
                                    exp_struct.setSign("+");
                                    exp_struct.setSide("R");
                                    exp_struct.setNumber(0);
                                }
                            }
                        }
                    } else {
                        exp_struct.setSign("+");                                                                        //если х
                        exp_struct.setSide("R");
                        exp_struct.setNumber(0);
                    }
                }
//*******************************************************************************************
                if (expretion.charAt(i) == '+') {
                    exp_struct.setSign("+");
                    for (int k = 0; k < i; k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("L");
                            exp_num_str = new StringBuilder();
                            for (int j = i + 1; j < expretion.length(); j++) {
                                if (expretion.charAt(j) != '|') {
                                    exp_num_str.append(expretion.charAt(j));
                                }
                            }
                            exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            break;
                        }
                    }
                    for (int k = i + 1; k < expretion.length(); k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("R");
                            exp_num_str = new StringBuilder();
                            for (int j = 0; j < i; j++) {
                                if (!WhatItIs.isOperation(String.valueOf(expretion.charAt(j)))||((expretion.charAt(j) == '|'))) {
                                    if (expretion.charAt(j) != '|') {
                                        exp_num_str.append(expretion.charAt(j));
                                    }
                                }
                            }
                            exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            break;
                        }
                    }
                }
//*******************************************************************************************
                if (expretion.charAt(i) == '-') {
                    exp_struct.setSign("-");
                    for (int k = 0; k < i; k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("L");
                            exp_num_str = new StringBuilder();
                            for (int j = i + 1; j < expretion.length(); j++) {
                                if (expretion.charAt(j) != '|') {
                                    exp_num_str.append(expretion.charAt(j));

                                }
                            }
                            exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            break;
                        }
                    }
                    for (int k = i + 1; k < expretion.length(); k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("R");
                            exp_num_str = new StringBuilder();
                            for (int j = 0; j < i; j++) {
                                if (!WhatItIs.isOperation(String.valueOf(expretion.charAt(j)))||((expretion.charAt(j) == '|'))) {
                                    if (expretion.charAt(j) != '|') {
                                        exp_num_str.append(expretion.charAt(j));
                                    }
                                }
                            }
                            exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            break;
                        }
                    }
                }
//*******************************************************************************************
                if (expretion.charAt(i) == '*') {
                    exp_struct.setSign("*");
                    for (int k = 0; k < i; k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("L");
                            exp_num_str = new StringBuilder();
                            for (int j = i + 1; j < expretion.length(); j++) {
                                if (expretion.charAt(j) != '|') {
                                    exp_num_str.append(expretion.charAt(j));
                                    exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                                }
                            }
                            break;
                        }
                    }
                    for (int k = i + 1; k < expretion.length(); k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("R");
                            exp_num_str = new StringBuilder();
                            for (int j = 0; j < i; j++) {
                                if (expretion.charAt(j) != '|') {
                                    exp_num_str.append(expretion.charAt(j));
                                    exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                                }
                            }
                            break;
                        }
                    }
                }
//*******************************************************************************************
                if (expretion.charAt(i) == '÷') {
                    exp_struct.setSign("/");
                    for (int k = 0; k < i; k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("L");
                            exp_num_str = new StringBuilder();
                            for (int j = i + 1; j < expretion.length(); j++) {
                                if (expretion.charAt(j) != '|') {
                                    exp_num_str.append(expretion.charAt(j));
                                    exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                                }
                            }
                            break;
                        }
                    }
                    for (int k = i + 1; k < expretion.length(); k++) {
                        if (WhatItIs.isVal(String.valueOf(expretion.charAt(k)))) {
                            exp_struct.setSide("R");
                            exp_num_str = new StringBuilder();
                            for (int j = 0; j < i; j++) {
                                if (expretion.charAt(j) != '|') {
                                    exp_num_str.append(expretion.charAt(j));
                                    exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                                }
                            }
                            break;
                        }
                    }
                }
//*******************************************************************************************
                if ((expretion.charAt(i) == '<') || (expretion.charAt(i) == '≤')) {
                    exp_struct.setSign(String.valueOf(expretion.charAt(i)));
                    if (WhatItIs.isVal(String.valueOf(expretion.charAt(i-1)))) {
                        exp_struct.setSide("L");
                        exp_num_str = new StringBuilder();
                        for (int j = i + 1; j < expretion.length(); j++) {
                            if (expretion.charAt(j) != '|') {
                                exp_num_str.append(expretion.charAt(j));
                                exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            }
                        }
                    } else {
                        exp_struct.setSide("R");
                        exp_num_str = new StringBuilder();
                        for (int j = 0; j < i; j++) {
                            if (expretion.charAt(j) != '|') {
                                exp_num_str.append(expretion.charAt(j));
                                exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            }
                        }
                    }
                }
//*******************************************************************************************
                if ((expretion.charAt(i) == '>') || (expretion.charAt(i) == '≥')) {
                    exp_struct.setSign(String.valueOf(expretion.charAt(i)));
                    if (WhatItIs.isVal(String.valueOf(expretion.charAt(i-1)))) {
                        exp_struct.setSide("L");
                        exp_num_str = new StringBuilder();
                        for (int j = i + 1; j < expretion.length(); j++) {
                            if (expretion.charAt(j) != '|') {
                                exp_num_str.append(expretion.charAt(j));
                                exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            }
                        }
                    } else {
                        exp_struct.setSide("R");
                        exp_num_str = new StringBuilder();
                        for (int j = 0; j < i; j++) {
                            if (expretion.charAt(j) != '|') {
                                exp_num_str.append(expretion.charAt(j));
                                exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                            }
                        }
                    }
                }
//*******************************************************************************************
                if (i == expretion.length() - 1) {
                    exp_num_str = new StringBuilder();
                    for (int j = 0; j <= i; j++) {
                        if (expretion.charAt(j) != '|') {
                            exp_num_str.append(expretion.charAt(j));
                        }
                    }
                    if (isNum.isNumber(exp_num_str.toString())) {
                        exp_struct.setNumber(Double.parseDouble(exp_num_str.toString()));
                        exp_struct.setSide("");
                        exp_struct.setSign("");
                    }
                }
            }
        //}
        return exp_struct;
    }
}
