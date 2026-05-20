# Ground Booking System

A full-stack sports ground booking platform where users can book sports grounds online and admins can manage bookings, grounds, users, and payments.

---

# 🚀 Tech Stack

## Frontend
- React.js
- React Router DOM
- Axios
- Bootstrap 5
- CSS

## Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Payment Gateway
- Cashfree (Test Mode)

---

# ✨ Features

## 👤 User Features
- User Registration & Login
- Browse Available Grounds
- View Ground Details
- Real-Time Ground Booking
- Booking History
- Payment History
- Contact Support

## 🛠️ Admin Features
- Admin Dashboard
- Add/Edit/Delete Grounds
- Manage Users
- Manage Bookings
- Refund Management
- Manage Payments
- View Contact Messages

---

# 📂 Project Structure

## Frontend Repository
```bash
GroundBookingFront
```

## Backend Repository
```bash
GroundBookingBack
```

---

# ⚙️ Frontend Setup

## Clone Frontend Repository

```bash
git clone https://github.com/YOUR_USERNAME/GroundBookingFront.git
```

## Navigate to Frontend

```bash
cd GroundBookingFront
```

## Install Dependencies

```bash
npm install
```

## Run Frontend

```bash
npm start
```

Frontend runs on:

```bash
http://localhost:3000
```

---

# ⚙️ Backend Setup

## Clone Backend Repository

```bash
git clone https://github.com/YOUR_USERNAME/GroundBookingBack.git
```

## Navigate to Backend

```bash
cd GroundBookingBack
```

---

# 🛠️ Configure Database

Update:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ground_booking
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# 🔐 Configure Environment Variables

Set Cashfree API keys in your system environment variables.

```env
CASHFREE_CLIENT_ID=YOUR_CLIENT_ID
CASHFREE_CLIENT_SECRET=YOUR_SECRET
```

---

# ▶️ Run Backend

Using Maven:

```bash
mvn spring-boot:run
```

Backend runs on:

```bash
http://localhost:8080
```

---

# 🔗 Important APIs

## User APIs
- POST `/reguser`
- POST `/login`

## Ground APIs
- GET `/Allgrounds`
- POST `/addGround`
- PUT `/updateground/{id}`
- DELETE `/deleteground/{id}`

## Booking APIs
- POST `/booking/create`
- GET `/booking/all`
- PUT `/booking/cancel/{id}`

## Payment APIs
- POST `/payment/create-order`

---

# 🔒 Authentication

- Role-Based Login
- Protected Routes
- LocalStorage Session Management

---

# 💳 Payment Integration

Integrated with Cashfree Payment Gateway in test mode.

---

# 📸 Main Screens

- Landing Page
- Login/Register Page
- Admin Dashboard
- User Dashboard
- Ground Details
- Booking Management
- Payment Management

---

# 👨‍💻 Author

Pratik Mashakhetri
