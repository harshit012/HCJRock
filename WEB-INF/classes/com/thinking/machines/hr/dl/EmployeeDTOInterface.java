package com.thinking.machines.hr.dl;
import java.util.*;
import java.math.*;
public interface EmployeeDTOInterface extends java.io.Serializable,Comparable<EmployeeDTOInterface>
{
public final String EMPLOYEE_DATA_FILE="designation.data";
public enum GENDER{MALE,FEMALE};
public static final GENDER MALE=GENDER.MALE;
public static final GENDER FEMALE=GENDER.FEMALE;

public void setEmployeeId(String employeeId);
public String getEmployeeId();
public void setName(String name);
public String getName();
public void setDesignationCode(int designationCode);
public int getDesignationCode();
public void setDesignation(String designation);
public String getDesignation();
public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();
public void setBasicSalary(BigDecimal basicSalary);
public BigDecimal getBasicSalary();
public void isIndian(boolean isIndian);
public boolean isIndian();
public void setGender(GENDER gender);
public String getGender();
public void setPANNumber(String panNumber);
public String getPANNumber();
public void setAadharCardNumber(String aadharCardNumber);
public String getAadharCardNumber();
}
