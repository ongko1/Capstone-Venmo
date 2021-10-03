package com.techelevator.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.item.Item;

public class MenuAction extends Menu {

    /** CREATE MAP FILE INVENTORY */
    private Inventory inventory = new Inventory();

    private VendingMachine vendingMachine = new VendingMachine(inventory);
    private PrintWriter out;
    private Scanner in;

    public MenuAction(InputStream input, OutputStream output) throws IOException {
        super(input, output);
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public void displayInventory() {
        for (Map.Entry<String, Item> entry : inventory.stock().entrySet()) {
            Item item = entry.getValue();
            String formattedString = String.format("%-5s %-22s %-5s %-10s",
                    entry.getKey(),
                    item.getName(),
                    item.getPriceAsString(),
                    item.getQuantity()  > 0 ? item.getQuantity() : "Sold Out");

            /*
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedString = String.format("%-5s %-22s %-5s %-10s",
                    entry.getKey(),
                    item.getName(),
                    df.format(item.getPrice()),
                    item.getQuantity()  > 0 ? item.getQuantity() : "Sold Out");
            */
            System.out.println(formattedString);
        }
    }

    public void feedMoney() {
        System.out.println("Please Insert U.S. Dollar Bills ($1 $2 $5 $10 $20 $50 $100) ");
        try {
            Double moneyInserted = in.nextDouble();
            in.nextLine(); // consume new line left-over
            if (moneyInserted == 1 || moneyInserted  == 2 || moneyInserted == 5 || moneyInserted == 10
                    || moneyInserted == 20 || moneyInserted == 50 || moneyInserted == 100) {
                vendingMachine.feedMoney(moneyInserted);
                System.out.println("Thank You For inserting "
                        + String.format("$%.2f", moneyInserted)
                        + "  Total Money Available : "+displayCurrentBalance());
            } else {
                System.out.println("Please Insert Valid Currency");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please Insert Valid Currency");
        }

    }

    public void selectProduct() {
        System.out.println("Please Select Product");
        String userSelection = in.nextLine();
        String returnString = vendingMachine.purchaseItem(userSelection);
        System.out.print(returnString);
        System.out.println("Your Balance now is " + displayCurrentBalance());
    }

    public String displayCurrentBalance() {
        return vendingMachine.getBalanceAsString();

    }

    public void finishTransaction()  {
        System.out.println(vendingMachine.returnChangeInCoins());
    }

    public void hiddenMenu() {
        vendingMachine.printAudit();
    }
}

