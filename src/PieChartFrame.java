import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Objects;

public class PieChartFrame extends JFrame {
    Map<String, Double> data;
    String fileName;
    SecureRandom rand;

    public PieChartFrame() {
        super("Pie Chart");
        rand = new SecureRandom();
        fileName = "src/data.csv";
        data = DataReader.convertToRelative(Objects.requireNonNull(DataReader.read(fileName)));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600, 400);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBackground(g);
        drawPieChart(g);
    }

    public void drawBackground(Graphics g) {
        g.setColor(new Color(153, 153, 153));
        g.fillPolygon(new int[]{50, 450, 450, 50}, new int[]{100, 100, 300, 300}, 4);

        // Change color back to black
        g.setColor(Color.BLACK);
    }

    public void drawPieChart(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        var ref = new Object() {
            int startAngle = 0;
            int startLabel = 150;
        };

        data.keySet().forEach(key -> {
            // Fill segment
            int arcAngle = Math.round((float) (data.get(key) * 360));
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g.fillArc(50, 100, 400, 150, ref.startAngle, arcAngle);

            if (ref.startAngle + arcAngle > 180) {
                int start = Math.max(ref.startAngle, 180);

                for (int y = 100; y <= 150; y++) {
                    g.drawArc(50, y, 400, 150, start,
                            arcAngle);
                }
            }

            ref.startAngle += arcAngle;

            // Put label
            g.fillPolygon(new int[]{490, 500, 500, 490},
                    new int[]{ref.startLabel, ref.startLabel, ref.startLabel + 10, ref.startLabel + 10},
                    4);
            g.setColor(Color.BLACK);
            g.drawString(key, 510, ref.startLabel + 9);
            ref.startLabel += 20;
        });

        g.drawPolygon(new int[] {480, 560, 560, 480}, new int[] {140, 140, ref.startLabel, ref.startLabel}, 4);

        g.drawOval(50, 100, 400, 150);
        g.drawLine(50, 175, 50, 225);
        g.drawLine(450, 175, 450, 225);
        g.drawArc(50, 150, 400, 150, 180, 180);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PieChartFrame());
    }
}
