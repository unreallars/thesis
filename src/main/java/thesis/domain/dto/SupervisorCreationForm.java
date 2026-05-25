package thesis.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@SuppressWarnings("unused")
public class SupervisorCreationForm {

  @NotNull(message = "Bitte gebe eine gültige GitHub ID ein")
  @Positive(message = "Bitte gebe eine gültige GitHub ID ein")
  private Integer userGithubId;

  public Integer getUserGithubId() {
    return userGithubId;
  }

  public void setUserGithubId(Integer userGithubId) {
    this.userGithubId = userGithubId;
  }
}
