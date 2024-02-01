import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Product testProduct;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
        testProduct = new Product(2L,"Product1", 20);
        productRepository.saveProduct(testProduct);
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteProduct(testProduct.getId());
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    public void testGetProductById() {
        Long productId = testProduct.getId();
        Product retrievedProduct = productRepository.getProductById(productId);
        assertNotNull(retrievedProduct);
        assertEquals(testProduct.getId(), retrievedProduct.getId());
    }

    @Test
    public void testSaveProduct() {
        Product newProduct = new Product(2L,"Product2",26);
        productRepository.saveProduct(newProduct);
        assertNotNull(newProduct.getId());
    }

    @Test
    public void testUpdateProduct() {
        testProduct.setName("UpdatedName");
        productRepository.updateProduct(testProduct);

        Product updatedProduct = productRepository.getProductById(testProduct.getId());
        assertEquals("UpdatedName", updatedProduct.getName());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = testProduct.getId();
        productRepository.deleteProduct(productId);
        assertNull(productRepository.getProductById(productId));
    }
}
