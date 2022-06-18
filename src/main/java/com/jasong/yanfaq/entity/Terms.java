package com.jasong.yanfaq.entity;

import lombok.Data;

@Data
public class Terms {
   private Integer pageNum;
   private Integer pageSize;
   private String search;
   private String subject;
   private String role;
}
