import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calcolatrice extends JFrame {
    private JTextField display; // Campo di testo per il risultato
    private double firstNumber = 0; // Primo numero dell'operazione
    private double secondNumber = 0; // Secondo numero dell'operazione
    private String operator = ""; // Operatore attuale

    public Calcolatrice() {
        setTitle("Calcolatrice Semplice");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Pannello pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonHandler());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();

            if ("0123456789".contains(input)) { // Gestione numeri
                if (display.getText().equals("0") || operator.isEmpty()) {
                    display.setText(input);
                } else {
                    display.setText(display.getText() + input);
                }
            } else if ("/*-+".contains(input)) { // Operatori
                firstNumber = Double.parseDouble(display.getText());
                operator = input;
                display.setText("0");
            } else if ("=".equals(input)) { // Calcolo risultato
                secondNumber = Double.parseDouble(display.getText());
                double result = calculate(firstNumber, secondNumber, operator);
                display.setText(String.valueOf(result));
                operator = ""; // Reset operator
            } else if ("C".equals(input)) { // Resetta tutto
                display.setText("0");
                firstNumber = 0;
                secondNumber = 0;
                operator = "";
            }
        }

        private double calculate(double a, double b, String op) {
            return switch (op) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                case "/" -> b != 0 ? a / b : Double.NaN;
                default -> 0;
            };
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calcolatrice calculator = new Calcolatrice();
            calculator.setVisible(true);
        });
    }
}
