CREATE DATABASE QuanLyKhoHang;
GO

USE QuanLyKhoHang;
GO

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    CategoryName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(255),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);
GO

CREATE TABLE Suppliers (
    SupplierID INT PRIMARY KEY IDENTITY(1,1),
    SupplierName NVARCHAR(200) NOT NULL,
    ContactName NVARCHAR(100),
    Phone VARCHAR(20),
    Email VARCHAR(100),
    Address NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);
GO

CREATE TABLE Products (
    ProductID INT PRIMARY KEY IDENTITY(1,1),
    ProductName NVARCHAR(200) NOT NULL,
    CategoryID INT,
    SKU VARCHAR(50) UNIQUE,
    UnitPrice DECIMAL(18, 2),
    ReorderLevel INT DEFAULT 10,
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1,
    
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);
GO

CREATE TABLE ProductSuppliers (
    ProductID INT,
    SupplierID INT,
    SupplierPrice DECIMAL(18, 2),
    LeadTimeDays INT DEFAULT 7,
    IsPreferred BIT DEFAULT 0,
    CreatedAt DATETIME DEFAULT GETDATE(),

    PRIMARY KEY (ProductID, SupplierID),

    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID)
);
GO

CREATE TABLE Warehouses (
    WarehouseID INT PRIMARY KEY IDENTITY(1,1),
    WarehouseName NVARCHAR(100) NOT NULL,
    Location NVARCHAR(255),
    Capacity INT,
    ManagerName NVARCHAR(100),
    Phone VARCHAR(20),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);
GO

CREATE TABLE Inventory (
    ProductID INT,
    WarehouseID INT,
    QuantityOnHand INT DEFAULT 0,
    QuantityReserved INT DEFAULT 0,
    LastUpdated DATETIME DEFAULT GETDATE(),

    PRIMARY KEY (ProductID, WarehouseID),

    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouses(WarehouseID)
);
GO

CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY IDENTITY(1,1),
    CustomerName NVARCHAR(100) NOT NULL,
    Phone VARCHAR(20),
    Email VARCHAR(100),
    Address NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);
GO

CREATE TABLE PurchaseOrders (
    POID INT PRIMARY KEY IDENTITY(1,1),
    SupplierID INT NOT NULL,
    WarehouseID INT,
    OrderDate DATETIME DEFAULT GETDATE(),
    ExpectedDeliveryDate DATETIME,
    TotalAmount DECIMAL(18, 2),
    Status NVARCHAR(50) DEFAULT 'Pending',
    Notes NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouses(WarehouseID)
);
GO

CREATE TABLE PurchaseOrderDetails (
    POID INT,
    ProductID INT,
    Quantity INT NOT NULL,
    UnitCost DECIMAL(18, 2) NOT NULL,
    ReceivedQuantity INT DEFAULT 0,

    PRIMARY KEY (POID, ProductID),

    FOREIGN KEY (POID) REFERENCES PurchaseOrders(POID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);
GO

CREATE TABLE SalesOrders (
    SalesOrderID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT,
    WarehouseID INT,
    OrderDate DATETIME DEFAULT GETDATE(),
    ShippingDate DATETIME,
    TotalAmount DECIMAL(18, 2),
    Status NVARCHAR(50) DEFAULT 'Pending',
    ShippingAddress NVARCHAR(MAX),
    Notes NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouses(WarehouseID)
);
GO

CREATE TABLE SalesOrderDetails (
    SalesOrderID INT,
    ProductID INT,
    Quantity INT NOT NULL,
    UnitPrice DECIMAL(18, 2) NOT NULL,
    Discount DECIMAL(5, 2) DEFAULT 0,
    
    PRIMARY KEY (SalesOrderID, ProductID),
    
    FOREIGN KEY (SalesOrderID) REFERENCES SalesOrders(SalesOrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);
GO

CREATE TABLE StockMovements (
    MovementID INT PRIMARY KEY IDENTITY(1,1), 
    ProductID INT NOT NULL,
    WarehouseID INT NOT NULL,
    MovementType NVARCHAR(50) NOT NULL,
    Quantity INT NOT NULL,
    ReferenceType NVARCHAR(50),
    ReferenceID INT,
    Notes NVARCHAR(MAX),
    MovementDate DATETIME DEFAULT GETDATE(),
    CreatedBy NVARCHAR(100),
    
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouses(WarehouseID)
);
GO

CREATE TABLE WarehouseTransfers (
    TransferID INT PRIMARY KEY IDENTITY(1,1),
    ProductID INT,
    FromWarehouseID INT,
    ToWarehouseID INT,
    Quantity INT NOT NULL,
    TransferDate DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(50) DEFAULT 'Pending',
    Notes NVARCHAR(MAX),

    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (FromWarehouseID) REFERENCES Warehouses(WarehouseID),
    FOREIGN KEY (ToWarehouseID) REFERENCES Warehouses(WarehouseID)
);
GO

CREATE INDEX IX_Products_CategoryID ON Products(CategoryID);
CREATE INDEX IX_ProductSuppliers_ProductID ON ProductSuppliers(ProductID);
CREATE INDEX IX_ProductSuppliers_SupplierID ON ProductSuppliers(SupplierID);
CREATE INDEX IX_Inventory_ProductID ON Inventory(ProductID);
CREATE INDEX IX_Inventory_WarehouseID ON Inventory(WarehouseID);
CREATE INDEX IX_PurchaseOrders_SupplierID ON PurchaseOrders(SupplierID);
CREATE INDEX IX_PurchaseOrderDetails_POID ON PurchaseOrderDetails(POID);
CREATE INDEX IX_SalesOrders_CustomerID ON SalesOrders(CustomerID);
CREATE INDEX IX_SalesOrderDetails_SalesOrderID ON SalesOrderDetails(SalesOrderID);
CREATE INDEX IX_StockMovements_ProductID ON StockMovements(ProductID);
CREATE INDEX IX_StockMovements_MovementDate ON StockMovements(MovementDate);