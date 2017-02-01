package com.javaee.boundary;

import com.javaee.datasource.DataSourceConfig;
import com.javaee.entity.Product;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class ProductServiceTest {

    @Deployment
    public static WebArchive deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "javaee.war");
        war.addClasses(DataSourceConfig.class, Product.class, ProductService.class);
        war.addAsResource("META-INF/persistence.xml");
        war.addAsLibrary(Maven.resolver().loadPomFromFile("pom.xml").resolve("com.h2database:h2").withoutTransitivity().asSingleFile());

        return war;
    }

    @EJB
    private ProductService productService;

    @Test
    public void test() {
        Product product = new Product();
        product.setCategory("Category A");
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(100));
        product.setAvailable(true);

        productService.saveProduct(product);

        product = new Product();
        product.setCategory("Category B");
        product.setName("Product B");
        product.setPrice(BigDecimal.valueOf(250));
        product.setAvailable(false);

        productService.saveProduct(product);

        product = new Product();
        product.setCategory("Other Cat");
        product.setName("Some other");
        product.setPrice(BigDecimal.valueOf(0));
        product.setAvailable(true);

        productService.saveProduct(product);

        Long notAvailableProductCount = productService.findNotAvailableProductCount();

        assertThat(notAvailableProductCount, is(1L));

        List<Product> products = productService.findProductsByName("Product");

        assertThat(products.size(), is(2));
    }
}