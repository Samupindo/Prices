package com.develop.prices.to;

import com.develop.prices.validation.PatchPutNotBlank;
import com.develop.prices.validation.PatchPutNotNull;

public class CreateCustomerTo {

        @PatchPutNotBlank
        private String name;
        @PatchPutNotNull
        private Integer phone;
        @PatchPutNotBlank
        private String email;

        public CreateCustomerTo() {
        }

        public CreateCustomerTo(String name, Integer phone, String email) {
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
