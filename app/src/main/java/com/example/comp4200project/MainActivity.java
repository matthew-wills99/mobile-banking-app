package com.example.comp4200project;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private TextView accountBalance;
    private TextView accountName;
    private ImageView prevAccount;
    private ImageView nextAccount;
    private Button btnTransfer;
    private Button btnDeposit;
    private Button btnWithdraw;
    private RecyclerView transactionsRecyclerView;

    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private int currentAccountIndex = 0;
    private TransactionsAdapter transactionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String message = "Welcome, " + sharedPreferences.getString("FirstName", "Name Not Found");

        initializeViews();

        welcomeMessage.setText(message);

        setupAccounts();
        setupTransactions();
        setupClickListeners();
    }

    private void initializeViews() {
        welcomeMessage = findViewById(R.id.welcomeMessage);
        accountBalance = findViewById(R.id.accountBalance);
        accountName = findViewById(R.id.accountName);
        prevAccount = findViewById(R.id.prevAccount);
        nextAccount = findViewById(R.id.nextAccount);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);

        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionsAdapter = new TransactionsAdapter(transactions);
        transactionsRecyclerView.setAdapter(transactionsAdapter);
    }

    private void setupAccounts() {
        accounts.add(new Account("Chequing Account", 5432.10));
        accounts.add(new Account("Savings Account", 12785.25));

        updateAccountDisplay();
    }

    private void setupTransactions() {
        transactions.clear();

        transactions.add(new Transaction(
                Transaction.Type.DEPOSIT,
                "Payroll deposit",
                200.00,
                "Nov 15, 2023 2:30 PM"
        ));

        transactions.add(new Transaction(
                Transaction.Type.WITHDRAWAL,
                "ATM withdrawal",
                50.00,
                "Nov 14, 2023 10:15 AM"
        ));

        transactions.add(new Transaction(
                Transaction.Type.TRANSFER,
                "Transfer to savings",
                100.00,
                "Nov 12, 2023 4:45 PM"
        ));

        transactionsAdapter.notifyDataSetChanged();
    }

    private void setupClickListeners() {
        prevAccount.setOnClickListener(v -> {
            if (currentAccountIndex > 0) {
                currentAccountIndex--;
                updateAccountDisplay();
            }
        });

        nextAccount.setOnClickListener(v -> {
            if (currentAccountIndex < accounts.size() - 1) {
                currentAccountIndex++;
                updateAccountDisplay();
            }
        });

        btnTransfer.setOnClickListener(v -> showTransactionDialog("Transfer"));
        btnDeposit.setOnClickListener(v -> showTransactionDialog("Deposit"));
        btnWithdraw.setOnClickListener(v -> showTransactionDialog("Withdraw"));
    }

    private void showTransactionDialog(String transactionType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_transaction, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        dialogTitle.setText(transactionType + " Funds");

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void updateAccountDisplay() {
        Account currentAccount = accounts.get(currentAccountIndex);
        accountName.setText(currentAccount.getName());
        accountBalance.setText(String.format("$%.2f", currentAccount.getBalance()));

        prevAccount.setVisibility(currentAccountIndex > 0 ? View.VISIBLE : View.INVISIBLE);
        nextAccount.setVisibility(currentAccountIndex < accounts.size() - 1 ? View.VISIBLE : View.INVISIBLE);
    }

    private static class Account {
        private String name;
        private double balance;

        public Account(String name, double balance) {
            this.name = name;
            this.balance = balance;
        }

        public String getName() { return name; }
        public double getBalance() { return balance; }
    }

    public static class Transaction {
        public enum Type {
            DEPOSIT,
            WITHDRAWAL,
            TRANSFER
        }

        private Type type;
        private String description;
        private double amount;
        private String date;

        public Transaction(Type type, String description, double amount, String date) {
            this.type = type;
            this.description = description;
            this.amount = amount;
            this.date = date;
        }
        public String getDescription() { return description; }
        public String getDate() { return date; }
        public String getFormattedAmount() {
            return (type == Type.DEPOSIT ? "+" : "-") + String.format("$%.2f", Math.abs(amount));
        }

        public int getIconResource() {
            switch (type) {
                case DEPOSIT: return R.drawable.ic_deposit;
                case WITHDRAWAL: return R.drawable.ic_withdrawal;
                case TRANSFER: return R.drawable.ic_transfer;
                default: return R.drawable.ic_deposit;
            }
        }

        public int getColorResource() {
            switch (type) {
                case DEPOSIT: return R.color.green;
                case WITHDRAWAL: return R.color.red;
                case TRANSFER: return R.color.blue;
                default: return R.color.black;
            }
        }
    }

    private class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {
        private List<Transaction> transactions;

        public TransactionsAdapter(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        @Override
        public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_transaction_item, parent, false);
            return new TransactionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionViewHolder holder, int position) {
            Transaction transaction = transactions.get(position);

            holder.description.setText(transaction.getDescription());
            holder.amount.setText(transaction.getFormattedAmount());
            holder.date.setText(transaction.getDate());
            holder.icon.setImageResource(transaction.getIconResource());
            holder.amount.setTextColor(ContextCompat.getColor(MainActivity.this, transaction.getColorResource()));
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }

        class TransactionViewHolder extends RecyclerView.ViewHolder {
            TextView description;
            TextView amount;
            TextView date;
            ImageView icon;

            public TransactionViewHolder(View itemView) {
                super(itemView);
                description = itemView.findViewById(R.id.transactionDescription);
                amount = itemView.findViewById(R.id.transactionAmount);
                date = itemView.findViewById(R.id.transactionDate);
                icon = itemView.findViewById(R.id.transactionIcon);
            }
        }
    }
}