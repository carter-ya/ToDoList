package com.ifengxue.todolist.web.validator;

import com.ifengxue.todolist.util.ValidatorUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 账号验证器
 *
 * @author LiuKeFeng
 * @date 2017-10-12
 */
public class AccountValidator implements ConstraintValidator<Account, CharSequence> {
  private Account account;
  @Override
  public void initialize(Account constraintAnnotation) {
    this.account = constraintAnnotation;
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    String accountStr = value.toString();
    if (account.phoneOrEmail()) {
      return ValidatorUtil.isPhone(accountStr) || ValidatorUtil.isEmail(accountStr);
    }
    if (account.phone()) {
      return ValidatorUtil.isPhone(accountStr);
    }
    return ValidatorUtil.isEmail(accountStr);
  }
}