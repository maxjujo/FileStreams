import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField idField, nameField, descField, costField, recordCountField;
    private JButton addButton;
    private RandomAccessFile file;
    private int recordCount;



    public RandProductMaker() {
        setTitle("Random Product Maker");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID:"));
        idField = new JTextField(10);
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descField = new JTextField(30);
        panel.add(descField);

        panel.add(new JLabel("Cost:"));
        costField = new JTextField(10);
        panel.add(costField);

        panel.add(new JLabel("Record Count:"));
        recordCountField = new JTextField(10);
        recordCountField.setEditable(false);
        panel.add(recordCountField);

        addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());
        panel.add(addButton);

        add(panel);

        try {
            file = new RandomAccessFile("products.dat", "rw");
            if (file.length() == 0) {
                recordCount = 0;
            } else {
                recordCount = (int) (file.length() / Product.RECORD_SIZE);
            }
            recordCountField.setText(String.valueOf(recordCount));
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            String name = nameField.getText();
            String description = descField.getText();
            double cost = Double.parseDouble(costField.getText());

            if (id.isEmpty() || name.isEmpty() || description.isEmpty() || cost <= 0) {
                JOptionPane.showMessageDialog(RandProductMaker.this, "Please fill all fields correctly.");
                return;
            }

            try {
                file.seek(file.length());
                Product product = new Product(id, name, description, cost);
                file.writeUTF(product.getID());
                file.writeUTF(product.getName());
                file.writeUTF(product.getDescription());
                file.writeDouble(product.getCost());

                recordCount++;
                recordCountField.setText(String.valueOf(recordCount));

                idField.setText("");
                nameField.setText("");
                descField.setText("");
                costField.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandProductMaker().setVisible(true);
        });
    }
}
