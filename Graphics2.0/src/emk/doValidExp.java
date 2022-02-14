package emk;

//Класс, служащий для приведения формулы в корректную форму,если это необходимо
class doValidExp {

    //Дописать в конец необходимый символ, если формула заканчивается на операцию
    static String doValidEnd(String str, StructCalc exp_struct){
        //На случай, если в формула недописана
        if (!str.equals("")) {
            if (WhatItIs.isOperation(String.valueOf(str.charAt(str.length() - 1)))) {                                         //если последний символ операция
                if (str.charAt(str.length() - 1) != '|') {                                                                      //но не модуль
                    boolean endExpFlag = false;                                                                             //флаг конца выражения
                    for (int j = 0; j < str.length(); j++) {                                                                //пройти по выражению
                        if (WhatItIs.isVal(String.valueOf(str.charAt(j)))) {                                                //для поиска в нем переменной
                            endExpFlag = true;
                        }

                    }
                    if (endExpFlag) {                                                                                       //если переменная есть, значит выражение типа |x+
                        str = str + "0";                                                                                    //добавляем к концу 0
                    } else {                                                                                                   //если переменной нет, значит выражение типа |5+
                        if (exp_struct.getVal().equals("x")) {                                                              //определеяем какая перменная является текущей
                            str = str + "x";                                                                                //добавляем ее в конец выражения
                        } else {
                            str = str + "y";
                        }
                    }
                }
            }
        }
        return str;
    }

    //Дописать в конец второй знак модуля, если в формуле всего один такой знак
    static String doValidAbs(String str){
        //На случай, если в формуле не закрыт знак модуля
        if (!str.equals("")) {
            int abs_count = 0;
            for (int i = 0; i < str.length(); i++) {                                                                               //считаем количество знаков '|'
                if (str.charAt(i) == '|') abs_count++;
            }
            if (abs_count % 2 == 1) {                                                                                            //если нечетное количество, то
                str = str + "|";                                                                                              //то добавляем знак в конец
            }
        }
        return str;
    }
}
