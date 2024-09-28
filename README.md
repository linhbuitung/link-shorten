## Introduction

link-shorten is my personal project for an university assignment. See the requirement --> open Requirements.pf
It's a Spring Boot application developed using Java and Kotlin. It uses Gradle for dependency management.


## Installation

1. Clone the repository: `git clone https://github.com/linhbuitung/link-shorten.git`
2. Navigate into the directory: `cd link-shorten`
3. Install the dependencies: `gradle build`

## Running the Application

Run the application: `gradle bootRun`
See the routes at port 8080

## Application Details

The application uses an H2 database for data storage. The database connection details are as follows:

- URL: `jdbc:h2:file:./db/urls;DATABASE_TO_UPPER=false`
- Username: `appUser`
- Password: `appPassword`

The application also includes a web interface, with templates developed using Thymeleaf and Bootstrap. The templates include error messages for various validation scenarios, such as duplicate URLs, incorrect URL format, and password requirements.

The application supports localization with Polish and German language validation messages.

