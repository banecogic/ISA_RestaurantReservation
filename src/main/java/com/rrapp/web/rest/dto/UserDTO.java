package com.rrapp.web.rest.dto;

import com.rrapp.domain.Authority;
import com.rrapp.domain.Friendrequest;
import com.rrapp.domain.User;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;
    private Set<String> friends;
    private Set<User> pendingRequestsFrom;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getLogin(), null, user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getActivated(), user.getLangKey(),
                user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()),
                user.getFriends2().stream().map(User::getLogin).collect(Collectors.toSet()),
                user.getPendingRequests().stream().map(Friendrequest::getRequester).collect(Collectors.toSet()));
    	/*Set<String> prijatelji = new HashSet<String>();
    	for(User prijatelj : user.getFriends2()){
    		prijatelji.add(prijatelj.getLogin());
    	}*/
    }

    public UserDTO(String login, String password, String firstName, String lastName,
        String email, boolean activated, String langKey, Set<String> authorities, Set<String> friends, Set<User> pendingRequestsFrom) {

        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
        this.friends = friends;
        this.pendingRequestsFrom = pendingRequestsFrom;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
    public Set<String> getFriends() {
        return friends;
    }
    public Set<User> getPendingRequests() {
		return pendingRequestsFrom;
	}

	@Override
    public String toString() {
		String pendingRequestsUsersLogins = "";
		for(User user : pendingRequestsFrom){
			pendingRequestsUsersLogins += (user.getLogin() + ", ");
		}
		if(pendingRequestsUsersLogins.length()!=0)
			pendingRequestsUsersLogins.substring(0, pendingRequestsUsersLogins.length()-2);
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            ", friends=" + friends +
            ", pendingRequests=" + pendingRequestsUsersLogins +
            "}";
    }
}
