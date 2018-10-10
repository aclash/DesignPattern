//adaptor pattern begin
//The adaptor pattern converts the interface of a class into another interface client expect, and lets
//classes work together that couldn't otherwise because of incompatible interfaces.
interface IBird{
    void makeSound();
}

class Sparrow implements IBird{
    public void makeSound(){
        System.out.println("Chirp!");
    }
}

interface IToyDuck{
    void squeak();
}

class PlasticDuck implements IToyDuck{
    public void squeak(){
        System.out.println("Squeak!");
    }
}

class BirdAdaptor implements IToyDuck{
    IBird bird;
    BirdAdaptor(IBird bird){
        this.bird = bird;
    }
    public void squeak(){
        bird.makeSound();
    }
}
//adaptor pattern end

//traditional decorator patter begin
//The decorator pattern attaches additional responsibility to object dynamically, and provides a flexible alternative to
//subclassing for extending functionality
class RPGCharacter{
    private int strength;
    private int health;
    private boolean confused = false;
    RPGCharacter(int strength, int health){
        this.strength = strength;
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void confuse(){
        confused = true;
        System.out.println("Confused!!!");
    }

    public void decreaseHealth(int loss){
        health -= loss;
        System.out.println("loss HP!!!");
    }
}

interface Attack{
    void attack(RPGCharacter attacker, RPGCharacter defender);
}

class PhysicalAttack implements Attack{
    public void attack(RPGCharacter attacker, RPGCharacter defender){
        defender.decreaseHealth(attacker.getStrength());
    }
}

abstract class AttackDecorator implements Attack{
    Attack action;
    AttackDecorator(Attack _action){
        action = _action;
    }
    public void attack(RPGCharacter attacker, RPGCharacter defender){
        action.attack(attacker, defender);
    }
}

class Confuse extends AttackDecorator{
    Confuse(Attack _action){
        super(_action);
    }
    public void attack(RPGCharacter attacker, RPGCharacter defender){
        defender.confuse();
        super.attack(attacker, defender);
    }
}
//traditional decorator patter end

//factory pattern begin
//define an interface for creating an object, but let subclasses decide which class to instantiate, let a class defer
//instantiation to subclass
class Widget{
    private String name;
    private String value;
    Widget(){
        name = "text";
        value = "static text";
    }
    void setValue(String _value){
        value = _value;
    }
    void draw(){
        System.out.println(value);
    }
}

class WidgetFactory{
    private static WidgetFactory instance = null;
    private WidgetFactory(){}
    public Widget makeWidget(){
        return new Widget();
    }
    public static WidgetFactory getWidgetFactory(){
        if (instance == null)
            instance =  new WidgetFactory();
        return instance;
    }
}
//factory pattern end

//template pattern begin
//Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets
//subclasses redefine certain steps of an algorithm without changing the algorithm’s structure.
abstract class OrderProcessTemplate{
    abstract void doSelect();
    abstract void doPayment();
    abstract void doDelivery();
    final void processOrder(){
        doSelect();
        doPayment();
        doDelivery();
    }
}

class NetOrder extends OrderProcessTemplate{
    void doSelect(){
        System.out.println("online select!!!");
    }
    void doPayment(){
        System.out.println("online payment!!!");
    }
    void doDelivery(){
        System.out.println("ship item!!!");
    }
}

class StoreOrder extends OrderProcessTemplate{
    void doSelect(){
        System.out.println("select!!!");
    }
    void doPayment(){
        System.out.println("pays at counter by credit card!!!");
    }
    void doDelivery(){
        System.out.println("deliver item to counter!!!");
    }
}
//template pattern end

//iterator patter begin
//Iterator pattern is used to traverse a container and access the container's element, it decouples algorithm
//from container.
interface Iterator{
    boolean hasNext();
    Object next();
}

interface Container{
    Iterator getIterator();
}

class IntContainer implements Container{
    private int list[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    public Iterator getIterator(){
        return new IntIterator();
    }
    private class IntIterator implements Iterator{
        private int index = 0;
        public boolean hasNext(){
            if (index < list.length)
                return true;
            else
                return false;
        }

        public Object next(){
            if (hasNext())
                return list[index++];
            else
                return null;
        }
    }
}
//iterator pattern end

public class Main {
    public static void main(String[] args) {

        //adaptor pattern begin
        System.out.println("*****************adaptor pattern begin!!!*****************");
        Sparrow sparrow = new Sparrow();
        IToyDuck birdAdaptor = new BirdAdaptor(sparrow);
        birdAdaptor.squeak();
        //adaptor pattern end

        //traditional decorator patter begin
        System.out.println("*****************traditional decorator patter begin!!!*****************");
        Attack atk = new Confuse(new PhysicalAttack());
        atk.attack(new RPGCharacter(100, 200), new RPGCharacter(50, 300));
        //traditional decorator patter end

        //factory patter begin
        System.out.println("*****************factory patter begin!!!*****************");
        WidgetFactory factory = WidgetFactory.getWidgetFactory();
        Widget w1 = factory.makeWidget();
        w1.draw();
        Widget w2 = factory.makeWidget();
        w2.setValue("fuck world.");
        w2.draw();
        //factory patter end

        //template pattern begin
        System.out.println("*****************template pattern begin!!!*****************");
        OrderProcessTemplate eOrder = new NetOrder();
        OrderProcessTemplate storeOrder = new StoreOrder();
        eOrder.processOrder();
        storeOrder.processOrder();
        //template pattern end

        //iterator patter begin
        System.out.println("*****************iterator pattern begin!!!*****************");
        IntContainer intList = new IntContainer();
        Iterator it = intList.getIterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        //iterator pattern end
    }
}
