package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.impl.ProductServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
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
        final int PADDING_LEFT = 80;

        public ChartCanvas() {
            setBackground(Color.WHITE);

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    updateTooltip(e.getX(), e.getY());
                }
            });
        }

        private void updateTooltip(int mouseX, int mouseY) {
            if (productList == null || productList.isEmpty()) {
                setToolTipText(null);
                return;
            }

            int height = getHeight();
            int drawHeight = height - PADDING_TOP - PADDING_BOTTOM;

            int maxQty = 0;
            for (ProductDTO p : productList) {
                if (p.getQuantityOnHand() > maxQty)
                    maxQty = p.getQuantityOnHand();
            }
            if (maxQty == 0)
                maxQty = 10;

            for (int i = 0; i < productList.size(); i++) {
                ProductDTO p = productList.get(i);
                int qty = p.getQuantityOnHand();
                int barHeight = (int) (((double) qty / maxQty) * drawHeight);
                int x = PADDING_LEFT + i * (BAR_WIDTH + GAP) + 10;
                int y = height - PADDING_BOTTOM - barHeight;

                if (mouseX >= x && mouseX <= x + BAR_WIDTH &&
                        mouseY >= y && mouseY <= height - PADDING_BOTTOM) {

                    DecimalFormat df = new DecimalFormat("#,###");
                    String tooltip = "<html>" +
                            "<b>Sản phẩm:</b> " + p.getProductName() + "<br>" +
                            "<b>Mã SKU:</b> " + p.getSku() + "<br>" +
                            "<b>Tồn kho:</b> " + qty + "<br>" +
                            "<b>Giá bán:</b> " + df.format(p.getPrice()) + " VND" +
                            "</html>";
                    setToolTipText(tooltip);
                    return;
                }
            }
            setToolTipText(null);
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
            if (productList == null || productList.isEmpty())
                return;

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
                if (p.getQuantityOnHand() > maxQty)
                    maxQty = p.getQuantityOnHand();
            }
            if (maxQty == 0)
                maxQty = 10;

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            int numLabels = 5;
            for (int i = 0; i <= numLabels; i++) {
                int value = (maxQty * i) / numLabels;
                int yPos = height - PADDING_BOTTOM - (drawHeight * i / numLabels);

                g2.drawLine(PADDING_LEFT - 5, yPos, PADDING_LEFT, yPos);

                String label = String.valueOf(value);
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, PADDING_LEFT - labelWidth - 10, yPos + 4);
            }

            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.drawString("Số lượng", 10, PADDING_TOP - 10);

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
                String label = p.getProductName();
                if (label.length() > 15)
                    label = label.substring(0, 14) + "..";
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, x + (BAR_WIDTH - labelWidth) / 2, height - PADDING_BOTTOM + 15);
            }
        }
    }
}