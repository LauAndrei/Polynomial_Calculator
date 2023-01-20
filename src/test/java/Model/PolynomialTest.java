package Model;

import Controller.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void add() {
        Polynomial P1 = new Polynomial("2*x^2+3*x");
        Polynomial P2 = new Polynomial("3*x^2+4");
        P1.add(P2);
        assertEquals("4+3*x+5*x^2",P1.toString());
    }

    @Test
    void subtract() {
        Polynomial P1 = new Polynomial("3*x^3+4*x-4");
        Polynomial P2 = new Polynomial("1*x^3-2*x+3");
        P1.subtract(P2);
        assertEquals("-7+6*x+2*x^3",P1.toString());
    }
}