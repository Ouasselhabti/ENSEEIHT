import javax.swing.*;
import java.awt.*;

public class LightUpPanel extends JPanel {

    private static final Color GREEN_COLOR = new Color(70, 200, 100);
    private static final Color RED_COLOR = new Color(200, 70, 70);

    private boolean isLit = false;

    public LightUpPanel() {
        setPreferredSize(new Dimension(50, 50));
        setBackground(RED_COLOR);
    }

    public void setLit(boolean isLit) {
        this.isLit = isLit;
        setBackground(isLit ? GREEN_COLOR : RED_COLOR);
    }

    public boolean isLit() {
        return isLit;
    }
}
