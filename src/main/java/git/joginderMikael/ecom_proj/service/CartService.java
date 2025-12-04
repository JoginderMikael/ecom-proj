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
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    @Autowired
    private ProductRepo productRepo;


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
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
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

    public void removeFromCart(int userId, Long productId, int quantity) {

        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found!"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()-> new RuntimeException("Cart Not Found for the user: " + user));

        Product product = productRepo.findById(Math.toIntExact(productId))
                .orElseThrow(()-> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(()->new RuntimeException("Item not found in the cart!"));

        if(cartItem.getQuantity() < quantity){
            throw  new RuntimeException("Not allowed to remove more quantity than exits in the cart");
        }

        //update the cart quantity
        cartItem.setQuantity(cartItem.getQuantity() - quantity);

        //if quantity is zero then remove it completely.
        if(cartItem.getQuantity() == 0){
            cart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }else{
            cartItemRepository.save(cartItem);
        }

        //updating the cart total
        double newTotal = cart.getItems()
                .stream()
                .mapToDouble(i->i.getQuantity() * i.getPriceAtAdd())
                .sum();

        cart.setTotal(newTotal);
        cartRepository.save(cart);
    }

    public void clearCart(int userId) {
        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found!"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()-> new RuntimeException("Cart Not Found for the user: " + user));

        //Remove all the items
        cart.getItems().clear();

        //Reset total
        cart.setTotal(0);

        //save the repository
        cartRepository.save(cart);
    }
}
