package com.thinking.machines.hr.dl;
import java.sql.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO)   throws DAOException
{
try
{
String vName=employeeDTO.getName().trim();
int vDesignationCode=employeeDTO.getDesignationCode();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String vDateOfBirth=sdf.format(employeeDTO.getDateOfBirth());
Double vSalary=Double.parseDouble(employeeDTO.getBasicSalary().toPlainString());
boolean isIndian=employeeDTO.isIndian();
String vIsIndian;
if(isIndian) vIsIndian="Yes";
else vIsIndian="No";
String vGender=employeeDTO.getGender().substring(0,1);
String vPanNumber=employeeDTO.getPANNumber().trim();
String vAadharCardNumber=employeeDTO.getAadharCardNumber().trim();

boolean designationCodeExists=new DesignationDAO().codeExists(employeeDTO.getDesignationCode());
if(designationCodeExists==false) throw new DAOException("Designation Code "+vDesignationCode+" Not Exists");

//***********************************jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,vPanNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number:"+vPanNumber+"   exists");
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,vAadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card Number:"+vAadharCardNumber+"   exists");
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,vDesignationCode);
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation");
}
preparedStatement.close();
resultSet.close();


preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_number,aadhar_card_number) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,vName);
preparedStatement.setInt(2,vDesignationCode);
preparedStatement.setDate(3,new java.sql.Date(employeeDTO.getDateOfBirth().getTime()));
preparedStatement.setDouble(4,vSalary);
preparedStatement.setString(5,vGender);
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,vPanNumber);
preparedStatement.setString(8,vAadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int newCode=resultSet.getInt(1);
employeeDTO.setEmployeeId("EMP"+String.valueOf(newCode+100000));

resultSet.close();
preparedStatement.close();
connection.close();
//***********************************jdbc ends starts*********************************************
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
int givenCode;
boolean vCodeExists=new DesignationDAO().codeExists(employeeDTO.getDesignationCode());
if(!vCodeExists) throw new DAOException("Designation code:"+employeeDTO.getDesignationCode()+" not exists");
String vEmployeeId=employeeDTO.getEmployeeId().trim();
try
{
if(vEmployeeId.length()>10)
{
throw new DAOException("Invalid employee id:"+vEmployeeId);
}
try
{
givenCode=Integer.parseInt(vEmployeeId.substring(3));
}
catch(NumberFormatException numberFormatException)
{
throw new DAOException("Invalid employee id:"+vEmployeeId);
}
if(givenCode<100000)
{
throw new DAOException("Invalid employeeId:"+vEmployeeId);
}
String vName=employeeDTO.getName().trim();
int finalEmployeeId=givenCode-100000;
int vDesignationCode=employeeDTO.getDesignationCode();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String vDateOfBirth=sdf.format(employeeDTO.getDateOfBirth());
double vSalary=Double.parseDouble(employeeDTO.getBasicSalary().toPlainString());
boolean isIndian=employeeDTO.isIndian();
String vIsIndian;
if(isIndian) vIsIndian="Yes";
else vIsIndian="No";

String vGender=employeeDTO.getGender().substring(0,1);

String vPANNumber=employeeDTO.getPANNumber().trim();
String vAadharCardNumber=employeeDTO.getAadharCardNumber().trim();


//***********************************jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,finalEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id"+vEmployeeId);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select * from employee where pan_number=? and employee_id!=?");
preparedStatement.setString(1,vPANNumber);
preparedStatement.setInt(2,finalEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number:"+vPANNumber+"   exists");
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,vDesignationCode);
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
System.out.println("@@ designation code:"+vDesignationCode);
throw new DAOException("Invalid designation");
}
preparedStatement.close();
resultSet.close();


preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=? and employee_id!=?");
preparedStatement.setString(1,vAadharCardNumber);
preparedStatement.setInt(2,finalEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card number:"+vAadharCardNumber+"   exists");
}
preparedStatement.close();
resultSet.close();


preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,vName);
preparedStatement.setInt(2,vDesignationCode);
preparedStatement.setDate(3,new java.sql.Date(employeeDTO.getDateOfBirth().getTime()));
preparedStatement.setDouble(4,vSalary);
preparedStatement.setString(5,vGender);
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,vPANNumber);
preparedStatement.setString(8,vAadharCardNumber);
preparedStatement.setInt(9,finalEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();

//***********************************jdbc ends starts*********************************************


}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}



public void delete(String employeeId) throws DAOException
{
try
{
int givenCode=0;
try
{
givenCode=Integer.parseInt(employeeId.substring(3));
}
catch(NumberFormatException numberFormatException)
{
throw new DAOException("Invalid employee id:"+employeeId);
}
if(givenCode<=100000)
{
throw new DAOException("Invalid employeeId:"+employeeId);
}

int finalEmployeeId=givenCode-100000;

//***********************************jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,finalEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id"+employeeId);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,finalEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();

//***********************************jdbc ends starts*********************************************

}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public int getCount() throws DAOException
{
int count=0;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as cnt  from employee");
resultSet.next();
count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return count;
}



public List<EmployeeDTO> getAll() throws DAOException
{
List<EmployeeDTO> list=new LinkedList<EmployeeDTO>();
EmployeeDTO employeeDTO;
try
{
String fEmployeeID;

//***********************************write jdbc code ends*********************************************

Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from employee");
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
fEmployeeID="EMP"+String.valueOf(resultSet.getInt("employee_id")+100000);

employeeDTO.setEmployeeId(fEmployeeID);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(new java.util.Date((resultSet.getDate("date_of_birth")).getTime()));
employeeDTO.setBasicSalary(new BigDecimal(String.valueOf(resultSet.getDouble("basic_salary"))));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
list.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
//***********************************write jdbc code ends*********************************************
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return list;
}

public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
try
{
String fEmployeeID;
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,Integer.parseInt(employeeId.substring(3))-100000);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id:"+employeeId);
}

employeeDTO=new EmployeeDTO();
fEmployeeID="EMP"+String.valueOf(resultSet.getInt("employee_id")+100000);
employeeDTO.setEmployeeId(fEmployeeID);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(new java.util.Date((resultSet.getDate("date_of_birth")).getTime()));
employeeDTO.setBasicSalary(new BigDecimal(String.valueOf(resultSet.getDouble("basic_salary"))));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));


resultSet.close();
preparedStatement.close();
connection.close();
//***********************************write jdbc code ends********************************************
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}


public EmployeeDTOInterface getByPANNumber(String panNumber)  throws DAOException
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
try
{
String fEmployeeID;
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN number:"+panNumber);
}

employeeDTO=new EmployeeDTO();
fEmployeeID="EMP"+String.valueOf(resultSet.getInt("employee_id")+100000);
employeeDTO.setEmployeeId(fEmployeeID);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(new java.util.Date((resultSet.getDate("date_of_birth")).getTime()));
employeeDTO.setBasicSalary(new BigDecimal(String.valueOf(resultSet.getDouble("basic_salary"))));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));


resultSet.close();
preparedStatement.close();
connection.close();
//***********************************write jdbc code ends********************************************

}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}



public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
try
{
String fEmployeeID;
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar Card number:"+aadharCardNumber);
}

employeeDTO=new EmployeeDTO();
fEmployeeID="EMP"+String.valueOf(resultSet.getInt("employee_id")+100000);
employeeDTO.setEmployeeId(fEmployeeID);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(new java.util.Date((resultSet.getDate("date_of_birth")).getTime()));
employeeDTO.setBasicSalary(new BigDecimal(String.valueOf(resultSet.getDouble("basic_salary"))));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));


resultSet.close();
preparedStatement.close();
connection.close();
//***********************************write jdbc code ends********************************************

}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}


public boolean designationCodeExists(int designationCode) throws DAOException
{
try
{
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}

resultSet.close();
preparedStatement.close();
connection.close();
return false;
//***********************************write jdbc code ends********************************************
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public boolean employeeIdExists(String employeeId) throws DAOException
{
try
{
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,Integer.parseInt(employeeId.substring(3))-100000);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}

resultSet.close();
preparedStatement.close();
connection.close();
return false;
//***********************************write jdbc code ends********************************************
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}




public boolean panNumberExists(String panNumber) throws DAOException
{
try
{
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}

resultSet.close();
preparedStatement.close();
connection.close();
return false;
//***********************************write jdbc code ends********************************************
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
try
{
//***********************************write jdbc code starts*********************************************
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}

resultSet.close();
preparedStatement.close();
connection.close();
return false;
//***********************************write jdbc code ends********************************************
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}
