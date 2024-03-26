-- Insert into Categories table
INSERT INTO Categories (category)
VALUES 
  ('Graphics Card'),('Memory'),('Monitor'),('Motherboard'),('Peripherals'),('Power Supply'),('Processor'),('Storage');

-- Insert into Products table
INSERT INTO Products (name, description, category, price, image_path, recommended)
VALUES 
  ('ASUS ROG Strix X570-E Gaming Motherboard', 'High-performance motherboard with advanced features', 'Motherboard', 349.99, 'images/products/X570-E.png', false),
  ('Corsair Vengeance LPX 16GB DDR4 RAM', 'High-speed RAM for seamless multitasking', 'Memory', 99.99, 'images/products/Vengeance-LPX.png', true),
  ('Samsung 970 EVO Plus 1TB NVMe SSD', 'Fast and reliable storage solution', 'Storage', 199.99, 'images/products/970-EVO-Plus.png', true),
  ('MSI GeForce RTX 3080 Gaming X Trio', 'Powerful graphics card for immersive gaming', 'Graphics Card', 799.99, 'images/products/RTX-3080.png', true),
  ('Intel Core i9-9900K Desktop Processor', 'High-performance processor for demanding tasks', 'Processor', 499.99, 'images/products/Core-i9-9900K.png', false),
  ('Seagate BarraCuda 2TB Internal Hard Drive', 'Large-capacity hard drive for storage-intensive applications', 'Storage', 79.99, 'images/products/BarraCuda-2TB.png', false),
  ('Logitech G502 HERO Gaming Mouse', 'High-precision gaming mouse with customizable features', 'Peripherals', 69.99, 'images/products/G502-HERO.png', false),
  ('LG UltraGear 27GN950-B 27" Gaming Monitor', 'Immersive gaming monitor with high refresh rate and HDR support', 'Monitor', 899.99, 'images/products/UltraGear-27GN950-B.png', false),
  ('AMD Ryzen 9 5900X Desktop Processor', 'Powerful processor for high-performance computing', 'Processor', 549.99, 'images/products/Ryzen-9-5900X.png', false),
  ('Crucial MX500 1TB SATA SSD', 'Reliable solid-state drive for fast and efficient storage', 'Storage', 129.99, 'images/products/MX500-1TB.png', false),
  ('NVIDIA GeForce GTX 1660 Super Graphics Card', 'Mid-range graphics card for smooth gaming experience', 'Graphics Card', 299.99, 'images/products/GTX-1660-Super.png', false),
  ('Corsair RM750x 750W Power Supply', 'High-quality power supply for efficient and reliable performance', 'Power Supply', 139.99, 'images/products/RM750x.png', false),
  ('ASUS TUF Gaming VG27AQ 27" Monitor', 'Gaming monitor with high refresh rate and adaptive sync technology', 'Monitor', 499.99, 'images/products/TUF-VG27AQ.png', false),
  ('Logitech G Pro Mechanical Gaming Keyboard', 'Durable and responsive mechanical keyboard for gaming', 'Peripherals', 129.99, 'images/products/G-Pro-Keyboard.png', false),
  ('G.SKILL Trident Z RGB 16GB DDR4 RAM', 'RGB-enabled high-performance RAM for stylish builds', 'Memory', 149.99, 'images/products/Trident-Z-RGB.png', false),
  ('Western Digital Blue 4TB Internal Hard Drive', 'Large-capacity hard drive for reliable storage', 'Storage', 109.99, 'images/products/WD-Blue-4TB.png', false),
  ('AMD Radeon RX 6800 XT Graphics Card', 'High-end graphics card for exceptional gaming performance', 'Graphics Card', 899.99, 'images/products/RX-6800-XT.png', false),
  ('Intel Core i7-11700K Desktop Processor', 'High-performance processor for gaming and content creation', 'Processor', 399.99, 'images/products/Core-i7-11700K.png', true),
  ('Seagate FireCuda 2TB SSHD', 'Hybrid drive combining SSD and HDD technologies for fast storage', 'Storage', 109.99, 'images/products/FireCuda-2TB.png', false),
  ('Razer DeathAdder V2 Gaming Mouse', 'Ergonomic gaming mouse with high-precision sensor', 'Peripherals', 79.99, 'images/products/DeathAdder-V2.png', true),
  ('BenQ EX2780Q 27" Gaming Monitor', 'Immersive gaming monitor with built-in speakers and HDR support', 'Monitor', 499.99, 'images/products/EX2780Q.png', true);
  
  
  
-- Insert into Users table
INSERT INTO Users (username, password, email, user_role)
VALUES
-- Password1@ = 548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f
('user1', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user1@example.com', 'admin'),
('user2', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user2@example.com', 'customer'),
('user3', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user3@example.com', 'customer'),
('user4', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user4@example.com', 'customer'),
('user5', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user5@example.com', 'customer'),
('user6', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user6@example.com', 'customer'),
('user7', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user7@example.com', 'customer'),
('user8', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user8@example.com', 'customer'),
('user9', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user9@example.com', 'customer'),
('user10', '548d8cf86e2d301f6e1f5dc621cba2e409e8e814ba35ca1feeff6b0b995d848f', 'user10@example.com', 'customer');

-- Insert into UserDetails table
INSERT INTO UserDetails (user_id, user_first_name, user_last_name, phone_number, home_address)
VALUES
(1, 'John', 'Doe', '1111111111', '123 Main St'),
(2, 'Jane', 'Smith', '2222222222', '456 Elm St'),
(3, 'Michael', 'Johnson', '3333333333', '789 Oak St'),
(4, 'Emily', 'Brown', '4444444444', '321 Pine St'),
(5, 'David', 'Taylor', '5555555555', '654 Maple St'),
(6, 'Olivia', 'Miller', '6666666666', '987 Cedar St'),
(7, 'James', 'Anderson', '7777777777', '159 Birch St'),
(8, 'Sophia', 'Wilson', '8888888888', '753 Spruce St'),
(9, 'Daniel', 'Martinez', '9999999999', '246 Oak St'),
(10, 'Isabella', 'Davis', '0000000000', '852 Elm St');

-- Insert into PaymentMethods table
INSERT INTO PaymentMethods (user_id_details, payment_method_name, card_number, card_holder_name, expiration_month, expiration_year)
VALUES
(1, 'Visa', '1234567890123456', 'John Doe', '12', '2025'),
(2, 'MasterCard', '9876543210987654', 'Jane Smith', '11', '2024'),
(3, 'American Express', '4567890123456789', 'Michael Johnson', '10', '2023'),
(4, 'Visa', '3216549870321654', 'Emily Brown', '09', '2022'),
(5, 'MasterCard', '9870123456987012', 'David Taylor', '08', '2023'),
(6, 'American Express', '4567890123456789', 'Olivia Miller', '07', '2024'),
(7, 'Visa', '0123456789012345', 'James Anderson', '06', '2025'),
(8, 'MasterCard', '7890123456789012', 'Sophia Wilson', '05', '2026'),
(9, 'American Express', '4567890123456789', 'Daniel Martinez', '04', '2027'),
(10, 'Visa', '0123456789012345', 'Isabella Davis', '03', '2028');

-- Insert into Orders table
INSERT INTO Orders (user_id, order_date, total_amount, order_status)
VALUES
(1, '2023-06-01', 1599.98, 'Completed'),
(1, '2023-06-05', 849.99, 'Completed'),
(1, '2023-06-10', 999.99, 'Completed'),
(2, '2023-06-02', 499.99, 'Completed'),
(2, '2023-06-06', 849.99, 'Completed'),
(2, '2023-06-11', 1099.98, 'Completed'),
(3, '2023-06-03', 799.99, 'Completed'),
(3, '2023-06-07', 899.99, 'Completed'),
(3, '2023-06-12', 749.99, 'Completed'),
(4, '2023-06-04', 799.99, 'Completed'),
(4, '2023-06-08', 1599.98, 'Completed'),
(4, '2023-06-13', 999.99, 'Completed'),
(5, '2023-06-05', 749.99, 'Completed'),
(5, '2023-06-09', 849.99, 'Completed'),
(5, '2023-06-14', 1099.98, 'Completed'),
(6, '2023-06-06', 699.99, 'Completed'),
(6, '2023-06-10', 899.99, 'Completed'),
(6, '2023-06-15', 849.99, 'Completed'),
(7, '2023-06-07', 499.99, 'Completed'),
(7, '2023-06-11', 749.99, 'Completed'),
(7, '2023-06-16', 999.99, 'Completed'),
(8, '2023-06-08', 1599.98, 'Completed'),
(8, '2023-06-12', 899.99, 'Completed'),
(8, '2023-06-17', 799.99, 'Completed'),
(9, '2023-06-09', 849.99, 'Completed'),
(9, '2023-06-13', 1099.98, 'Completed'),
(9, '2023-06-18', 749.99, 'Completed'),
(10, '2023-06-10', 699.99, 'Completed'),
(10, '2023-06-14', 849.99, 'Completed'),
(10, '2023-06-19', 899.99, 'Completed');
-- Insert into CartItems table
INSERT INTO CartItems (order_id, product_id, user_id, quantity, price)
VALUES
-- Cart Items for user1
(1, 1, 1, 1, 349.99),
(2, 2, 1, 2, 99.99),
(3, 3, 1, 1, 199.99),
-- Cart Items for user2
(4, 4, 2, 1, 799.99),
(5, 5, 2, 1, 499.99),
(6, 6, 2, 1, 79.99),
-- Cart Items for user3
(7, 7, 3, 1, 899.99),
(8, 8, 3, 2, 549.99),
(9, 9, 3, 1, 129.99),
-- Cart Items for user4
(10, 10, 4, 1, 299.99),
(11, 11, 4, 1, 139.99),
(12, 12, 4, 1, 499.99),
-- Cart Items for user5
(13, 13, 5, 1, 129.99),
(14, 14, 5, 1, 109.99),
(15, 15, 5, 1, 899.99),
-- Cart Items for user6
(16, 16, 6, 2, 399.99),
(17, 17, 6, 1, 109.99),
(18, 18, 6, 1, 79.99),
-- Cart Items for user7
(19, 19, 7, 1, 499.99),
(20, 20, 7, 1, 499.99),
(21, 21, 7, 1, 1099.99),
-- Cart Items for user8
(22, 1, 8, 1, 1599.99),
(23, 2, 8, 1, 499.99),
(24, 3, 8, 1, 849.99),
-- Cart Items for user9
(25, 4, 9, 1, 699.99),
(26, 5, 9, 1, 849.99),
(27, 6, 9, 1, 1099.99),
-- Cart Items for user10
(28, 7, 10, 1, 849.99),
(29, 8, 10, 1, 699.99),
(30, 9, 10, 1, 899.99);

-- Insert into Payments table
INSERT INTO Payments (order_id_ext, payment_method_id_ext, payment_date)
VALUES
-- Payments for Order 1
(1, 1, '2023-06-01'),
-- Payments for Order 2
(2, 2, '2023-06-02'),
-- Payments for Order 3
(3, 3, '2023-06-03'),
-- Payments for Order 4
(4, 4, '2023-06-04'),
-- Payments for Order 5
(5, 5, '2023-06-05'),
-- Payments for Order 6
(6, 6, '2023-06-06'),
-- Payments for Order 7
(7, 7, '2023-06-07'),
-- Payments for Order 8
(8, 8, '2023-06-08'),
-- Payments for Order 9
(9, 9, '2023-06-09'),
-- Payments for Order 10
(10, 10, '2023-06-10');
