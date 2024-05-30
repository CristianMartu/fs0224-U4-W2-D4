//package crimartu;
package crimartu;

import com.github.javafaker.Faker;
import crimartu.entities.Customer;
import crimartu.entities.Order;
import crimartu.entities.Product;
import crimartu.interfaces.CustomProductFunction;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static CustomProductFunction createProducts = (int quantity, List<Product> list) -> {
//        Faker faker = new Faker();
//        for (int i = 0; i < quantity; i++) {
//            list.add(new Product(new Random().nextDouble(5.0, 200.0), faker.commerce().department(), faker.commerce().productName()));
//        }
//        return list;
        Faker faker = new Faker();
        String category = faker.commerce().department();
        for (int i = 0; i < quantity; i++) {
            list.add(new Product(new Random().nextDouble(5.0, 200.0), category, faker.commerce().productName()));
        }
        return list;
    };

    public static void main(String[] args) {
        List<Product> products = getProductList(5);
        Collections.shuffle(products);
        List<Order> orders = getOrderList();
//        System.out.println(products);
//        System.out.println();
//        System.out.println(orders);

        //Es1
        System.out.println("__________Es1__________");
        Map<Customer, List<Order>> es1 = orders.stream().collect(Collectors.groupingBy(Order::getCustomer));
        es1.forEach((customer, listProducts) -> System.out.println("Cliente: " + customer.getName() + "\nProdotti: \n\t" + listProducts));

        //Es2
        System.out.println("__________Es2__________");
        Map<Customer, Double> es2 = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(Order -> Order.getProducts().stream().mapToDouble(Product::getPrice).sum())));

        es2.forEach(((customer, aDouble) -> System.out.println(customer.getName() + "\t" + aDouble)));

        //Es3
        System.out.println("__________Es3__________");

        List<Product> list = products.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(3).toList();
        System.out.println(list);
//        OptionalDouble es3 = products.stream().mapToDouble(Product::getPrice).max();
//        if (es3.isPresent()) {
//            System.out.println("Prodotto più costoso è: " + es3.getAsDouble());
//        } else {
//            System.out.println("Nessuno elemento trovato");
//        }

        //Es4
        System.out.println("__________Es4__________");
        Double es4 = orders.stream().flatMap(order -> order.getProducts().stream())
                .collect(Collectors.averagingDouble(Product::getPrice));
        System.out.println(es4);

        List<Product> prodotti = orders.stream().flatMap(order -> order.getProducts().stream()).toList();
        Double result = prodotti.stream().collect(Collectors.averagingDouble(Product::getPrice));
        System.out.println(result);

        //Es5
        System.out.println("__________Es5__________");
        Map<String, Double> es5 = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        es5.forEach((category, sum) -> System.out.println(category + ": " + sum));


//        File file = new File("src/testo.text");
//        try {
//            FileUtils.writeStringToFile(file, "ciao", StandardCharsets.UTF_8, true);
//            System.out.println("File creato correttamente!");
//        } catch (IOException err) {
//            System.out.println(err.getMessage());
//        }
    }

    private static List<Product> getProductList(int quantity) {
        List<Product> products = new ArrayList<>();
        createProducts.create(quantity, products);
        createProducts.create(quantity, products);
        createProducts.create(quantity, products);
        return products;
    }

    private static List<Order> getOrderList() {
        Faker faker = new Faker();
        List<Order> orders = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(new Customer(faker.name().fullName(), faker.random().nextInt(1, 5)));
        }
        for (Customer customer : customers) {
            orders.add(new Order("In attesa", LocalDate.now(), LocalDate.now().plusDays(5), getProductList(5), customer));
        }
        return orders;
    }
}
