package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.dto.AddToCartRequest;
import git.joginderMikael.ecom_proj.model.Cart;
import git.joginderMikael.ecom_proj.model.CartItem;
import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.model.Product;
import git.joginderMikael.ecom_proj.repo.CartItemRepository;
import git.joginderMikael.ecom_proj.repo.CartRepository;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import git.joginderMikael.ecom_proj.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    @Autowired
    private ProductRepo productRepo;

    @Transactional
    public Cart addToCart(AddToCartRequest addToCartRequest, int userId) {

        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found!"));

        //creating a car or querying for a cart from DB
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepo.findById(Math.toIntExact(addToCartRequest.getProductId()))
                .orElseThrow(()-> new RuntimeException("Product Not found!"));

        //checking if the item is already existing
        CartItem cartItem = cartItemRepository.findByCartProduct(cart, product)
                .orElseGet(()->{
                   CartItem newCartItem = new CartItem();
                   newCartItem.setCart(cart);
                   newCartItem.setProduct(product);
                   newCartItem.setQuantity(0);
                   return newCartItem;
                });

        cartItem.setQuantity(cartItem.getQuantity()+addToCartRequest.getQuantity());

        cart.addItem(cartItem);
        cartRepository.save(cart);
        return null;
    }
}
