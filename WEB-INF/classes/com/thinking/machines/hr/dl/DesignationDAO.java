package com.thinking.machines.hr.dl;
import java.sql.*;
import java.util.*;
public class DesignationDAO
{
public void add(DesignationDTO designationDTO) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
String vTitle=designationDTO.getTitle().trim();
System.out.println(vTitle);
if(vTitle.length()==0) throw new DAOException("Invalid designation");
preparedStatement=connection.prepareStatement("select title from designation where title=?");
preparedStatement.setString(1,vTitle);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values (?)");
preparedStatement.setString(1,vTitle);
preparedStatement.executeUpdate();
preparedStatement.close();

preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,vTitle);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
designationDTO.setCode(resultSet.getInt("code"));
resultSet.close();
preparedStatement.close();
connection.close();
}
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public void update(DesignationDTO designationDTO) throws DAOException
{
try
{
String vTitle=designationDTO.getTitle();
int vCode=designationDTO.getCode();
if(vCode<=0) throw new DAOException("Invalid designation code:"+vCode);
if(vTitle.length()==0) throw new DAOException("Invalid designation:"+vTitle);
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,vCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
vCode=resultSet.getInt("code");
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code:"+vCode);
}
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("select * from designation where title=? and code!=?");
preparedStatement.setString(1,vTitle);
preparedStatement.setInt(2,vCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,vTitle);
preparedStatement.setInt(2,vCode);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public void deleteByCode(int code) throws DAOException
{
try
{
String designationTitle=null;
int vCode;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
if(code<=0) throw new DAOException("Invalid designation code:"+code);
preparedStatement=connection.prepareStatement("select title from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code:"+code);
}
designationTitle=resultSet.getString("title");
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
preparedStatement.close();
connection.close();
throw new DAOException("Designation:"+designationTitle+" is alloted to some employee");
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
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
resultSet=statement.executeQuery("select count(*) as cnt  from designation");
resultSet.next();
count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return count;
}

public List<DesignationDTO> getAll() throws DAOException
{
List<DesignationDTO> list=new LinkedList<>();
try
{
DesignationDTO designationDTO;
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation");
while(resultSet.next())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
list.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return list;
}


public DesignationDTO getByCode(int code) throws DAOException
{
DesignationDTO designationDTO=new DesignationDTO();
try
{
int vCode;
String vTitle;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
if(code<=0) throw new DAOException("Invalid designation code");
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation code not exists");
}
vCode=resultSet.getInt("code");
vTitle=resultSet.getString("title");
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return designationDTO;
}






public DesignationDTO getByTitle(String title) throws DAOException
{
DesignationDTO designationDTO=new DesignationDTO();
try
{
String vTitle;
int vCode;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
if(title.length()==0) throw new DAOException("Invalid designation");
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation not exists:"+title);
}
vCode=resultSet.getInt("code");
vTitle=resultSet.getString("title");
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return designationDTO;
}




public boolean codeExists(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
if(code<=0) throw new DAOException("Invalid designation code");
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,code);
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
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return false;
}
public boolean titleExists(String title) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
if(title.length()==0) throw new DAOException("Invalid designation");
preparedStatement=connection.prepareStatement("select title from designation where title=?");
preparedStatement.setString(1,title);
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
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return false;
}
}