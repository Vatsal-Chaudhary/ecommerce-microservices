-- Create products table if it doesn't exist
CREATE TABLE IF NOT EXISTS products (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        description TEXT,
                                        price DECIMAL(10,2) NOT NULL,
                                        stock INTEGER NOT NULL DEFAULT 0,
                                        category VARCHAR(100),
                                        image_url VARCHAR(500),
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample products only if table is empty
INSERT INTO products (name, description, price, stock, category, image_url)
SELECT * FROM (VALUES
                   ('Laptop Pro 15"', 'High-performance laptop', 1299.99, 25, 'Electronics', 'https://example.com/laptop.jpg'),
                   ('Wireless Headphones', 'Noise-canceling headphones', 199.99, 50, 'Electronics', 'https://example.com/headphones.jpg'),
                   ('Gaming Mouse', 'RGB gaming mouse', 79.99, 100, 'Electronics', 'https://example.com/mouse.jpg'),
                   ('Coffee Maker', 'Automatic coffee maker', 89.99, 30, 'Kitchen', 'https://example.com/coffee.jpg'),
                   ('Running Shoes', 'Lightweight running shoes', 129.99, 75, 'Sports', 'https://example.com/shoes.jpg')
              ) AS v(name, description, price, stock, category, image_url)
WHERE NOT EXISTS (SELECT 1 FROM products LIMIT 1);