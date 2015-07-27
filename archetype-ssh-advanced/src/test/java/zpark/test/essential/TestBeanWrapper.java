package zpark.test.essential;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class TestBeanWrapper {

    @Test
    public void test1() {
        Product p = new Product();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(p);
        Map<String, Object> pvs = new HashMap<String, Object>();
        pvs.put("name", "电视机");
        pvs.put("price", 3000.00);
        bw.setPropertyValues(pvs);
        System.out.println(p);
    }
}

class Product {
    private String name;
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", price=" + price + "]";
    }

}
