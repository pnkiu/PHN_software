package view;


import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class LoginFrame extends JFrame {
	private JTextField jtextfield_username;
	private JPasswordField jtextfield_password;
    private JButton jbutton_login;
	public LoginFrame() {
		this.init();
	}

	private void init() {
		this.setTitle("Login");
		this.setSize(400, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		// Form Panel
		JPanel formLogin = new JPanel();
		formLogin.setBackground(Color.white);
		formLogin.setPreferredSize(new Dimension(400, 500));
        formLogin.setLayout(new BoxLayout(formLogin, BoxLayout.Y_AXIS));
        formLogin.setBorder(new EmptyBorder(60, 60, 60, 60));
		
		// Title
		Font login_font = new Font("Arial", Font.BOLD, 25);
		JLabel jlabel_title = new JLabel("Login", SwingConstants.CENTER);
		jlabel_title.setFont(login_font);

		formLogin.add(jlabel_title);
		formLogin.add(Box.createVerticalStrut(20));

		// Label UserName
        JLabel jlabel_username = new JLabel("User Name");
        jlabel_username.setFont(new Font("Arial", Font.PLAIN, 14));
        jlabel_username.setAlignmentX(Component.LEFT_ALIGNMENT);
        formLogin.add(jlabel_username);
        
        jtextfield_username = new JTextField();
        jtextfield_username.setPreferredSize(new Dimension(200, 35));
        jtextfield_username.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        formLogin.add(jtextfield_username);
        formLogin.add(Box.createVerticalStrut(20));
       
		// Label Password
		JLabel jlabel_password = new JLabel("Password");
		jlabel_password.setFont(new Font("Arial", Font.PLAIN, 14));
		jlabel_password.setAlignmentX(Component.LEFT_ALIGNMENT);
		formLogin.add(jlabel_password);

		jtextfield_password = new JPasswordField();
		jtextfield_password.setPreferredSize(new Dimension(200, 35));
		jtextfield_password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		formLogin.add(jtextfield_password);
		formLogin.add(Box.createVerticalStrut(30));
		
		// Login Button
		Font font_button_login = new Font("Arial", Font.BOLD, 14);
		jbutton_login = new JButton("Login");
		jbutton_login.setFont(font_button_login);
		jbutton_login.setBackground(new Color(135, 206, 235));
		jbutton_login.setFocusPainted(false);
		jbutton_login.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbutton_login.setPreferredSize(new Dimension(250, 35));
		jbutton_login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		formLogin.add(jbutton_login);
		formLogin.add(Box.createVerticalStrut(25));
		
		this.add(formLogin);
                // Hiệu ứng pháo hoa
        FireworksPanel fireworks = new FireworksPanel();
        this.setGlassPane(fireworks);

        jbutton_login.addActionListener(e -> {
            fireworks.startFireworks();
        });
		
		this.setVisible(true);
		
	}
        // --- Class Fireworks Panel ---
    class FireworksPanel extends JComponent {
        private final java.util.List<Particle> particles = new ArrayList<>();
        private final Random rand = new Random();
        private final Timer timer;

        public FireworksPanel() {
            setOpaque(false);
            timer = new Timer(30, e -> update());
        }

        public void startFireworks() {
            setVisible(true);
            for (int i = 0; i < 8; i++) {
                createExplosion(rand.nextInt(getWidth()), rand.nextInt(getHeight() / 2) + 100);
            }
            timer.start();
        }

        private void createExplosion(int x, int y) {
            for (int i = 0; i < 80; i++) {
                double angle = Math.toRadians(i * (360 / 80));
                double speed = 2 + rand.nextDouble() * 3;
                particles.add(new Particle(x, y, Math.cos(angle) * speed, Math.sin(angle) * speed,
                        new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
            }
        }

        private void update() {
            Iterator<Particle> it = particles.iterator();
            while (it.hasNext()) {
                Particle p = it.next();
                p.x += p.vx;
                p.y += p.vy;
                p.vy += 0.1; // gravity
                p.life -= 0.03;
                if (p.life <= 0) it.remove();
            }
            repaint();
            if (particles.isEmpty()) {
                timer.stop();
                setVisible(false);
            }
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            for (Particle p : particles) {
                g2.setColor(p.color);
                g2.fillOval((int) p.x, (int) p.y, 5, 5);
            }
            g2.dispose();
        }
    }

    // --- Particle class ---
    class Particle {
        double x, y, vx, vy, life = 1.0;
        Color color;

        Particle(double x, double y, double vx, double vy, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
        }
    }


	public static void main(String[] args) {
		new LoginFrame();
	}

}
