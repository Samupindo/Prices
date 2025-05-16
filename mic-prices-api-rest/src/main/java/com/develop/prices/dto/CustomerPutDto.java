package com.develop.prices.dto;

import com.develop.prices.validation.PhoneNotNull;
import com.develop.prices.validation.StringNotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public class CustomerPutDto implements Serializable {

  @Size(max = 100, message = "The name can only have 100 characteres")
  @Pattern(regexp = "\\p{L}[\\p{L}\\s]+", message = "The name can only have letters and spaces")
  @StringNotBlank
  private String name;

  @PhoneNotNull private Integer phone;

  @StringNotBlank private String email;

  public CustomerPutDto() {}

  public CustomerPutDto(String name, Integer phone, String email) {
    this.name = name;
    this.phone = phone;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
