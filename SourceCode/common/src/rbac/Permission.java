/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author chin
 */
@Data
public class Permission implements Serializable {
    final private String action;
    final private String target;
}
