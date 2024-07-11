/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACCESS
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet{
    
    private static final String Jdbcurl="jdbc:mysql://localhost:3306/User";
    private static final String Jdbcuser="root";
    private static final String Jdbcpassword="123@Root";
    
    private Connection con;
    @Override
    public void init(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(Jdbcurl, Jdbcuser, Jdbcpassword);
                if(con!=null){
                    System.out.println("Database connected Successfully");
                }else{
                    System.out.println("Database is null");
                }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    @Override
     public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException , IOException{ 
         String Username=request.getParameter("username");
         String Password=request.getParameter("password");
         
         String sql="Insert into Authentication(username,password)values(?,?)";
         
          
        try {
            PreparedStatement pmt = con.prepareStatement(sql);
            pmt.setString(1,Username);
            pmt.setString(2, Password);
            
            int rownumber=pmt.executeUpdate();
            if(rownumber>0){
                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
     }
}
