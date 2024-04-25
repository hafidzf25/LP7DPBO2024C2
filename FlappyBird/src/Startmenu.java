import javax.swing.*;

public class Startmenu {
    private JPanel MainPanel;
    private JButton mulaiButton;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        MainPanel = mainPanel;
    }

    public JButton getMulaiButton() {
        return mulaiButton;
    }

    public void setMulaiButton(JButton mulaiButton) {
        this.mulaiButton = mulaiButton;
    }
}
