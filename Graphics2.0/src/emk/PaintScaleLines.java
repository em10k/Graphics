package emk;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Класс, рисующий линии разметки (масштаб)
public class PaintScaleLines extends Graphics{

    //Функция рисования масштаба
    void paint_scale_lines(int scl_width, GraphicsContext gc){                                                          //на вход получаем величину масштаба и поле рисования
        gc.clearRect(0, 0, formWidth, formHeight);
        gc.setStroke(Color.BLACK);
        double line_x1, line_y1, line_x2, line_y2;
        //Вертикальные линии от 0 до правого конца
        line_x1 = line_x2 = graphWidth/2;
        line_y1 = 0;
        line_y2 = graphHeight;
        for (int i=0; i<graphWidth/scl_width; i++){
            if (i==0){                                                                                                  //основные оси сделать толще
                gc.setLineWidth(2);
            }
            else{
                gc.setLineWidth(0.5);
            }
            gc.strokeLine(line_x1,line_y1,line_x2,line_y2);
            if((scl_width<=30)&&(scl_width>20)){                                                                        //при изменении масштаба выводить не все числа
                if (i%2==0) {
                    gc.fillText(String.valueOf(i), line_x1 + 3, (graphHeight) / 2 + 15);                           //преобразование из числа в строку
                }
            }
            if((scl_width<=20)&&(scl_width>10)){
                if (i%5==0) {
                    gc.fillText(String.valueOf(i), line_x1 + 3, (graphHeight) / 2 + 15);
                }
            }
            if((scl_width<=10)&&(scl_width>2)) {
                if (i % 10 == 0) {
                    gc.fillText(String.valueOf(i), line_x1 + 3, (graphHeight) / 2 + 15);
                }
            }
            if(scl_width>30) gc.fillText(String.valueOf(i),line_x1+3,(graphHeight)/2+15);
            line_x1 += scl_width;                                                                                       //сдвиг на право
            line_x2 += scl_width;
        }
        //Вертикальные линии от 0 до левого конца
        line_x1 = line_x2 = graphWidth/2;
        line_y1 = 0;
        line_y2 = graphHeight;
        for (int i=0; i<graphWidth/scl_width; i++){
            gc.strokeLine(line_x1,line_y1,line_x2,line_y2);
            if((scl_width<=30)&&(scl_width>20)){
                if ((i%2==0)&&(i!=0)) {
                    gc.fillText("-" + String.valueOf(i), line_x1 + 3, (graphHeight)/2 + 15);                   //преобразование из числа в строку
                }
            }
            if((scl_width<=20)&&(scl_width>10)){
                if ((i%5==0)&&(i!=0)) {
                    gc.fillText("-" + String.valueOf(i), line_x1 + 3, (graphHeight)/2 + 15);                   //преобразование из числа в строку
                }
            }
            if((scl_width<=10)&&(scl_width>2)) {
                if ((i%10==0)&&(i!=0)){
                    gc.fillText("-" + String.valueOf(i), line_x1 + 3, (graphHeight)/2 + 15);                   //преобразование из числа в строку
                }
            }
            if((scl_width>30)&&(i!=0)) gc.fillText("-" + String.valueOf(i), line_x1 + 3, (graphHeight)/2 + 15);//преобразование из числа в строку
            line_x1 -= scl_width;
            line_x2 -= scl_width;
        }
        //Горизонтальные линии от 0 вниз
        line_y1 = line_y2 = graphHeight/2;
        line_x1 = 0;
        line_x2 = graphWidth;
        for (int i=0; i<graphHeight/scl_width; i++){
            if (i==0){
                gc.setLineWidth(2);
            }
            else{
                gc.setLineWidth(0.5);
            }
            gc.strokeLine(line_x1,line_y1,line_x2,line_y2);
            if((scl_width<=30)&&(scl_width>20)){
                if ((i%2==0)&&(i!=0)) {
                    gc.fillText("-"+String.valueOf(i),graphWidth/2+3,line_y1+15);
                }
            }
            if((scl_width<=20)&&(scl_width>10)){
                if ((i%5==0)&&(i!=0)) {
                    gc.fillText("-"+String.valueOf(i),graphWidth/2+3,line_y1+15);
                }
            }
            if((scl_width<=10)&&(scl_width>2)) {
                if ((i%10==0)&&(i!=0)){
                    gc.fillText("-"+String.valueOf(i),graphWidth/2+3,line_y1+15);
                }
            }
            if((scl_width>30)&&(i!=0))  gc.fillText("-"+String.valueOf(i),graphWidth/2+3,line_y1+15);
            line_y1 = line_y1 + scl_width;
            line_y2 = line_y2 + scl_width;
        }
        //Горизонтальные линии от 0 вверх
        line_y1 = line_y2 = graphHeight/2;
        line_x1 = 0;
        line_x2 = graphWidth;
        for (int i=0; i<graphHeight/scl_width; i++){
            gc.strokeLine(line_x1,line_y1,line_x2,line_y2);
            if((scl_width<=30)&&(scl_width>20)){
                if ((i%2==0)&&(i!=0)) {
                    gc.fillText(String.valueOf(i),graphWidth/2+3,line_y1+15);
                }
            }
            if((scl_width<=20)&&(scl_width>10)){
                if ((i%5==0)&&(i!=0)) {
                    gc.fillText(String.valueOf(i),graphWidth/2+3,line_y1+15);
                }
            }
            if((scl_width<=10)&&(scl_width>2)) {
                if ((i%10==0)&&(i!=0)){
                    gc.fillText(String.valueOf(i),graphWidth/2+3,line_y1+15);
                }
            }
            if((scl_width>30)&&(i!=0))  gc.fillText(String.valueOf(i),graphWidth/2+3,line_y1+15);
            line_y1 = line_y1 - scl_width;
            line_y2 = line_y2 - scl_width;
        }
        gc.setLineWidth(1);
    }
}
