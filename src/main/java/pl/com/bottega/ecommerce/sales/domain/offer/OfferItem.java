/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    private ProductData productData;

    private int quantity;

    private Money totalCost;

    private Discount discount;

    public OfferItem(ProductData productData, int quantity, Money totalCost, Discount discount) {
        this.productData = productData;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.discount = discount;

        BigDecimal discountValue = new BigDecimal(0);
        if (discount != null) {
            discountValue = discountValue.subtract(discount.getValue().getValue());
        }
        this.totalCost.setValue(getProductData().getPrice().getValue().multiply(new BigDecimal(quantity)).subtract(discountValue));
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    @Override public int hashCode() {
        return Objects.hash(totalCost.getCurrency(), discount.getValue(), discount.getCause(), productData.getId(), productData.getName(),
                            productData.getPrice(), productData.getSnapshotDate(), productData.getType(), quantity, totalCost.getValue());
    }

    @Override public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * @param item
     * @param delta acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {
        if (productData.getPrice() == null) {
            if (other.productData.getPrice() != null) {
                return false;
            }
        } else if (!productData.getPrice().equals(other.productData.getPrice())) {
            return false;
        }
        if (productData.getName() == null) {
            if (other.productData.getName() != null) {
                return false;
            }
        } else if (!productData.getName().equals(other.productData.getName())) {
            return false;
        }

        if (productData.getId() == null) {
            if (other.productData.getId() != null) {
                return false;
            }
        } else if (!productData.getId().equals(other.productData.getId())) {
            return false;
        }
        if (productData.getType() != other.productData.getType()) {
            return false;
        }

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getValue().compareTo(other.totalCost.getValue()) > 0) {
            max = totalCost.getValue();
            min = other.totalCost.getValue();
        } else {
            max = other.totalCost.getValue();
            min = totalCost.getValue();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

}
