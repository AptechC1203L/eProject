package rbac;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 * @author chin
 */
@Data
public class Role implements Serializable {
    private List<Permission> permissions;
}
