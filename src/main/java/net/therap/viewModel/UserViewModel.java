package net.therap.viewModel;

import net.therap.model.Role;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
public class UserViewModel {

    private String userName;
    private String password;
    private Role role;

    public UserViewModel() {
    }

    public UserViewModel(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
