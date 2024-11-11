
# CRM System Project Report

The CRM (Customer Relationship Management) System is an application designed
to manage interactions with customers, track sales, and maintain a record of
customer details and communications. The system aims to improve customer
relationships and streamline the sales process.


## Tech Stack

**Client:** React, Redux, TailwindCSS

**Server:** Node, Express

**Backend:** Java, Spring Boot

**Frontend:** Android (Java)

**Database:** MySQL

**Tools/Frameworks:** IntelliJ IDEA, Android Studio, Hibernate (for ORM), Spring
Data JPA

**Logging:** SLF4J, Logback

## Setup Instruction

To deploy this project run

1. Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/Atul663/CRM-System.git
```

2. Configure Database

Install and set up MySQL.

Create a new database, Name of DB :- crm_db

Configure the application.properties file in src/main/resources/ for MySQL
connection:


spring.datasource.url=jdbc:mysql://localhost:3306/crm_db

spring.datasource.username=Your Username

spring.datasource.password=Your Password

spring.jpa.hibernate.ddl-auto=update


3. Build the Backend
Use Maven to clean and install the necessary dependencies for the backend:

```bash
mvn clean install
```

4. Run the Backend


To start the backend, run the following command:

```bash
mvn spring-boot:run
```
The backend will now be running at http://localhost:8080 by default.





## Important API

**Lead Controller**

• POST /api/leads/create: Create a new lead.

• GET /api/leads/all: Fetch all leads.

• GET /api/leads/{id}: Fetch a lead by ID.

• DELETE /api/leads/{id}: Delete a lead by ID.

• PUT /api/leads/{id}: Update a lead.

• POST /api/leads/{id}/convertToContact: Convert a lead to a contact.


**Sales Controller**

• POST /api/sales/create: Create a new sale.

• GET /api/sales/all: Fetch all sales.

• GET /api/sales/{id}: Fetch a sale by ID.

• DELETE /api/sales/{id}: Delete a sale by ID.

• PUT /api/sales/{id}/stage: Update sales stage.

• PUT /api/sales/{id}: Update sales details.

• GET /api/sales/contact/{contactId}: Fetch sales associated with a contact.

• GET /api/sales/stage/{salesStage}: Fetch sales by stage.

**Interaction Controller**

• POST /api/interactions/add: Add a new interaction.

• GET /api/interactions/all/upcoming: Fetch all upcoming interactions.

• GET /api/interactions/all/old: Fetch all old interactions.

• GET /api/interactions/upcoming_interactions/{contactId}: Fetch upcoming
interactions by contact ID.

• GET /api/interactions/old_interactions/{contactId}: Fetch old interactions by
contact ID.

• DELETE /api/interactions/old/{interactionId}: Delete an old interaction.

• DELETE /api/interactions/upcoming/{interactionId}: Delete an upcoming
interaction.

• PUT /api/interactions/upcoming/{interactionId}: Update an upcoming interaction.

**Contact Controller Endpoints**

• POST /api/contacts/create: Create a new contact.

• GET /api/contacts/all: Fetch all contacts.

• GET /api/contacts/{id}: Fetch a contact by ID.

• DELETE /api/contacts/{id}: Delete a contact by ID.

• PUT /api/contacts/{id}: Update a contact by ID.

• PUT /api/contacts/{id}/incrementSales: Increment the sales count for a specific
contact by its ID.

**Activity Controller**

• GET /api/activities/report: Generate activity report for a given date range.


## Conclusion

8. Conclusion

The CRM System project successfully meets the defined objectives, providing a
comprehensive tool for managing customer relationships, tracking sales, logging
interactions, and generating detailed activity reports. The system is highly modular
and can be expanded to include more features in the future.
This report covers the key aspects of the project, from its design and features to the
technologies used and the challenges faced. If you need further details or
modifications, feel free to let me know!

