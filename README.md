# Spring Boot - JPA - lombok

Spring Data JPA with Spring Boot Application with minimal configuration

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Installing
Enable annotation processing in your IDE

In Intelij IDEA up to date lombok plugin must be installed!

In project root directory execute following commands:

```gradlew clean build```

```docker-compose up```

## Usage

### Creating New Mission (POST):

For Example:

```

http://localhost:8080/mission/create?missionName=someMission&imageryType=Panchromatic&startDate=2018-03-02&endDate=2018-04-01
{
• missionName: “someMission”,
• imageryType: “Panchromatic”,
• startDate: “2018-03-02”,
• endDate: "2018-04-01"
}
```

### Updating a Mission by name (PUT):

For Example:
```
http://localhost:8080/mission/update?missionName=someMission&imageryType=Hyperspectral&startDate=2018-05-07&endDate=2018-08-04
{
• missionName: “someMission”,
• imageryType: “Hyperspectral”,
• startDate: “2018-05-07”,
• endDate: "2018-08-04"
}
```

### Deleting a Mission by name (DELETE):

For Example:

```
http://localhost:8080/mission/delete?missionName=someMission
{
• missionName: “someMission”
}
```

### Creating New Product (POST):

For Example:

```
http://localhost:8080/product/create?acquisitionDate=2018-09-08&missionName=newMission&price=250&footprint=18769,22213,99123,8876
{
• acquisitionDate: “2018-09-08”,
• missionName: “newMission”,
• price: 250
• footprint: ["18769", "22213", "99123", "8876"]
}
```

### Read Product by ID (GET):

For Example:

```
http://localhost:8080/product/read?productId=2
{
• productId: 2
}
```

### Deleting Product by ID (DELETE):

For Example:

```
http://localhost:8080/product/delete?productId=2
{
• productId: 2
}
```

### Order products (PUT):

For Example:

```
http://localhost:8080/product/order?productId=2,3
{
• productId: [2, 3]
}
```

### Product get lower dates than: (GET):

For Example:

```
http://localhost:8080/product/lower?date=2018-06-07
{
• date: “2018-06-07”
}
```

### Product get greater dates than: (GET):

For Example:

```
http://localhost:8080/product/greater?date=2018-04-07
{
• date: “2018-04-07”
}
```

### Product get date between two dates: (GET):

For Example:

```
http://localhost:8080/product/between?startDate=2018-06-07&endDate=2018-12-07
{
• startDate: “2018-06-07”,
• endDate: “2018-12-07”
}
```

## Built With

* [Spring](https://spring.io/) - Spring Framework
* [Hibernate](http://hibernate.org/) - Hibernate Framework
* [Gradle](https://gradle.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Database container
* [MySql](https://www.mysql.com/) - Database

## Author

* **Filip Kusztelak**


