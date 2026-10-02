# Return Module Analysis

## 1. Relationship between Return and Sale
The relationship between `Return` and `Sale` is an **Association** (specifically a direct reference). `Return` references an existing `Sale` object via an attribute to identify the original transaction being refunded[cite: 2]. It is not inheritance because a return is not a type of sale[cite: 2]. It is not composition or aggregation because both entities have independent lifecycles (a sale exists prior to and independently of any return)[cite: 2].

## 2. Representation of Returned Products
In the `Return` class, returned products are represented using a collection attribute, specifically `List<Product>`[cite: 1]. This attribute stores only the specific `Product` instances from the original sale that the customer chooses to return, allowing partial returns[cite: 1].