# ChatterBox

<div align="center">

# ChatterBox 🗨️

### A Modern Real-Time Chat Application

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-blue?style=for-the-badge&logo=react)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.9-blue?style=for-the-badge&logo=typescript)](https://www.typescriptlang.org/)
[![License](https://img.shields.io/github/license/uday/chatterbox?style=for-the-badge)](LICENSE)

**Real-time messaging platform with modern UI and secure authentication**

</div>

## 🚀 Modern Tech Stack

<div align="center">

### Backend

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-red?style=flat-square&logo=openjdk)](https://www.java.com/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue?style=flat-square&logo=springsecurity)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6-orange?style=flat-square&logo=apachemaven)](https://maven.apache.org/)

### Frontend

[![React](https://img.shields.io/badge/React-19-blue?style=flat-square&logo=react)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.9-blue?style=flat-square&logo=typescript)](https://www.typescriptlang.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-v4-38B2AC?style=flat-square&logo=tailwindcss)](https://tailwindcss.com/)
[![Vite](https://img.shields.io/badge/Vite-7.1-purple?style=flat-square&logo=vite)](https://vitejs.dev/)

</div>

## ✨ Key Features

<div align="center">

[![Authentication](https://img.shields.io/badge/🔐%20Authentication-Secure%20Login/Registration-blue?style=for-the-badge)](#)
[![Realtime](https://img.shields.io/badge/💬%20Realtime-WebSocket%20Messaging-green?style=for-the-badge)](#)
[![Responsive](https://img.shields.io/badge/📱%20Responsive-Mobile%20Friendly-orange?style=for-the-badge)](#)
[![Validation](https://img.shields.io/badge/✅%20Validation-Form%20Validation%20&%20Sanitization-purple?style=for-the-badge)](#)
[![UI](https://img.shields.io/badge/🎨%20Modern%20UI-Tailwind%20CSS%20Components-red?style=for-the-badge)](#)

</div>
</div>
## 🏗️ Architecture

```
┌─────────────────┐    REST API    ┌──────────────────┐
│   React Frontend│◄──────────────►│ Spring Boot Backend │
└─────────────────┘  WebSocket     └──────────────────┘
                                    │
                                    ▼
                              ┌──────────┐
                              │ MySQL DB │
                              └──────────┘
```

## 📁 Project Structure

```
chatterbox/
├── src/main/java/com/uday/chatterbox/
│   ├── controller/     # REST controllers
│   ├── dta/           # DTOs (Data Transfer Objects)
│   ├── model/         # JPA entities
│   ├── repository/    # Spring Data repositories
│   ├── security/      # Security configuration
│   └── ChatterboxApplication.java  # Main application class
└── pom.xml            # Maven configuration

frontend/
├── src/
│   ├── components/    # React components
│   ├── hooks/         # Custom React hooks
│   ├── lib/           # Utility functions
│   ├── App.tsx        # Main App component
│   └── main.tsx       # Application entry point
├── package.json       # Frontend dependencies
└── vite.config.ts     # Vite configuration
```

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Node.js 16+**
- **MySQL 8+**
- **Maven 3.6+**

### Backend Setup

1. Navigate to the backend directory:

   ```bash
   cd chatterbox
   ```

2. Configure database in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/chatterbox_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   Or on Windows:

   ```cmd
   mvnw.cmd spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

## 🔄 API Endpoints

| Method | Endpoint  | Description         |
| ------ | --------- | ------------------- |
| POST   | /register | Register new user   |
| POST   | /login    | User authentication |
| GET    | /users    | Get users list      |

## 🛠️ Development

### Backend Development

```bash
# Run tests
./mvnw test

# Package application
./mvnw package

# Run with profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Frontend Development

```bash
# Run development server
npm run dev

# Build for production
npm run build

# Lint code
npm run lint
```

## 📦 Deployment

### Backend

```bash
# Build JAR file
./mvnw clean package

# Run JAR
java -jar target/chatterbox-0.0.1-SNAPSHOT.jar
```

### Frontend

```bash
# Build production assets
npm run build

# Serve with your preferred web server
```

## 🧪 Testing

### Backend Tests

```bash
./mvnw test
```

### Frontend Tests

```bash
npm run test
```

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Uday** - _Initial work_ - [uday](https://github.com/uday)

## 🙏 Acknowledgments

- Thanks to the Spring Boot team for the excellent framework
- React and Vite communities for amazing tools
- All contributors to this project

---

<p align="center">Made with ❤️ for developers</p>
