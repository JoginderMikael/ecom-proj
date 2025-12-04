package git.joginderMikael.ecom_proj.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private EcomUsers user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    private double total;

}
