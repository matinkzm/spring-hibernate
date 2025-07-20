
# Orphan Sponsorship Management System

A comprehensive web application built using Spring Boot for managing orphan sponsorship programs. This system allows charitable organizations to track orphans, manage sponsors, handle financial transactions, assign and manage assets, generate reports, and support hierarchical user roles such as administrators, sponsors, and representatives.

---

## 📌 Project Overview

This project is designed for institutions involved in the care and sponsorship of orphans. The main goal is to streamline operations such as sponsorship payments, orphan profile tracking, asset allocation, and reporting.

The system supports three main user roles with distinct responsibilities and access levels:
- **Administrator (Admin)**
- **Sponsor**
- **Representative (Link between sponsors and organization)**

---

## 🧑‍💼 User Roles and Permissions

### 1. Administrator
The admin has complete control over the system:
- Add, edit, or disable sponsors, representatives, and orphans.
- Assign orphans to sponsors and revoke sponsorships.
- Manage physical assets and assign them to users.
- Track and manage all transactions.
- Access every sponsor’s dashboard and perform transactions on their behalf.
- Monitor user activities and maintain logs.
- Send real-time notifications (e.g., payment confirmation, medical alerts).

### 2. Sponsor
The sponsor financially supports one or more orphans:
- View list of sponsored orphans and their current status.
- Perform and track payments.
- Access personal and historical payment data.
- Submit requests to sponsor more orphans.
- Modify password and recover lost access credentials.

### 3. Representative
Acts as a sponsor with delegated authority over a group of other sponsors:
- Access sponsor accounts and view orphan information.
- Submit payments on behalf of sponsors.
- Manage sponsor communications and reports.
- Supervise sponsor contributions and provide assistance.

---

## ⚙️ System Functionalities

### 🧾 User and Access Management
- Role-based access control with detailed permission management.
- Admins can deactivate accounts to restrict access.
- Secure authentication, optional two-factor login.

### 💳 Financial Transactions
- Perform single or grouped payments to multiple orphans.
- All payments are logged with timestamps and descriptions.
- Admins and representatives can make payments on behalf of sponsors.

### 📦 Asset Management
- Track assets using unique IDs and status flags.
- Assign assets to users for a fixed duration.
- Notify admins before the usage period expires.
- Manage warehouse status and inventory changes.

### 📈 Reporting and Analytics
- Generate categorized reports based on:
  - Orphan education, health, and behavior
  - Sponsor contributions
  - Payment histories and missed contributions
- Apply advanced filters such as date, user type, and status.

### 🔔 Notification System
- Real-time alerts on:
  - New payments
  - Medical visits
  - Asset usage deadlines
  - Status changes of orphans
  - orphans that does not have a sponsor

### 🗂 Activity Logs
- Track all user actions and system changes.
- Maintain orphan and sponsor history across time.
- Ensure traceability of financial and asset-related operations.

---

## 🛠️ Technical Stack

- **Backend Framework**: Spring Boot (Java 17)
- **Build Tool**: Apache Maven
- **Persistence**: Spring Data JPA (Database config required)
- **Validation**: Spring Boot Validation
- **Notifications**: To be integrated via email/SMS/gateway
- **Security**: Not yet implemented, recommended use of Spring Security

---

## 👤 Author

**Matin Kazemi**  
[GitHub](https://github.com/matinkzm) | [LinkedIn](https://linkedin.com/in/matin-kazemi-927701292)
