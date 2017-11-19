package com.ifengxue.todolist.web.validator;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 非法字符验证器
 *
 * @author LiuKeFeng
 * @date 2017-09-30
 */
public class IllegalCharacterValidator implements
    ConstraintValidator<IllegalCharacter, CharSequence> {

  /**
   * 默认的非法字符
   */
  public static final Set<Character> DEFAULT_ILLEGAL_CHARACTERS = Sets
      .newHashSet('%', '\'', '+', ';', '<', '>', '"', '#', '$', '*', '|', '/', ':', '=');
  private Set<Character> characters;

  @Override
  public void initialize(IllegalCharacter constraintAnnotation) {
    characters = new HashSet<>(constraintAnnotation.characters().length);
    for (char ch : constraintAnnotation.characters()) {
      characters.add(ch);
    }
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    return isValid(value, characters);

  }

  /**
   * 非法字符检查
   * @param value 待检查字符
   * @param characters 非法字符表
   * @return true:没有非法字符;false:有非法字符
   */
  public static boolean isValid(CharSequence value, Set<Character> characters) {
    if (value == null || characters.isEmpty()) {
      return true;
    }
    String strValue = value.toString();
    if (strValue.isEmpty()) {
      return true;
    }
    for (char ch : strValue.toCharArray()) {
      if (characters.contains(ch)) {
        return false;
      }
    }
    return true;
  }
}