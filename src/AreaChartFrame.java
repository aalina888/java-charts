import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AreaChartFrame extends JFrame {
    Map<String, Integer> data;
    String fileName;

    public AreaChartFrame() {
        super("Aria Chart");
        fileName = "src/data.csv";
        data = DataReader.convertToAbsolute(Objects.requireNonNull(DataReader.read(fileName)));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600, 400);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBackground(g);
        drawAxes(g);
        drawTicksOnXAxis(g);
        drawTicksOnYAxis(g);
        drawAreaChart(g);
    }

    public void drawBackground(Graphics g) {
        g.setColor(new Color(153, 153, 153));
        g.fillPolygon(new int[]{50, 550, 550, 50}, new int[]{50, 50, 350, 350}, 4);

        // Change color back to black
        g.setColor(Color.BLACK);
    }

    public void drawAxes(Graphics g) {
        // X Axis
        g.drawLine(45, 350, 555, 350);

        // Y Axis
        g.drawLine(50, 350, 50, 50);
    }

    public void drawTicksOnXAxis(Graphics g) {
        int numberOfTicks = data.keySet().size() - 1;

        for (int x = 50; x <= 550; x += 500 / numberOfTicks) {
            g.drawLine(x, 350, x, 355);
        }
    }

    public void drawTicksOnYAxis(Graphics g) {
        int maximumValue = Collections.max(data.values());

        int i = 0;
        for (int y = 350; y >= 50; y -= 300 / (maximumValue / 10 + 1)) {
            g.drawLine(45, y, 550, y);
            g.drawString(String.valueOf(i * 10), 15, y + 5);
            i++;
        }
    }

    public void drawAreaChart(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        List<Integer> xArrayList = new ArrayList<>();
        List<Integer> yArrayList = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        var ref = new Object() {
            int x = 50;
        };

        int numberOfTicks = data.keySet().size() - 1;
        int maximumValue = Collections.max(data.values());
        data.keySet().forEach(key -> {
            labels.add(key);
            xArrayList.add(ref.x);
            yArrayList.add((int) (350 - (data.get(key) / 10.0 * (300 / (maximumValue / 10 + 1)))));
            ref.x += 500 / numberOfTicks;
        });

        ref.x = 40;
        labels.forEach(label -> {
            g.drawString(label, ref.x, 380);
            ref.x += 500 / numberOfTicks;
        });

        xArrayList.add(550);
        yArrayList.add(350);

        xArrayList.add(50);
        yArrayList.add(350);

        int[] xArray = new int[xArrayList.size()];
        int[] yArray = new int[yArrayList.size()];
        ref.x = 0;
        xArrayList.forEach(x -> {
            xArray[ref.x] = x;
            ref.x++;
        });
        ref.x = 0;
        yArrayList.forEach(y -> {
            yArray[ref.x] = y;
            ref.x++;
        });

        g.setStroke(new BasicStroke(2));
        g.drawPolygon(xArray, yArray, xArray.length);
        g.setColor(new Color(142, 124, 195));
        g.fillPolygon(xArray, yArray, xArray.length);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AreaChartFrame());
    }
}
