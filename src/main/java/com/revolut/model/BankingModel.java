package com.revolut.model;

import com.revolut.exceptions.BankingException;
import com.revolut.queue.Transaction;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Banking model.
 */
public final class BankingModel {

    private static BankingModel bankingModel;
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private static Map<String, Transaction> transactionMap =
            new ConcurrentHashMap<>();

    private BankingModel() {
    }

    /**
     * Gets banking model.
     *
     * @return the banking model
     */
    public static BankingModel getBankingModel() {
        if (bankingModel == null) {
            synchronized (BankingModel.class) {
                bankingModel = new BankingModel();
            }
        }
        return bankingModel;
    }

    /**
     * Create account account.
     *
     * @param account the account
     * @return the account
     */
    public Account createAccount(Account account) {
        if (accounts.containsKey(account.getAccountId())) {
            throw new BankingException();
        } else {
            String uuid = UUID.randomUUID().toString();
            account.setAccountId(uuid);
            accounts.put(uuid, account);
        }
        return account;
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    public User createUser(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new BankingException();
        } else {
            String uuid = UUID.randomUUID().toString();
            user.setUserId(uuid);
            users.put(uuid, user);
        }
        return user;
    }

    /**
     * Add amount into account.
     *
     * @param accountId       the account id
     * @param amount          the amount
     * @param transactionDate the transaction date
     */
    public void addAmountIntoAccount(String accountId, double amount,
                                     LocalDateTime transactionDate) {
        if (accounts.containsKey(accountId)) {
            accounts.get(accountId).setAmount(accounts.get(accountId).getAmount() + amount);
            accounts.get(accountId).setLastTransactionDate(transactionDate);
        }
    }

    /**
     * Reduce amount from account.
     *
     * @param accountId       the account id
     * @param amount          the amount
     * @param transactionTime the transaction time
     */
    public void reduceAmountFromAccount(String accountId, double amount,
                                        LocalDateTime transactionTime) {
        if (accounts.containsKey(accountId)) {
            accounts.get(accountId).setAmount(accounts.get(accountId).getAmount() - amount);
            accounts.get(accountId).setLastTransactionDate(transactionTime);
        }
    }

    /**
     * Gets account balance.
     *
     * @param fromAccountId the from account id
     * @return the account balance
     */
    public double getAccountBalance(String fromAccountId) {
        if (accounts.containsKey(fromAccountId)) {
            return accounts.get(fromAccountId).getAmount();
        }
        return 0;
    }

    /**
     * Update account account.
     *
     * @param account the account
     * @return the account
     */
    public Account updateAccount(Account account) {
        return null;
    }

    /**
     * Delete account.
     *
     * @param accountId the account id
     */
    public void deleteAccount(String accountId) {
    }

    public void addTransaction(Transaction transaction) {
        transactionMap.put(transaction.getTransactionId(), transaction);
    }

    public void updateTransaction(Transaction transaction) {
        if (transactionMap.containsKey(transaction.getTransactionId()))
            transactionMap.put(transaction.getTransactionId(), transaction);
    }

    public void markTransactionCompleted(Transaction transaction) {
        if (transactionMap.containsKey(transaction.getTransactionId()))
            transactionMap.put(transaction.getTransactionId(), transaction);
    }

}
