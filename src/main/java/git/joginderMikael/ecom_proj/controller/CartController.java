package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.AddToCartRequest;
import git.joginderMikael.ecom_proj.dto.RemoveCartItemRequest;
import git.joginderMikael.ecom_proj.model.Cart;
import git.joginderMikael.ecom_proj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam int userId,
            @RequestBody AddToCartRequest addToCartRequest){
        Cart cart = cartService.addToCart(addToCartRequest, userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFromCart(
        @RequestParam int userId,
        @RequestBody RemoveCartItemRequest removeCartItemRequest
        ){
        cartService.removeFromCart(userId, removeCartItemRequest.getProductId(), removeCartItemRequest.getQuantity());
        return new ResponseEntity<>("Item removed from Cart!", HttpStatus.OK);
}

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam int userId){
        cartService.clearCart(userId);
        return new ResponseEntity<>("Cart cleared Sucessfully", HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam int userId){
        Cart cart = cartService.viewCart(userId);
        if(cart == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
