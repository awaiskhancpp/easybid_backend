# Antique & Auction Platform

A user-friendly platform enabling buyers and sellers to auction or purchase antique, unique, or collectible items. This SaaS/MVP project focuses on transparency, secure transactions, and ease of use.

---

## **Features**

### **1. User Management**

- **Sellers**:
  - List items for auction.
  - Set starting bids and manage listings.
- **Buyers**:
  - Browse items and place bids.
  - Track active auctions and receive notifications.
- **Admins**:
  - Oversee platform activities.
  - Manage disputes and approve listings.

### **2. Item Listings**

- Detailed item descriptions with image and video support.
- Categories (e.g., antiques, collectibles, modern art).
- Configurable starting bid, auction duration, and optional "Buy Now" pricing.

### **3. Auction Mechanism**

- Real-time bidding with countdown timers.
- Notifications for bid updates (outbid, win alerts).
- **Optional**: Proxy bidding where users set max bids.

### **4. Search and Filters**

- Advanced search by category, price range, location, etc.
- Highlight trending or featured auctions.

### **5. Payment & Security**

- Integrated payment gateway for secure transactions.
- Escrow service for holding payments until item delivery.

### **6. Reviews and Ratings**

- Feedback system to build buyer/seller trust.

### **7. Analytics and Insights**

- Sales insights (bid history, popular items).
- Personalized buyer recommendations.

### **8. Admin Dashboard**

- Manage users, monitor auctions, and resolve disputes.
- Approve high-value listings.

---

## **Tech Stack**

### **Backend**:

- **Java Spring Boot**: API development.
- **PostgreSQL**: Database for users, items, and bids.
- **Redis**: Real-time auction updates using WebSockets.

### **Frontend**:

- **React**: Buyer/seller dashboards with a mobile-first design.

### **Deployment**:

- **Docker Compose**: For PostgreSQL database.

---

## **Development Setup**

### **Requirements**

- **Java 17+**
- **Docker & Docker Compose**
- **Node.js 22+**
- **Bruno** (optional, for API testing)

---

## **Getting Started**

### **Step 1: Clone the Repository**

```bash
git clone https://github.com/KashifKhn/easybid_java_spring
cd easybid_java_spring
```

---

### **Step 4: Build and Run Backend**

1. Navigate to the backend directory:
   ```bash
   cd easybid_java_spring
   ```
2. Build the Spring Boot application:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

---

### **Step 5: Start Frontend**

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   pnpm install
   ```
3. Start the React development server:
   ```bash
   pnpm dev
   ```

---

### **Step 6: Access the Application**

- **Frontend**: [http://localhost:3000](http://localhost:3000)
- **Backend API**: [http://localhost:8080/api/v1](http://localhost:8080/api/v1)
- **Backend Open API Doc (swagger)**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## **Contributing**

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Added feature-name"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## **Future Enhancements**

- AI-based valuation of items.
- Live-streamed auctions with chat.
- Mobile app for seamless auction experience.

---

## **License**

This project is licensed under the MIT License. See the `LICENSE` file for more details.
