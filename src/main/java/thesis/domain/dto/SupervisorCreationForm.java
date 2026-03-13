package thesis.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SupervisorCreationForm {
  @NotNull(message = "Bitte gebe eine gültige Github-Id ein")
  @Positive(message = "Bitte gebe eine gültige Github-Id ein")
  private Integer userGithubId;

  public Integer getUserGithubId() {
    return userGithubId;
  }

  public void setUserGithubId(Integer userGithubId) {
    this.userGithubId = userGithubId;
  }
}
