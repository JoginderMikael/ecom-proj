package git.joginderMikael.ecom_proj.controller;


import git.joginderMikael.ecom_proj.dto.UpdateUserRolesRequest;
import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.Product;
import git.joginderMikael.ecom_proj.repo.OrderRepo;
import git.joginderMikael.ecom_proj.service.AdminUserService;
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
    public ResponseEntity<List<Order>> getAllOrders()
    {
        return null;
    }

    @GetMapping("/orders?status=PENDING")
    public ResponseEntity<List<Order>> getAllPendingOrders()
    {
        return null;
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam String status)
    {
        return null;
    }

    /*
    DASHBOARD ANALYTICS
     */
    @GetMapping("/analytics/stats")
    public ResponseEntity<Statistics> getStatistics()
    {
        return null;
    }

    @GetMapping("/analytics/monthly-sales")
    public ResponseEntity<Statistics> getMonthlySales()
    {
        return null;
    }
    @GetMapping("/analytics/revenue")
    public ResponseEntity<Statistics> getRevenue()
    {
        return null;
    }
    @GetMapping("analytics/top-products")
    public ResponseEntity<Statistics> getTopProducts()
    {
        return null;
    }
}
