package org.example.ui;

import org.example.dao.AccountDao;
import org.example.dao.BankDao;
import org.example.dao.BranchDao;
import org.example.dao.CustomerDao;
import org.example.dao.CustomerInfoDao;
import org.example.dao.LoanDao;
import org.example.entity.Account;
import org.example.entity.AccountType;
import org.example.entity.Address;
import org.example.entity.Bank;
import org.example.entity.Branch;
import org.example.entity.Customer;
import org.example.entity.CustomerAccount;
import org.example.entity.CustomerInfo;
import org.example.entity.Loan;
import org.example.entity.LoanType;
import org.hibernate.classic.Session;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import static org.example.entity.Gender.FEMALE;
import static org.example.entity.Gender.MALE;
import static org.example.ui.TypeManager.getType;

public class UserInterface {

    private final Session session;

    private final BankDao bankDao;

    private final BranchDao branchDao;

    private final AccountDao accountDao;

    private final LoanDao loanDao;

    private final CustomerDao customerDao;

    private final CustomerInfoDao customerInfoDao;

    private final Scanner scanner;

    public UserInterface(Session session,
                         BankDao bankDao,
                         BranchDao branchDao,
                         AccountDao accountDao,
                         LoanDao loanDao,
                         CustomerDao customerDao,
                         CustomerInfoDao customerInfoDao,
                         Scanner scanner) {
        this.session = session;
        this.bankDao = bankDao;
        this.branchDao = branchDao;
        this.accountDao = accountDao;
        this.loanDao = loanDao;
        this.customerDao = customerDao;
        this.customerInfoDao = customerInfoDao;
        this.scanner = scanner;
    }

    public void displayUserInterface() {
        System.out.println("\nPlease choose an option: ");
        System.out.println("1. Apply for a Loan;");
        System.out.println("2. Apply for an Account;");

        String option = scanner.nextLine();

        switch (option) {
            case "1":
                createLoan();
                break;
            case "2":
                createAccount();
                break;
            default:
                System.out.println("No such option!");
        }
    }

    private void createAccount() {
        try {
            session.beginTransaction();
            Account account = new Account(getType(AccountType.class), getAmount());

            Branch branch = getBranch();
            account.setBranch(branch);
            branch.getAccounts().add(account);

            Customer customer = getCustomer();
            customerDao.addCustomer(customer);

            CustomerAccount customerAccount = new CustomerAccount(new Date());
            customerAccount.setAccount(account);
            customerAccount.setCustomer(customer);

            account.getCustomers().add(customerAccount);
            customer.getAccounts().add(customerAccount);
            accountDao.addAccount(account);

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    private void createLoan() {
        try {
            session.beginTransaction();
            Loan loan = new Loan(getType(LoanType.class), getAmount());

            Branch branch = getBranch();
            loan.setBranch(branch);
            branch.getLoans().add(loan);

            Customer customer = getCustomer();
            customerDao.addCustomer(customer);

            customer.getLoans().add(loan);
            loan.getCustomers().add(customer);

            loanDao.addLoan(loan);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    private BigDecimal getAmount() {
        System.out.println("\nType amount: ");
        String balance = scanner.nextLine();
        return new BigDecimal(balance);
    }

    private Customer getCustomer() {
        System.out.println("\nPlease choose an option: ");
        System.out.println("1. Add new customer;");
        System.out.println("2. Select customer from existing one;");

        String option = scanner.nextLine();

        return switch (option) {
            case "1" -> createNewCustomer();
            case "2" -> selectExistingCustomer();
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };
    }

    private Customer createNewCustomer() {
        System.out.println("\nCustomer details: ");
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();

        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.println("Date of birth(dd-MM-yyyy): ");
        String dateOfBirth = scanner.nextLine();

        System.out.println("IDNP: ");
        String idnp = scanner.nextLine();

        System.out.println("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        CustomerInfo customerInfo = getCustomerInfo();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Customer customer = new Customer(
                    firstName,
                    lastName,
                    simpleDateFormat.parse(dateOfBirth),
                    idnp,
                    phoneNumber,
                    email
            );
            customer.setInfo(customerInfo);
            customerInfo.setCustomer(customer);

            return customer;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomerInfo getCustomerInfo() {
        System.out.println("Country: ");
        String country = scanner.nextLine();

        System.out.println("City: ");
        String city = scanner.nextLine();

        System.out.println("Postal code: ");
        String postalCode = scanner.nextLine();

        System.out.println("Street: ");
        String street = scanner.nextLine();

        System.out.println("Occupation: ");
        String occupation = scanner.nextLine();

        System.out.println("Citizenship: ");
        String citizenship = scanner.nextLine();

        System.out.println("Gender(M/F): ");
        String gender = scanner.nextLine();

        Address address = new Address(country, city, street, postalCode);
        return new CustomerInfo(
                address,
                occupation,
                citizenship,
                gender.equalsIgnoreCase("M") ? MALE : FEMALE
        );
    }

    private Customer selectExistingCustomer() {
        System.out.println("Select the customer email: ");
        customerDao.getEmails()
                .forEach(System.out::println);

        String email = scanner.nextLine();

        return customerDao.getCustomerByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect email"));
    }

    private Branch getBranch() {
        System.out.println("\nPlease choose an option: ");
        System.out.println("1. Add new branch;");
        System.out.println("2. Select a branch from existing one;");

        String option = scanner.nextLine();

        return switch (option) {
            case "1" -> createNewBranch();
            case "2" -> selectExistingBranch();
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };
    }

    private Branch createNewBranch() {
        System.out.println("\nBranch details: ");
        System.out.println("Branch Name: ");
        String name = scanner.nextLine();

        System.out.println("Branch Country: ");
        String country = scanner.nextLine();

        System.out.println("Branch City: ");
        String city = scanner.nextLine();

        System.out.println("Branch Postal code: ");
        String postalCode = scanner.nextLine();

        System.out.println("Branch Street: ");
        String street = scanner.nextLine();

        Bank bank = getBank();

        Address address = new Address(country, city, street, postalCode);
        Branch branch = new Branch(name, address);
        branch.setBank(bank);
        bank.getBranches().add(branch);

        return branch;
    }

    private Branch selectExistingBranch() {
        return null;
    }

    private Bank getBank() {
        System.out.println("\nPlease choose an option: ");
        System.out.println("1. Add a new bank;");
        System.out.println("2. Select existing bank;");
        String option = scanner.nextLine();

        return switch (option) {
            case "1" -> createNewBank();
            case "2" -> selectExistingBank();
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };
    }

    private Bank createNewBank() {
        System.out.println("\nBank details: ");

        System.out.println("Bank name: ");
        String name = scanner.nextLine();

        System.out.println("Branch Country: ");
        String country = scanner.nextLine();

        System.out.println("Branch City: ");
        String city = scanner.nextLine();

        System.out.println("Branch Postal code: ");
        String postalCode = scanner.nextLine();

        System.out.println("Branch Street: ");
        String street = scanner.nextLine();

        Address address = new Address(country, city, street, postalCode);

        return new Bank(name, address);
    }

    private Bank selectExistingBank() {
        return null;
    }
}
