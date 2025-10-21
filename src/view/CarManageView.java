package view;

import javax.swing.JFrame;

public class CarManageView extends JFrame {
    public CarManageView(){
        this.init();
    }

    private void init() {
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);




        
        this.setVisible(true);
    }

}
