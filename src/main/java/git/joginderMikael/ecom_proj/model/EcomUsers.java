package git.joginderMikael.ecom_proj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcomUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE
    })
    @JoinTable(
            name = "ecom_users_roles",
            joinColumns = @JoinColumn(name = "ecom_users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_name")
    )
    private Set<Role> roles = new HashSet<>();
}
