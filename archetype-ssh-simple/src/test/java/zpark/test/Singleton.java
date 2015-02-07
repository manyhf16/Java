package zpark.test;

class Singleton{
    
    
    private static int counter1;
    private static int counter2 = 0;
    private static Singleton singleton=new Singleton();
    public Singleton() {
       counter1++;
       counter2++;
    }
    public static int getCounter1() {
       return counter1;
    }
    public static int getCounter2() {
       return counter2;
    }
    /**
     * @Description:实例化
     * @return
     * @author YHJ create at 2011-6-4 下午08:34:43
     */
    public static Singleton getInstance(){
       return singleton;
    }
    
    public static void main(String[] args) {
        System.out.println(Singleton.getCounter1());
        System.out.println(Singleton.getCounter2());
    }
   
}
