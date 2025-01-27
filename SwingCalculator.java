import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingCalculator {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        // Create text field for input/output
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setHorizontalAlignment(SwingConstants.RIGHT);

        // Create buttons
        String[] buttonLabels = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", ".", "=", "+"
        };
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            buttonPanel.add(button);
        }

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Add button functionality
        CalculatorEngine engine = new CalculatorEngine(textField);
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addActionListener(engine);
            }
        }

        frame.setVisible(true);
    }
}

class CalculatorEngine implements ActionListener {
    private JTextField textField;
    private String operator = "";
    private double num1 = 0;
    private boolean isOperatorClicked = false;

    public CalculatorEngine(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (isOperatorClicked) {
                textField.setText(command);
                isOperatorClicked = false;
            } else {
                textField.setText(textField.getText() + command);
            }
        } else if ("+-*/".contains(command)) {
            num1 = Double.parseDouble(textField.getText());
            operator = command;
            isOperatorClicked = true;
        } else if ("=".equals(command)) {
            double num2 = Double.parseDouble(textField.getText());
            double result = 0;
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": 
                    if (num2 != 0) result = num1 / num2;
                    else textField.setText("Error");
                    return;
            }
            textField.setText(String.valueOf(result));
            isOperatorClicked = false;
        }
    }
}
