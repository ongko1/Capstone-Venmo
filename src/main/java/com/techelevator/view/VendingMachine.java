package com.techelevator.view;

import com.techelevator.item.Item;

import java.util.Map;

public class VendingMachine {
    private Inventory inventory;
    private CoinBox coinBox;
    private Logger logger;
    private Logger logger2;

    public VendingMachine(Inventory inventory) {
        this.inventory = inventory;
        coinBox = new CoinBox(); /* initialize balance =0 */
        logger = new Logger("log.txt");
        logger2 = new Logger( "audit.txt");
    }

    public void feedMoney(Double billInserted) {
        coinBox.addMoney(billInserted);
        String billInsertedAsString = String.format("$%.2f",billInserted);
        logger.logEvent("FEED MONEY:",
                billInsertedAsString,
                getBalanceAsString());
    }

    public Double getBalanceInPennies() {
        Double balance = coinBox.getBalanceInPennies();
        return balance;
    }

    public String getBalanceAsString() {
        String returnString = coinBox.getBalanceAsString();
        return returnString;
    }

    public String returnChangeInCoins()  {
        logger.logEvent("GIVE CHANGE:",
                getBalanceAsString(),
                "$0.00");
        String returnString = coinBox.returnChangeAsCoins(getBalanceInPennies());
        return returnString;
    }

    public String purchaseItem(String slotLocation) {
        try {
            if (inventory.returnCurrentQty(slotLocation) == 0) {
                return inventory.stock().get(slotLocation).getName() + " Sold Out \n";
            } else if (coinBox.getBalanceInPennies() <
                    inventory.stock().get(slotLocation).getPriceInPennies()) {
                return "Please Insert Additional Funds \n";
            } else {
                String balanceBeforePurchase = getBalanceAsString();

                // SUBSTRACT Quantity in ItemMap qty=qty-1
                inventory.stock().get(slotLocation).substractQuantity();

                // SUBTRACT MONEY from CoinBox class
                // price from item map
                Double debit = inventory.stock().get(slotLocation).getPriceInPennies();
                coinBox.withdrawMoney(debit);

                // print message
                String successfulPurchase = "Dispensing --> "
                        + inventory.stock().get(slotLocation).getName() + " "
                        + "$ "+inventory.stock().get(slotLocation).getPriceAsString()+" "
                        + inventory.stock().get(slotLocation).getSound()+"\n";
                // print file log.txt
                logger.logEvent(
                        inventory.stock().get(slotLocation).getName() + "  "+ slotLocation,
                        balanceBeforePurchase,
                        getBalanceAsString());
                return successfulPurchase;
            }

        } catch (NullPointerException e) {
            return "Please Make A Valid Selection \n";
        }
    }

    public void printAudit() {
        double totalSales = 0.0;
        for (Map.Entry<String, Item> entry : inventory.stock().entrySet()) {
            Item item = entry.getValue();
            Double itemMoneySold = item.getPrice()* (5-item.getQuantity());
            totalSales += itemMoneySold;
            String formattedString = String.format("%s|%s", item.getName().trim(), itemMoneySold);
            logger2.logAudit(formattedString);
        }
        logger2.logAudit("TOTAL SALES|"+String.format("%.2f",totalSales).trim());
    }

}
