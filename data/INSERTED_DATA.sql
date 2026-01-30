USE QuanLyKhoHang;
GO

INSERT INTO Categories (CategoryName, Description, IsActive) VALUES
(N'Hoa tươi cắt cành', N'Hoa hồng, ly, cúc cắt cành', 1),
(N'Hoa chậu trang trí', N'Các loại hoa trồng trong chậu', 1),
(N'Hoa khô nghệ thuật', N'Hoa khô, hoa bất tử', 1),
(N'Lan Hồ Điệp', N'Chuyên các dòng Lan cao cấp', 1),
(N'Cây cảnh văn phòng', N'Cây xanh để bàn, nội thất', 1),
(N'Phụ kiện gói hoa', N'Giấy gói, ruy băng, nơ', 1),
(N'Chậu - Bình cắm', N'Gốm, sứ, thủy tinh, nhựa', 1),
(N'Đất trồng & Giá thể', N'Đất sạch, xơ dừa, tro trấu', 1),
(N'Phân bón & Thuốc', N'Dinh dưỡng cho hoa, thuốc trừ sâu', 1),
(N'Dụng cụ làm vườn', N'Kéo, bình tưới, xẻng', 1),
(N'Quà tặng kèm', N'Gấu bông, thiệp, sô cô la', 1),
(N'Dịch vụ trang trí', N'Phí nhân công decor sự kiện', 1),
(N'Hạt giống hoa', N'Các loại hạt giống F1', 1),
(N'Combo Valentine', N'Gói combo lễ tình nhân', 1),
(N'Sản phẩm ngừng kinh doanh', N'Danh mục lưu kho cũ', 0);
GO

INSERT INTO Suppliers (SupplierName, ContactName, Phone, Email, Address, IsActive) VALUES
(N'Dalat Hasfarm', N'Nguyễn Văn A', '0901000001', 'sales@hasfarm.vn', N'Đà Lạt', 1),
(N'Vườn Lan Mokara Củ Chi', N'Trần Thị B', '0901000002', 'lan@cuchi.vn', N'Củ Chi, TP.HCM', 1),
(N'Gốm Sứ Bát Tràng', N'Lê Văn C', '0901000003', 'contact@battrang.com', N'Hà Nội', 1),
(N'Phụ Kiện Hoa Sài Gòn', N'Phạm Thị D', '0901000004', 'sale@phukien.sg', N'Quận 5, TP.HCM', 1),
(N'Nhập Khẩu Hà Lan Tulips', N'Mr. John', '0901000005', 'john@tulip.nl', N'Amsterdam (Nhập khẩu)', 1),
(N'Xưởng In Ấn Bao Bì Việt', N'Đỗ Văn E', '0901000006', 'baobi@vietprint.vn', N'Bình Dương', 1),
(N'Nhà Vườn Sa Đéc', N'Ngô Thị F', '0901000007', 'hoa@sadec.vn', N'Đồng Tháp', 1),
(N'Phân Bón Bình Điền', N'Vũ Văn G', '0901000008', 'info@binhdien.com', N'Long An', 1),
(N'Thủy Tinh Pha Lê Tiệp', N'Đặng Thị H', '0901000009', 'sales@phaletiep.vn', N'Quận 1, TP.HCM', 1),
(N'Gấu Bông Cao Cấp', N'Bùi Văn I', '0901000010', 'gaubong@teddy.vn', N'Quận 3, TP.HCM', 1),
(N'Vườn Ươm Cây Giống', N'Hồ Thị K', '0901000011', 'caygiong@nongnghiep.vn', N'Đồng Nai', 1),
(N'Công Ty Logistics Hoa', N'Lý Văn L', '0901000012', 'ship@logistics.vn', N'Tân Bình, TP.HCM', 1),
(N'Chợ Đầu Mối Thủ Đức', N'Tiểu thương M', '0901000013', NULL, N'Thủ Đức', 1),
(N'NCC Đã Ngừng Hợp Tác', N'Nguyễn Văn X', '0901000014', 'admin@stopped.vn', N'Hà Nội', 0),
(N'Đại Lý Đất Sạch', N'Trần Văn N', '0901000015', 'datsach@organic.vn', N'Quận 12', 1);
GO

INSERT INTO Warehouses (WarehouseName, Location, Capacity, ManagerName, Phone, IsActive) VALUES
(N'Kho Lạnh Trung Tâm', N'Quận 7 (Bảo quản hoa tươi)', 1000, N'Quản Lý A', '0902000001', 1),
(N'Kho Phụ Kiện & Bao Bì', N'Quận Tân Bình', 2000, N'Quản Lý B', '0902000002', 1),
(N'Kho Gốm Sứ & Chậu', N'Bình Dương', 5000, N'Quản Lý C', '0902000003', 1),
(N'Cửa Hàng Quận 1', N'Showroom trưng bày', 200, N'Cửa Hàng Trưởng D', '0902000004', 1),
(N'Cửa Hàng Quận 3', N'Chi nhánh 2', 150, N'Cửa Hàng Trưởng E', '0902000005', 1),
(N'Kho Hàng Hủy/Lỗi', N'Khu vực xử lý rác', 100, N'Nhân Viên F', '0902000006', 1),
(N'Kho Hàng Chờ Xử Lý', N'Khu vực kiểm đếm', 300, N'Nhân Viên G', '0902000007', 1),
(N'Kho Online', N'Kho phục vụ E-commerce', 400, N'Quản Lý H', '0902000008', 1),
(N'Kho Dự Trữ Tết', N'Kho thuê ngoài thời vụ', 1000, N'Quản Lý I', '0902000009', 0),
(N'Kho Văn Phòng', N'Vật dụng văn phòng phẩm', 50, N'Admin', '0902000010', 1);
GO

INSERT INTO Products (ProductName, CategoryID, SKU, UnitPrice, ReorderLevel, IsActive) VALUES
(N'Hồng Đỏ Đà Lạt (Bó 50)', 1, 'HONG-DO-DL', 150000, 20, 1),
(N'Hồng Trắng Nhập (Cành)', 1, 'HONG-TR-NK', 25000, 50, 1),
(N'Ly Ù Vàng (Bó 5)', 1, 'LY-U-VA', 120000, 15, 1),
(N'Lan Hồ Điệp Trắng (Vòi)', 4, 'LAN-TR-V1', 250000, 10, 1),
(N'Lan Hồ Điệp Tím (Vòi)', 4, 'LAN-TI-V1', 260000, 10, 1),
(N'Baby Trắng Khô (Bó)', 3, 'BABY-KHO', 85000, 30, 1),
(N'Cỏ Lau Decor (Bó)', 3, 'LAU-DECOR', 45000, 40, 1),
(N'Chậu Sứ Trắng Bát Tràng', 7, 'CHAU-SU-TR', 55000, 20, 1),
(N'Bình Thủy Tinh Cao', 7, 'BINH-TT-CAO', 120000, 15, 1),
(N'Giấy Gói Kraft (Xấp)', 6, 'GIAY-KRAFT', 65000, 50, 1),
(N'Ruy Băng Lụa 2cm (Cuộn)', 6, 'RUY-BANG-2', 15000, 100, 1),
(N'Kéo Cắt Cành Nhật', 10, 'KEO-NHAT', 350000, 5, 1),
(N'Phân Tan Chậm (Gói)', 9, 'PHAN-TAN', 25000, 50, 1),
(N'Xương Rồng Mini', 5, 'XR-MINI', 35000, 30, 1),
(N'Kim Tiền Để Bàn', 5, 'KIM-TIEN', 150000, 10, 1),
(N'Thiệp Chúc Mừng 20/11', 11, 'THIEP-2011', 5000, 200, 1),
(N'Gấu Bông Size L', 11, 'GAU-L', 180000, 10, 1),
(N'Đất Sạch Tribat (Bao)', 8, 'DAT-TRIBAT', 40000, 40, 1),
(N'Dịch Vụ Gói Quà VIP', 12, 'DV-GOI-VIP', 100000, 0, 1),
(N'Hàng Lỗi Thanh Lý', 15, 'LOI-TL', 10000, 0, 1);
GO

INSERT INTO ProductSuppliers (ProductID, SupplierID, SupplierPrice, LeadTimeDays, IsPreferred) VALUES
(1, 1, 120000, 1, 1),
(1, 7, 115000, 2, 0),
(2, 5, 18000, 7, 1),
(3, 1, 90000, 1, 1),
(4, 2, 180000, 3, 1),
(5, 2, 190000, 3, 1),
(6, 4, 60000, 1, 1),
(7, 4, 30000, 1, 1),
(8, 3, 35000, 5, 1),
(9, 9, 80000, 2, 1),
(10, 6, 40000, 3, 1),
(10, 4, 45000, 1, 0),
(11, 4, 8000, 1, 1),
(12, 10, 250000, 7, 1),
(13, 8, 15000, 2, 1),
(14, 11, 20000, 2, 1),
(15, 11, 100000, 2, 1),
(16, 6, 2000, 5, 1),
(17, 10, 120000, 3, 1),
(18, 15, 25000, 2, 1);
GO

INSERT INTO Customers (CustomerName, Phone, Email, Address, IsActive) VALUES
(N'Nguyễn Thị Thu', '0903000001', 'thu@gmail.com', N'Quận 1, TP.HCM', 1),
(N'Trần Văn Hùng', '0903000002', 'hung@yahoo.com', N'Quận 3, TP.HCM', 1),
(N'Công Ty Sự Kiện ABC', '0903000003', 'event@abc.vn', N'Quận 7 (Khách Doanh Nghiệp)', 1),
(N'Khách Vãng Lai', '0000000000', NULL, N'Tại Quầy', 1),
(N'Shop Hoa Tươi Bình Dương', '0903000004', 'shopbd@gmail.com', N'Bình Dương (Khách Sỉ)', 1),
(N'Lê Thị Mơ', '0903000005', NULL, N'Tân Bình', 1),
(N'Ngân Hàng Vietcombank CN 1', '0903000006', 'admin@vcb.vn', N'Quận 1', 1),
(N'Wedding Planner Hạnh Phúc', '0903000007', 'plan@happy.vn', N'Thảo Điền', 1),
(N'Chuỗi Cafe The Coffee', '0903000008', 'purchasing@coffee.vn', N'Toàn Quốc', 1),
(N'Phạm Văn Mách', '0903000009', 'mach@gym.vn', N'Quận 4', 1),
(N'Hội Phụ Nữ Phường 1', '0903000010', NULL, N'Quận 5', 1),
(N'Khách Hàng VIP Gold', '0903000011', 'vip@gmail.com', N'Vinhome Central Park', 1),
(N'Khách Hàng Nợ Xấu', '0903000012', 'noxau@gmail.com', N'Chưa trả tiền', 0),
(N'Hoàng Thùy Linh', '0903000013', 'linh@showbiz.vn', N'Quận 2', 1),
(N'Resort Phan Thiết', '0903000014', 'resort@pt.vn', N'Bình Thuận', 1);
GO

INSERT INTO Inventory (ProductID, WarehouseID, QuantityOnHand, QuantityReserved) VALUES
(1, 1, 500, 50),
(1, 4, 20, 0),
(2, 1, 200, 10),
(3, 1, 150, 0),
(4, 1, 50, 5),
(6, 2, 1000, 0),
(8, 3, 500, 20),
(8, 4, 10, 0),
(8, 5, 15, 0),
(10, 2, 2000, 0),
(10, 4, 50, 0),
(12, 2, 30, 0),
(16, 4, 100, 0),
(18, 3, 500, 0),
(20, 6, 15, 0),
(1, 6, 50, 0),
(5, 1, 40, 2),
(11, 2, 500, 0),
(14, 3, 100, 0),
(17, 4, 10, 2);
GO
INSERT INTO Inventory (ProductID, WarehouseID, QuantityOnHand, QuantityReserved) VALUES
(7, 1, 0, 0),
(9, 3, 0, 0),
(13, 3, 100, 0),
(15, 1, 0, 0),
(19, 1, 0, 0);
GO

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) VALUES
(1, 1, '2026-01-01', '2026-01-02', 15000000, 'Received', N'Nhập hoa định kỳ tuần 1'),
(2, 1, '2026-01-02', '2026-01-05', 8000000, 'Received', N'Nhập Lan Củ Chi'),
(3, 3, '2026-01-03', '2026-01-10', 50000000, 'Pending', N'Nhập Sứ Bát Tràng container'),
(5, 1, '2026-01-05', '2026-01-12', 20000000, 'Shipped', N'Đang nhập khẩu từ Hà Lan'),
(4, 2, '2026-01-06', '2026-01-07', 3000000, 'Received', N'Phụ kiện gấp'),
(6, 2, '2026-01-08', '2026-01-15', 5000000, 'Pending', N'In bao bì Tết'),
(1, 1, '2026-01-10', '2026-01-11', 2000000, 'Cancelled', N'Hủy do NCC báo hết hàng'),
(7, 1, '2026-01-11', '2026-01-12', 4000000, 'Received', N'Hoa Sa Đéc giá rẻ'),
(8, 3, '2026-01-12', '2026-01-13', 10000000, 'Received', N'Phân bón vụ mới'),
(9, 3, '2026-01-13', '2026-01-20', 15000000, 'Shipped', N'Thủy tinh cao cấp'),
(10, 2, '2026-01-14', '2026-01-15', 6000000, 'Received', N'Gấu bông Valentine'),
(11, 3, '2026-01-14', '2026-01-15', 3000000, 'Received', N'Cây giống'),
(1, 4, '2026-01-15', '2026-01-15', 500000, 'Received', N'Giao thẳng ra shop Q1'),
(1, 1, '2026-01-15', '2026-01-16', 12000000, 'Pending', N'Hoa Tết đợt 1'),
(15, 3, '2026-01-15', '2026-01-16', 2000000, 'Pending', N'Đất sạch bổ sung');
GO

INSERT INTO PurchaseOrderDetails (POID, ProductID, Quantity, UnitCost, ReceivedQuantity) VALUES
(1, 1, 100, 120000, 100),
(1, 3, 50, 90000, 50),
(2, 4, 30, 180000, 30),
(2, 5, 20, 190000, 20),
(3, 8, 1000, 35000, 0),
(3, 14, 500, 25000, 0),
(4, 2, 200, 18000, 0),
(5, 6, 50, 60000, 50),
(6, 10, 2000, 2000, 0),
(7, 1, 20, 120000, 0),
(8, 1, 100, 35000, 100),
(9, 13, 500, 20000, 500),
(10, 9, 100, 80000, 0),
(11, 17, 50, 120000, 50),
(12, 14, 100, 20000, 100),
(13, 1, 10, 120000, 10),
(14, 1, 500, 120000, 0),
(14, 3, 200, 90000, 0),
(15, 18, 100, 25000, 0),
(5, 11, 100, 10000, 50);
GO

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, ShippingDate, TotalAmount, Status, ShippingAddress, Notes) VALUES
(4, 4, '2026-01-10', '2026-01-10', 500000, 'Delivered', N'Tại quầy', N'Khách mua bó hoa nhỏ'),
(1, 1, '2026-01-10', '2026-01-10', 2000000, 'Delivered', N'Q1', N'Sinh nhật'),
(3, 1, '2026-01-11', '2026-01-11', 15000000, 'Delivered', N'Q7', N'Sự kiện tất niên'),
(2, 5, '2026-01-11', '2026-01-12', 300000, 'Delivered', N'Q3', N'Giao trễ 1 ngày'),
(5, 1, '2026-01-12', NULL, 5000000, 'Processing', N'Bình Dương', N'Khách sỉ lấy hoa'),
(6, 4, '2026-01-12', NULL, 800000, 'Cancelled', N'Tân Bình', N'Khách hủy vì đổi ý'),
(7, 1, '2026-01-13', '2026-01-13', 3000000, 'Delivered', N'Q1', N'Hoa quầy giao dịch'),
(8, 2, '2026-01-13', NULL, 2000000, 'Pending', N'Thảo Điền', N'Cần phụ kiện gấp'),
(12, 4, '2026-01-14', '2026-01-14', 5000000, 'Delivered', N'Vinhome', N'Khách VIP tặng vợ'),
(4, 5, '2026-01-14', '2026-01-14', 150000, 'Delivered', N'Tại quầy', N'Mua chậu cây'),
(13, 1, '2026-01-15', NULL, 1000000, 'Cancelled', N'Q5', N'Từ chối phục vụ do nợ'),
(9, 4, '2026-01-15', NULL, 20000000, 'Processing', N'Toàn quốc', N'Hợp đồng tháng 1'),
(1, 4, '2026-01-15', NULL, 500000, 'Pending', N'Q1', N'Giao sáng mai'),
(10, 1, '2026-01-15', NULL, 800000, 'Processing', N'Q4', N'Đang bó hoa'),
(14, 1, '2026-01-16', NULL, 10000000, 'Pending', N'Q2', N'Showbiz Event'),
(15, 3, '2026-01-16', NULL, 30000000, 'Shipped', N'Phan Thiết', N'Gốm sứ resort'),
(4, 4, '2026-01-16', '2026-01-16', 200000, 'Delivered', N'Tại quầy', NULL),
(2, 5, '2026-01-17', NULL, 1200000, 'Pending', N'Q3', NULL),
(3, 1, '2026-01-17', NULL, 5000000, 'Pending', N'Q7', NULL),
(11, 4, '2026-01-17', '2026-01-17', 2000000, 'Returned', N'Q5', N'Khách trả hàng do hoa héo');
GO

INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice, Discount) VALUES
(1, 1, 5, 150000, 0),
(2, 4, 2, 250000, 0),
(2, 17, 1, 180000, 10),
(3, 1, 100, 150000, 20),
(4, 14, 2, 35000, 0),
(5, 1, 50, 140000, 5),
(6, 16, 10, 5000, 0),
(7, 5, 5, 260000, 0),
(8, 10, 20, 65000, 0),
(9, 1, 20, 150000, 0),
(9, 19, 1, 100000, 100),
(10, 14, 3, 35000, 0),
(12, 3, 200, 120000, 10),
(13, 1, 5, 150000, 0),
(14, 2, 10, 25000, 0),
(15, 1, 50, 150000, 0),
(16, 8, 500, 55000, 15),
(20, 1, 10, 150000, 0),
(17, 11, 2, 15000, 0),
(18, 4, 3, 250000, 5);
GO

INSERT INTO StockMovements (ProductID, WarehouseID, MovementType, Quantity, ReferenceType, ReferenceID, Notes, MovementDate, CreatedBy) VALUES
(1, 1, 'IN', 100, 'PurchaseOrder', 1, N'Nhập hàng PO1', '2026-01-02', N'Thủ Kho'),
(1, 4, 'OUT', 5, 'SalesOrder', 1, N'Bán lẻ SO1', '2026-01-10', N'Nhân Viên'),
(1, 1, 'OUT', 20, 'SalesOrder', 2, N'Bán SO2', '2026-01-10', N'Thủ Kho'),
(1, 1, 'OUT', 100, 'SalesOrder', 3, N'Xuất sự kiện SO3', '2026-01-11', N'Thủ Kho'),
(1, 1, 'ADJUSTMENT', -2, 'Damaged', NULL, N'Hủy hoa gãy dập', '2026-01-11', N'Quản Lý'),
(1, 6, 'IN', 2, 'Damaged', NULL, N'Nhập kho hủy', '2026-01-11', N'Quản Lý'),
(1, 1, 'TRANSFER', -20, 'Transfer', 1, N'Chuyển sang shop Q1', '2026-01-12', N'Thủ Kho'),
(1, 4, 'TRANSFER', 20, 'Transfer', 1, N'Nhận từ kho lạnh', '2026-01-12', N'Cửa Hàng Trưởng'),
(8, 3, 'IN', 1000, 'PurchaseOrder', 3, N'Nhập gốm', '2026-01-12', N'Thủ Kho Gốm'),
(1, 4, 'IN', 10, 'Return', 20, N'Khách trả hàng SO20', '2026-01-17', N'Cửa Hàng Trưởng'),
(1, 4, 'ADJUSTMENT', -10, 'Damaged', NULL, N'Hủy hàng trả bị hỏng', '2026-01-17', N'Quản Lý'),
(1, 6, 'IN', 10, 'Damaged', NULL, N'Nhập kho hủy', '2026-01-17', N'Quản Lý'),
(17, 2, 'IN', 50, 'PurchaseOrder', 11, N'Nhập gấu bông', '2026-01-14', N'Thủ Kho'),
(17, 2, 'OUT', 1, 'SalesOrder', 2, N'Xuất gấu bông', '2026-01-10', N'Thủ Kho'),
(10, 2, 'ADJUSTMENT', -5, 'Lost', NULL, N'Thất thoát khi kiểm kê', '2026-01-15', N'Kiểm Soát Viên'),
(14, 5, 'OUT', 2, 'SalesOrder', 4, N'Bán cây', '2026-01-14', N'NVBH'),
(8, 3, 'OUT', 500, 'SalesOrder', 16, N'Xuất sỉ resort', '2026-01-16', N'Thủ Kho'),
(1, 1, 'IN', 20, 'PurchaseOrder', 7, N'Nhập PO7', '2026-01-11', N'Thủ Kho'),
(1, 1, 'ADJUSTMENT', -20, 'Correction', NULL, N'Sửa sai nhập PO7 (Đã hủy)', '2026-01-11', N'Admin'),
(5, 1, 'OUT', 5, 'SalesOrder', 7, N'Xuất Lan', '2026-01-13', N'Thủ Kho');
GO

INSERT INTO WarehouseTransfers (ProductID, FromWarehouseID, ToWarehouseID, Quantity, TransferDate, Status, Notes) VALUES
(1, 1, 4, 20, '2026-01-12', 'Completed', N'Cấp hồng đỏ cho shop Q1'),
(1, 1, 5, 20, '2026-01-12', 'Completed', N'Cấp hồng đỏ cho shop Q3'),
(8, 3, 4, 10, '2026-01-10', 'Completed', N'Cấp chậu sứ cho shop Q1'),
(8, 3, 5, 10, '2026-01-10', 'Completed', N'Cấp chậu sứ cho shop Q3'),
(1, 1, 6, 2, '2026-01-11', 'Completed', N'Chuyển hoa hỏng sang kho hủy'),
(1, 4, 6, 10, '2026-01-17', 'Completed', N'Chuyển hàng trả sang kho hủy'),
(10, 2, 4, 50, '2026-01-05', 'Completed', N'Cấp giấy gói'),
(10, 2, 5, 50, '2026-01-05', 'Completed', N'Cấp giấy gói'),
(16, 2, 4, 100, '2026-01-08', 'Completed', N'Cấp thiệp'),
(2, 1, 4, 50, '2026-01-18', 'Pending', N'Đang chuyển hồng trắng'),
(3, 1, 5, 30, '2026-01-18', 'InTransit', N'Xe đang chạy giao Ly'),
(1, 1, 8, 100, '2026-01-15', 'Completed', N'Chuyển sang kho Online'),
(4, 1, 4, 5, '2026-01-14', 'Cancelled', N'Hủy do shop báo còn hàng'),
(17, 2, 4, 10, '2026-01-14', 'Completed', N'Cấp gấu bông'),
(14, 3, 4, 20, '2026-01-14', 'Pending', N'Cấp xương rồng từ vườn');
GO

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-12 08:30:00', 850000, 'Completed', N'Khách vãng lai sáng sớm');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 1, 5, 150000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-13 10:00:00', 2500000, 'Completed', N'Đơn cty sự kiện');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 4, 10, 250000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-14 15:00:00', 450000, 'Completed', N'Khách mua lẻ');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 6, 5, 85000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-15 09:30:00', 1800000, 'Completed', N'Khách quen');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 17, 10, 180000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-16 14:00:00', 5500000, 'Completed', N'Tiệc cưới');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 1, 30, 150000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-17 11:00:00', 3200000, 'Completed', N'Sinh nhật sếp');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 8, 50, 55000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-18 16:00:00', 200000, 'Completed', N'Vắng khách');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 19, 2, 100000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-19 09:00:00', 1500000, 'Completed', N'Văn phòng đặt hoa');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 15, 10, 150000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-20 10:30:00', 2800000, 'Completed', N'Khách VIP');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 5, 10, 260000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-21 13:00:00', 8500000, 'Completed', N'Sự kiện tất niên lớn');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 1, 50, 150000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-22 15:30:00', 4000000, 'Completed', N'Dư âm sự kiện');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 3, 30, 120000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
(19, 1, 0, 0);
GO

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) VALUES
(1, 1, '2026-01-01', '2026-01-02', 15000000, 'Received', N'Nhập hoa định kỳ tuần 1'),
(2, 1, '2026-01-02', '2026-01-05', 8000000, 'Received', N'Nhập Lan Củ Chi'),
(3, 3, '2026-01-03', '2026-01-10', 50000000, 'Pending', N'Nhập Sứ Bát Tràng container'),
(5, 1, '2026-01-05', '2026-01-12', 20000000, 'Shipped', N'Đang nhập khẩu từ Hà Lan'),
(4, 2, '2026-01-06', '2026-01-07', 3000000, 'Received', N'Phụ kiện gấp'),
(6, 2, '2026-01-08', '2026-01-15', 5000000, 'Pending', N'In bao bì Tết'),
(1, 1, '2026-01-10', '2026-01-11', 2000000, 'Cancelled', N'Hủy do NCC báo hết hàng'),
(7, 1, '2026-01-11', '2026-01-12', 4000000, 'Received', N'Hoa Sa Đéc giá rẻ'),
(8, 3, '2026-01-12', '2026-01-13', 10000000, 'Received', N'Phân bón vụ mới'),
(9, 3, '2026-01-13', '2026-01-20', 15000000, 'Shipped', N'Thủy tinh cao cấp'),
(10, 2, '2026-01-14', '2026-01-15', 6000000, 'Received', N'Gấu bông Valentine'),
(11, 3, '2026-01-14', '2026-01-15', 3000000, 'Received', N'Cây giống'),
(1, 4, '2026-01-15', '2026-01-15', 500000, 'Received', N'Giao thẳng ra shop Q1'),
(1, 1, '2026-01-15', '2026-01-16', 12000000, 'Pending', N'Hoa Tết đợt 1'),
(15, 3, '2026-01-15', '2026-01-16', 2000000, 'Pending', N'Đất sạch bổ sung');
GO

INSERT INTO PurchaseOrderDetails (POID, ProductID, Quantity, UnitCost, ReceivedQuantity) VALUES
(1, 1, 100, 120000, 100),
(1, 3, 50, 90000, 50),
(2, 4, 30, 180000, 30),
(2, 5, 20, 190000, 20),
(3, 8, 1000, 35000, 0),
(3, 14, 500, 25000, 0),
(4, 2, 200, 18000, 0),
(5, 6, 50, 60000, 50),
(6, 10, 2000, 2000, 0),
(7, 1, 20, 120000, 0),
(8, 1, 100, 35000, 100),
(9, 13, 500, 20000, 500),
(10, 9, 100, 80000, 0),
(11, 17, 50, 120000, 50),
(12, 14, 100, 20000, 100),
(13, 1, 10, 120000, 10),
(14, 1, 500, 120000, 0),
(14, 3, 200, 90000, 0),
(15, 18, 100, 25000, 0),
(5, 11, 100, 10000, 50);
GO

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, ShippingDate, TotalAmount, Status, ShippingAddress, Notes) VALUES
(4, 4, '2026-01-10', '2026-01-10', 500000, 'Delivered', N'Tại quầy', N'Khách mua bó hoa nhỏ'),
(1, 1, '2026-01-10', '2026-01-10', 2000000, 'Delivered', N'Q1', N'Sinh nhật'),
(3, 1, '2026-01-11', '2026-01-11', 15000000, 'Delivered', N'Q7', N'Sự kiện tất niên'),
(2, 5, '2026-01-11', '2026-01-12', 300000, 'Delivered', N'Q3', N'Giao trễ 1 ngày'),
(5, 1, '2026-01-12', NULL, 5000000, 'Processing', N'Bình Dương', N'Khách sỉ lấy hoa'),
(6, 4, '2026-01-12', NULL, 800000, 'Cancelled', N'Tân Bình', N'Khách hủy vì đổi ý'),
(7, 1, '2026-01-13', '2026-01-13', 3000000, 'Delivered', N'Q1', N'Hoa quầy giao dịch'),
(8, 2, '2026-01-13', NULL, 2000000, 'Pending', N'Thảo Điền', N'Cần phụ kiện gấp'),
(12, 4, '2026-01-14', '2026-01-14', 5000000, 'Delivered', N'Vinhome', N'Khách VIP tặng vợ'),
(4, 5, '2026-01-14', '2026-01-14', 150000, 'Delivered', N'Tại quầy', N'Mua chậu cây'),
(13, 1, '2026-01-15', NULL, 1000000, 'Cancelled', N'Q5', N'Từ chối phục vụ do nợ'),
(9, 4, '2026-01-15', NULL, 20000000, 'Processing', N'Toàn quốc', N'Hợp đồng tháng 1'),
(1, 4, '2026-01-15', NULL, 500000, 'Pending', N'Q1', N'Giao sáng mai'),
(10, 1, '2026-01-15', NULL, 800000, 'Processing', N'Q4', N'Đang bó hoa'),
(14, 1, '2026-01-16', NULL, 10000000, 'Pending', N'Q2', N'Showbiz Event'),
(15, 3, '2026-01-16', NULL, 30000000, 'Shipped', N'Phan Thiết', N'Gốm sứ resort'),
(4, 4, '2026-01-16', '2026-01-16', 200000, 'Delivered', N'Tại quầy', NULL),
(2, 5, '2026-01-17', NULL, 1200000, 'Pending', N'Q3', NULL),
(3, 1, '2026-01-17', NULL, 5000000, 'Pending', N'Q7', NULL),
(11, 4, '2026-01-17', '2026-01-17', 2000000, 'Returned', N'Q5', N'Khách trả hàng do hoa héo');
GO

INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice, Discount) VALUES
(1, 1, 5, 150000, 0),
(2, 4, 2, 250000, 0),
(2, 17, 1, 180000, 10),
(3, 1, 100, 150000, 20),
(4, 14, 2, 35000, 0),
(5, 1, 50, 140000, 5),
(6, 16, 10, 5000, 0),
(7, 5, 5, 260000, 0),
(8, 10, 20, 65000, 0),
(9, 1, 20, 150000, 0),
(9, 19, 1, 100000, 100),
(10, 14, 3, 35000, 0),
(12, 3, 200, 120000, 10),
(13, 1, 5, 150000, 0),
(14, 2, 10, 25000, 0),
(15, 1, 50, 150000, 0),
(16, 8, 500, 55000, 15),
(20, 1, 10, 150000, 0),
(17, 11, 2, 15000, 0),
(18, 4, 3, 250000, 5);
GO

INSERT INTO StockMovements (ProductID, WarehouseID, MovementType, Quantity, ReferenceType, ReferenceID, Notes, MovementDate, CreatedBy) VALUES
(1, 1, 'IN', 100, 'PurchaseOrder', 1, N'Nhập hàng PO1', '2026-01-02', N'Thủ Kho'),
(1, 4, 'OUT', 5, 'SalesOrder', 1, N'Bán lẻ SO1', '2026-01-10', N'Nhân Viên'),
(1, 1, 'OUT', 20, 'SalesOrder', 2, N'Bán SO2', '2026-01-10', N'Thủ Kho'),
(1, 1, 'OUT', 100, 'SalesOrder', 3, N'Xuất sự kiện SO3', '2026-01-11', N'Thủ Kho'),
(1, 1, 'ADJUSTMENT', -2, 'Damaged', NULL, N'Hủy hoa gãy dập', '2026-01-11', N'Quản Lý'),
(1, 6, 'IN', 2, 'Damaged', NULL, N'Nhập kho hủy', '2026-01-11', N'Quản Lý'),
(1, 1, 'TRANSFER', -20, 'Transfer', 1, N'Chuyển sang shop Q1', '2026-01-12', N'Thủ Kho'),
(1, 4, 'TRANSFER', 20, 'Transfer', 1, N'Nhận từ kho lạnh', '2026-01-12', N'Cửa Hàng Trưởng'),
(8, 3, 'IN', 1000, 'PurchaseOrder', 3, N'Nhập gốm', '2026-01-12', N'Thủ Kho Gốm'),
(1, 4, 'IN', 10, 'Return', 20, N'Khách trả hàng SO20', '2026-01-17', N'Cửa Hàng Trưởng'),
(1, 4, 'ADJUSTMENT', -10, 'Damaged', NULL, N'Hủy hàng trả bị hỏng', '2026-01-17', N'Quản Lý'),
(1, 6, 'IN', 10, 'Damaged', NULL, N'Nhập kho hủy', '2026-01-17', N'Quản Lý'),
(17, 2, 'IN', 50, 'PurchaseOrder', 11, N'Nhập gấu bông', '2026-01-14', N'Thủ Kho'),
(17, 2, 'OUT', 1, 'SalesOrder', 2, N'Xuất gấu bông', '2026-01-10', N'Thủ Kho'),
(10, 2, 'ADJUSTMENT', -5, 'Lost', NULL, N'Thất thoát khi kiểm kê', '2026-01-15', N'Kiểm Soát Viên'),
(14, 5, 'OUT', 2, 'SalesOrder', 4, N'Bán cây', '2026-01-14', N'NVBH'),
(8, 3, 'OUT', 500, 'SalesOrder', 16, N'Xuất sỉ resort', '2026-01-16', N'Thủ Kho'),
(1, 1, 'IN', 20, 'PurchaseOrder', 7, N'Nhập PO7', '2026-01-11', N'Thủ Kho'),
(1, 1, 'ADJUSTMENT', -20, 'Correction', NULL, N'Sửa sai nhập PO7 (Đã hủy)', '2026-01-11', N'Admin'),
(5, 1, 'OUT', 5, 'SalesOrder', 7, N'Xuất Lan', '2026-01-13', N'Thủ Kho');
GO

INSERT INTO WarehouseTransfers (ProductID, FromWarehouseID, ToWarehouseID, Quantity, TransferDate, Status, Notes) VALUES
(1, 1, 4, 20, '2026-01-12', 'Completed', N'Cấp hồng đỏ cho shop Q1'),
(1, 1, 5, 20, '2026-01-12', 'Completed', N'Cấp hồng đỏ cho shop Q3'),
(8, 3, 4, 10, '2026-01-10', 'Completed', N'Cấp chậu sứ cho shop Q1'),
(8, 3, 5, 10, '2026-01-10', 'Completed', N'Cấp chậu sứ cho shop Q3'),
(1, 1, 6, 2, '2026-01-11', 'Completed', N'Chuyển hoa hỏng sang kho hủy'),
(1, 4, 6, 10, '2026-01-17', 'Completed', N'Chuyển hàng trả sang kho hủy'),
(10, 2, 4, 50, '2026-01-05', 'Completed', N'Cấp giấy gói'),
(10, 2, 5, 50, '2026-01-05', 'Completed', N'Cấp giấy gói'),
(16, 2, 4, 100, '2026-01-08', 'Completed', N'Cấp thiệp'),
(2, 1, 4, 50, '2026-01-18', 'Pending', N'Đang chuyển hồng trắng'),
(3, 1, 5, 30, '2026-01-18', 'InTransit', N'Xe đang chạy giao Ly'),
(1, 1, 8, 100, '2026-01-15', 'Completed', N'Chuyển sang kho Online'),
(4, 1, 4, 5, '2026-01-14', 'Cancelled', N'Hủy do shop báo còn hàng'),
(17, 2, 4, 10, '2026-01-14', 'Completed', N'Cấp gấu bông'),
(14, 3, 4, 20, '2026-01-14', 'Pending', N'Cấp xương rồng từ vườn');
GO

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-12 08:30:00', 850000, 'Completed', N'Khách vãng lai sáng sớm');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 1, 5, 150000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-13 10:00:00', 2500000, 'Completed', N'Đơn cty sự kiện');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 4, 10, 250000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-14 15:00:00', 450000, 'Completed', N'Khách mua lẻ');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 6, 5, 85000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-15 09:30:00', 1800000, 'Completed', N'Khách quen');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 17, 10, 180000);

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-16 14:00:00', 5500000, 'Completed', N'Tiệc cưới');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 1, 30, 150000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-17 11:00:00', 3200000, 'Completed', N'Sinh nhật sếp');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 8, 50, 55000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-18 16:00:00', 200000, 'Completed', N'Vắng khách');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 19, 2, 100000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-19 09:00:00', 1500000, 'Completed', N'Văn phòng đặt hoa');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 15, 10, 150000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-20 10:30:00', 2800000, 'Completed', N'Khách VIP');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 5, 10, 260000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-21 13:00:00', 8500000, 'Completed', N'Sự kiện tất niên lớn');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 1, 50, 150000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-22 15:30:00', 4000000, 'Completed', N'Dư âm sự kiện');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 3, 30, 120000); 

INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) 
VALUES (4, 1, '2026-01-23 09:00:00', 1200000, 'Completed', N'Cuối tuần');
INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) 
VALUES ((SELECT MAX(SalesOrderID) FROM SalesOrders), 14, 30, 35000); 
GO

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (1, 1, '2026-01-20', '2026-01-21', 18500000, 'Received', N'Nhập hoa tươi đợt 1 tuần 3');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 1, 150, 120000, 150);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 3, 20, 95000, 20);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (2, 1, '2026-01-21', '2026-01-24', 12000000, 'Received', N'Nhập Lan cao cấp');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 4, 30, 185000, 30);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 5, 35, 195000, 35);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (4, 2, '2026-01-22', '2026-01-23', 4500000, 'Received', N'Nhập phụ kiện gói hoa');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 10, 1000, 42000, 1000);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 11, 200, 9000, 200);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (4, 2, '2026-01-23', '2026-01-24', 3200000, 'Received', N'Nhập hoa khô trang trí');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 6, 40, 62000, 40);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 7, 50, 32000, 50);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (3, 3, '2026-01-24', '2026-01-31', 25000000, 'Received', N'Nhập chậu Bát Tràng cao cấp');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 8, 600, 38000, 600);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 9, 150, 85000, 150);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (7, 1, '2026-01-25', '2026-01-26', 8500000, 'Received', N'Nhập hoa Sa Đéc giá tốt');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 1, 80, 118000, 80);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 2, 100, 19000, 100);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (11, 3, '2026-01-26', '2026-01-27', 5500000, 'Received', N'Nhập cây cảnh văn phòng');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 14, 150, 22000, 150);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 15, 50, 105000, 50);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (10, 2, '2026-01-27', '2026-01-28', 9000000, 'Received', N'Chuẩn bị Valentine 14/2');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 17, 80, 125000, 80);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (8, 3, '2026-01-28', '2026-01-29', 3800000, 'Received', N'Nhập phân bón và đất trồng');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 13, 200, 16000, 200);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 18, 100, 27000, 100);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (1, 1, '2026-01-29', '2026-01-30', 22000000, 'Received', N'Nhập hoa tươi cuối tuần lớn');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 1, 200, 122000, 200);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 3, 50, 92000, 50);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 2, 150, 18500, 150);

INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, ExpectedDeliveryDate, TotalAmount, Status, Notes) 
VALUES (4, 2, '2026-01-30', '2026-01-31', 2500000, 'Received', N'Nhập thiệp và dụng cụ làm vườn');
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 16, 500, 3000, 500);
INSERT INTO PurchaseOrderDetails (PurchaseOrderID, ProductID, Quantity, UnitCost, ReceivedQuantity) 
VALUES ((SELECT MAX(PurchaseOrderID) FROM PurchaseOrders), 12, 5, 280000, 5);

GO

INSERT INTO Inventory (ProductID, WarehouseID, QuantityOnHand, QuantityReserved, LastUpdated)
SELECT 
    p.ProductID, 
    1,
    0,
    0, 
    GETDATE()
FROM Products p
WHERE NOT EXISTS (
    SELECT 1 
    FROM Inventory i 
    WHERE i.ProductID = p.ProductID AND i.WarehouseID = 1
);
GO