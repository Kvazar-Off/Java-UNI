package sixlab;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

// класс используется для обработки событий окна
class OurWindowAdapter extends WindowAdapter {
    public void windowClosing (WindowEvent wE) {
        System.exit (0);
    }
}

class Frames extends Frame {

    int count = 0; //количество фигур
    int numbers[];	//id
    java.util.List<Figure> figures = new ArrayList<Figure>(); //список фигур
    Frame childFrame ; // Дочерняя форма
    Canvas cnv;
    Label objectLab, speedLab, objectLabForChange, speedLabForChange, colorLab,idLabForChange, idLabForChangeNew;
    TextField speedTB, speedCh;
    Choice colorTB, figureTB;
    Button addBut, changeBut;
    Choice objectChForChange, objectIdForChange;
    Color color;

    Frames() {

        this.setTitle("LAB_6");
        this.setSize (200, 280);

        numbers = new int [10];
        for (int i = 0; i < 10; i ++)
            numbers[i] = -1;

        objectLab = new Label("Фигура: ");
        speedLab = new Label("Скорость: ");
        objectLabForChange = new Label("Фигура: ");
        speedLabForChange = new Label("Скорость: ");
        colorLab = new Label("Цвет: ");
        idLabForChange = new Label("Номер фигуры");
        idLabForChangeNew = new Label("Изменить номер");

        speedTB = new TextField();
        speedTB.setText("1");
        colorTB = new Choice(); // TODO я не понимаю что за стандартный элемент выбора цвета
        colorTB.addItem("Красный");
        colorTB.addItem("Розовый");
        colorTB.addItem("Синий");
        colorTB.addItem("Черный");
        figureTB = new Choice(); // выбор запускаемой фигуры с помощью выпадающего списка названий
        figureTB.addItem("круг");
        figureTB.addItem("квадрат");

        addBut = new Button("Добавить");
        changeBut = new Button("Изменить");

        objectChForChange = new Choice();
        speedCh = new TextField(); // регулировка скорости перемещения выбранной фигуры с помощью текстового поля
        speedCh.setText("1");

        objectIdForChange = new Choice();

        cnv = new Canvas() {
            public void paint(Graphics g) {
                //
            }
        };

        //интерфейс УО
        GridBagConstraints gbc = new GridBagConstraints(); // класс используется для указания ограничений на размещение компонентов в контейнере с менеджером компоновки GridBagLayout
        this.setLayout (new GridBagLayout()); //  менеджер компоновки GridBagLayout для текущего контейнера

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 1;
        this.add (objectLab, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 1;
        this.add (figureTB, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 2;
        this.add (colorLab, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 2;
        this.add (colorTB, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 3;
        this.add (speedLab, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 3;
        this.add (speedTB, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;  gbc.gridy = 4;
        gbc.gridwidth = 3;
        this.add (addBut, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        this.add (new Label(""), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 6;
        this.add (objectLabForChange, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 6;
        this.add (objectChForChange, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 7;
        this.add (speedLabForChange, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 7;
        this.add (speedCh, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        this.add (changeBut, gbc);

        addBut.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent aE) {
                AddButFunc();
            }
        });

        changeBut.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent aE) {
                ChangeButFunc();
            }
        });

        this.addWindowListener (new OurWindowAdapter());

        //ДО
        childFrame= new Frame(); // Создать дочернюю форму
        childFrame.setSize (800, 600); // с размером
        childFrame.setLocation (450, 100);
        childFrame.add(cnv);
        childFrame.show(); // Перерисовать область клиента окна
        childFrame.addWindowListener (new OurWindowAdapter());
        childFrame.setResizable(false); // запрет на изменение размера окна ДО
    }

    public void AddButFunc() {
        if (IsInteger(speedTB.getText())) { //проверка скорости
                if (CheckColor(colorTB.getSelectedItem())) { //проверка цвета
                    int num = count + 1;
                    numbers[count] = num;
                    count++;
                    objectChForChange.addItem(numbers[count-1] + "");
                    objectIdForChange.addItem(numbers[count-1] + "");

                    if (CheckFigure(figureTB.getSelectedItem(), num)) {
                        figures.get(count-1).start();
                    }
                    else {
                        figures.get(count-1).start();
                    }
                }
        }
    }

    public void ChangeButFunc() {
        if (IsInteger(objectChForChange.getSelectedItem())) { //проверка номера объекта
            int num = Integer.parseInt(objectChForChange.getSelectedItem());

            int foundNumber = -1;
            for (int i = 0; i < count; i ++) { //поиск объекта
                if (numbers[i] == num)
                    foundNumber = i;
            }
            figures.get(foundNumber).speed = Integer.parseInt(speedCh.getText()); // изменение скорости
        }
    }

    public boolean IsInteger(String string) {
        try {
            Integer.parseInt(string);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean CheckFigure(String fgr, int id) {
        if (fgr.equalsIgnoreCase("круг")) {
            figures.add(new Circle(this.cnv, color, Integer.parseInt(speedTB.getText()), id));
        }
        else if (fgr.equalsIgnoreCase("квадрат")) {
            figures.add(new Quadrangle(this.cnv, color, Integer.parseInt(speedTB.getText()), id));
        }
        else {
            return false;
        }
        return true;
    }


    public boolean CheckColor(String clr) {
        if (clr.equalsIgnoreCase("красный")) {
            this.color = Color.red;
        }
        else if (clr.equalsIgnoreCase("розовый")) {
            this.color = Color.pink;
        }
        else if (clr.equalsIgnoreCase("синий")) {
            this.color = Color.blue;
        }
        else if (clr.equalsIgnoreCase("желтый")) {
            this.color = Color.yellow;
        }
        else if (clr.equalsIgnoreCase("зеленый")) {
            this.color = Color.green;
        }
        else if (clr.equalsIgnoreCase("черный")) {
            this.color = Color.black;
        }
        else
            return false;

        return true;
    }

    public static void main (String[] args) {
        Frames Fr = new Frames();
        Fr.show();
    }
}

abstract class Figure extends Thread{
    Point point = new Point(50, 50);
    Canvas cnv;
    Graphics g;
    Color color;
    public int speed;
    double angle;
    int id;
    int step = 4;

    void moveTo() {
        this.show(false);
        this.point.x += this.speed*Math.cos(this.angle)*step;
        this.point.y += this.speed*Math.sin(this.angle)*step;
        checkBorder();
        this.show(true);
    }

    void show(boolean sh) {
        //
    }

    void checkBorder() {
        //
    }

    public void run() {
        while(true) {
            moveTo();
            try {
                sleep(130);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Circle extends Figure{
    int radius = 70;

    void show(boolean sh) {
        if (sh)
            g.setColor(color);
        else
            g.setColor(Color.white);

        g.fillOval(point.x, point.y, radius, radius);
        g.drawString(this.id + "", point.x, point.y);
    }

    void checkBorder() {
        boolean border = false;
        //фигура достигла правой границы холста
        if ((cnv.getWidth()-point.x <= this.radius) && (Math.cos(this.angle) > 0)) {
            this.angle = Math.PI - this.angle;
            border = true;
        }
        //фигура достигла левой границы холста
        else if ((point.x <= 0) && (Math.cos(this.angle) < 0)) {
            this.angle = Math.PI - this.angle;
            border = true;
        }
        //фигура достигла нижней границы холста
        if ((cnv.getHeight()-point.y <= this.radius) && (Math.sin(this.angle) > 0)) {
            this.angle *= (-1);
            border = true;
        }
        //фигура достигла верхней границы холста
        else if ((point.y <= 0) && (Math.sin(this.angle) < 0)) {
            this.angle *= (-1);
            border = true;
        }

        if (border) {
            this.point.x += this.speed*Math.cos(this.angle);
            this.point.y += this.speed*Math.sin(this.angle);
        }
    }

    Circle(Canvas cnv, Color color, int speed, int id) {
        this.cnv = cnv;
        this.color = color;
        this.speed = speed;
        this.angle = Math.random()*2*Math.PI;
        this.g = cnv.getGraphics();
        this.id = id;
    }
}
class Quadrangle extends Figure{

    int height = 70;

    void show(boolean sh) {
        if (sh)
            g.setColor(color);
        else
            g.setColor(Color.white);

        g.fillRect(point.x-height/2, point.y-height/2, height, height); // используется вычитание height/2, чтобы верхний левый угол прямоугольника был расположен в точке (point.x, point.y), а не в его центре
        g.drawString(this.id + "", point.x-height/2-2, point.y-height/2-2);
    }

    void checkBorder() {
        boolean border = false;
        if ((cnv.getWidth()-point.x <= this.height/2) && (Math.cos(this.angle) > 0)) {
            this.angle = Math.PI - this.angle;
            border = true;
        }
        else if ((point.x <= this.height/2) && (Math.cos(this.angle) < 0)) {
            this.angle = Math.PI - this.angle;
            border = true;
        }

        if ((cnv.getHeight()-point.y <= this.height/2) && (Math.sin(this.angle) > 0)) {
            this.angle *= (-1);
            border = true;
        }
        else if ((point.y <= this.height/2) && (Math.sin(this.angle) < 0)) {
            this.angle *= (-1);
            border = true;
        }

        if (border) {
            this.point.x += this.speed*Math.cos(this.angle);
            this.point.y += this.speed*Math.sin(this.angle);
        }
    }

    Quadrangle(Canvas cnv, Color color, int speed, int id) {
        this.cnv = cnv;
        this.color = color;
        this.speed = speed;
        this.angle = Math.random()*2*Math.PI;
        this.g = cnv.getGraphics();
        this.id = id;
    }
}
