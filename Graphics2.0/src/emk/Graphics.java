package emk;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;
import jdk.nashorn.internal.objects.Global;

import javax.swing.*;
import javax.swing.text.Style;
import javax.tools.Tool;
import java.awt.event.MouseEvent;
import java.io.*;

//Главный класс приложения, необходимый для отображения всех элементов на форме, управляющий всеми классами и функциями
public class Graphics extends Application{

    private StructCalc exp_struct;
    private PaintScaleLines paintScaleLines;
    private Calculate calc;
    private PaintGraph paintGraph;

    private GraphicsContext gc;
    double formWidth = 1100;                                                                                            //ширина формы
    double formHeight = 900;                                                                                            //высота формы
    double graphWidth = 900;                                                                                  //ширина формы
    double graphHeight = 900;                                                                                    //высота формы

    private ComboBox<String> cb_func;
    private Label lb_display, lb_tooltip;
    private Button btn_zero,btn_one,btn_two,btn_three,btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine;
    private Button btn_x,btn_y,btn_clear,btn_plus,btn_minus,btn_mult,btn_division,btn_point,btn_del,btn_ok;
    private Button btn_less, btn_more, btn_abs;

    private boolean flag_btn_less= false;
    private boolean flag_btn_more = false;

    //Основная фнукция запуска приложения
    public static void main(String[] args) {
        launch(args);
    }

    //Конструктор класса, вызывающийся при старте приложения
    public void start(Stage myStage){

        exp_struct = new StructCalc();
        paintScaleLines = new PaintScaleLines();
        calc = new Calculate();
        paintGraph = new PaintGraph();


        myStage.setTitle("Графопостроитель");
        HBox HMainBox = new HBox();
            HMainBox.setAlignment(Pos.CENTER);
            HMainBox.setSpacing(2);
        VBox VRight_panel = new VBox();
            VRight_panel.setAlignment(Pos.CENTER_LEFT);
            VRight_panel.setSpacing(2);
        BorderPane BP_panel_tooltip = new BorderPane();

        BorderPane HCb_lb = new BorderPane();
        HCb_lb.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200,0.5),null,null))); //Фон лейбла
        HBox H_XYclear = new HBox();
            H_XYclear.setAlignment(Pos.CENTER);
        GridPane GSigns = new GridPane();
            GSigns.setAlignment(Pos.CENTER);
        GridPane GNumbers = new GridPane();
            GNumbers.setAlignment(Pos.CENTER);
        Scene myScene = new Scene(HMainBox,formWidth,formHeight);
        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.show();
        //*****************************************

        Canvas myCanvas = new Canvas(graphWidth,graphHeight);                                                           //создать холст
        gc = myCanvas.getGraphicsContext2D();                                                                           //получить граф. контекст для холста

        Separator VSep_graph_panel= new Separator();                                                                    //разделитель
            VSep_graph_panel.setOrientation(Orientation.VERTICAL);
        Separator HSep_disp_x = new Separator();
            HSep_disp_x.setOrientation(Orientation.HORIZONTAL);
        Separator HSep_x_signs = new Separator();
            HSep_x_signs.setOrientation(Orientation.HORIZONTAL);
        Separator HSep_signs_num = new Separator();
            HSep_signs_num.setOrientation(Orientation.HORIZONTAL);
        Separator HSep_num_ok = new Separator();
            HSep_num_ok.setOrientation(Orientation.HORIZONTAL);

        Font smallFont = new Font("Kristen ITC",20);
        Font bigFont = new Font("Kristen ITC",25);

        lb_display = new Label();
        lb_display.setAlignment(Pos.CENTER_RIGHT);
        lb_display.setFont(Font.font(25));
        lb_display.setMaxSize(100,30);
        lb_display.setFont(Font.font("Kristen ITC",20));

        int bigBtnSize = 60;                                                                                            //размер большой кнопки
        int smallBtnSize = 45;                                                                                          //размер мелкой кнопки

        lb_tooltip = new Label();
        lb_tooltip.setMinSize(bigBtnSize*3,bigBtnSize*2);
        lb_tooltip.setMaxSize(bigBtnSize*3,bigBtnSize*2);
        lb_tooltip.setFont(smallFont);
        lb_tooltip.setWrapText(true);                                                                                   //разрешить перенос текста


        btn_x = new Button("x");
        btn_x.setMinSize(smallBtnSize, smallBtnSize);
            btn_x.setOnAction(btn_Handler);
            btn_x.setFont(smallFont);
        btn_y = new Button("y");
            btn_y.setMinSize(smallBtnSize, smallBtnSize);
            btn_y.setDisable(true);
            btn_y.setOnAction(btn_Handler);
            btn_y.setFont(smallFont);
        btn_clear = new Button("Отчистить");
            btn_clear.setMinSize(smallBtnSize *2, smallBtnSize);
            btn_clear.setOnAction(btn_Handler);
            btn_clear.setFont(Font.font("Kristen ITC",15));
        btn_plus = new Button("+");
            btn_plus.setMinSize(smallBtnSize, smallBtnSize);
            btn_plus.setOnAction(btn_Handler);
            btn_plus.setFont(smallFont);
        btn_minus = new Button("-");
            btn_minus.setMinSize(smallBtnSize, smallBtnSize);
            btn_minus.setOnAction(btn_Handler);
            btn_minus.setFont(smallFont);
        btn_mult = new Button("*");
            btn_mult.setMinSize(smallBtnSize, smallBtnSize);
            btn_mult.setOnAction(btn_Handler);
            btn_mult.setFont(smallFont);
        btn_division = new Button("÷");
            btn_division.setMinSize(smallBtnSize, smallBtnSize);
            btn_division.setOnAction(btn_Handler);
            btn_division.setFont(smallFont);

        //Задаем анимацию подсказки
        KeyValue initKeyValue = new KeyValue(lb_tooltip.translateYProperty(),85 );                              //начальное положение текста
                                        // для лейбла - по оси Y       отступ от верхней точки вниз на 85
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);                                                 //первый фрейм
                                        //длительность появления 0 в начальной точке
        KeyValue endKeyValue = new KeyValue(lb_tooltip.translateYProperty(), 15);                               //конечное положение текста
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.5), endKeyValue);                                             //конечный фрейм
        Timeline timeline = new Timeline(initFrame,endFrame);                                                           //задаем анимацию

        btn_less = new Button("<");
            btn_less.setMinSize(smallBtnSize, smallBtnSize);
            btn_less.setOnAction(btn_Handler);
            btn_less.setFont(smallFont);
            btn_less.setOnMouseEntered(event -> {
                lb_tooltip.setTextFill(Color.BLACK);
                if (btn_less.getText().equals("<")) {
                    lb_tooltip.setText("Нажмите еще раз, чтобы получить '≤'");
                }
                else{
                    lb_tooltip.setText("Нажмите еще раз, чтобы получить '<'");
                }
                timeline.play();                                                                                        //запускаем анимацию
            });
            btn_less.setOnMouseExited(event -> {
                lb_tooltip.setText("");
                timeline.stop();                                                                                        //останавливаем анимацию
            });
        btn_more = new Button(">");
            btn_more.setMinSize(smallBtnSize, smallBtnSize);
            btn_more.setOnAction(btn_Handler);
            btn_more.setFont(smallFont);
            btn_more.setOnMouseEntered(event -> {
                lb_tooltip.setTextFill(Color.BLACK);
                if (btn_more.getText().equals(">")) {
                    lb_tooltip.setText("Нажмите еще раз, чтобы получить '≥'");
                }
                else{
                    lb_tooltip.setText("Нажмите еще раз, чтобы получить '>'");
                }
                timeline.play();
            });
            btn_more.setOnMouseExited(event -> {
                lb_tooltip.setText("");
                timeline.stop();
            });

        btn_abs = new Button("|a|");
            btn_abs.setMinSize(smallBtnSize, smallBtnSize);
            btn_abs.setOnAction(btn_Handler);
            btn_abs.setFont(Font.font("Kristen ITC",15));
            btn_abs.setOnMouseEntered(event -> {
                lb_tooltip.setTextFill(Color.BLACK);
                int abs_count = 0;
                for (int i=0;i<lb_display.getText().length();i++){
                    if (lb_display.getText().charAt(i)=='|') abs_count++;
                }
                final int absCount = abs_count;
                if (absCount%2==0) {
                    lb_tooltip.setText("Нажмите, чтобы открыть модуль");
                }
                else{
                    if (lb_display.getText().charAt(lb_display.getText().length()-1)!='|') {
                        lb_tooltip.setText("Нажмите еще раз, чтобы закрыть модуль");
                    }
                    else{
                        lb_tooltip.setText("Напишите символ");
                    }
                }
                timeline.play();
            });
            btn_abs.setOnMouseExited(event -> {
                lb_tooltip.setText("");
                timeline.stop();
            });


        btn_zero = new Button("0");
        btn_zero.setMinSize(bigBtnSize, bigBtnSize);
            btn_zero.setOnAction(btn_Handler);
            btn_zero.setFont(bigFont);
        btn_one = new Button("1");
            btn_one.setMinSize(bigBtnSize, bigBtnSize);
            btn_one.setOnAction(btn_Handler);
            btn_one.setFont(bigFont);
        btn_two = new Button("2");
            btn_two.setMinSize(bigBtnSize, bigBtnSize);
            btn_two.setOnAction(btn_Handler);
            btn_two.setFont(bigFont);
        btn_three = new Button("3");
            btn_three.setMinSize(bigBtnSize, bigBtnSize);
            btn_three.setOnAction(btn_Handler);
            btn_three.setFont(bigFont);
        btn_four = new Button("4");
            btn_four.setMinSize(bigBtnSize, bigBtnSize);
            btn_four.setOnAction(btn_Handler);
            btn_four.setFont(bigFont);
        btn_five = new Button("5");
            btn_five.setMinSize(bigBtnSize, bigBtnSize);
            btn_five.setOnAction(btn_Handler);
            btn_five.setFont(bigFont);
        btn_six = new Button("6");
            btn_six.setMinSize(bigBtnSize, bigBtnSize);
            btn_six.setOnAction(btn_Handler);
            btn_six.setFont(bigFont);
        btn_seven = new Button("7");
            btn_seven.setMinSize(bigBtnSize, bigBtnSize);
            btn_seven.setOnAction(btn_Handler);
            btn_seven.setFont(bigFont);
        btn_eight = new Button("8");
            btn_eight.setMinSize(bigBtnSize, bigBtnSize);
            btn_eight.setOnAction(btn_Handler);
            btn_eight.setFont(bigFont);
        btn_nine = new Button("9");
            btn_nine.setMinSize(bigBtnSize, bigBtnSize);
            btn_nine.setOnAction(btn_Handler);
            btn_nine.setFont(bigFont);
            
        ImageView delImage = new ImageView("file:C:/Users/635_6/Desktop/Java/Projects/Graphics2.0/src/pictures/del.png"); //создали иконку
            delImage.setFitHeight(30);                                                                                  //показали размеры
            delImage.setFitWidth(30);
        btn_del = new Button("Удал.", delImage);                                                                   //создали кнопку и повесили иконку
            btn_del.setMinSize(bigBtnSize, bigBtnSize);
            btn_del.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);                                                     //выставили, чтобы отображалась только иконка
            btn_del.setOnAction(btn_Handler);
            //btn_del.setDisable(true);
        btn_point = new Button(".");
            btn_point.setMinSize(bigBtnSize, bigBtnSize);
            btn_point.setOnAction(btn_Handler);
            btn_point.setFont(Font.font("Kristen ITC",25));
        ImageView gearsImage = new ImageView("file:C:/Users/635_6/Desktop/Java/Projects/Graphics2.0/src/pictures/gears.png"); //создали иконку
        gearsImage.setFitHeight(40);                                                                                    //показали размеры
        gearsImage.setFitWidth(40);
        btn_ok = new Button("Построить", gearsImage);
            btn_ok.setMinSize(bigBtnSize*3, smallBtnSize);
            btn_ok.setDisable(true);
            btn_ok.setOnAction(btn_Handler);
            btn_ok.setFont(smallFont);

        //Нарисовать линии сетки графики
        paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);

        //Чтение из файла
        try{
            FileInputStream inputStream = new FileInputStream("C:/Users/635_6/Desktop/Java/Projects/Graphics2.0/src/emk/formula.txt");
            byte[] inpStreamArray = new byte [inputStream.available()];                                                 //массив char длиной в inputStream, т.е. в строку из файла
            inputStream.read(inpStreamArray);                                                                           //прочитать строку в массив
            String strLine = new String(inpStreamArray);                                                                //из массива сделать строку
            inputStream.close();                                                                                        //закрываем поток на чтение
            if (strLine.equals("")){                                                                                    //если строка пустая, то выставить прочерки
                exp_struct.setSide("-");
                exp_struct.setSign("-");
            }
            System.out.println(strLine);
            lb_display.setText(strLine);
            //Приводим формулу к корректному виду, если это необходимо
            lb_display.setText(doValidExp.doValidEnd(lb_display.getText(),exp_struct));
            lb_display.setText(doValidExp.doValidAbs(lb_display.getText()));

            for (int i = 0; i < strLine.length(); i++) {                                                                //проходим по строке
                if (strLine.charAt(i)=='x') {                                                                           //ищем в ней икс
                    exp_struct.setVal("x");                                                                             //если есть, то запоминаем его как текущение значение
                    break;                                                                                              //выходим из цикла
                }
                else if(strLine.charAt(i)=='y') {                                                                       //ищем в ней игрик
                    exp_struct.setVal("y");                                                                             //если есть игрик, то запоминаем его
                    break;
                }
                else{
                    exp_struct.setVal("x");                                                                             //икс запоминаем его как текущение значение
                    if (i==strLine.length()-1) {
                        lb_display.setText("");
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Файла не существует!");
        }
        catch (IOException e){
            System.out.println("Ошибка при чтении из файла");
        }

        //Работа с выпадающим списком
        ObservableList<String> funcTypes = FXCollections.observableArrayList("f(x)=", "f(y)=");                   //элементы находящиеся в вып. списке
        cb_func = new ComboBox<>(funcTypes);
        if (exp_struct.getVal().equals("x")){                                                                           //если переменная задана как икс
            cb_func.setValue("f(x)=");                                                                                  //то выставим его, как текущее
            btn_x.setDisable(false);
            btn_y.setDisable(true);
        }
        else {
            cb_func.setValue("f(y)=");
            btn_x.setDisable(true);
            btn_y.setDisable(false);
        }

        cb_func.setPrefSize(bigBtnSize +10, smallBtnSize);
        cb_func.setStyle("-fx-font-family: 'Kristen ITC'; -fx-font-size:12");                                           //задаем шрифт и его размер для вып. списка
        Tooltip func_tt = new Tooltip("Нажмите 'F', чтобы навести фокус на элемент");
        func_tt.setFont(Font.font("Kristen ITC",15));
        cb_func.setTooltip(func_tt);
        cb_func.setOnAction(event -> {
            if(cb_func.getValue().equals("f(x)=")){
                btn_x.setDisable(false);
                btn_y.setDisable(true);
                btn_clear.setDisable(true);
                btn_del.setDisable(true);
                btn_less.setDisable(false);
                btn_more.setDisable(false);
                lb_display.setText("");
                exp_struct.setVal("x");
                exp_struct.setAbs(false);
                paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);
            }
            else{
                btn_x.setDisable(true);
                btn_y.setDisable(false);
                btn_clear.setDisable(true);
                btn_del.setDisable(true);
                btn_less.setDisable(false);
                btn_more.setDisable(false);
                lb_display.setText("");
                exp_struct.setVal("y");
                exp_struct.setAbs(false);
                paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);
            }
        });
        cb_func.setOnMouseEntered(event -> {
            lb_tooltip.setText("Выберите функцию");
            timeline.play();
        });
        cb_func.setOnMouseExited(event -> {
            lb_tooltip.setText("");
            timeline.stop();
        });

        //Блокировка или разблокировка элементов в зависимости от содержимого лейбла
        int lb_length = lb_display.getText().length();                                                                  //длина введенной формулы
        if(lb_length>0){                                                                                                //при длине больше, чем 0 символов
            btn_del.setDisable(false);
            btn_clear.setDisable(false);
            Parsing.parse_formula(lb_display.getText(),exp_struct);
            calc.calculate(exp_struct);
            paintGraph.paint_graph(exp_struct,gc);
        }
        else{
            btn_del.setDisable(true);
            btn_clear.setDisable(true);
            Parsing.parse_formula(lb_display.getText(),exp_struct);
            calc.calculate(exp_struct);
            paintGraph.paint_graph(exp_struct,gc);
        }


        //Добавление элементов в сетку
        HCb_lb.setLeft(cb_func);
        HCb_lb.setRight(lb_display);

        H_XYclear.getChildren().addAll(btn_x, btn_y, btn_clear);

        GSigns.add(btn_plus,0,0);
        GSigns.add(btn_minus,1,0);
        GSigns.add(btn_mult,2,0);
        GSigns.add(btn_division,3,0);
        GSigns.add(btn_less,0,1);
        GSigns.add(btn_more,1,1);
        GSigns.add(btn_abs,2,1);

        GNumbers.add(btn_seven,0,0);
        GNumbers.add(btn_eight,1,0);
        GNumbers.add(btn_nine,2,0);
        GNumbers.add(btn_four,0,1);
        GNumbers.add(btn_five,1,1);
        GNumbers.add(btn_six,2,1);
        GNumbers.add(btn_one,0,2);
        GNumbers.add(btn_two,1,2);
        GNumbers.add(btn_three,2,2);
        GNumbers.add(btn_point,0,3);
        GNumbers.add(btn_zero,1,3);
        GNumbers.add(btn_del,2,3);

        VRight_panel.getChildren().addAll(HCb_lb,HSep_disp_x,H_XYclear,HSep_x_signs,GSigns,HSep_signs_num,GNumbers,HSep_num_ok,btn_ok);

        BP_panel_tooltip.setCenter(VRight_panel);
        BP_panel_tooltip.setBottom(lb_tooltip);

        HMainBox.getChildren().addAll(myCanvas,VSep_graph_panel,BP_panel_tooltip);

        //Действия,совершаемые при кручении колесика мышкии
        myScene.setOnScroll(event -> {
            gc.clearRect(0, 0, formWidth, formHeight);                                                             //обозначали квадрат, в котором должен находиться курсор, для срабатывания действия
            int scl = exp_struct.getScl_width();
            if (event.isAltDown()) {                                                                                    //если кнопка alt нажата
                if(scl!=126) {                                                                                          //если максимальное значение не достигнуто
                    scl+=2;                                                                                             //то увеличить масштаб
                    exp_struct.setScl_width(scl);                                                                       //записать значение в структуру
                    paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);                                   //перечертить линии
                }
                else{                                                                                                   //если достигнуто, то увеличивать больше не надо
                    paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);                                   //перечертить линии
                }
                if (!lb_display.getText().equals("")){                                                                  //если поле с формулой пустое (очищено)
                    paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);                                   //перечертить линии
                    calc.calculate(exp_struct);                                                                         //вызвать функцию и рассчитать новые значения х и у
                    paintGraph.paint_graph(exp_struct,gc);                                                              //перерисовать график
                }
            }
            else {
                if(scl!=4) {
                    scl-= 2;
                    exp_struct.setScl_width(scl);
                    paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);                                   //перечертить линии
                }
                else{
                    paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);                                   //перечертить линии
                }
                if (!lb_display.getText().equals("")){
                    paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);                                   //перечертить линии
                    calc.calculate(exp_struct);
                    paintGraph.paint_graph(exp_struct,gc);
                }
            }
        });
        if ((exp_struct.getSide().equals("L"))&&(exp_struct.getSign().equals("/"))&&(exp_struct.getNumber()==0)){
            //JOptionPane.showMessageDialog(null, "Деление на ноль!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            lb_tooltip.setTextFill(Color.RED);
            lb_tooltip.setText("Ошибка!\nДеление на ноль!");
        }
        //Связываем кнопки на форме с кнопками на физической клавиатуре
        myStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent->{
            switch (keyEvent.getCode()){
                case NUMPAD0:
                    btn_zero.fire();
                    break;
                case NUMPAD1:
                    btn_one.fire();
                    break;
                case NUMPAD2:
                    btn_two.fire();
                    break;
                case NUMPAD3:
                    btn_three.fire();
                    break;
                case NUMPAD4:
                    btn_four.fire();
                    break;
                case NUMPAD5:
                    btn_five.fire();
                    break;
                case NUMPAD6:
                    btn_six.fire();
                    break;
                case NUMPAD7:
                    btn_seven.fire();
                    break;
                case NUMPAD8:
                    btn_eight.fire();
                    break;
                case NUMPAD9:
                    btn_nine.fire();
                    break;
                case DECIMAL:
                    btn_point.fire();
                    break;
                case ADD:
                    btn_plus.fire();
                    break;
                case SUBTRACT:
                    btn_minus.fire();
                    break;
                case MULTIPLY:
                    btn_mult.fire();
                    break;
                case DIVIDE:
                    btn_division.fire();
                    break;
                case BACK_SPACE:
                    btn_del.fire();
                    break;
                case DELETE:
                    btn_clear.fire();
                    break;
                case COMMA:
                    btn_less.fire();
                    break;
                case PERIOD:
                    btn_more.fire();
                    break;
                case BACK_SLASH:
                    btn_abs.fire();
                    break;
                case X:
                    btn_x.fire();
                    break;
                case Y:
                    btn_y.fire();
                    break;
                case ENTER:
                    btn_ok.fire();
                    break;
                case F:
                    cb_func.requestFocus();                                                                             //запросить фокус на элемент
                    break;
            }
        });
    }

    //Функция выполняющаяся при закрытии прилоежния
    public void stop(){
        //Запись в файл
        try{
            FileWriter fileWriter = new FileWriter("C:/Users/635_6/Desktop/Java/Projects/Graphics2.0/src/emk/formula.txt", false);
            fileWriter.write(lb_display.getText());
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("Ошибка при создании и записи в файл файла");
            //e.printStackTrace();
        }
    }

    //Действия, совершаемые при нажатии на различные кнопки
    private EventHandler<ActionEvent> btn_Handler = event -> {
        lb_tooltip.setText("");
        if (event.getSource() == btn_zero) {
            System.out.println("0");
            if ((lb_display.getText().equals(""))||(!lb_display.getText().equals("0"))) {                               //пустая строка, либо не 0 в ней
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {  //предыдущий символ не переменная (х0)
                        lb_display.setText(lb_display.getText() + "0");
                    }
                }
                else lb_display.setText(lb_display.getText() + "0");
            }
        }
        if (event.getSource() == btn_one){
            System.out.println("1");
            if(lb_display.getText().equals("0")){
                lb_display.setText("1");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "1");
                    }
                }
                else lb_display.setText(lb_display.getText() + "1");
            }
        }
        if (event.getSource() == btn_two) {
            System.out.println("2");
            if(lb_display.getText().equals("0")){
                lb_display.setText("2");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "2");
                    }
                }
                else lb_display.setText(lb_display.getText() + "2");
            }
        }
        if (event.getSource() == btn_three){
            System.out.println("3");
            if(lb_display.getText().equals("0")){
                lb_display.setText("3");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "3");
                    }
                }
                else lb_display.setText(lb_display.getText() + "3");
            }
        }
        if (event.getSource() == btn_four) {
            System.out.println("4");
            if(lb_display.getText().equals("0")){
                lb_display.setText("4");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "4");
                    }
                }
                else lb_display.setText(lb_display.getText() + "4");
            }
        }
        if (event.getSource() == btn_five){
            System.out.println("5");
            if(lb_display.getText().equals("0")){
                lb_display.setText("5");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "5");
                    }
                }
                else lb_display.setText(lb_display.getText() + "5");
            }
        }
        if (event.getSource() == btn_six) {
            System.out.println("6");
            if(lb_display.getText().equals("0")){
                lb_display.setText("6");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "6");
                    }
                }
                else lb_display.setText(lb_display.getText() + "6");
            }
        }
        if (event.getSource() == btn_seven){
            System.out.println("7");
            if(lb_display.getText().equals("0")){
                lb_display.setText("7");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "7");
                    }
                }
                else lb_display.setText(lb_display.getText() + "7");
            }
        }
        if (event.getSource() == btn_eight) {
            System.out.println("8");
            if(lb_display.getText().equals("0")){
                lb_display.setText("8");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "8");
                    }
                }
                else lb_display.setText(lb_display.getText() + "8");
            }
        }
        if (event.getSource() == btn_nine){
            System.out.println("9");
            if(lb_display.getText().equals("0")){
                lb_display.setText("9");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1)))) {
                        lb_display.setText(lb_display.getText() + "9");
                    }
                }
                else lb_display.setText(lb_display.getText() + "9");
            }
        }
        if (event.getSource() == btn_del){
            System.out.println("DEL");
            String str = lb_display.getText();
            String newStr ="";
            int absInStr = 0;
            if (str.length()>1) {
                //Необходимо узнать есть ли знак '|' в формуле
                for (int i = 0; i < str.length()-1; i++) {
                    if (str.charAt(i)=='|'){
                        absInStr++;                                                                                     //изменяем флаг
                    }
                    newStr += str.charAt(i);
                    lb_display.setText(newStr);
                }
                if (absInStr==0){                                                                                       //если флаг не изменен
                    exp_struct.setAbs(false);                                                                           //то модуля в формуле нет
                }
                else exp_struct.setAbs(true);                                                                           //если изменен, то есть
            }
            else lb_display.setText("");
        }
        if (event.getSource() == btn_clear) {
            System.out.println("CLR");
            lb_display.setText("");
            btn_less.setDisable(false);
            btn_more.setDisable(false);
            exp_struct.setAbs(false);
            paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);
        }
        if (event.getSource() == btn_point) {
            System.out.println("POINT");
            int pointFlag = 0;
            if (!lb_display.getText().equals("")){
                if (isNum.isNumber(lb_display.getText().charAt(lb_display.getText().length()-1))){                      //проверка - если предыдущый символ число - то тру
                                                                                                                        //взять из лейбла значение, стоящее на месте (длина значения лейбла -1)
                    for(int i = lb_display.getText().length()-1; i>=0;i--){                                             //от предпоследнего элемента идем до нулевого
                        if (WhatItIs.isOperation( String.valueOf(lb_display.getText().charAt(i)))||(i==0) ){            //если встретилась операция или дошли до конца - тут char преобразовали в String
                            for (int j = i; j<lb_display.getText().length(); j++){                                      //то идем от текущего элемента до конца строки
                                if (lb_display.getText().charAt(j)=='.'){                                               //с целью проверки на наличие точки в числе
                                    pointFlag = 1;                                                                      //если находим точку, то выставляем флаг
                                    break;                                                                              //выходим из цикла, дальше искать смысла нет
                                }
                            }
                            if (pointFlag == 0) lb_display.setText(lb_display.getText() + ".");                         //если флаг остался равным 0, значит можно добавить точку в конец
                        }
                    }

                }
            }
            else lb_display.setText(lb_display.getText() + "0.");
        }

        if (event.getSource() == btn_x) {
            System.out.println("x");
            if(lb_display.getText().equals("0")||(lb_display.getText().equals("0."))){
                lb_display.setText("x");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (lb_display.getText().charAt(lb_display.getText().length() - 1) == '.') {
                        String str = lb_display.getText();
                        String newStr = "";
                        if (str.length() > 1) {
                            for (int i = 0; i < str.length()-1; i++) {
                                newStr += str.charAt(i);
                                lb_display.setText(newStr);
                            }
                        }
                    }
                }
                if((lb_display.getText().length()==0)||
                        (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))) {
                    lb_display.setText(lb_display.getText() + "x");
                }
            }
        }
        if (event.getSource() == btn_y) {
            System.out.println("y");
            if(lb_display.getText().equals("0")||(lb_display.getText().equals("0."))){
                lb_display.setText("y");
            }
            else{
                if (lb_display.getText().length()!=0) {
                    if (lb_display.getText().charAt(lb_display.getText().length() - 1) == '.') {
                        String str = lb_display.getText();
                        String newStr = "";
                        if (str.length() > 1) {
                            for (int i = 0; i < str.length() - 1; i++) {
                                newStr += str.charAt(i);
                                lb_display.setText(newStr);
                            }
                        }
                    }
                }
                if((lb_display.getText().length()==0)||
                        (!WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))) {
                            lb_display.setText(lb_display.getText() + "y");
                }
            }
        }
        if (event.getSource() == btn_plus) {
            System.out.println("+");
            if(!lb_display.getText().equals("")){
                if ((lb_display.getText().charAt(lb_display.getText().length()-1)=='.')||
                    ((WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))&&
                    (lb_display.getText().charAt(lb_display.getText().length()-1)!='|'))){
                    String str = lb_display.getText();
                    String newStr ="";
                    if (str.length()>1) {
                        for (int i = 0; i < str.length()-1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                        }
                    }
                    lb_display.setText(lb_display.getText() + "+");
                }
                else if((!WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))||
                        (lb_display.getText().charAt(lb_display.getText().length()-1)=='|')){
                            lb_display.setText(lb_display.getText() + "+");
                }
            }
        }
        if (event.getSource() == btn_minus) {
            System.out.println("-");
            if(!lb_display.getText().equals("")){
                if ((lb_display.getText().charAt(lb_display.getText().length()-1)=='.')){
                    String str = lb_display.getText();
                    String newStr ="";
                    if (str.length()>1) {
                        for (int i = 0; i < str.length()-1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                        }
                    }
                    lb_display.setText(lb_display.getText() + "-");
                }
                else lb_display.setText(lb_display.getText() + "-");
            }
            else{
                lb_display.setText(lb_display.getText() + "-");
            }
        }
        if (event.getSource() == btn_mult) {
            System.out.println("*");
            if(!lb_display.getText().equals("")){
                if ((lb_display.getText().charAt(lb_display.getText().length()-1)=='.')||
                    (WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))){
                    String str = lb_display.getText();
                    String newStr ="";
                    if (str.length()>1) {
                        for (int i = 0; i < str.length()-1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                        }
                    }
                    lb_display.setText(lb_display.getText() + "*");
                }
                else if((!WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))||
                        (lb_display.getText().charAt(lb_display.getText().length()-1)=='|')) {
                            lb_display.setText(lb_display.getText() + "*");
                }
            }
        }
        if (event.getSource() == btn_division) {
            System.out.println("÷");
            if(!lb_display.getText().equals("")){
                if ((lb_display.getText().charAt(lb_display.getText().length()-1)=='.')||
                    (WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))){
                    String str = lb_display.getText();
                    String newStr ="";
                    if (str.length()>1) {
                        for (int i = 0; i < str.length()-1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                        }
                    }
                    lb_display.setText(lb_display.getText() + "÷");
                }
                else if((!WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))||
                        (lb_display.getText().charAt(lb_display.getText().length()-1)=='|')) {
                            lb_display.setText(lb_display.getText() + "÷");
                }
            }
        }
        if (event.getSource() == btn_less) {
            String str_less;
            if (!flag_btn_less) {
                System.out.println("<");
                str_less = "<";
                btn_less.setText("≤");
                flag_btn_less = !flag_btn_less;
            }
            else{
                System.out.println("≤");
                str_less = "≤";
                btn_less.setText("<");
                flag_btn_less = !flag_btn_less;
            }
            if(!lb_display.getText().equals("")){
                if (WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1)))){
                    String str = lb_display.getText();
                    String newStr ="";
                    if (str.length()>1) {
                        for (int i = 0; i < str.length()-1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                        }
                    }
                    lb_display.setText(lb_display.getText() + str_less);
                }
                if (WhatItIs.isNumber(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1)))||
                   (WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))){
                    lb_display.setText(lb_display.getText() + str_less);
                }
            }
        }
        if (event.getSource() == btn_more) {
            String str_more;
            if (!flag_btn_more) {
                System.out.println(">");
                str_more = ">";
                btn_more.setText("≥");
                flag_btn_more = !flag_btn_more;
            }
            else{
                System.out.println("≥");
                str_more = "≥";
                btn_more.setText(">");
                flag_btn_more = !flag_btn_more;
            }
            if(!lb_display.getText().equals("")){
                if (WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1)))){
                    String str = lb_display.getText();
                    String newStr ="";
                    if (str.length()>1) {
                        for (int i = 0; i < str.length()-1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                        }
                    }
                }
                if (WhatItIs.isNumber(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1)))||
                   (WhatItIs.isVal(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1))))){
                        lb_display.setText(lb_display.getText() + str_more);
                }
            }
        }

        if (event.getSource() == btn_abs) {
            int abs_count = 0;
            for (int i=0;i<lb_display.getText().length();i++){                                                          //считаем количество | в строке
                if (lb_display.getText().charAt(i)=='|') abs_count++;
            }
            if (abs_count%2==0) {                                                                                       //если модуля нет или закрыт
                if (!lb_display.getText().equals("")) {                                                                 //если строка не пуста
                    if ((lb_display.getText().charAt(lb_display.getText().length() - 1) != '.')&&                       //пред. символ не точка
                        (!WhatItIs.isNumber(String.valueOf(lb_display.getText().charAt(lb_display.getText().length() - 1))))&&  //и не число
                        ((lb_display.getText().charAt(lb_display.getText().length() - 1) != '|'))){                     //и нет открытого модуля
                            lb_display.setText(lb_display.getText() + "|");                                             //тогда можно открыть модуль
                            btn_less.setDisable(true);
                            btn_more.setDisable(true);
                    }
                }
                else {
                    lb_display.setText(lb_display.getText() + "|");                                                     //если строчка пустая, то модуль можно сразу открыть
                    btn_less.setDisable(true);
                    btn_more.setDisable(true);
                }
            }
            else{                                                                                                       //если модуль открыт
                if (lb_display.getText().charAt(lb_display.getText().length()-1)=='.') {                                //если пред. символ точка
                    String str = lb_display.getText();                                                                  //то перепесать строку заменив точку на |
                    String newStr = "";
                    if (str.length() > 1) {
                        for (int i = 0; i < str.length() - 1; i++) {
                            newStr += str.charAt(i);
                            lb_display.setText(newStr);
                            lb_display.setText(lb_display.getText()+"|");
                        }
                        btn_more.setDisable(false);
                        btn_less.setDisable(false);
                    }
                }
                if (!WhatItIs.isOperation(String.valueOf(lb_display.getText().charAt(lb_display.getText().length()-1)))) {  //если пред. символ не операция
                    lb_display.setText(lb_display.getText()+"|");                                                       //тогда можно закрыть модуль
                    btn_more.setDisable(false);
                    btn_less.setDisable(false);
                }
            }
        }

        if (event.getSource() == btn_ok){
            System.out.println("OK");
            paintScaleLines.paint_scale_lines(exp_struct.getScl_width(), gc);

            //На случай, если в формула недописана
            lb_display.setText(doValidExp.doValidEnd(lb_display.getText(), exp_struct));

            //На случай, если в формуле не закрыт знак модуля
            lb_display.setText(doValidExp.doValidAbs(lb_display.getText()));

            //Запись в файл
            try{
                FileWriter fileWriter = new FileWriter("C:/Users/635_6/Desktop/Java/Projects/Graphics2.0/src/emk/formula.txt", false);
                fileWriter.write(lb_display.getText());
                fileWriter.flush();
                fileWriter.close();
            }
            catch (IOException e) {
                System.out.println("Ошибка при создании и записи в файл файла");
                //e.printStackTrace();
            }
            Parsing.parse_formula(lb_display.getText(), exp_struct);
            calc.calculate(exp_struct);
            paintGraph.paint_graph(exp_struct,gc);
            if ((exp_struct.getSide().equals("L"))&&(exp_struct.getSign().equals("/"))&&(exp_struct.getNumber()==0)){
                JOptionPane.showMessageDialog(null, "Деление на ноль!","Ошибка!",JOptionPane.ERROR_MESSAGE);
            }
        }

        //Управление блокировкой кнопки "Построить"
        int lb_length = lb_display.getText().length();                                                                  //длина введенной формулы
        if(lb_length>0){                                                                                                //при длине больше, чем 0 символов
            btn_ok.setDisable(false);                                                                                   //разблокировать
            btn_del.setDisable(false);
            btn_clear.setDisable(false);
        }
        else{
            btn_ok.setDisable(true);                                                                                    //заблокировать
            btn_del.setDisable(true);
            btn_clear.setDisable(true);
        }
    };

}
