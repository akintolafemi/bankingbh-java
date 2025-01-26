## Description

[bankingbh] bankingbh API to be used for opening a new "current account" of already existing customers. It contains the following endpoints

1. [Current account creation](http://localhost:3001/api/v1/open-current-account) [POST]
2. [Fetch Customer Accounts and Details](http://localhost:3001/api/v1/customer/:customer_id) [GET]
3. [Fetch Account Transactions](http://localhost:3001/api/v1/transactions/:account_number) [GET]

### Technologies Used

- **Framework**: Java (21) Springboot
- **ORM**: JPA
- **Database**: Sqlite
- **Authentication**: Bearer Token

## Project setup

After pulling the repo, follow these steps to set up the project:

1. Proceed to open the project in your Java code editor.
2. Dependencies should download automatically.
3. Update application.properties (src/main/resources/application.properties) file with value of your chosen (JWT authorization) for variable 'bearer.token'
4. Also, proceed to src/main/resources/static/js/script.js. Open the script file and update the value of the variable 'BEARER_TOKEN' on line 2 with same value as above. This will ensure the same bearer token is used when testing the frontend
5. Click on run from your IDE and the project should start up on port 3001 (or any other port of your chosen, this can be updated in application.properties file)
6. Database is automatically created and data is seeded to it. See below for seeded records
7. API Documentation with Swagger(OpenAPI) will be visible at http://localhost:3001/swagger-ui/index.html
8. Static UI page is served at http://localhost:3001/index.html
9. 9 test cases were written to test the following. To run the tests, depending on your IDE, click on the project and click on test.
- Test for when no authorization token is found in header
- Test for when invalid authorization token is sent in header
- Test for get valid customer details 
- Test for get invalid customer details
- Test for get valid account transactions
- Test for get invalid account transactions
- Test for invalid customer on create current account
- Test for null customer on create current account
- Test for invalid initial credit on create current account


## Resources

- [Springboot Documentation](https://spring.io/projects/spring-boot).
- [JPA Documentation](https://spring.io/projects/spring-data-jpa).

# bankingbh

## Seeded Data

# Customers

| Customer ID                          | Full Name         | Email                            |
| ------------------------------------ | ----------------- | -------------------------------- |
| ad4501a3-233b-41d6-8d80-0e6e0a4ae2b4 | Jane Doe          | janedoe@gmail.com                |
| 639da415-bd0e-4807-a4e8-6979c1910c25 | Micheal Oluwafemi | michealakintola106.pog@gmail.com |

# Accounts

| Customer ID                          | Account Number | Account Balance | Account Type |
| ------------------------------------ | -------------- | --------------- | ------------ |
| ad4501a3-233b-41d6-8d80-0e6e0a4ae2b4 | 0011223344     | 5000.00         | savings      |
| 639da415-bd0e-4807-a4e8-6979c1910c25 | 1020304050     | 4000.00         | savings      |