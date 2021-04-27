package com.thinking.machines.hr.dl;
import java.sql.*;

public class AdministratorDAO
{
public AdministratorDTO getByUsername(String username) throws DAOException
{
AdministratorDTO administrator=null;
try
{
Class.forName("com.mysql.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
if(username.length()==0) 
{
connection.close();
throw new DAOException("Invalid username");
}
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select* from administrator where uname=?");
preparedStatement.setString(1,username);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid username:"+username);
}
resultSet.getString("uname");
String password=resultSet.getString("pwd").trim();
connection.close();
administrator=new AdministratorDTO();
administrator.setUsername(username);
administrator.setPassword(password);
}
catch(Throwable e)
{
throw new DAOException(e.getMessage());
}
return administrator;
}



}