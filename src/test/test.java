package test;

import view.LoginFrame;
import javax.swing.SwingUtilities;

public class test {
    public static void main(String[] args) {
        {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
}