import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


//import com.mysql.jdbc.PreparedStatement;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Search extends Application {

	String name;
	 public static final ObservableList data = FXCollections.observableArrayList();
	 
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Location and the movie:");

       TextField text= new TextField();
       TextField text2= new TextField();
       text.setMaxWidth(100);
       text2.setMaxWidth(100);
       TextField text3= new TextField();
       text3.setMaxWidth(100);
       Label lb = new Label("   X:");
       Label lb2 = new Label("   Y:"); 
       Label lb3 =new Label("Radius");
       Button btn = new Button("Search");
       

       btn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
        	   
               try {
            		  Connection conn = getConnection();
         			  Statement st = conn.createStatement();
         			 ResultSet re = st.executeQuery("SELECT * FROM project4.location"); 
        			  
        			  String str;
         			  String[] cinemas = new String[10];
         			  int i=0;
	         		if(text.getText().isEmpty() && text2.getText().isEmpty()||text3.getText().isEmpty()) {
	         			
	         			Stage menuStage = new Stage();
	                    
	                    CinemasList cl=new CinemasList("all");
	                    cl.start(menuStage);
	                    menuStage.show();
	         		}
	         		else {
	         			Double a,b,c;
	         			a=Double.parseDouble(text.getText()); //x
	         			b=Double.parseDouble(text2.getText()); //y
	         			c=Double.parseDouble(text3.getText()); //radius
	         			while(re.next()) {
	         				str=re.getString("cinemas");
	         				double x=re.getInt("X");
	         				double y=re.getInt("Y");
	         			
	         				if(Math.sqrt((x-a)*(x-a)+(y-b)*(y-b))<=c ) {
	         					cinemas[i]=str;

	         					System.out.println("cinima "+ cinemas[i] );
	         					i++;
	         				}
	         				
	         				
	         				
	         		}
	         			
         			
                    Stage menuStage = new Stage();
                    
                    CinemasList cl=new CinemasList(cinemas);
                    cl.start(menuStage);
                    menuStage.show();
         		}
     			 
               }
     				  catch(Exception ex)
     				  {
     				  System.out.println("ERROR: " + ex.getMessage());
     				  }	
        	  
        		   
    
           }
		
           
       });
   
   

        VBox vBox = new VBox(lb,text,lb2,text2,lb3,text3,btn);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        

         Scene scene = new Scene(vBox, 500,500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

	
	
	
	   
	 public static Connection getConnection() {
		  try
		  {
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root", "MySQL");   
				
		 return conn;
		 }
		  catch(Exception ex)
		  {
		  System.out.println("ERROR: " + ex.getMessage());
		  }
		  return null;
	
	    }
		    
	public static void main(String[] args) {
		launch(args);
	}

}