# Duke's Bookstore

A sample Jakarta EE 10 web application showcasing JSF, CDI, EJB, JPA, and internationalization, deployed on [Open Liberty](https://openliberty.io/).

## 📦 Project Overview

This application simulates an online bookstore, demonstrating:
- **JSF 4.0** for UI
- **CDI 4.0** for managed beans
- **Jakarta Persistence (JPA 3.1)** with HSQLDB
- **Enterprise Beans (EJB 4.0)** for business logic
- **Resource Bundles** for localization

## 🛠️ Build Instructions

To compile and package the project:

```bash
mvn clean package
````

This generates a deployable WAR file at:

```
target/dukes-bookstore-10-SNAPSHOT.war
```

## 🚀 Running Locally with Liberty

1. Start the Liberty server:

```bash
mvn liberty:run
```

2. Open your browser at:

```
http://localhost:9081/dukes-bookstore/
```

> If needed, modify port or config via `src/main/liberty/config/server.xml`.

---

## 🐳 Running with Docker (Recommended)

You can build and run the app entirely inside a Docker container.

### 🔧 1. Build the Docker image

Build the image:

```bash
docker build -t dukes-bookstore .
```

### 🚀 2. Run the container

```bash
docker run -p 9081:9081 dukes-bookstore
```

Then open in your browser:

```
http://localhost:9081/dukes-bookstore/
```

> The app will automatically build and start inside the container using Maven and Open Liberty.

---

## 🗂️ Directory Layout

```
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java     
    │   ├── liberty
    │   │   └── config
    │   │       ├── dropins
    │   │       └── server.xml
    │   ├── resources
    │   │   ├── jakarta/tutorial/dukesbookstore/web/messages
    │   │   │   └── Messages.properties
    │   │   └── META-INF
    │   │       ├── bookstore.taglib.xml
    │   │       └── persistence.xml
    │   └── webapp
    │       ├── bookcashier.xhtml
    │       ├── bookcatalog.xhtml
    │       ├── bookdetails.xhtml
    │       ├── bookordererror.xhtml
    │       ├── bookreceipt.xhtml
    │       ├── bookshowcart.xhtml
    │       ├── bookstoreTemplate.xhtml
    │       ├── bookstore.xhtml
    │       ├── index.xhtml
    │       ├── resources
    │       │   ├── css
    │       │   └── images
    │       └── WEB-INF
    │           ├── beans.xml
    │           ├── bookstore.taglib.xml
    │           ├── faces-config.xml
    │           └── web.xml
    └── test
        └── java
```

## 🧰 Dependencies

* Jakarta EE 10 API (`jakarta.jakartaee-api`)
* HSQLDB (embedded database)
* Open Liberty runtime (`liberty-maven-plugin`)

## 📝 License

This project is distributed under the [Eclipse Distribution License v1.0](https://www.eclipse.org/org/documents/edl-v10.php).
