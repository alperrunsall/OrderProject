package com.patika;

import com.patika.model.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class OrderService {
    private List<Product> productList = new ArrayList<>();
    private Set<Customer> customerList = new HashSet<>();
    private List<Order> orderList = new ArrayList<>();
    private Map<String, Bill> billList = new HashMap<>();
    private int orderCount = 10;

    public OrderService(){
        init();
    }

    private void init(){
        //Create customers and products
        productList.add(new Product("Telefon", 1, 10000d, ProductType.ELECTRONIC));
        productList.add(new Product("Kulaklık", 2, 650d, ProductType.ELECTRONIC));
        productList.add(new Product("Gömlek", 100, 20.5d, ProductType.CLOTHING));
        productList.add(new Product("Şort", 100, 99d, ProductType.CLOTHING));
        productList.add(new Product("Ceket", 100, 500d, ProductType.CLOTHING));
        productList.add(new Product("Ayakkabı", 100, 599.99d, ProductType.CLOTHING));
        productList.add(new Product("Köpek Maması", 100, 700d, ProductType.PET));
        productList.add(new Product("Mama Kabı", 100, 99.99d, ProductType.PET));

        customerList.add(new Customer("Cem", "Bir", "cemdrman@gmail.com", 26));
        customerList.add(new Customer("Alper", "Bir", "alperrunsall@gmail.com", 24));
        customerList.add(new Customer("Alper", "iki", "alperrunsall@gmail.com", 40));
        customerList.add(new Customer("Cem", "İki", "cemdrman@gmail.com", 30));
        customerList.add(new Customer("Cem", "Üç", "cemdrman@gmail.com", 24));
        customerList.add(new Customer("Alper", "Üç", "alperrunsall@gmail.com", 19));
        customerList.add(new Customer("Cem", "Dört", "cemdrman@gmail.com", 28));

        //Create orders randomly
        for (int i = 0; i < orderCount; i++) {
            Random random = new Random();
            Customer[] customers = customerList.toArray(new Customer[0]);
            Customer customer = customers[random.nextInt(customerList.size())];

            int maxProductCount = 6;
            int productCount = random.nextInt(maxProductCount) + 1;

            double totalPrice = 0d;

            for (int j = 1; j <= productCount; j++) {
                Product product = productList.get(random.nextInt(productList.size()));
                if (product.getStock() > 0) {
                    productList.add(product);
                    product.setStock(product.getStock() - 1);
                    totalPrice += product.getPrice();
                }
            }


            Bill bill = new Bill();

            bill.setAmount(totalPrice);
            bill.setTime(randomDateTime());

            billList.put(String.valueOf(billList.size()),bill);


            Order newOrder = new Order(customer,
                    productList,
                    LocalDateTime.now(),
                    bill);

            orderList.add(newOrder);
        }
    }

    private LocalDateTime randomDateTime() { // Random time
        long minDay = LocalDate.of(2024, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2024, 7, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay + 1);

        Instant instant = Instant.ofEpochMilli(randomDay * 24 * 60 * 60 * 1000);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Customer createNewCustomer(String name, String surName, String email, int age){
        Customer newCustomer = new Customer();

        newCustomer.setName(name);
        newCustomer.setSurname(name);
        newCustomer.setMail(email);
        newCustomer.setAge(age);
        customerList.add(newCustomer);

        return newCustomer;
    }

    public List<Customer> listCustomers(){
        return customerList.stream().toList();
    }
    public List<Bill> listBills(){
        return billList.values().stream().toList();
    }
    public List<Bill> listBillsByLimit(double limit){
        return billList.values().stream()
                .filter(bill -> bill.getAmount() > limit)
                .toList();
    }
    public double listAverageBillsByLimit(double limit){
        return billList.values().stream()
                .filter(bill -> bill.getAmount() > limit)
                .mapToDouble(Bill::getAmount)
                .average()
                .orElse(0.0);
    }

    public List<String> getCustomerNamesByBillLimit(double limit){
        return orderList.stream()
                .filter(order -> order.getBill().getAmount() < limit)
                .map(order -> order.getCustomer().getName())
                .toList();
    }

    public List<String> getSectorOfMonthWithAverage(int month, double average){
        return orderList.stream()
                .filter(bill -> bill.getBill().getTime().getMonthValue() == month)
                .filter(bill -> bill.getBill().getAmount() < average)
                .map(bill -> bill.getProductList().get(0).getType().toString())
                .toList();
    }

    public List<Customer> listCustomersByLetter(char letter){
        return customerList.stream()
                .filter(customer -> customer.getName().toLowerCase().contains(String.valueOf(letter)))
                .toList();
    }
    public List<Bill> listBillByMonth(int month){ //which month of year
        return billList.values().stream()
                .filter(bill -> bill.getTime().getMonth().getValue() == month)
                .toList();
    }





}
