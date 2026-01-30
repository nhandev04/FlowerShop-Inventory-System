USE QuanLyKhoHang;
GO

CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL,
    FullName NVARCHAR(100),
    Role VARCHAR(20) CHECK (Role IN ('ADMIN', 'EMPLOYEE')),
    IsActive BIT DEFAULT 1
);

INSERT INTO Users (Username, Password, FullName, Role) VALUES 
('admin', '123', N'Quản Trị Viên', 'ADMIN'),
('staff', '123', N'Nhân Viên Bán Hàng', 'EMPLOYEE');
GO