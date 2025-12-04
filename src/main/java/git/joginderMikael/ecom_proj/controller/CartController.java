package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.AddToCartRequest;
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
}
