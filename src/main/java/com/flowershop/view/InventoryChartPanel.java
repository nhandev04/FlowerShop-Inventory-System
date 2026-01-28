package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.impl.ProductServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class InventoryChartPanel extends JPanel {

    private final ProductService productService;
    private List<ProductDTO> productList;
    private ChartCanvas canvas;
    private JScrollPane scrollPane;

    public InventoryChartPanel() {
        this.productService = new ProductServiceImpl();
        this.productList = productService.getAllProducts();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("THỐNG KÊ TỒN KHO (Kéo thanh trượt để xem thêm)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 51, 102));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        // 2. VÙNG VẼ BIỂU ĐỒ (Đặt trong thanh cuộn)
        canvas = new ChartCanvas();
        scrollPane = new JScrollPane(canvas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm mới biểu đồ");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.addActionListener(e -> {
            this.productList = productService.getAllProducts();
            canvas.revalidate();
            canvas.repaint();
        });
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.add(btnRefresh);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private class ChartCanvas extends JPanel {
        final int BAR_WIDTH = 50;
        final int GAP = 30;
        final int PADDING_TOP = 40;
        final int PADDING_BOTTOM = 60;
        final int PADDING_LEFT = 50;

        public ChartCanvas() {
            setBackground(Color.WHITE);
        }

        @Override
        public Dimension getPreferredSize() {
            int count = (productList != null) ? productList.size() : 0;
            int totalWidth = PADDING_LEFT + count * (BAR_WIDTH + GAP) + 100;
            int totalHeight = 400;
            return new Dimension(totalWidth, totalHeight);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (productList == null || productList.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int height = getHeight();
            int drawHeight = height - PADDING_TOP - PADDING_BOTTOM;
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(PADDING_LEFT, height - PADDING_BOTTOM, getWidth() - 20, height - PADDING_BOTTOM);
            g2.drawLine(PADDING_LEFT, height - PADDING_BOTTOM, PADDING_LEFT, PADDING_TOP);

            int maxQty = 0;
            for (ProductDTO p : productList) {
                if (p.getQuantityOnHand() > maxQty) maxQty = p.getQuantityOnHand();
            }
            if (maxQty == 0) maxQty = 10;

            for (int i = 0; i < productList.size(); i++) {
                ProductDTO p = productList.get(i);
                int qty = p.getQuantityOnHand();
                int barHeight = (int) (((double) qty / maxQty) * drawHeight);
                int x = PADDING_LEFT + i * (BAR_WIDTH + GAP) + 10;
                int y = height - PADDING_BOTTOM - barHeight;

                g2.setColor(new Color(0, 102, 204));
                g2.fillRect(x, y, BAR_WIDTH, barHeight);
                g2.setColor(Color.WHITE);
                g2.drawRect(x, y, BAR_WIDTH, barHeight);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                String qtyStr = String.valueOf(qty);
                int strWidth = g2.getFontMetrics().stringWidth(qtyStr);
                g2.drawString(qtyStr, x + (BAR_WIDTH - strWidth) / 2, y - 5);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                String label = p.getSku();
                if (label.length() > 8) label = label.substring(0, 7) + "..";
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, x + (BAR_WIDTH - labelWidth) / 2, height - PADDING_BOTTOM + 15);
            }
        }
    }
}