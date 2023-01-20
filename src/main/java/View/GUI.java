package View;

import Controller.Controller;

import javax.swing.*;

public class GUI extends JFrame {
    private final Controller controller;
    private JTextField firstPolynomial;
    private JTextField secondPolynomial;
    private JButton addButton;
    private JButton subtractButton;
    private JPanel mainPanel;
    private JButton computeButton;
    private JButton derivativeButton;
    private JButton multiplyButton;
    private JLabel confirmOpLabel;
    private JLabel computingLabel;
    private JLabel resultingLabel;
    private JLabel resultLabel;
    private JButton divideButton;
    private JButton integrateButton;
    private String operation;

    public GUI() {
        controller = new Controller();

        operation = "";
        setContentPane(mainPanel);
        setTitle("Polynomial Calculator");
        setBounds(600, 200, 600, 410);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton.addActionListener(e -> {
            confirmOpLabel.setText("Add Operation Selected!");
            controller.setP1(firstPolynomial.getText());
            controller.setP2(secondPolynomial.getText());
            operation = "add";
        });

        subtractButton.addActionListener(e -> {
            confirmOpLabel.setText("Subtract Operation Selected!");
            operation = "subtract";
            controller.setP1(firstPolynomial.getText());
            controller.setP2(secondPolynomial.getText());
        });

        multiplyButton.addActionListener(e -> {
            confirmOpLabel.setText("Multiply Operation Selected!");
            operation = "multiply";
            controller.setP1(firstPolynomial.getText());
            controller.setP2(secondPolynomial.getText());
        });

        divideButton.addActionListener(e -> {
            confirmOpLabel.setText("Divide Operation Selected!");
            operation = "divide";
            controller.setP1(firstPolynomial.getText());
            controller.setP2(secondPolynomial.getText());
        });

        derivativeButton.addActionListener(e -> {
            confirmOpLabel.setText("Derivative Operation Selected!");
            operation = "derivative";
            controller.setP1(firstPolynomial.getText());
        });

        integrateButton.addActionListener(e -> {
            confirmOpLabel.setText("Integrate Operation Selected!");
            operation = "integrate";
            controller.setP1(firstPolynomial.getText());
        });

        computeButton.addActionListener(e -> {
            if (!operation.isEmpty()) {
                computingLabel.setText("Computing the" + " " + operation + " operation..");
                resultingLabel.setText("The result will be shown below:");
                controller.doOperation(operation);
//              firstPolynomial.setText(controller.getP1());
//              secondPolynomial.setText("");
                resultLabel.setText(controller.getP1());
            }else{
                confirmOpLabel.setText("Please select an operation before computing!");
            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}