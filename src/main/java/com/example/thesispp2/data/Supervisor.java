package com.example.thesispp2.data;

import java.util.List;

public class Supervisor {
  private int id;
  private String name;
  private String mail;

  public List<SpecialField> getSpecialFields() {
    return specialFields;
  }

  public void setSpecialFields(List<SpecialField> specialFields) {
    this.specialFields = specialFields;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<File> getFiles() {
    return files;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public List<Topic> getTopics() {
    return topics;
  }

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }

  private List<SpecialField> specialFields;
  private List<File> files;
  private List<Link> links;
  private List<Topic> topics;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }
}
