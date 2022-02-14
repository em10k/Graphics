package emk;

//Класс, служащий для того, чтобы узнать является ли содержимое строки числом
class isNum {

    //Проверка для строки
    static boolean isNumber(String str_number){                                                                         //на вход получает строку
        try{
            Double.parseDouble(str_number);                                                                             //попытка привести строку к типу дабл
        }
        catch (NumberFormatException e) {                                                                               //отловить ошибку данного типа
            return false;
        }
        return true;
    }

    //Проверка для символа
    static boolean isNumber(char char_number){                                                                          //на вход получает символ
        try{
            String num = String.valueOf(char_number);                                                                   //преобразование char в String, т.к. char нельзя проиводить к типу дабл
            Double.parseDouble(num);                                                                                    //попытка привести строку к типу дабл
        }
        catch (NumberFormatException e) {                                                                               //отловить ошикбу данного типа
            return false;
        }
        return true;
    }
}
