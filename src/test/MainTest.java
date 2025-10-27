
            package test;

    import view.MainApplicationFrame;
    import javax.swing.*;

public class MainTest {
    public static void main(String[] args) {
        // Thiết lập Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chạy ứng dụng trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainApplicationFrame frame = new MainApplicationFrame();
                    frame.setVisible(true);
                    System.out.println("Ứng dụng đã khởi động thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi khởi động ứng dụng: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}