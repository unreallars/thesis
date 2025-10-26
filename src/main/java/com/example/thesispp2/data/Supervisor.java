package com.example.thesispp2.data;

import java.util.List;

public class Supervisor {
  private long id;
  private String name;
  private String mail;
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
