
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
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


//START OF CONNECTINO
/*
try {
	Connection conn = getConnection();
	System.out.println(temp);
	String query = "INSERT INTO 'project4'.'movies' ('title','rating') VALUES (?,?)";// "insert
														
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	preparedStmt.setString(1, temp);
	preparedStmt.setString(2, "R");
	preparedStmt.execute();
	System.out.println("Congrats, you have saved it as draft!");
} catch (Exception ex) {
	System.out.println("ERROR: " + ex.getMessage());
}
*/
//END OF CONNECTION
public class Admin extends Application {

	
	
	
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin");

       TextField text= new TextField();
    
       text.setMaxWidth(200);
       
       Label lb = new Label("Please enter password!");
  
    
       Button btn = new Button("Enter");
       
       
       btn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
        	   
        	   if(text.getText().equals("admin")) {
               	Stage menuStage = new Stage();
               	
               	admovie moviedb = new admovie();
               	moviedb.start(menuStage);
               	menuStage.show();
               	primaryStage.close();
               }
        	   else {
        		   Alert alert = new Alert(AlertType.INFORMATION);
        		   alert.setHeaderText(null);
        		   alert.setContentText("Wrong password!");
        		   alert.showAndWait();
        	   }
        		   
    
           }
		
       });
   
   

        VBox vBox = new VBox(lb,text,btn);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
      

        Scene scene = new Scene(vBox, 260, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
		       
	   
		 
		    
	public static void main(String[] args) {
		launch(args);
	}

		 

	
   
}