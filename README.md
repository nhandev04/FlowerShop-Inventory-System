# Hệ thống Quản lý Kho Shop Hoa (Flower Shop Inventory System)

> **Đồ án cuối kỳ môn Lập trình Java**
> **Tác giả:** Hoa Le
> **Trường:** University of Information Technology (UIT) - VNU-HCM

## Giới thiệu
**Hệ thống Quản lý Kho Shop Hoa** là phần mềm quản lý quy trình lưu kho và vận hành cho các cửa hàng hoa. Ứng dụng tập trung giải quyết bài toán quản lý số lượng lớn các loại hoa tươi, phụ kiện, vật tư với đặc thù hạn sử dụng ngắn và luân chuyển nhanh.

Dự án được xây dựng theo mô hình **3-Layer (MVC)**, áp dụng các **Design Patterns** nâng cao để đảm bảo tính mở rộng và bảo trì.

## Tính năng nổi bật

### 1. Quản lý Kho hàng (Inventory Core)
- **Danh mục đa dạng:** Quản lý hoa tươi, phụ kiện, chậu, phân bón....
- **Theo dõi thời gian thực:** Cập nhật số lượng tồn kho ngay lập tức khi có giao dịch.
- **Cảnh báo:** Tự động báo động các mặt hàng sắp hết (Reorder Level).
- **Quản lý Nhà cung cấp:** Lưu trữ thông tin nguồn nhập hàng chi tiết.

### 2. Nghiệp vụ Xuất/Nhập (Inbound & Outbound)
- **Tạo phiếu bán hàng (Xuất kho):** Tự động trừ tồn kho khi hoàn tất đơn hàng.
- **Lịch sử giao dịch:** Ghi vết đầy đủ lịch sử nhập/xuất để đối soát.

### 3. Báo cáo & Thống kê (Reporting)
- **Dashboard thông minh:** Tổng quan tình hình kinh doanh, số lượng đơn hàng và tổng giá trị kho.
- **Biểu đồ trực quan:**
    - Biểu đồ đường (Line Chart): Phân tích xu hướng biến động kho/doanh thu.
    - Biểu đồ tròn (Pie Chart): Tỷ trọng hàng hóa theo danh mục.
- **Xuất báo cáo:** Hỗ trợ xuất dữ liệu kho ra file Excel (.xlsx) để kiểm kê thực tế.

### 4. Hệ thống & Bảo mật
- **Phân quyền người dùng:** Cơ chế đăng nhập bảo mật cho Admin (Quản lý) và Nhân viên kho/bán hàng.
- **Giao diện hiện đại:** Thiết kế phẳng (FlatLaf), thân thiện với người dùng.

## Công nghệ sử dụng

| Category | Technology |
|Data Access| **JDBC** (Java Database Connectivity) |
|Database| **Microsoft SQL Server** |
|UI Framework| **Java Swing** |
|Look & Feel| **FlatLaf** (Modern UI Theme) |
|Charting| **JFreeChart** |
|Export| **Apache POI** (Excel Processing) |
|Build Tool| Maven |

## Kiến trúc & Design Patterns
Dự án áp dụng triệt để các mẫu thiết kế để đảm bảo code "sạch" (Clean Code):

1.  **MVC (Model-View-Controller):** Tách biệt logic xử lý và giao diện người dùng.
2.  **Singleton Pattern:** Quản lý kết nối Database và sự kiện hệ thống tập trung.
3.  **DAO Pattern:** Trừu tượng hóa việc truy xuất dữ liệu SQL.
4.  **Observer Pattern:** Cơ chế tự động đồng bộ dữ liệu giữa Kho và Bán hàng (Real-time update).
5.  **Factory Pattern:** Linh hoạt trong việc mở rộng định dạng xuất báo cáo (Excel, PDF...).

## Cài đặt & Chạy

1.  **Clone dự án:**
    ```bash
    git clone https://github.com/Hoale66P1/FlowerShop-Inventory-System
    ```
2.  **Cấu hình Database:**
    - Mở SQL Server Management Studio.
    - Chạy script `QuanLyKhoHang.sql` để tạo cấu trúc bảng.
    - Chạy script `INSERT_DATA.sql` để nạp dữ liệu mẫu.
    - Cập nhật thông tin kết nối trong file `DatabaseConnection.java`.
3.  **Khởi chạy:**
    - Mở dự án bằng IntelliJ IDEA / Eclipse.
    - Run file `Main.java`.
    - Tài khoản Admin: `admin` / `123`.
    - Tài khoản Nhân viên: `staff` / `123`.

---
Code with ❤️ by **Hoa Le**