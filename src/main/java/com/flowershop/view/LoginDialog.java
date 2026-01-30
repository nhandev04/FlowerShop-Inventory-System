package com.flowershop.view;

import com.flowershop.dao.impl.UserDAOImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginDialog extends JDialog {

    private JComboBox<String> cboUsername;
    private JPasswordField txtPassword;
    private boolean authenticated = false;
    private String userRole = "";

    public LoginDialog(Frame parent) {
        super(parent, "Đăng Nhập Hệ Thống", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setPreferredSize(new Dimension(450, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblUsername = new JLabel("Tài khoản:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] usernames = { "admin", "staff" };
        cboUsername = new JComboBox<>(usernames);
        cboUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboUsername.setPreferredSize(new Dimension(200, 35));
        formPanel.add(cboUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(200, 35));
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
        formPanel.add(txtPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(130, 40));
        btnLogin.setBackground(new Color(25, 118, 210));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> handleLogin());

        JButton btnCancel = new JButton("Thoát");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setPreferredSize(new Dimension(130, 40));
        btnCancel.setBackground(new Color(189, 189, 189));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> {
            authenticated = false;
            dispose();
        });

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        SwingUtilities.invokeLater(() -> cboUsername.requestFocusInWindow());
    }

    private void handleLogin() {
        String username = (String) cboUsername.getSelectedItem();
        String password = new String(txtPassword.getPassword());

        if (username == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn tài khoản và nhập mật khẩu!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDAOImpl userDAO = new UserDAOImpl();
        com.flowershop.model.dto.UserDTO user = userDAO.checkLogin(username, password);

        if (user != null) {
            authenticated = true;
            userRole = user.getRole();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Tài khoản hoặc mật khẩu không đúng!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocusInWindow();
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getUserRole() {
        return userRole;
    }
}
