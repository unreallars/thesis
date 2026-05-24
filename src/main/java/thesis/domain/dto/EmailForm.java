package thesis.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailForm {

  @Email(message = "Bitte eine gültige E-Mail-Adresse angeben")
  @NotBlank(message = "Bitte eine gültige E-Mail-Adresse angeben")
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
