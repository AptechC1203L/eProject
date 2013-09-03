/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author chin
 */
@Data
public class User implements Serializable {
    final private String userId;
    final private String name;
}
