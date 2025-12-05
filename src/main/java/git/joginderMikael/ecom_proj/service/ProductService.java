package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.Product;
import git.joginderMikael.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;


    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

    public void purchaseProduct(Product product, int quantityBought) {
        int newStockQuantity = product.getStockQuantity() - quantityBought;
        product.setStockQuantity(newStockQuantity);
        if(newStockQuantity == 0){
            product.setProductAvailable(false);
        }
        repo.save(product);
    }
}
