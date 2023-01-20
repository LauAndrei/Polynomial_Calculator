package Controller;

import Model.Polynomial;

public class Controller {
    private Polynomial p1;
    private Polynomial p2;

    public Controller(){

    }

    public void setP1(String P1){
        p1 = new Polynomial(P1);
    }

    public void setP2(String P2){
        p2 = new Polynomial(P2);
    }

    public void doOperation(String operation){
        switch (operation){
            case "add" -> p1.add(p2);
            case "subtract" -> p1.subtract(p2);
            case "multiply" -> p1.multiply(p2);
            case "divide" -> p1.divide(p2);
            case "derivative" -> p1.derivate();
            case "integrate" -> p1.integrate();
            default -> System.out.println("invalid op");
        }
    }

    public String getP1(){
        return p1.toString();
    }

    public String getP2(){
        return p2.toString();
    }
}
