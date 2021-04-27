package com.thinking.machines.hr.dl;
import java.io.*;
public class AdministratorDTO implements java.io.Serializable,Comparable<AdministratorDTO>
{
private String username;
private String password;
public AdministratorDTO()
{
username="";
password="";
}

public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}

public boolean equals(Object other)
{
if(!(other instanceof AdministratorDTO)) return false;
AdministratorDTO  administratorDTO;
administratorDTO=(AdministratorDTO)other;
return this.username.equals(administratorDTO.username); 
}
public int compareTo(AdministratorDTO other)
{
return this.username.compareToIgnoreCase(other.username);
}
public int hashCode()
{
return username.hashCode();
}

}