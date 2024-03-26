
CREATE DATABASE IF NOT EXISTS TechBuild;

USE TechBuild;
-- User Table
CREATE TABLE IF NOT EXISTS Users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  user_role VARCHAR(10) NOT NULL
);

-- UserDetail Table
CREATE TABLE IF NOT EXISTS UserDetails (
  user_id INT PRIMARY KEY NOT NULL,
  user_first_name VARCHAR(30) ,
  user_last_name VARCHAR(30) ,
  phone_number VARCHAR(12)  UNIQUE,
  home_address VARCHAR(100) ,
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- PaymentMethod Table
CREATE TABLE IF NOT EXISTS PaymentMethods (
  payment_method_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id_details INT ,
  payment_method_name VARCHAR(30) ,
  card_number VARCHAR(30) ,
  card_holder_name VARCHAR(60) ,
  expiration_month VARCHAR(2),
  expiration_year VARCHAR(4),
  FOREIGN KEY (user_id_details) REFERENCES UserDetails(user_id) ON DELETE CASCADE

);


-- Categories Table
CREATE TABLE IF NOT EXISTS Categories (
  category VARCHAR(40) PRIMARY KEY NOT NULL  
);

-- Product Table
CREATE TABLE IF NOT EXISTS Products (
  product_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  category VARCHAR(30) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  image_path VARCHAR(150) NOT NULL,
  recommended BOOLEAN NOT NULL,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY (category) REFERENCES Categories(category)
  
);




-- Order Table
CREATE TABLE IF NOT EXISTS Orders (
  order_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  order_date DATE NOT NULL,
  total_amount DECIMAL(10, 2) NOT NULL ,
  order_status ENUM('Completed','Cancelled','Awaiting Shipment','Shipped'),
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- CartItem Table
CREATE TABLE IF NOT EXISTS CartItems (
  order_item_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT,
  product_id INT NOT NULL,
  user_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10, 2),
  FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES Products(product_id),
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);


-- Payment Table
CREATE TABLE IF NOT EXISTS Payments (
  payment_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id_ext INT NOT NULL,
  payment_method_id_ext INT NOT NULL,
  payment_date DATE NOT NULL,
  FOREIGN KEY (order_id_ext) REFERENCES Orders(order_id) ON DELETE CASCADE, 
  FOREIGN KEY (payment_method_id_ext) REFERENCES PaymentMethods(payment_method_id) ON DELETE CASCADE
);

