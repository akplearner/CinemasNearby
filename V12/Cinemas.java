import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Cinemas extends Application {
	
	 private final String name;
	 private final TableView<MovieDetails> table = new TableView<>();
	 private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
     final HBox hb = new HBox();
            
     public Cinemas(String n) {
    	 name = n;
     }
 
     public void start(Stage stage) {
    	 Scene scene = new Scene(new Group());
    	 stage.setWidth(580);
    	 stage.setHeight(600);
    	
    	 TableColumn firstCol = new TableColumn("Cinemas");
    	 firstCol.setMinWidth(200);
    	 firstCol.setCellValueFactory( new PropertyValueFactory<>("cinemas"));
 
    	 TableColumn secondCol = new TableColumn("Show Time");
    	 secondCol.setMinWidth(300);
    	 secondCol.setCellValueFactory( new PropertyValueFactory<>("time"));
    	 ArrayList<String> movNames = new ArrayList<String>();
    	 LinkedList<String> timeList = new LinkedList<String>();
    	 try {
    		 Connection conn = getConnection();
    		 Statement st = conn.createStatement();
    		 ResultSet re = st.executeQuery("SELECT * FROM project4.cinemas");
    		
    		 String cinemas;
    		
    		 while(re.next()) {
    			 cinemas =re.getString("location"); 
				 if(name.equals(re.getString("title")) ) {	
					data.add(new MovieDetails(re.getString("location"),re.getString("time")));
				 }
    		 }
    		 }
    	 catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
			}
    	 table.setItems(data);
    	 table.getColumns().addAll(firstCol, secondCol);

        /*final Button addButton = new Button("Admin");
        addButton.setOnAction((ActionEvent e) -> {
           // data.add(new MovieDetails("Z","X"));
        
         });
        
       
 
        hb.getChildren().addAll(addButton);
        */
        hb.setSpacing(10);
        

        
 
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
   
   
    public static class MovieDetails {
    
        private final SimpleStringProperty cinemas;
        private final SimpleStringProperty time;
 
        private MovieDetails(String col1, String col2) {  
            this.cinemas = new SimpleStringProperty(col1);
            this.time = new SimpleStringProperty(col2);
        }
 
       
 
        public String getCinemas() {
            return cinemas.get();
        }
 
        public void setCinemas(String col2) {
        	cinemas.set(col2);
        }
        
        public String getTime() {
            return time.get();
        }
 
        public void setTime(String col3) {
        	time.set(col3);
        }
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