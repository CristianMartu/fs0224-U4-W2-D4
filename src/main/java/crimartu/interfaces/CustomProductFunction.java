package crimartu.interfaces;

import crimartu.entities.Product;

import java.util.List;

@FunctionalInterface
public interface CustomProductFunction {
    public List<Product> create(int quantity, List<Product> list);
}

