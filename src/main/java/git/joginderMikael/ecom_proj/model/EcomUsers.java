package git.joginderMikael.ecom_proj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private Integer id;
    private String username;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
}
