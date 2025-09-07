# Prescription Management System

A **Prescription Management System** built with **Spring Boot**, **Thymeleaf**, **Spring Security (JWT)**, and integrated with **RxNav API** for checking drug interactions. The system allows doctors to manage prescriptions, view interactions, and maintain patient records securely.

---

## Features

- **Prescription Management**
  - Create, update, and delete prescriptions.
  - Store patient details: name, age, gender, diagnosis, medicines, next visit date.
  - Search and filter prescriptions by date.

- **Drug Interaction Checker**
  - Integrates with [RxNav API](https://rxnav.nlm.nih.gov/) to fetch drug interactions.
  - Shows interactions dynamically on the prescription form.

- **Authentication & Security**
  - Spring Security with JWT-based authentication.
  - No anonymous access: all users must log in.
  - Login and registration pages with form validation.

- **Frontend**
  - Modern UI with **Thymeleaf** templates.
  - Responsive design with separate CSS for forms and general styling.
  - Dynamic interaction table displayed for prescribed medicines.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT, Spring Web, Spring Data JPA, Hibernate
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Database:** MySQL (or any relational DB)
- **External APIs:** RxNav REST API for drug interactions
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito

---

## Installation

1. **Clone the repository**

```bash
git clone https://github.com/yourusername/prescription-management.git
cd prescription-management
````

2. **Configure Database**

* Create a MySQL database, e.g., `prescription_db`.
* Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/prescription_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

3. **Run the Application**

```bash
mvn spring-boot:run
```

4. **Access the App**

* Open your browser: [http://localhost:8080/prescriptions](http://localhost:8080/prescriptions)
* You will be redirected to the **Login page**.
* Register a new user to start using the system.

---

## API Endpoints (Protected by JWT)

| Method | Endpoint                     | Description                       |
| ------ | ---------------------------- | --------------------------------- |
| GET    | `/prescriptions`             | List all prescriptions            |
| GET    | `/prescriptions/new`         | Show create prescription form     |
| POST   | `/prescriptions`             | Create new prescription           |
| GET    | `/prescriptions/{id}/edit`   | Show edit form for a prescription |
| POST   | `/prescriptions/{id}`        | Update prescription               |
| POST   | `/prescriptions/{id}/delete` | Delete prescription               |
| POST   | `/auth/login`                | Authenticate user (JWT)           |
| POST   | `/auth/register`             | Register new user                 |

---

## RxNav Integration

* Converts medicine names to **RxCUI**.
* Fetches interactions via **`https://rxnav.nlm.nih.gov/REST/interaction/interaction.json`**.
* Displays a **table of drug interactions** on the prescription form dynamically.

---

## Project Structure

```
src/main/java/com/cmed/prescription
├── controller
│   ├── webController
│   └── auth
├── model
│   ├── domain
│   └── dto
├── repository
├── service
├── security
│   ├── JwtTokenService
│   ├── AuthUserDetailsService
│   └── SecurityConfig
└── PrescriptionApplication.java
```

* `controller/webController` – handles prescriptions pages.
* `controller/auth` – handles login and registration.
* `service` – business logic and RxNav integration.
* `model/dto` – request/response DTOs for prescriptions, auth, and RxNav API.
* `security` – JWT configuration and user authentication.
* `templates` – Thymeleaf templates for UI.

---

## Security

* All `/prescriptions/**` routes require authentication.
* JWT is used for session-less authentication.
* Login and registration pages are publicly accessible.
* Unauthorized users are redirected to the login page.

---

## Future Improvements

* Add email to registration and password recovery.
* Enhance UI with **Bootstrap** or **Tailwind CSS**.
* Add **role-based access** (admin, doctor, pharmacist).
* Implement unit and integration tests for API endpoints.
* Add **pagination and search filters** for prescription lists.
* Store drug interaction history in database.

---

