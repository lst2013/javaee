package com.javaee.boundary;

import com.javaee.entity.Product;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProductService {

    @PersistenceContext(unitName = "javaeeUN")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveProduct(Product product) {
        entityManager.persist(product);
    }

    public List<Product> findProductsByName(String name) {
        TypedQuery<Product> query = entityManager.createNamedQuery("findProductsByName", Product.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public Long findNotAvailableProductCount() {
        TypedQuery<Long> query = entityManager.createNamedQuery("findNotAvailableProductCount", Long.class);
        return query.getSingleResult();
    }
}