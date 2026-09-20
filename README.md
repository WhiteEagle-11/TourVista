# 🌍 TourVista

> **AI-Powered Intelligent Travel Planning Platform**

TourVista is a full-stack travel planning platform that generates personalized travel itineraries by combining **places, weather, routes, recommendations, and AI-powered itinerary generation** into a single workflow.

Instead of simply listing tourist attractions, TourVista processes destination data and contextual information to help generate a structured travel plan based on factors such as **trip duration, budget, destination, weather, and available attractions**.

---

## ✨ Features

* 🗺️ **Destination & Place Discovery** — Find attractions and points of interest using location-based APIs.
* 🌦️ **Weather Integration** — Retrieve destination weather information to provide context for trip planning.
* 🛣️ **Route Information** — Use routing data to understand distances and travel between locations.
* 🎯 **Recommendation Engine** — Evaluate and rank potential attractions based on relevant trip factors.
* 🤖 **AI-Powered Itinerary Generation** — Uses Qwen3 to transform structured travel information into a natural-language travel plan.
* 💰 **Budget-Aware Planning** — Incorporates the user's trip budget into itinerary planning.
* 📅 **Multi-Day Itinerary Planning** — Organizes recommended destinations and activities across the requested number of days.
* 🔌 **REST API Backend** — Spring Boot backend exposes APIs for trip management and travel-planning operations.
* 🗄️ **Persistent Storage** — PostgreSQL stores trip and application data.
* 🧩 **Modular Architecture** — Separates external APIs, recommendation logic, itinerary optimization, and AI generation.

---

## 🏗️ Architecture

```text
                        ┌──────────────────┐
                        │    React / Vite   │
                        │    Frontend       │
                        └────────┬─────────┘
                                 │
                                 ▼
                        ┌──────────────────┐
                        │   Spring Boot    │
                        │   REST API       │
                        └────────┬─────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
              ▼                  ▼                  ▼
       ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
       │ Places &    │    │   Weather   │    │   Routing   │
       │ Geocoding   │    │   Service   │    │   Service   │
       └──────┬──────┘    └──────┬──────┘    └──────┬──────┘
              │                  │                  │
              └──────────────────┼──────────────────┘
                                 ▼
                    ┌────────────────────────┐
                    │  Recommendation Engine │
                    └────────────┬───────────┘
                                 │
                                 ▼
                    ┌────────────────────────┐
                    │ Itinerary Optimization │
                    └────────────┬───────────┘
                                 │
                                 ▼
                         ┌─────────────┐
                         │   Qwen3 AI  │
                         │   Generator  │
                         └──────┬──────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │ Final Travel    │
                       │ Plan / Itinerary│
                       └─────────────────┘

                         ┌─────────────┐
                         │ PostgreSQL  │
                         └─────────────┘
```

---

## 🛠️ Tech Stack

### Frontend

* React
* Vite
* JavaScript
* HTML5
* CSS3

### Backend

* Java 21
* Spring Boot
* Spring REST
* Maven
* Bean Validation

### Database

* PostgreSQL
* Spring Data JPA
* Hibernate

### AI

* Qwen3
* Prompt-based itinerary generation

### External APIs

* **Geoapify** — Geocoding, places, and routing
* **Open-Meteo** — Weather data

### Testing & Development

* JUnit
* Maven
* Postman
* Git
* GitHub

---

## 🔄 How It Works

TourVista follows a multi-stage travel-planning pipeline.

### 1. User Input

The user provides trip information such as:

```text
Destination
Number of Days
Budget
```

### 2. Destination Data Collection

TourVista collects relevant destination information through external services.

```text
Destination
     │
     ├──► Geocoding
     │
     ├──► Places / Attractions
     │
     ├──► Weather
     │
     └──► Routes
```

### 3. Recommendation Engine

The collected information is processed to identify and rank suitable attractions.

The recommendation layer considers available travel context before passing candidate destinations to the itinerary-planning stage.

### 4. Itinerary Optimization

The selected attractions are organized according to the user's:

* Trip duration
* Budget
* Destination
* Travel constraints
* Available activities

### 5. AI Generation

The structured itinerary data is passed to **Qwen3**, which converts the underlying travel information into a human-readable travel plan.

### 6. Final Travel Plan

The user receives a structured itinerary containing recommended activities and destinations organized across the trip duration.

---

## 📁 Project Structure

```text
TourVista/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── ...
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │       └── ...
│   │
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── ...
│   ├── package.json
│   └── vite.config.js
│
└── README.md
```

> The exact directory structure may vary depending on the current version of the project.

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

* Java 21+
* Maven
* Node.js
* npm
* PostgreSQL
* Git

---

## 🔑 Environment Variables

TourVista requires API credentials for external services.

Create the required environment variables before running the backend.

Example:

```env
GEOAPIFY_API_KEY=your_geoapify_api_key
```

If Qwen3 is accessed through an API provider, configure the corresponding API credentials according to the provider being used.

**Do not commit API keys or other secrets to GitHub.**

---

## 🗄️ Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE tourvista;
```

Configure the database connection in:

```text
backend/src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tourvista
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ Running the Backend

Navigate to the backend directory:

```bash
cd backend
```

Build the project:

```bash
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend will start on the configured Spring Boot port.

---

## ▶️ Running the Frontend

Navigate to the frontend:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Open the local Vite development URL shown in the terminal.

---

## 🔌 Example API

### Create a Trip

```http
POST /api/trips
Content-Type: application/json
```

Example request:

```json
{
  "destination": "Manali",
  "days": 4,
  "budget": 15000
}
```

Example response structure:

```json
{
  "id": 1,
  "destination": "Manali",
  "days": 4,
  "budget": 15000
}
```

---

## 🧪 Testing

Run the backend test suite with:

```bash
mvn test
```

JUnit tests are used to validate backend components and application behavior.

---

## 🧠 Engineering Concepts Demonstrated

TourVista was designed to demonstrate practical software engineering concepts including:

* RESTful API design
* Layered backend architecture
* DTO-based request/response handling
* Dependency Injection
* Input validation
* Centralized exception handling
* Database persistence with JPA/Hibernate
* External API integration
* Recommendation algorithms
* AI/LLM integration
* Service abstraction
* Modular architecture
* Automated testing

---

## 🔮 Future Improvements

Potential improvements include:

* [ ] User authentication and authorization
* [ ] Personalized user profiles
* [ ] Hotel and accommodation integration
* [ ] Flight and transportation integration
* [ ] More advanced itinerary optimization
* [ ] Real-time travel cost estimation
* [ ] Interactive maps
* [ ] Saved and shareable itineraries
* [ ] Weather-aware activity rescheduling
* [ ] Improved recommendation scoring
* [ ] Deployment using Docker and cloud infrastructure
* [ ] Comprehensive integration and end-to-end testing

---

## 🎯 Project Goal

The goal of TourVista is to explore how **traditional software engineering, external data sources, recommendation systems, and generative AI** can work together to solve a practical travel-planning problem.

Rather than relying solely on an LLM, TourVista separates **data collection, recommendation, optimization, and natural-language generation**, creating a more structured approach to AI-assisted travel planning.

---

## 👨‍💻 Author

**Shivansh Chauhan**

B.Tech Computer Science Engineering
Galgotias College of Engineering and Technology

* GitHub: [WhiteEagle-11](https://github.com/WhiteEagle-11)
* LinkedIn: [Shivansh Chauhan](https://linkedin.com/in/shivi0101)
* LeetCode: [Shiva_9084](https://leetcode.com/u/Shiva_9084/)

---

## ⭐ If You Find This Project Interesting

Feel free to explore the repository, raise an issue, or suggest improvements.
