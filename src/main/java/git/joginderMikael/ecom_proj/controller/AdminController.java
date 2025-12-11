package git.joginderMikael.ecom_proj.controller;


import git.joginderMikael.ecom_proj.dto.UpdateUserRolesRequest;
import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderStatus;
import git.joginderMikael.ecom_proj.model.Product;
import git.joginderMikael.ecom_proj.repo.OrderRepo;
import git.joginderMikael.ecom_proj.service.AdminOrderService;
import git.joginderMikael.ecom_proj.service.AdminUserService;
import git.joginderMikael.ecom_proj.service.OrderService;
import git.joginderMikael.ecom_proj.service.ProductService;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProductService  productService;

    @Autowired
    private AdminOrderService  adminOrderService;

    /*
    USER MANAGEMENT
     */
    @GetMapping("/users")
    public ResponseEntity<List<EcomUsers>> getAllUsers()
    {
        return new ResponseEntity<>(adminUserService.listAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/roles")
    public ResponseEntity<EcomUsers> updateRoles(
            @PathVariable int id,
            @RequestBody UpdateUserRolesRequest request
    )
    {
        return new ResponseEntity<>(adminUserService.updateUserRoles(id, request.getRoles()), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/block")
    public ResponseEntity<EcomUsers> blockUser(@PathVariable int id){
      return new ResponseEntity<>(adminUserService.blockUser(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/unblock")
    public ResponseEntity<EcomUsers> unBlockUser(@PathVariable int id){
        return new ResponseEntity<>(adminUserService.unBlockUser(id), HttpStatus.OK);
    }

    /*
    PRODUCT MANAGEMENT
     */

    @PutMapping("/products/{id}/stock")
    public ResponseEntity<Product> updateStock(@RequestParam Integer stockQuantity, @PathVariable Long id){

        return ResponseEntity.ok(productService.updateStock(id, stockQuantity));
    }

    @PutMapping("/products/{id}/toggle")
    public ResponseEntity<Product> toggleProduct(@PathVariable Long id){
        return  ResponseEntity.ok(productService.toggleProduct(id));
    }

    @GetMapping("/products/low-stock")
    public ResponseEntity<List<Product>> lowStock(
            @RequestParam(defaultValue = "10") int threshold
    ){
        return ResponseEntity.ok(productService.getLowStockProducts(threshold));
    }

    /*
    ORDER MANAGEMENT
     */

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) OrderStatus orderStatus)
    {
       List<Order> orders;

       if(orderStatus != null)
       {
           orders = adminOrderService.getOrderByStatus(orderStatus);
       }else {
           orders = adminOrderService.getAllOrders();
       }

       return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus orderStatus)
    {
        //http://localhost:8081/api/admin/orders/1/status?orderStatus=DELIVERED
        Order updated =  adminOrderService.updateStatus(id, orderStatus);
        return ResponseEntity.ok(updated);
    }


}
