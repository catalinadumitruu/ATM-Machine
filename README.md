# ATM-Machine
Basic ATM Machine using Spring Boot.


**DEMO** (video): https://youtu.be/LG7VoMHuxac

   When I first started developing this app the first steps were analyzing the main functionalities. For visual representation I chose to use an UML behavioral diagram which is represented through **Use Case Diagram**:

![GitHub Logo](/screenshots/usecaseDiagram.jpg)

  As it can be seen a client can *login, deposit money, check the available amount of money* or *pull cash out of the account and logout*. These were the basic functionalities I wanted to implement.

  After this I started developing a short **Class Diagram** which allowed me to get a bigger picture of the project structure:

![GitHub Logo](/screenshots/ClassDiagram_ATM_Machine.jpg)

  I decided to have 3 main classes: **Clients** (for displaying names so things could be more personal), **Accounts** (the main object from our functionalities) and **Roles** (because clients can be **normal users** and have the basic functionalities described above or they can de **admins** and have extra functionalities: deleting accounts, clients, selecting data about them and so on).
  
  Considering the **database** I used a MySQL server with a pre-populated database. To **map the Java persistent objects to the database I used *Hibernate***.
  
  ![GitHub Logo](/screenshots/Capture.PNG)
  
   Another **important thing to mention** is that I decided to have an **username and password login** for taking advantage of existing tools (***Spring Security***
) and as a proof of concept. In a **real case scenario**, I think that all the data were transmitted through **the magnetic stripe of the card**.
  For authentication I used username and password. For password I have chosen a 4 digit PIN encrypted with BCrypt algorithm.
  
  I have also chosen to have a **minimum front-end for login** because in this way it is easier to present my idea.

  I also implemented (through Exceptions) some basic **business rules** :
  1. The minimum amount in someone's account can be lower than 50 RON (due to taxes and bank charges there has to be some money in account)
  2. There is a minimum sum that can be withdrawn at a moment (50 RON)
  3. All money (inserted or withdrawn) has to be multiple of 10 (10, 50, 100, ... RON) 
  
  I have also used some ***Java 8 features*** (Java Stream API for Bulk Data Operations on Collections). An example  can be found below :
  
  ``` @GetMapping("/accounts")
    public List<SimplifiedAccount> getAccounts() {
        List<BankAccount> accounts =  accountService.getAccounts();
        return accounts.stream().map(account -> new SimplifiedAccount(account.getIBAN(), account.getAmount(), account.getClient())).collect(Collectors.toList());
    }
  ```
   (In *BankController.java* class) 
   
   Another **Java 8 feature** used was ***Optional*** class, that is a container object which may or may not contain a non-null value.
   
   For testing I **created 2 profiles**:
   1. A ***production profile*** that includes client's login
   2. A ***testing profile*** for testing easily our requests
   
   I tested all the operations for Clients and Accounts controllers and also the connection to * http:localhost8080/... *
   
   
   
