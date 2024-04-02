<p align="center">
  <img src="https://raw.githubusercontent.com/Top-Films/assets/main/png/top-films-logo-white-transparent.png" width="400" alt="logo"/>
  <br><br>
</p>

![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)

[![CircleCI](https://dl.circleci.com/status-badge/img/circleci/2ZAeGbe6WDTLfg9j64XbkD/4851d60c-21be-4f9c-8c73-2a96c808a017/tree/main.svg?style=shield)](https://dl.circleci.com/status-badge/redirect/circleci/2ZAeGbe6WDTLfg9j64XbkD/4851d60c-21be-4f9c-8c73-2a96c808a017/tree/main)
![Static Badge](https://img.shields.io/badge/license-Apache%202.0%20with%20Commons%20Clause-green)

## Overview
The Top Films Primary API is the interface for all Top Films data outside of authentication and authorization.

## Prerequisites
- Git
- Java 21
- Maven

## Installation

1. Clone this repository:
```bash
https://github.com/Top-Films/primary-api.git
```
2. Navigate to the directory
```bash
cd primary-api
```
3. Add the DATASOURCE_URL, DB_USERNAME, and DB_PASSWORD environment variables to the run settings

4. Run the application
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Contributing
Contributions are welcome! If you'd like to contribute to the project, please follow these steps:

- Fork the repository.
- Create a new branch for your feature or bug fix.
- Make your changes and commit them.
- Push your changes to your fork.
- Submit a pull request to the main repository.

## License
This project is licensed under the Apache 2.0 License with Commons Clause.
