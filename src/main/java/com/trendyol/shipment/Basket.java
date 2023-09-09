package com.trendyol.shipment;

import javax.naming.SizeLimitExceededException;
import java.util.List;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() throws SizeLimitExceededException {

        if (products.size() > 5) {
            throw new SizeLimitExceededException("Max 5 products should be on Shipment");
        }
        int[] itemCounterByShipmentSize = countItemsOnTheBasket(getProducts());

        int value = checkThreePairsForShipment(itemCounterByShipmentSize);
        if (value != -1) {
            return upgradeShipmentSize(value);
        } else {
            value = getLargestProduct(itemCounterByShipmentSize);
        }
        return valueToShipmentSize(value);
    }

    public ShipmentSize upgradeShipmentSize(int packageToUpgrade) {
        packageToUpgrade += 1;
        if (packageToUpgrade == 4) {
            return valueToShipmentSize(3);
        } else {
            return valueToShipmentSize(packageToUpgrade);
        }
    }


    private ShipmentSize valueToShipmentSize(int value) {
        if (value == 0) {
            return ShipmentSize.SMALL;
        } else if (value == 1) {
            return ShipmentSize.MEDIUM;
        } else if (value == 2) {
            return ShipmentSize.LARGE;
        } else {
            return ShipmentSize.X_LARGE;
        }
    }

    private int getLargestProduct(int[] itemCounterByShipmentSize) {
        for (int t = itemCounterByShipmentSize.length - 1; t > -1; t--) {
            if (itemCounterByShipmentSize[t] > 0) {
                return t;
            }
        }
        return -1;
    }


    private int checkThreePairsForShipment(int[] itemCounterByShipmentSize) {
        int pakcageToUpgrade = -1;
        for (int i = 0; i < itemCounterByShipmentSize.length; i++) {
            if (itemCounterByShipmentSize[i] >= 3) {
                pakcageToUpgrade = i;
            }
        }
        return pakcageToUpgrade;
    }

    public int[] countItemsOnTheBasket(List<Product> products) {
        int[] itemCounterByShipmentSize = new int[4];

        for (Product product : products) {
            if (product.getSize() == ShipmentSize.SMALL) {
                itemCounterByShipmentSize[0] += 1;

            } else if (product.getSize() == ShipmentSize.MEDIUM) {
                itemCounterByShipmentSize[1] += 1;

            } else if (product.getSize() == ShipmentSize.LARGE) {
                itemCounterByShipmentSize[2] += 1;

            } else if (product.getSize() == ShipmentSize.X_LARGE) {
                itemCounterByShipmentSize[3] += 1;
            }
        }
        return itemCounterByShipmentSize;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
