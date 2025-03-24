import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * ShowTable is to show Java GUI of the Benchmark Results of sorting algor data from text files
 * It should allow user to open a JFileChooser to read a text file
 * It should compute all statistical calculations for the sorting: Size;Avg Count;Coef Count(%);Avg Time(ns);Coef Time(%)
 * This should be on a 5 col, row 12 table to show all results.
 * <p>
 * Course: CMSC 451
 * <p>
 * Date: 1/31/2025
 * <p>
 * Project: Project 1
 *
 * @author Victoria Lee
 *
 * @version JRE17
 */
public class ShowTable {

	/**
	 * This calls the Java GUI Benchmark table and calculated values.
	 * @param args runs program
	 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ShowTable().createAndShowGUI(); // calls the Java GUI Benchmark table and calculated values.
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This creates and show the Java GUI Benchmark table and calculated values.
     * @throws IOException
     */
    void createAndShowGUI() throws IOException { // creating the Java GUI benchmark table
        JFrame frame = new JFrame("Benchmark Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Allows the user to choose the text file to read.
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	
            String filePath = fileChooser.getSelectedFile().getPath(); // Get file path
            String fileName = fileChooser.getSelectedFile().getName(); // Get the file name
            frame.setTitle(fileName); // Set the JFrame title to the name of the chosen file
            
            // Read data from the selected file
            String[][] data = readData(filePath);
            String[] columnNames = {"Size", "Avg Count", "Coef Count(%)", "Avg Time(ns)", "Coef Time(%)"};

            JTable table = new JTable(new DefaultTableModel(data, columnNames));
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
    
    /**
     * This reads the txt file and parse the values to calculate the statistical requirements
     * It should calculate: Size;Avg Count;Coef Count(%);Avg Time(ns);Coef Time(%) based on the data txt file
     * @param file user selected the text file to take data and calculate statistical results for benchmark
     * @return data this returns the calculated results for the benchmark to appear on JAva GUI
     * @throws IOException should throw an exception if the Java GUI Benchmark Results Fails
     */
    private String[][] readData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        String[][] data = new String[12][5]; // in puts data based on 12 rows and 5 col. (12 rows because max size is 1200)
        int i = 0;
        while ((line = reader.readLine()) != null) { // prepares to read the text file by line and parse the values.
            String[] parts = line.split(" ");
            int size = Integer.parseInt(parts[0]);
            double totalCritical = 0;
            double totalTime = 0;
            double[] criticalOps = new double[40];
            double[] times = new double[40];
            
            // Organize for the calculations from text; sum the values based on index[even or odd]
            for (int j = 0; j < 40; j++) {
                criticalOps[j] = Double.parseDouble(parts[2 * j + 1]);
                times[j] = Double.parseDouble(parts[2 * j + 2]);
                totalCritical += criticalOps[j];
                totalTime += times[j];
            }

            // Calculate the avgcrit and avgtime
            double avgCritical = totalCritical / 40;
            double avgTime = totalTime / 40;
            
            // Calc std varenance for cirt count and time
            double criticalVariance = 0;
            double timeVariance = 0;
            
            // Sum the dev for critvar and timevar
            for (int j = 0; j < 40; j++) {
                criticalVariance += Math.pow(criticalOps[j] - avgCritical, 2);
                timeVariance += Math.pow(times[j] - avgTime, 2);
            }
            
            // Calculate the std of the critical variance and time
            double criticalStdDev = Math.sqrt(criticalVariance / 40);
            double timeStdDev = Math.sqrt(timeVariance / 40);
            
            // Calculate the coefficient of variation (CV) for both critical count and time
            double criticalCov =  (criticalStdDev / avgCritical) * 100; // Coefficient of variation in percentage
            double timeCov = (timeStdDev / avgTime) * 100; // Coefficient of variation in percentage
            
            // Store the results in the data array for the Java GUI benchmark per 5 cols and 12 rows of data.
            data[i][0] = String.valueOf(size);
            data[i][1] = String.valueOf(avgCritical); // Average critical count
            data[i][2] = String.format("%.2f%%", criticalCov); // Coefficient of variation for critical count
            data[i][3] = String.valueOf(avgTime); // Average time in ns
            data[i][4] = String.format("%.2f%%", timeCov); // Coefficient of variation for time
            i++;
        }
        reader.close();
        return data;
    }
}
