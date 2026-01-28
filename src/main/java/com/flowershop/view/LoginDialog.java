package com.flowershop.view;

import com.flowershop.model.dto.UserDTO;
import com.flowershop.service.UserService;
import com.flowershop.service.impl.UserServiceImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {
    private String userRole = null;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private boolean isAuthenticated = false;
    private final UserService userService;

    public LoginDialog(Frame parent) {
        super(parent, "Đăng nhập Hệ thống", true);
        this.userService = new UserServiceImpl();

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

        btnLogin.addActionListener(e -> processLogin());

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

    private void processLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDTO loginUser = userService.login(user, pass);

        if (loginUser != null) {
            this.userRole = loginUser.getRole();
            this.isAuthenticated = true;
            JOptionPane.showMessageDialog(this, "Xin chào: " + loginUser.getFullName());
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