# **Banking Service**
Rest API for banking services


## Tech Stack

1. Java - 15

2. Maven - 3.8.x

3. Spring Boot - 2.4.X

4. PostgreSQL

## Requirements

1. **Admin role with features**:

    * Sign in/out as admin.
    
    * Add bank employees.
    
    * Delete employees.

2. **Employee role with feature**:

    * Sign in/out as an employee. 
    
    * Create a customer.
    
    * Create accounts like savings, salary, loan, current account etc.
    
    * Link customers with accounts.
    
    * Update KYC for a customer.
    
    * Get details of a customer.
    
    * Delete customer.
    
    * Get account balance for an account.
    
    * Transfer money from one account to another.
    
    * Print Account statement of an account for a time range in pdf.
    
    * Calculate interest for the money annually (at 3.5% p.a.) and update the account balance.



## Steps to Setup

**1. Clone the application**

```bash
https://github.com/MathanRajOlaganathan/Banking-Service.git
```

**2. Build and run the app using maven**

```bash
cd banking-system
mvn package
java -jar target/banking-system-1.0.0.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```
Alternatively, you can run the built  jar which is  placed under FoodDelivery-Spicy directory -

```bash
java -jar banking-system-1.0.0.jar
```


The server will start at <http://localhost:9090>.

The swagger will start at <http://localhost:9090/swagger-ui/>.

**Application Jar**  [banking-system-1.0.0.jar](banking-system-1.0.0.jar)

**API Documentation:** [Banking-System-Rest-Documentation.pdf](Banking-System-Rest-Documentation.pdf)



## Next Steps

1. Improve the performance.

2. Unit Testing/Integration Testing.

3. CI/CD





