package org.example.ui;

import org.example.dao.AccountDao;
import org.example.dao.impl.AccountDaoImpl;
import org.example.dao.BankDao;
import org.example.dao.impl.BankDaoImpl;
import org.example.dao.BranchDao;
import org.example.dao.impl.BranchDaoImpl;
import org.example.dao.CustomerDao;
import org.example.dao.impl.CustomerDaoImpl;
import org.example.dao.FinancialProfileDao;
import org.example.dao.impl.FinancialProfileDaoImpl;
import org.example.dao.LoanDao;
import org.example.dao.impl.LoanDaoImpl;
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
import org.example.pojo.CustomerFinancialProfile;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.example.entity.Gender.FEMALE;
import static org.example.entity.Gender.MALE;
import static org.example.ui.TypeManager.getType;

public class UserInterface {

    private Session session;

    private BankDao bankDao;

    private BranchDao branchDao;

    private AccountDao accountDao;

    private LoanDao loanDao;

    private CustomerDao customerDao;

    private FinancialProfileDao financialProfileDao;

    private final Scanner scanner = new Scanner(System.in);
    ;

    public void displayUserInterface() {
        boolean continueLoop = true;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        while (continueLoop) {
            try {
                session = sessionFactory.openSession();
                session.beginTransaction();

                accountDao = new AccountDaoImpl(session);
                bankDao = new BankDaoImpl(session);
                branchDao = new BranchDaoImpl(session);
                customerDao = new CustomerDaoImpl(session);
                loanDao = new LoanDaoImpl(session);
                financialProfileDao = new FinancialProfileDaoImpl(session);

                System.out.println("\nPlease choose an option: ");
                System.out.println("1. Apply for a Loan;");
                System.out.println("2. Apply for an Account;");
                System.out.println("3. Remove a Loan;");
                System.out.println("4. Remove an Account;");
                System.out.println("5. Update Loan amount;");
                System.out.println("6. Update Account amount;");
                System.out.println("7. Exit.");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        createLoan();
                        break;
                    case "2":
                        createAccount();
                        break;
                    case "3":
                        removeLoan();
                        break;
                    case "4":
                        removeAccount();
                        break;
                    case "5":
                        updateLoanAmount();
                        break;
                    case "6":
                        updateAccountAmount();
                        break;
                    case "7":
                        continueLoop = false;
                        break;
                    default:
                        System.out.println("No such option!");
                }
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                if (session.isConnected()) {
                    session.close();
                }
            }
        }
        HibernateUtil.shutdown();
    }

    private void createAccount() {
        Account account = new Account(getType(AccountType.class));

        BigDecimal amount = getAmount();
        account.setAmount(amount);

        Branch branch = getBranch();
        account.setBranch(branch);
        branch.getFinancialProfiles().add(account);

        Customer customer = getCustomer();
        customerDao.addCustomer(customer);

        CustomerAccount customerAccount = new CustomerAccount(new Date());
        customerAccount.setAccount(account);
        customerAccount.setCustomer(customer);

        account.getCustomers().add(customerAccount);
        customer.getAccounts().add(customerAccount);
        accountDao.addAccount(account);
    }

    private void createLoan() {
        Loan loan = new Loan(getType(LoanType.class));

        BigDecimal amount = getAmount();
        loan.setAmount(amount);

        Branch branch = getBranch();
        loan.setBranch(branch);
        branch.getFinancialProfiles().add(loan);

        Customer customer = getCustomer();
        customerDao.addCustomer(customer);

        customer.getLoans().add(loan);
        loan.getCustomers().add(customer);

        loanDao.addLoan(loan);
    }

    private void removeLoan() {
        printIdsWithCustomers(
                "Select loan id to be deleted: ",
                loanDao.getCustomersWithLoansIds());
        String loanId = scanner.nextLine();

        if (!financialProfileDao.existsFinancialProfileById(loanId)) {
            throw new IllegalArgumentException("Loan with id = %s does not exist!".formatted(loanId));
        }

        loanDao.removeLoanById(loanId);
    }

    private void removeAccount() {
        printIdsWithCustomers(
                "Select account id to be deleted: ",
                accountDao.getCustomersWithAccountIds());
        String accountId = scanner.nextLine();

        if (!financialProfileDao.existsFinancialProfileById(accountId)) {
            throw new IllegalArgumentException("Account with id = %s does not exist!".formatted(accountId));
        }

        accountDao.removeAccountById(accountId);
    }

    private void updateLoanAmount() {
        List<CustomerFinancialProfile> customersWithLoansIds = loanDao.getCustomersWithLoansIds();

        if (customersWithLoansIds.isEmpty()) {
            throw new IllegalStateException("No loans in the system!");
        }

        printIdsWithCustomers(
                "Select Loan to be updated:",
                customersWithLoansIds);

        updateAmount();
    }

    private void updateAccountAmount() {
        List<CustomerFinancialProfile> customersWithAccountIds = accountDao.getCustomersWithAccountIds();

        if (customersWithAccountIds.isEmpty()) {
            throw new IllegalStateException("No accounts in the system!");
        }

        printIdsWithCustomers(
                "Select Account to be updated:",
                customersWithAccountIds);

        updateAmount();
    }

    private void updateAmount() {
        String id = scanner.nextLine();

        if (!financialProfileDao.existsFinancialProfileById(id)) {
            throw new IllegalArgumentException("No financial profile with id = %s!".formatted(id));
        }

        BigDecimal amount = financialProfileDao.getLoanAmount(id);
        System.out.println("Initial amount = " + amount);

        System.out.println("Introduce new amount: ");
        BigDecimal newAmount = new BigDecimal(scanner.nextLine());

        financialProfileDao.updateAmount(id, newAmount);
    }

    private void printIdsWithCustomers(
            String message,
            List<CustomerFinancialProfile> customerFinancialProfiles
    ) {
        System.out.println(message);
        customerFinancialProfiles.forEach(System.out::println);
    }

    private BigDecimal getAmount() {
        System.out.println("\nType amount: ");
        String balance = scanner.nextLine();
        return balance.equals("") ? null : new BigDecimal(balance);
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
        List<String> emails = customerDao.getEmails();

        if (emails.isEmpty()) {
            throw new IllegalStateException("No customers in the system!");
        }

        emails.forEach(System.out::println);
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
        System.out.println("Select branch code: ");
        List<Map<Integer, String>> branches = branchDao.getBranches();

        if (branches.isEmpty()) {
            throw new IllegalStateException("No branches in the system!");
        }

        branches.forEach(System.out::println);
        String id = scanner.nextLine();

        return branchDao.getBranchById(Integer.parseInt(id))
                .orElseThrow(() -> new IllegalArgumentException("Incorrect branch id!"));
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
        System.out.println("Select bank code: ");
        List<Map<String, String>> banks = bankDao.getBankCodes();

        if (banks.isEmpty()) {
            throw new IllegalStateException("No banks in system");
        }

        banks.forEach(System.out::println);
        String code = scanner.nextLine();

        return bankDao.getBankByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect bank code!"));
    }
}
