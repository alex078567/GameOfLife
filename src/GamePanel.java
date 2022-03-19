import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
//класс GamePanel который расширяет JPanel и реализует интерфейс ActionListener
//для того чтобы считывать нажатия с клавиатуры
public class GamePanel extends JPanel implements ActionListener {
    static int SCREEN_WIDTH = 10000;
    static int SCREEN_HEIGHT = 10000;
    static int unit_size = 1;
    public double f = 1;

    // объявляем переменую new game но пока не объявляем ее
    algorithm newgame;
    boolean running = true;
    AffineTransform newTR= new AffineTransform();
    //от значения задержки будет зависить количество кадров в секунду
    Timer t = new Timer(200, this);
    //Конструктор
    GamePanel() {
        //Задаем размер панели
        this.setPreferredSize(new Dimension(10000, 10000));
        //цвет фона
        this.setBackground(Color.black);
        //вызываем метод startGame
        startGame();
        // может ли компонент получить фокус ( необходимо для возможности
        // масштабирования)
        this.setFocusable(true);
        //добавляем keylistener чтобы можно было считывать нажатия
        this.addKeyListener(new MyKeyAdapter());
    }
    // метод который начинает игру
    public void startGame() {
        //создаем экземпляр класса algorithm заданного размера
        //10000X10000
        newgame = new algorithm(SCREEN_WIDTH, SCREEN_HEIGHT);
        //вызываем метод который случайным образом заполняет
        //поле живыми клетками
        newgame.RandomSeed(3_000_000);

    }
    // метод в котором определяется графика
    // Graphics это абстрактный класс в котором определяется
    //графика для дальнейшего рендеринга средствами java
    public void draw(Graphics g) {
        //задаем цвет
        g.setColor(Color.green);
        //создаем массив типа байт где хранятся значения
        //живых и мертвых клеток на данный момент
        //одномерный массив выбран для ускорения работы программы
        byte[] arr = newgame.getGen();
        //цикл который закрашивает все живые клетки
        for (int i = 0; i < arr.length; i++) {
         if (arr[i] == 1) {
         g.fillRect((i % SCREEN_WIDTH), (i / SCREEN_WIDTH), unit_size, unit_size);
         }
         }
        long i = System.currentTimeMillis();
        //"новое поколение" создаем новое поколение согласно правилам
        // клеточного автомата игра "жизнь"
        newgame.nextgen();
        i -= System.currentTimeMillis();
        System.out.println(i);
    }

    //вызываем метод paintComponent который используется для рисования
    public void paintComponent(Graphics g) {
        // переводим объект g в 2D графику, чтобы
       // было возможно масштабирование
        Graphics2D g2 = (Graphics2D) g;
       // newTR.translate(getWidth()*(1-f)/2, getHeight()*(1-f)/2);
        //задаем масштаб
        newTR.scale(f, f);

        f=1;
        //System.out.println(f);





        //изменяем масштаб графики
        g2.transform(newTR);
       // g2.setTransform(savedOriginalTransform);
        super.paintComponent(g);
        // вызываем метод в котором определена гафика
        draw(g);
        //запускаем таймер
        t.start();





    }
    //данный метод запускается каждый раз когда срабатывает таймер
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    //здесь переопределяется метод, который определен в суперклассе jpanel
    // для считывания нажатий с клавиатуры
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    //переменная f глобальной и масштаб графики будет меняться
                    //в зависимости от ее значения
                    f = 0.9;
                    break;
                case KeyEvent.VK_DOWN:
                    f = 1.1;
                    break;
            }
        }
    }
}

