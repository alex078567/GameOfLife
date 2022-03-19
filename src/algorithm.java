public class algorithm {
    private int height;
    private int width;
    private int Asize;
    private int Seednumber;
    byte[] gen ;
    public algorithm(int width, int height) {
        //здесь задаются ширина и высота поля
        //(в нашем случае они одинаковы)
        this.width = width;
        this.height = height;
        this.Asize = width * height;
        gen = new byte[Asize];
    }
    //метод который создает новое поколение
    public void nextgen() {
        //массив в котором содержиться старое поколение
        byte[] lostgen = new byte[Asize];
        System.arraycopy(gen, 0, lostgen, 0, Asize);
        //массив в который записывается новое поколение
        byte[] newgen = new byte [Asize];
        /*
        * в алгоритме мы исходим из идеи
        * что большей частью игра жизнь
        * пуста => мы не считаем выживет ли клетка для каждой клетки
        * поля а ищем живые клетки и рассматриваем
        * пространство вокруг них
        * */
        for (int i=0; i<Asize; i++) {
            if (lostgen[i] == 1){

                int y = i/width;
                int x = i%width;
                int pos1 = y*width + x;
                int check ;
                //проверяем выживет ли конкретная клетка
                //которая сейчас жива
                check = isAlive(x, y, lostgen);
                if ( check > 0 ){
                    newgen[y * width + x] = 1;
                }else{
                    newgen[y * width + x] = 0;
                }
                //проверяем 8 клеток вокруг нее
                for (int k = x-1;k <= x+1; k++){
                    for (int j = y-1; j <= y+1;j++){
                        int pos = j*width+k;
                        if((k>=0&&j>0)&&(k<width&&j<height)&&pos1!=pos) {
                            check = isAlive(k, j, lostgen);
                            if ( check > 0 ){
                                newgen[j * width + k] = 1;
                            }else{
                                newgen[j * width + k] = 0;
                            }
                        }

                    }
                }
            }
        }
        System.arraycopy(newgen, 0, gen, 0, Asize);
    }
    // проверяет клетки вокруг клетки для которой вызывается функция
    // и выясняет выживет ли клетка в следующем поколении
    protected int isAlive(int x, int y, byte[] d) {
        int count1 =0;
        int pos1 = y*width + x;
        for (int i = x-1;i <= x+1; i++){
            for (int j = y-1; j <= y+1;j++){
                int pos = j*width +i;
                if ((i>=0&&j>0)&&(i<width&&j<height)&&pos1!=pos){
                    if (d[pos] == 1) {
                        count1++;
                    }
                }
            }
        }
        if (d[pos1] == 0) {
            if (count1 == 3) {
                return 1;
            }
            return 0;
        }
        else {
            if (count1 < 2 || count1 >3) {
                return 0;
            }
            return 1;
        }
    }
    //метод который засеевает поле жизнью
    public void RandomSeed(int seeds) {
        Seednumber=seeds;
        for (int i = 0; i < Seednumber; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            gen[y * width + x] = 1;
        }
    }
    // метод который возвращает массив gen где содержится состояние поля на текущий момент
    public byte[] getGen() {
        return gen;
    }
}
