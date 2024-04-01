package com.patika;

import java.time.Month;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();

        System.out.println("All customers : " + service.listCustomers());

        System.out.println("New customer created! :" + service.createNewCustomer("Ahmet","Müşteri", "random@g.com", 25).getName());

        char letter = 'c';
        System.out.println("Customers with the letter " + letter + " in their names " + service.listCustomersByLetter(letter));

        int whichMonthForBills = 6;
        System.out.println("Bills in " + Month.of(whichMonthForBills) + " " +  service.listBillByMonth(whichMonthForBills));

        System.out.println("All bills: " + service.listBills());

        double billsOver = 1500d;
        System.out.println("Bills over " + billsOver + "TL: " + service.listBillsByLimit(billsOver));

        System.out.println("Average of bills over " + billsOver + "TL: " + service.listAverageBillsByLimit(billsOver));

        double billsUnder = 5000d;
        System.out.println("Customer names of bills under " + billsUnder +"TL: " + service.getCustomerNamesByBillLimit(billsUnder));

        int monthForSector = 6;
        double billForSector = 5050d;
        System.out.println("Sector name of bills under " + billForSector + "TL in " + Month.of(monthForSector) + ": " + service.getSectorOfMonthWithAverage(monthForSector,billForSector));
    }
}