# NoteNestor

**Smart Notes & Todo Management API**

<div align="center">

![Java](https://img.shields.io/badge/java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

*Organize your thoughts, track your progress*

</div>

## Overview

NoteNestor is a modern REST API for note-taking and task management. Built with Spring Boot, it offers categorized notes with file attachments and a smart todo system with email notifications.

## Core Functionality

### 📋 Todo System
- **Three-Stage Workflow**: Started ➜ Progress ➜ Completed  
- **Smart Reminders**: Email notifications for incomplete tasks
- **Task Management**: Create, update, delete todos
- **Progress Tracking**: Monitor task completion status

### 📚 Notes Organization  
- **Category-Based**: Create custom categories for better organization
- **Rich Notes**: Add content with file attachments
- **Smart Search**: Find notes instantly with keyword search
- **Note Cloning**: Copy existing notes as templates
- **File Support**: Attach documents, images, and other files

### 🗂️ Advanced Features
- **Recycle Bin**: Deleted notes are safely stored for recovery
- **Auto-Restore**: Recover accidentally deleted notes anytime  
- **Auto-Cleanup**: System automatically purges old deleted notes after 30 days
- **Permanent Delete**: Option to permanently remove notes from recycle bin

## Authentication Features
Same robust security as CloudShare:
- JWT token authentication
- Email verification links
- Password reset via email
- Secure password changes

---


## Installation

```bash
# Clone repository
git clone https://github.com/Surendra1341/NoteNestor.git
cd NoteNestor

# Setup database
mysql -u root -p
CREATE DATABASE notenestor;

# Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/notenestor
jwt.secret=your-secret-key
spring.mail.username=your-email@domain.com

# Run application
mvn spring-boot:run
```

## API Reference

<details>
<summary><b>Authentication</b></summary>

```http
POST   /auth/register         # User registration
POST   /auth/login            # User login
POST   /auth/verify-email     # Email verification
POST   /auth/forgot-password  # Password reset request
PUT    /auth/reset-password   # Password reset
PUT    /auth/change-password  # Change password
```

</details>

<details>
<summary><b>Todos</b></summary>

```http
GET    /todos           # List todos
POST   /todos           # Create todo
PUT    /todos/{id}      # Update todo
DELETE /todos/{id}      # Delete todo
PATCH  /todos/{id}/status # Update status
```

</details>

<details>
<summary><b>Categories</b></summary>

```http
GET    /categories      # List categories
POST   /categories      # Create category
PUT    /categories/{id} # Update category
DELETE /categories/{id} # Delete category
```

</details>

<details>
<summary><b>Notes</b></summary>

```http
GET    /notes                  # List all notes
POST   /notes                  # Create note (with file upload)
GET    /notes/category/{id}    # Notes by category
PUT    /notes/{id}             # Update note
DELETE /notes/{id}             # Delete note (to recycle bin)
POST   /notes/{id}/copy        # Copy note
GET    /notes/search?q=keyword # Search notes
```

</details>

<details>
<summary><b>Recycle Bin</b></summary>

```http
GET    /recycle            # View deleted notes
POST   /recycle/{id}/restore # Restore note
DELETE /recycle/{id}       # Permanent delete
DELETE /recycle/cleanup    # Force cleanup
```

</details>

## Usage Examples

### Create Todo
```bash
curl -X POST http://localhost:8080/todos \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title": "API Documentation", "description": "Write comprehensive docs"}'
```

### Create Note with File
```bash
curl -X POST http://localhost:8080/notes \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "title=Meeting Notes" \
  -F "content=Project discussion points" \
  -F "categoryId=1" \
  -F "file=@meeting-notes.pdf"
```

### Search Notes
```bash
curl -X GET "http://localhost:8080/notes/search?q=meeting" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## Tech Stack

| Component | Technology |
|-----------|------------|
| **Framework** | Spring Boot 3.x |
| **Database** | MySQL 8.0 |
| **Security** | Spring Security + JWT |
| **Email** | Spring Mail |
| **Documentation** | Swagger/OpenAPI |
| **Build Tool** | Maven |

## Development

**Requirements:** Java 17+, Maven 3.6+, MySQL 8.0+

**API Documentation:** `http://localhost:8080/swagger-ui.html`

**Base URL:** `http://localhost:8080`

## Contributing

Contributions welcome! Please read our contributing guidelines and submit pull requests.

---

<div align="center">

**[⭐ Star this repo](https://github.com/Surendra1341/NoteNestor)** • **[🐛 Report Bug](https://github.com/Surendra1341/NoteNestor/issues)** • **[✨ Request Feature](https://github.com/Surendra1341/NoteNestor/issues)**

Made with ❤️ by [Surendra](https://github.com/Surendra1341)

</div>
