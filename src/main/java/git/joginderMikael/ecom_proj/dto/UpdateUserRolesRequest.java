package git.joginderMikael.ecom_proj.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRolesRequest {
    private Set<String> roles;
}
