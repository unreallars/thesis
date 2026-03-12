package thesis.service.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import thesis.domain.User;
import thesis.service.SupervisorService;
import thesis.service.UserService;

@Component
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();
  private final SupervisorService supervisorService;
  private final UserService userService;

  public AppUserService(SupervisorService supervisorService, UserService userService) {
    this.supervisorService = supervisorService;
    this.userService = userService;
  }

  @Value("${app.admins}")
  private Set<Integer> admins;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User originalUser = defaultService.loadUser(userRequest);
    Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
    authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
    Integer id = originalUser.getAttribute("id");
    String username = originalUser.getAttribute("login");
    if (supervisorService.findByGithubId(id) != null) {
      authorities.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
    }
    if (admins.contains(id)) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    if (userService.findByGithubId(id) == null) {
      userService.save(new User(id, username));
    }
    return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
  }
}
