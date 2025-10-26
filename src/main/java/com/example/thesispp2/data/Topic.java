package com.example.thesispp2.data;

import java.util.List;

public class Topic {
  private String title;
  private String description;
  private List<SpecialField> specialFields;
  private List<Requirement> requirements;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
