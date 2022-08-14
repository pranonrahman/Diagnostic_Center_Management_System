package net.therap.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.therap.entity.Role;

import java.io.Serializable;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private Role role;
}
