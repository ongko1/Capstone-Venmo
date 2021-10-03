package com.techelevator.item;

public class Chip extends Item {

    public Chip(String name, Double price, Integer quantity ) {
        super(name, price, quantity);
    }

    @Override
    public String getSound() {
        return "\"Crunch Crunch, Yum!\"";
    }

}
