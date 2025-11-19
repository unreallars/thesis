package com.example.thesispp2.data;

import java.util.List;

public class Topic {
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<SpecialField> getSpecialFields() {
    return specialFields;
  }

  public void setSpecialFields(List<SpecialField> specialFields) {
    this.specialFields = specialFields;
  }

  public List<Requirement> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<Requirement> requirements) {
    this.requirements = requirements;
  }

  private Integer id;
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
