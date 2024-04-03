import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame implements ActionListener {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;
    private RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        setTitle("Random Product Search");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());
        JPanel resultPanel = new JPanel(new BorderLayout());

        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        searchPanel.add(new JLabel("Enter partial product name: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Initialize RandomAccessFile
        try {
            randomAccessFile = new RandomAccessFile("products.dat", "r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String partialName = searchField.getText();
            searchProducts(partialName);
        }
    }

    private void searchProducts(String partialName) {
        try {
            randomAccessFile.seek(0); // Reset file pointer to beginning

            String id, name, description;
            double cost;
            StringBuilder matchingProducts = new StringBuilder();

            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                id = randomAccessFile.readUTF();
                name = randomAccessFile.readUTF().trim(); // Remove trailing spaces
                description = randomAccessFile.readUTF().trim(); // Remove trailing spaces
                cost = randomAccessFile.readDouble();

                // Check if the name contains the partial name
                if (name.contains(partialName)) {
                    matchingProducts.append("ID: ").append(id).append("\n");
                    matchingProducts.append("Name: ").append(name).append("\n");
                    matchingProducts.append("Description: ").append(description).append("\n");
                    matchingProducts.append("Cost: $").append(cost).append("\n\n");
                }
            }

            if (matchingProducts.length() == 0) {
                resultArea.setText("No matching products found.");
            } else {
                resultArea.setText(matchingProducts.toString());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandProductSearch().setVisible(true);
        });
    }
}
