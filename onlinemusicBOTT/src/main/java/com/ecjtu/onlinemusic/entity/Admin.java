package com.ecjtu.onlinemusic.entity;

import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Admin implements Serializable {
  @NotNull(message = "管理员id不能为空。",groups = {update.class})
  private Integer id;
  @NotNull(message = "管理员邮箱mail不能为空。",groups = {insert.class})  // 前端数据验证。   @Validated分组验证。
  @Email(message = "管理员邮箱mail有误。", groups = {insert.class})
  private String mail;
  private String name;
  @NotNull(message = "管理员密码password不能为空。")  // // 前端数据验证。   @Validated分组验证。
  private String password;
}
