import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataReader {

    public static String read(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            String lineSeparator = System.getProperty("line.separator");
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(lineSeparator);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Integer> convertToAbsolute(String dataFromFile) {
        Map<String, Integer> result = new TreeMap<>();

        String lineSeparator = System.getProperty("line.separator");
        String[] labels = dataFromFile.split(lineSeparator)[0].split(" ");
        String[] values = dataFromFile.split(lineSeparator)[1].split(" ");

        for (int i = 0; i < labels.length; i++) {
            result.put(labels[i], Integer.parseInt(values[i]));
        }

        return result;
    }

    public static Map<String, Double> convertToRelative(String dataFromFile) {
        Map<String, Double> result = new TreeMap<>();

        String lineSeparator = System.getProperty("line.separator");
        String[] labels = dataFromFile.split(lineSeparator)[0].split(" ");
        String[] valuesInString = dataFromFile.split(lineSeparator)[1].split(" ");

        List<Integer> values = new ArrayList<>();
        Arrays.stream(valuesInString).forEach(v -> values.add(Integer.parseInt(v)));
        int sum = values.stream().reduce(0, Integer::sum);

        for (int i = 0; i < labels.length; i++) {
            result.put(labels[i], values.get(i) / (double) sum);
        }

        return result;
    }
}
