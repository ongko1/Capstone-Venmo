package com.techelevator.view;

import com.techelevator.item.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Inventory {

    private TreeMap<String, Item> itemMap = new TreeMap<>();

    public Inventory() {
        String fileName = "vendingmachine.csv";
        File inventoryFile = new File(fileName);
        /* create map file from vendingmachine.csv initial quantity to 5*/
        try (Scanner readFile = new Scanner(inventoryFile)) {
            while (readFile.hasNextLine()) {
                String currentString = readFile.nextLine();
                /*** index array 0-3
                 0  1             2    3
                 A1|Potato Crisps|3.05|Chip
                 */
                String[] tempArray = currentString.split("\\|");
                if (tempArray[3].equals("Candy")) {
                    Candy candy = new Candy(tempArray[1], Double.parseDouble(tempArray[2]), 5);
                    itemMap.put(tempArray[0], candy);
                } else if (tempArray[3].equals("Chip")) {
                    Chip chip = new Chip(tempArray[1], Double.parseDouble(tempArray[2]), 5);
                    itemMap.put(tempArray[0], chip);
                } else if (tempArray[3].equals("Drink")) {
                    Drink drink = new Drink(tempArray[1], Double.parseDouble(tempArray[2]), 5);
                    itemMap.put(tempArray[0], drink);
                } else if (tempArray[3].equals("Gum")) {
                    Gum gum = new Gum(tempArray[1], Double.parseDouble(tempArray[2]), 5);
                    itemMap.put(tempArray[0], gum);
                }
            }
        } catch (FileNotFoundException e) { System.out.println("File Vending Machine : "+fileName + " is not found"); }
    }

    public int returnCurrentQty(String slotLocation) {
        return itemMap.get(slotLocation).getQuantity();
    }

    /* return Item class itemMap */
    public Map<String, Item> stock() {
        return itemMap;
    }

}

