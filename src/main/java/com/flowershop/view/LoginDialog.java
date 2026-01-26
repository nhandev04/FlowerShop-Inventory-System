package com.flowershop.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {
    private String userRole = null;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private boolean isAuthenticated = false;

    public LoginDialog(Frame parent) {
        super(parent, "Đăng nhập Hệ thống", true);
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnlCenter.add(new JLabel("Tài khoản:"));
        txtUser = new JTextField();
        pnlCenter.add(txtUser);

        pnlCenter.add(new JLabel("Mật khẩu:"));
        txtPass = new JPasswordField();
        pnlCenter.add(txtPass);

        add(pnlCenter, BorderLayout.CENTER);

        JButton btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));

        this.getRootPane().setDefaultButton(btnLogin);

        btnLogin.addActionListener(e -> checkLogin());

        JPanel pnlButton = new JPanel();
        pnlButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pnlButton.add(btnLogin);
        add(pnlButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                txtUser.requestFocus();
            }
        });
    }

    private void checkLogin() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (user.equals("admin") && pass.equals("123")) {
            userRole = "ADMIN";
            isAuthenticated = true;
            dispose();
        } else if (user.equals("staff") && pass.equals("123")) {
            userRole = "EMPLOYEE";
            isAuthenticated = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi Đăng Nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public String getUserRole() {
        return userRole;
    }
}