package com.thinking.machines.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
public class ServletThree extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception ee)
{
//do nothing
}
}

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
BufferedReader br=request.getReader();
String d;
StringBuffer b=new StringBuffer();
while(true)
{
d=br.readLine();
if(d==null) break;
b.append(d);
}

String rawData=b.toString();
System.out.println("Request arrived i.e. :"+rawData);
Gson gson=new Gson();
Customer c=gson.fromJson(rawData,Customer.class);

PrintWriter pw=response.getWriter();
response.setContentType("application/json");
pw.print(gson.toJson(c));
pw.flush();
}catch(Exception exception)
{
System.out.println(exception);
try
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception ee)
{
// do nothing
}
}
}
}