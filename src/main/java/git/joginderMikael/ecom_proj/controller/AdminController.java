package git.joginderMikael.ecom_proj.controller;


import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.Product;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.hibernate.stat.Statistics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {


    /*
    USER MANAGEMENT
     */
    @GetMapping("/users")
    public ResponseEntity<List<EcomUsers>> getAllUsers()
    {
        return null;
    }

    @PutMapping("/users/{id}/roles")
    public ResponseEntity<EcomUsers> updateRoles(@PathVariable Long id)
    {
        return null;
    }

    @PutMapping("/users/{id}/block")
    public ResponseEntity<EcomUsers> accessBlock(){
        return null;
    }

    @PutMapping("/users/{id}/unblock")
    public ResponseEntity<EcomUsers> accessUnblock(){
        return null;
    }

    /*
    PRODUCT MANAGEMENT
     */

    @PutMapping("/products/{id}/stock")
    public ResponseEntity<Product> updateStock(@RequestBody Product product, @PathVariable Long id){
        return null;
    }

    @PutMapping("/products/{id}/toggle")
    public ResponseEntity<Product> updateToggle(@RequestBody Product product, @PathVariable Long id){
        return null;
    }

    @GetMapping("/products/low-stock")
    public ResponseEntity<Product> lowStock(){
        return null;
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
