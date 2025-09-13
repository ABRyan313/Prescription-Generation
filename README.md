

<img width="1335" height="630" alt="Capture" src="https://github.com/user-attachments/assets/28ff6246-a631-41ca-8cd2-0a822a4be3b8" />


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
- **Database:** H2 Database
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

2. **Run the Application**

```bash
mvn spring-boot:run
```

3. **Access the App**

* Open your browser: [http://localhost:8080/prescriptions](http://localhost:8080/prescriptions)
* You will be redirected to the **Login page**.
* Register a new user to start using the system.

---

## API Endpoints (Protected by JWT)

| Method  | Endpoint                     | Description                                         |
| ------- | ---------------------------- | --------------------------------------------------- |
| GET     | `/prescriptions`             | List all prescriptions (Thymeleaf view)             |
| GET     | `/prescriptions/new`         | Show create prescription form (Thymeleaf)           |
| POST    | `/prescriptions`             | Create new prescription (Thymeleaf form submission) |
| GET     | `/prescriptions/{id}/edit`   | Show edit form for a prescription (Thymeleaf)       |
| POST    | `/prescriptions/{id}`        | Update prescription (Thymeleaf form submission)     |
| POST    | `/prescriptions/{id}/delete` | Delete prescription (Thymeleaf form submission)     |
| **GET** | **`/api/v1/prescription`**   | **Return all prescriptions as JSON**                |
| POST    | `/auth/login`                | Authenticate user (JWT)                             |
| POST    | `/auth/register`             | Register new user                                   |

---

## RxNav Integration

* Converts medicine names to **RxCUI**.
* Fetches interactions via **`https://rxnav.nlm.nih.gov/REST/interaction/interaction.json`**.
* Displays a **table of drug interactions** on the prescription form dynamically.

---

## Project Structure

```
├── controller
│   ├── restController
│   │   └── PrescriptionRestController.java
│   └── webController
│       ├── LoginController.java
│       ├── RegisterController.java
│       ├── PrescriptionController.java
│       └── PrescriptionReportController.java
│
├── mapper
│   └── PrescriptionMapper.java
│
├── model
│   ├── domain
│   │   ├── Prescription.java
│   │   └── AuthUser.java
│   └── dto
│       ├── CreatePrescriptionRequest.java
│       ├── UpdatePrescriptionRequest.java
│       ├── PrescriptionResponse.java
│       ├── auth
│       │   ├── AuthRequest.java
│       │   └── AuthResponse.java
│       └── rxcuiDto
│           ├── InteractionPair.java
│           └── RxNavResponse.java
│
├── persistence
│   ├── entity
│   │   ├── UserEntity.java
│   │   └── PrescriptionEntity.java
│   └── repository
│       ├── UserRepository.java
│       └── PrescriptionRepository.java
│
├── security
│   ├── AuthUserDetailsService.java
│   ├── JwtTokenService.java
│   └── SecurityConfig.java
│
├── service
│   ├── AuthService.java
│   ├── PrescriptionService.java
│   ├── PrescriptionReportService.java
│   └── RxNavService.java
│
└── PrescriptionApplication.java

---


```

---

1. **Controller Layer**

   * `webController` → renders UI via Thymeleaf (prescriptions, reports).
   * `restController` renders JSON response of prescription list(/api/v1/prescription).
   * `auth` → handles login & registration.

2. **Service Layer**

   * Contains business logic for prescriptions, reports, and external API integration (RxNav).

3. **Model Layer**

   * `domain` → core objects.
   * `dto` → organized by feature/module:

     * `auth` → login/register requests & responses
     * `rxcuiDto` → RxNav API DTOs
     * prescriptions → directly in `dto`

4. **Persistence Layer**

   * `entity` → DB entities
   * `repository` → Spring Data repositories

5. **Security Layer**

   * JWT, user authentication, and Spring Security configuration.

6. **Mapper Layer**

   * `PrescriptionMapper` for converting Entity ↔ DTO ↔ Domain.

7. **Templates**

   * Thymeleaf files remain in `src/main/resources/templates`
   * Organized similarly: `prescriptions/`, `auth/`, `reports/`

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
* Add **pagination.
* Store drug interaction history in database.

---

