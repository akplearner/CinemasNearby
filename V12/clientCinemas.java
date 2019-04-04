import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
public class clientCinemas extends Application {
	 private final String name;
	 public final TableView<Details> table = new TableView<>();
	 public final ObservableList<Details> data2= FXCollections.observableArrayList();
    public final ListView<Details> table2 = new ListView<>(); 
    public final ObservableList data= FXCollections.observableArrayList();
    
    public clientCinemas(String n) {
    	 name = n;
     }
     public void start(Stage stage) {
    	 Scene scene = new Scene(new Group());
    	 stage.setWidth(660);
    	 stage.setHeight(500);
    	
    	   TableColumn firstCol = new TableColumn("Cinema Name");
           firstCol.setMinWidth(200);
           firstCol.setCellValueFactory( new PropertyValueFactory<>("title"));
    
           TableColumn secondCol = new TableColumn("time");
           secondCol.setMinWidth(200);
           secondCol.setCellValueFactory( new PropertyValueFactory<>("time"));
           data.add("Cinema Name" +"\t\t"+ "times");
    	 try {
    		 Connection conn = getConnection();
			 Statement st = conn.createStatement();
			 ResultSet re = st.executeQuery("SELECT * FROM project4.cinemas");
			 //String concastTime="";
			
			 String cinemas;
			  while(re.next()) {
				  cinemas= re.getString("location");
				  if(name.equals(cinemas)) {
					System.out.println(re.getString("title"));
						// data2.add(new Details(re.getString("title"),re.getString("time")));
						 data.add((re.getString("title") + "\t\t\t"+ re.getString("time")));
			  	}
				  }
			  }
    	 catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}
    	
 
    	 //table2.setEditable(true);
    	 table2.setEditable(true);
    	 table2.setPrefWidth(500);
    	 table2.setPrefHeight(300);
    	 //table.getColumns().addAll(firstCol, secondCol);
    	 //table2.setItems(data);
    	 table2.setItems(data);
    	 
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(table2);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }

     public static class Details {
    
        private final SimpleStringProperty title;
        private final SimpleStringProperty time;
         
 
        private Details(String column1, String col) {
            this.title =  new SimpleStringProperty(column1);
            this.time = new SimpleStringProperty(col);
        }
 
        public String gettitle() {
            return title.get();
        }
 
        public void settitle(String column1) {
        	title.set(column1);
        }
        
        public String getTime() {
            return time.get();
        }
 
        public void setTime(String col) {
        	time.set(col);
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
    
  
} 