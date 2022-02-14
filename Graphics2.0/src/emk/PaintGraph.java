package emk;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Класс, рисующий график
public class PaintGraph extends Graphics {
    void paint_graph(StructCalc exp_struct, GraphicsContext gc){                                                        //на вход получает структуру и поле рисования
        gc.setStroke(Color.RED);
        double step = exp_struct.getStep();                                                                                              //шаг, который определен в calculate
        int count = 0;

        //Прорисовка графика с делением на переменную (отдельно от остальных, т.к. там необходимо сделать разрыв между частями графика)
        for (int i=0; i<exp_struct.getElement_count()-1; i++){
            if ((exp_struct.getSign().equals("/"))&&(exp_struct.getSide().equals("R"))) {                               //если знак деление и х находит в правой части (10/х)
                if (i < (graphWidth / exp_struct.getScl_width()) * 2 * (1/step)) {
                    if (i == (exp_struct.getElement_count() - 1) / 2) i++;                                              //то центральную линию между двумя графиками не рисуем
                    gc.strokeLine(graphWidth / 2 + exp_struct.getX_array()[i] * exp_struct.getScl_width(),
                            (graphHeight / 2) - exp_struct.getY_array()[i] * exp_struct.getScl_width(),
                            graphWidth / 2 + exp_struct.getX_array()[i + 1] * exp_struct.getScl_width(),
                            (graphHeight / 2) - exp_struct.getY_array()[i + 1] * exp_struct.getScl_width());
                }
            }
            else {
                if (i < (graphWidth / exp_struct.getScl_width()) * 2 * (1/step)) {
                    count++;
                }
            }
        }

        //Прорисовка остальных графиков
        if (!exp_struct.getSide().equals("-")) {
            if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals(">")) ||                             //если есть знак
                    (exp_struct.getSign().equals("≤")) || (exp_struct.getSign().equals("≥"))) {
                gc.setStroke(Color.RED);                                                                                //то применить к кисточке красный цвет
                gc.setLineWidth(1);                                                                                     //толщину сделать 1
                gc.setFill(Color.rgb(200, 0, 0, 0.2));                                             //к заливке применить красный цвет с некотрой прозрачностью
            }
            if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals(">"))) {                             //если знак строгий
                gc.setLineDashes(7, 10);                                                                                //то линию рисовать пунктиром (7-длина линии, 10 - длина пропуска)
            }
            if (exp_struct.getVal().equals("x")) {                                                                      //если значение равно иксу
                if (exp_struct.getSide().equals("L")) {
                    if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals("≤"))) {                     //при этом меньше его
                        gc.fillRect(0, 0, graphWidth / 2 + exp_struct.getX_array()[count] * exp_struct.getScl_width(), graphHeight);    //закрасить левый квадрат
                    }
                    if ((exp_struct.getSign().equals(">")) || (exp_struct.getSign().equals("≥"))) {                     //если больше
                        gc.fillRect(graphWidth / 2 + exp_struct.getX_array()[count] * exp_struct.getScl_width(), 0, graphWidth, graphHeight); //закрасить правый квадрат
                    }
                }
                else{
                    if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals("≤"))) {                     //при этом меньше его
                        gc.fillRect(graphWidth / 2 + exp_struct.getX_array()[count] * exp_struct.getScl_width(), 0, graphWidth, graphHeight); //закрасить правый квадрат
                    }
                    if ((exp_struct.getSign().equals(">")) || (exp_struct.getSign().equals("≥"))) {                     //если больше
                        gc.fillRect(0, 0, graphWidth / 2 + exp_struct.getX_array()[count] * exp_struct.getScl_width(), graphHeight);    //закрасить левый квадрат
                    }
                }
            }
            if (exp_struct.getVal().equals("y")) {                                                                      //если значение равно игрику
                if (exp_struct.getSide().equals("L")) {
                    if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals("≤"))) {                     //при этом меньше его
                        gc.fillRect(0, (graphHeight / 2) - exp_struct.getY_array()[0] * exp_struct.getScl_width(), graphHeight, graphHeight); //закарасить нижний квадрат
                    }
                    if ((exp_struct.getSign().equals(">")) || (exp_struct.getSign().equals("≥"))) {                     //если больше
                        gc.fillRect(0, 0, graphHeight, (graphHeight / 2) - exp_struct.getY_array()[0] * exp_struct.getScl_width()); //закарасить верхний квадрат
                    }
                }
                else {
                    if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals("≤"))) {                     //при этом меньше его
                        gc.fillRect(0, 0, graphHeight, (graphHeight / 2) - exp_struct.getY_array()[0] * exp_struct.getScl_width()); //закарасить верхний квадрат
                    }
                    if ((exp_struct.getSign().equals(">")) || (exp_struct.getSign().equals("≥"))) {                     //если больше
                        gc.fillRect(0, (graphHeight / 2) - exp_struct.getY_array()[0] * exp_struct.getScl_width(), graphHeight, graphHeight); //закарасить нижний квадрат
                    }

                }
            }
            if (exp_struct.getIsAbs()){
                for (int i=0; i<exp_struct.getElement_count()-1; i++) {                                                 //если есть модуль, то линию нужно начертить по отрезкам
                    gc.strokeLine(graphWidth / 2 + exp_struct.getX_array()[i] * exp_struct.getScl_width(),
                            (graphHeight / 2) - exp_struct.getY_array()[i] * exp_struct.getScl_width(),
                            graphWidth / 2 + exp_struct.getX_array()[i + 1] * exp_struct.getScl_width(),
                            (graphHeight / 2) - exp_struct.getY_array()[i + 1] * exp_struct.getScl_width());
                }
            }
            else {
                gc.strokeLine(graphWidth / 2 + exp_struct.getX_array()[0] * exp_struct.getScl_width(),               //начертить линию одну по координатам
                        (graphHeight / 2) - exp_struct.getY_array()[0] * exp_struct.getScl_width(),
                        graphWidth / 2 + exp_struct.getX_array()[count] * exp_struct.getScl_width(),
                        (graphHeight / 2) - exp_struct.getY_array()[count] * exp_struct.getScl_width());
            }
            if ((exp_struct.getSign().equals("<")) || (exp_struct.getSign().equals(">")) ||                             //если был один из этих знаков
                    (exp_struct.getSign().equals("≤")) || (exp_struct.getSign().equals("≥"))) {                          //то вернуть старые значения
                gc.setStroke(Color.BLACK);
                gc.setLineDashes(0, 0);
                gc.setFill(Color.BLACK);
            }
        }
    }
}
