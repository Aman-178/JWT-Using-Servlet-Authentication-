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
import static java.lang.System.out;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ACCESS
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    
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
    private final String SECRET_KEY = GenrateSecretKey.SecretKey();
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException , IOException{
        
        String Username=request.getParameter("username");
        String Password=request.getParameter("password");
        
       
            if(isvalid(Username,Password)){     
             String token=JWTUtil.generateToken(Username,SECRET_KEY);
             response.setContentType("application/json");
             response.getWriter().write("{\"accessToken\": \"" + token + "\"}");
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            
    }
   
    public boolean isvalid(String Username,String Password){
         String sql="select * from Authentication where username =? and  password =?";
            try{
                PreparedStatement pmt= con.prepareStatement(sql);
                pmt.setString(1, Username);
                pmt.setString(2, Password);
                ResultSet res=pmt.executeQuery();
                if(res.next()){
                    return true;
                }
            }catch(SQLException ex){
                System.out.println("Error: in statement");
            }
            return false;
        };
    
    
    
}
