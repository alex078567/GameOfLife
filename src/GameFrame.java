import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;
//GameFrame расширяет класс JFrame
public class GameFrame extends JFrame {
    GameFrame(){

        // создаем новую область прокрутки
        // в ней создается экземпляр класса GamePanel
        JScrollPane scrollPane = new JScrollPane(new GamePanel(),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //задаем размер области просмотра в области прокрутки
        scrollPane.getViewport().setPreferredSize(new Dimension(1500, 769));
        //this.add(new GamePanel());

        this.getContentPane().add(scrollPane);

        this.setTitle("GameofLife");
        //можно не задавать размер главного окна так как он уже задан
         //this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //можно ли менять размер
        this.setResizable(false);
        //метод pack() подбирает размер окна в зависимости от содержимого
        this.pack();
        //видимость окна
        this.setVisible(true);
        //задать позицию окно относительно левого верхнего угла
        this.setLocation(0,0);
    }
}