# CRUD Bank App 🏦

## Description

The CRUD Bank App is a banking application that allows users to manage various types of accounts, make deposits, withdrawals, earn yield, and perform transfers between accounts.

## Class Diagram

![UML Class Diagram](https://github.com/guillermoaviles/CRUD-Bank-App/assets/33820055/0ba81e7b-22d9-4292-bbd3-7a20a2e831c5)


## Setup

To set up and run the project locally, follow these steps:

1. Clone the repository.
2. Configure your database settings in the `application.properties` file.
3. Build and run the application using your preferred IDE or the command line.
4. To run the app, first run the `discovery-service` instance.
5. Open an Eureka local window at http://localhost:8761/ and make sure that it is running properly.
6. Next, replace the user name in `CRUDBankApplication.java` in `crud-bank-app`.
7. Run `crud-bank-app` and reload the Eureka window to see if the crud-bank-app instance appears.
8. Run `transaction-data-service` and reload the Eureka window to see if the transaction-data-service instance appears.
9. In order to experiment with fake funds, first create a user by clicking "Add Checking Account" on the frontend. Create as many accounts as you’d like.
10. Next, add fake funds to an account by sending a PUT request to: http://localhost:8080/api/accounts/checking/{accountNumber}.
11. You are now able to send funds to any checking, savings or investment accounts.

Note: Investment accounts have a lockup time of 2 days per deposit. In order to unlock the funds before the 2 day period, modify the unlock date to sooner in a database administration tool like DBeaver.




## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database

## Controllers and Routes Structure

The project follows a RESTful API structure with the following main controllers and routes:

### Account Controller

- GET `/api/accounts/checking`: Get all checking accounts.
- GET `/api/accounts/checking/{accountNumber}`: Get a checking account by account number.
- GET `/api/accounts/checking/owner/{name}`: Get a checking account by owner.
- POST `/api/accounts/checking`: Create a new checking account.
- PUT `/api/accounts/checking/{accountNumber}`: Update a checking account.
- PATCH `/api/accounts/checking/transfer/{fromId}/{destinationId}/{amount}`: Transfer funds between accounts.
- DELETE `/api/accounts/checking/{accountNumber}`: Delete a checking account.

### Investment Account Controller

- GET `/api/accounts/investment`: Get all investment accounts.
- GET `/api/accounts/investment/{accountNumber}`: Get an investment account by account number.
- POST `/api/accounts/investment`: Create a new investment account.
- PUT `/api/accounts/investment/{accountNumber}`: Update an investment account.
- DELETE `/api/accounts/investment/{accountNumber}`: Delete an investment account.


## Future Work

Some potential future enhancements for the project include:

- Adding user authentication and authorization.
- Implementing additional account types (e.g., savings, credit).
- Enhancing error handling and validation.
- Adding more advanced transaction features.

## Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Hibernate Documentation](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html)

## Code Examples

### InvestmentAccount.java

    private void liquidateDeposits(BigDecimal withdrawalAmount) {
        LocalDate currentDate = LocalDate.now();

        List<Deposit> depositsToDelete = new ArrayList<>();
        for (Deposit deposit : deposits) {
            if (currentDate.isAfter(deposit.getUnlockDate())) {
                BigDecimal depositAmount = calculateAmountWithInterest(deposit);
                if (withdrawalAmount.compareTo(depositAmount) >= 0) {
                    withdrawalAmount = withdrawalAmount.subtract(depositAmount);
                    setBalance(getBalance().subtract(depositAmount));
                    depositsToDelete.add(deposit);
                } else if (withdrawalAmount.compareTo(depositAmount) < 0) {
                    deposit.setAmount(depositAmount.subtract(withdrawalAmount));
                    setBalance(getBalance().subtract(deposit.getAmount()));
                    withdrawalAmount = BigDecimal.valueOf(0);
                }
            }
        }
        for (Deposit deposit : depositsToDelete) {
            deposits.remove(deposit);
        }
    }

