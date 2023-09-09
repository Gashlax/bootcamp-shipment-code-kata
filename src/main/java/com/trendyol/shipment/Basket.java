package com.trendyol.shipment;

import javax.naming.SizeLimitExceededException;
import java.util.List;

public class Basket {
    private List<Product> products;

    private final ShipmentSize[] shipmentSizeTypes = ShipmentSize.values();


    public ShipmentSize getShipmentSize() throws SizeLimitExceededException {

        if (products.size() > 5) {
            throw new SizeLimitExceededException("Max 5 products should be on Shipment");
        }
        int[] itemCounterByShipmentSize = countItemsOnTheBasketByType(getProducts());

        int value = checkForDesiredNumberOfPairTypes(itemCounterByShipmentSize, 3);

        if (value != -1) {
            return upgradeShipmentSizeToUpperSize(value);
        } else {
            value = getLargestSizeProduct(itemCounterByShipmentSize);
        }
        return indexToShipmentSize(value);
    }

    /**
     * Takes product list as a param and returns
     * item Counter By Shipment Size
     *
     * @param products
     * @return int[]
     */
    public int[] countItemsOnTheBasketByType(List<Product> products) {
        int[] itemCounterByShipmentSize = new int[4];

        int sizeTypeCounter = 0;
        for (ShipmentSize shipmentSize : shipmentSizeTypes) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getSize() == shipmentSize) {
                    itemCounterByShipmentSize[sizeTypeCounter] += 1;
                }
            }
            sizeTypeCounter++;
        }
        return itemCounterByShipmentSize;
    }

    /**
     * Checks for three of a kind and returns the index of type else
     *  returns -1
     *
     * @param itemCounterByShipmentSize
     * @param desiredPairTypes
     * @return int
     */
    private int checkForDesiredNumberOfPairTypes(int[] itemCounterByShipmentSize, int desiredPairTypes) {
        int pakcageToUpgrade = -1;
        for (int i = 0; i < itemCounterByShipmentSize.length; i++) {
            if (itemCounterByShipmentSize[i] >= desiredPairTypes) {
                pakcageToUpgrade = i;
            }
        }
        return pakcageToUpgrade;
    }

    /**
     * To upgrade package type to upper size
     *
     * @param packageToUpgrade
     * @return ShipmentSize
     */
    public ShipmentSize upgradeShipmentSizeToUpperSize(int packageToUpgrade) {
        packageToUpgrade += 1;
        if (packageToUpgrade == shipmentSizeTypes.length) {
            return indexToShipmentSize(shipmentSizeTypes.length - 1);
        } else {
            return indexToShipmentSize(packageToUpgrade);
        }
    }

    /**
     * Converts int to Shipment Size
     *
     * @param indexOfSize
     * @return ShipmentSize
     */
    private ShipmentSize indexToShipmentSize(int indexOfSize) {
        return shipmentSizeTypes[indexOfSize];
    }

    /**
     * Gets largest package size on the basket
     *
     * @param itemCounterByShipmentSize
     * @return int
     */
    private int getLargestSizeProduct(int[] itemCounterByShipmentSize) {
        for (int t = itemCounterByShipmentSize.length - 1; t > -1; t--) {
            if (itemCounterByShipmentSize[t] > 0) {
                return t;
            }
        }
        return -1;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
