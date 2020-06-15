/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibelajarpbo.model;

/**
 *
 * @author SWIFT 3
 */
public abstract class Users {
    protected String username;
    protected String password;

    abstract public String getUsername();
    abstract public void setUsername(String username);
    abstract public String getPassword();
    abstract public void setPassword(String password);
}
