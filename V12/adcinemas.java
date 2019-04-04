//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.text.Font;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class adcinemas extends Application {
//	private final TableView<CinemaDetails> table = new TableView<>();
//	private final ObservableList<CinemaDetails> data = FXCollections.observableArrayList();
//    final HBox hb = new HBox();  
//    
//    @Override
//    public void start(Stage stage) {
//        Scene scene = new Scene(new Group());
//        stage.setWidth(900);
//        stage.setHeight(600);
//    //adding Columns Names
//        TableColumn cinemaNameCol = new TableColumn("cinemaName");
//        cinemaNameCol.setMinWidth(200);
//        cinemaNameCol.setCellValueFactory( new PropertyValueFactory<>("cinemaName"));
// 
//        TableColumn XCol = new TableColumn("X");
//        XCol.setMinWidth(200);
//        XCol.setCellValueFactory( new PropertyValueFactory<>("X"));
//
//        TableColumn YCol = new TableColumn("Y");
//        YCol.setMinWidth(200);
//        YCol.setCellValueFactory( new PropertyValueFactory<>("Y"));
//        
//        TableColumn TypeCol = new TableColumn("Type");
//        TypeCol.setMinWidth(200);
//        TypeCol.setCellValueFactory( new PropertyValueFactory<>("type"));
//        
//        try {
//			  Connection conn = getConnection();
//			  Statement st = conn.createStatement();
//			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
//			  while(re.next()) {
//				  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
//				  System.out.println(re.getString("cinemas")+"--"+re.getString("X")+"--"+re.getString("Y")+"--"+re.getString("type"));
//			  }
//		}catch(Exception ex){
//			System.out.println("ERROR: " + ex.getMessage()); 
//		}
//        
//        table.setItems(data);
//        table.getColumns().addAll(cinemaNameCol, XCol, YCol, TypeCol);
// 
//        TextField cinemaNameAdd = new TextField();
//    	TextField cinemaXAdd = new TextField();
//    	TextField cinemaYAdd = new TextField();
//    	TextField cinemaTypeAdd = new TextField();
//        
//        final Button addButton = new Button("Add");
//        addButton.setOnAction(new EventHandler<ActionEvent>() {
//        	
//        	 @Override
//             public void handle(ActionEvent e) {
//             	
//        	System.out.println("Add button press");
//
//        	if(!cinemaNameAdd.getText().isEmpty()) {
//        		if (cinemaNameAdd.getText().contains("")) {
//        			String tempCinemaName = cinemaNameAdd.getText();
//        			String tempXName = cinemaXAdd.getText();
//        			String tempYName = cinemaYAdd.getText();
//        			String tempCinemaType=cinemaTypeAdd.getText(); 
//        			
//    				System.out.println(tempCinemaName);
//        			//START OF CONNECTINO
//        			try {
//        				Connection conn = getConnection();
//        				System.out.println(tempCinemaName);
//        				String query = "INSERT INTO `project4`.`location` (`cinemas`, `X`, `Y`, `type`) VALUES (?, ?,?,?);";// "insert
//        																	
//        				PreparedStatement preparedStmt = conn.prepareStatement(query);
//        				preparedStmt.setString(1, tempCinemaName);
//        				preparedStmt.setString(2, tempXName);
//        				preparedStmt.setString(3, tempYName);
//        				preparedStmt.setString(4, tempCinemaType);
//        				preparedStmt.execute();
//        				System.out.println("Congrats, you have saved it as draft!");
//        			} catch (Exception ex) {
//        				System.out.println("ERROR: " + ex.getMessage());
//        			}
//        			//END OF CONNECTION	
//        			data.add(new CinemaDetails(tempCinemaName,tempXName,tempYName,tempCinemaType));
//
//        			data.clear();
//        			try {
//		    			  Connection conn = getConnection();
//		    			  Statement st = conn.createStatement();
//		    			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
//		    			  while(re.next()) {
//		    					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type"))); 
//		    			  }
//		    		}catch(Exception ex){
//		    			System.out.println("ERROR: " + ex.getMessage());
//		    		}
//        			System.out.println("Inserted: "+tempCinemaName+"--"+tempCinemaType);
//        		}
//        		else 
//        			System.out.println("Error: format cinema inputs");
//        	}
//        	else 
//        		System.out.println("Error add info in text");			
//        	}
//         }
//         );
//        
//        final Button del = new Button("Delete");
//        del.setOnAction(new EventHandler<ActionEvent>() {
//        	
//            @Override
//            public void handle(ActionEvent e) {
//            	System.out.println("Delete button selected: "+table.getSelectionModel().getSelectedIndex()+ "-"+
//            			table.getSelectionModel().getSelectedItem().getCinemaName()
//            			);
//            	
//            		String itemToRemove = table.getSelectionModel().getSelectedItem().getCinemaName();
//      
//            		String sql="DELETE FROM project4.location WHERE (cinemas = ?);";
//            		try 
//            		{
//            			Connection conn = getConnection();
//        				PreparedStatement stmt = conn.prepareStatement(sql);
//        				stmt.setString(1, itemToRemove);
//        				stmt.executeUpdate();
//        				
//        				System.out.println(itemToRemove+"-is deleted!!");
//          			}
//            		catch(Exception ex)
//          			{
//            			System.out.println("ERROR: " + ex.getMessage());
//          			}	
//            		
//            		data.clear();
//        			try {
//		    			  Connection conn = getConnection();
//		    			  Statement st = conn.createStatement();
//		    			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
//		    			  while(re.next()) {
//		    					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type"))); 
//		    			  }
//		    		}catch(Exception ex){
//		    			System.out.println("ERROR: " + ex.getMessage());
//		    		}
//      
//            	}
//        });
//        
//        
//        hb.getChildren().addAll(addButton);
//        hb.getChildren().addAll(del);
//        hb.getChildren().addAll(cinemaNameAdd);
//        hb.getChildren().addAll(cinemaXAdd);
//        hb.getChildren().addAll(cinemaYAdd);
//        hb.getChildren().addAll(cinemaTypeAdd);
//        hb.setSpacing(10);
//        
//        final Label label =  new Label("Filter by Type: ");
//        label.setFont(new Font("Arial", 18));
//  
//        final ComboBox<String> typeComboBox = new ComboBox();   
//        typeComboBox.getItems().addAll("ALL","Multiplex", "Odeon", "Mom & Pop Movies", "Indie Art Theaters");
//        typeComboBox.setValue("All");
//        
//        typeComboBox.valueProperty().addListener(
//        		new ChangeListener<String>() {
//		        	public void changed(ObservableValue ov, String t, String t1) {
//			            data.clear();
//			            try {
//			    			  Connection conn = getConnection();
//			    			  Statement st = conn.createStatement();
//			    			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
//			    			  while(re.next()) {
//			    				  if(t1.equals("ALL")) {
//			    					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
//			    				  }
//			    			  }
//			    		}catch(Exception ex){
//			    			System.out.println("ERROR: " + ex.getMessage());
//			    		}
//
//			            try {
//			            	Connection conn = getConnection();
//			            	Statement st = conn.createStatement();
//			  			  	ResultSet re = st.executeQuery("SELECT * FROM project4.location");
//			  				while(re.next()) {
//			  					if(t1.equals(re.getString("type"))) {
//			  						 data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
//			  					}
//			  				 }
//			  			}catch(Exception ex){
//			  				System.out.println("ERROR: " + ex.getMessage());
//			  			}
//			           
//		            } 
//        		}
//        );
//        
//        final VBox vbox = new VBox();
//        vbox.setSpacing(10);
//        vbox.setPadding(new Insets(30, 0, 0, 30));
//        vbox.getChildren().addAll(label,typeComboBox);
//        vbox.getChildren().addAll(table, hb);
// 
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);
// 
//        stage.setScene(scene);
//        stage.show();
//    }
// 
//    public static class CinemaDetails {
// 
//        //private final SimpleStringProperty firstName;
//    	private final SimpleStringProperty cinemaName;
//        private final SimpleStringProperty X;
//        private final SimpleStringProperty Y;
//        private final SimpleStringProperty type;
// 
//        private CinemaDetails(String col1, String col2, String col3, String col4) {
//            this.cinemaName = new SimpleStringProperty(col1);
//            this.X = new SimpleStringProperty(col2);
//            this.Y = new SimpleStringProperty(col3);
//            this.type = new SimpleStringProperty(col4);
//        }
// 
//        public String getCinemaName() {
//            return cinemaName.get();
//        }
// 
//        public void setCinemaName(String col1) {
//        	cinemaName.set(col1);
//        }
// 
//        public String getX() {
//            return X.get();
//        }
// 
//        public void setX(String col2) {
//        	X.set(col2);
//        }
//        
//        public String getY() {
//            return Y.get();
//        }
// 
//        public void setY(String col3) {
//        	Y.set(col3);
//        }
//        
//        public String getType() {
//            return type.get();
//        }
// 
//        public void setType(String col4) {
//        	type.set(col4);
//        }
//    }
//    public static Connection getConnection() {
//		  try
//		  {
//		 Class.forName("com.mysql.jdbc.Driver");
//		 Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root", "267731354Cb");   
//				 return conn;
//		 }
//		  catch(Exception ex)
//		  {
//		  System.out.println("ERROR: " + ex.getMessage());
//		  }
//		  return null;
//	    }
//    
//    public static void main(String[] args) {
//        launch(args);
//    }
//} 

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class adcinemas extends Application {
	private final TableView<CinemaDetails> table = new TableView<>();
	private final ObservableList<CinemaDetails> data = FXCollections.observableArrayList();
    final HBox hb = new HBox();  
    
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setWidth(860);
        stage.setHeight(600);
    //adding Columns Names
        TableColumn cinemaNameCol = new TableColumn("cinemaName");
        cinemaNameCol.setMinWidth(200);
        cinemaNameCol.setCellValueFactory( new PropertyValueFactory<>("cinemaName"));
 
        TableColumn XCol = new TableColumn("X");
        XCol.setMinWidth(200);
        XCol.setCellValueFactory( new PropertyValueFactory<>("X"));

        TableColumn YCol = new TableColumn("Y");
        YCol.setMinWidth(200);
        YCol.setCellValueFactory( new PropertyValueFactory<>("Y"));
        
        TableColumn TypeCol = new TableColumn("Type");
        TypeCol.setMinWidth(200);
        TypeCol.setCellValueFactory( new PropertyValueFactory<>("type"));
        
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
			  while(re.next()) {
				  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
				  System.out.println(re.getString("cinemas")+"--"+re.getString("X")+"--"+re.getString("Y")+"--"+re.getString("type"));
			  }
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}
        
        table.setRowFactory(
        listen->{
       	TableRow<CinemaDetails> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            	CinemaDetails rowData = row.getItem();
                System.out.println("Double click on: "+rowData.getCinemaName());
                Stage menuStage = new Stage();
                
    			adtime time = new adtime(rowData.getCinemaName());
                time.start(menuStage);
    			
    			//Cinemas cinema = new Cinemas(newValue.getMovieName());
                //cinema.start(menuStage);
                menuStage.show();
                
            }
        });
        return row;
        });
        table.setItems(data);
        table.getColumns().addAll(cinemaNameCol, XCol, YCol, TypeCol);
 
        TextField cinemaNameAdd = new TextField();
    	TextField cinemaXAdd = new TextField();
    	TextField cinemaYAdd = new TextField();
    	TextField cinemaTypeAdd = new TextField();
        
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	 @Override
             public void handle(ActionEvent e) {
             	
        	System.out.println("Add button press");

        	if(!cinemaNameAdd.getText().isEmpty()) {
        		if (cinemaNameAdd.getText().contains("")) {
        			String tempCinemaName = cinemaNameAdd.getText();
        			String tempXName = cinemaXAdd.getText();
        			String tempYName = cinemaYAdd.getText();
        			String tempCinemaType=cinemaTypeAdd.getText(); 
        			
    				System.out.println(tempCinemaName);
        			//START OF CONNECTINO
        			try {
        				Connection conn = getConnection();
        				System.out.println(tempCinemaName);
        				String query = "INSERT INTO `project4`.`location` (`cinemas`, `X`, `Y`, `type`) VALUES (?, ?,?,?);";// "insert
        																	
        				PreparedStatement preparedStmt = conn.prepareStatement(query);
        				preparedStmt.setString(1, tempCinemaName);
        				preparedStmt.setString(2, tempXName);
        				preparedStmt.setString(3, tempYName);
        				preparedStmt.setString(4, tempCinemaType);
        				preparedStmt.execute();
        				System.out.println("Congrats, you have saved it as draft!");
        			} catch (Exception ex) {
        				System.out.println("ERROR: " + ex.getMessage());
        			}
        			//END OF CONNECTION	
        			data.add(new CinemaDetails(tempCinemaName,tempXName,tempYName,tempCinemaType));

        			data.clear();
        			try {
		    			  Connection conn = getConnection();
		    			  Statement st = conn.createStatement();
		    			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
		    			  while(re.next()) {
		    					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type"))); 
		    			  }
		    		}catch(Exception ex){
		    			System.out.println("ERROR: " + ex.getMessage());
		    		}
        			System.out.println("Inserted: "+tempCinemaName+"--"+tempCinemaType);
        		}
        		else 
        			System.out.println("Error: format cinema inputs");
        	}
        	else 
        		System.out.println("Error add info in text");			
        	}
         }
         );
        
        final Button del = new Button("Delete");
        del.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {
            	System.out.println("Delete button selected: "+table.getSelectionModel().getSelectedIndex()+ "-"+
            			table.getSelectionModel().getSelectedItem().getCinemaName()
            			);
            	
            		String itemToRemove = table.getSelectionModel().getSelectedItem().getCinemaName();
      
            		String sql="DELETE FROM project4.location WHERE (cinemas = ?);";
            		try 
            		{
            			Connection conn = getConnection();
        				PreparedStatement stmt = conn.prepareStatement(sql);
        				stmt.setString(1, itemToRemove);
        				stmt.executeUpdate();
        				
        				System.out.println(itemToRemove+"-is deleted!!");
          			}
            		catch(Exception ex)
          			{
            			System.out.println("ERROR: " + ex.getMessage());
          			}	
            		
            		data.clear();
        			try {
		    			  Connection conn = getConnection();
		    			  Statement st = conn.createStatement();
		    			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
		    			  while(re.next()) {
		    					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type"))); 
		    			  }
		    		}catch(Exception ex){
		    			System.out.println("ERROR: " + ex.getMessage());
		    		}
      
            	}
        });
        
        
        hb.getChildren().addAll(addButton);
        hb.getChildren().addAll(del);
        hb.getChildren().addAll(cinemaNameAdd);
        hb.getChildren().addAll(cinemaXAdd);
        hb.getChildren().addAll(cinemaYAdd);
        hb.getChildren().addAll(cinemaTypeAdd);
        hb.setSpacing(10);
        
        final Label label =  new Label("Filter by Type: ");
        label.setFont(new Font("Arial", 18));
  
        final ComboBox<String> typeComboBox = new ComboBox();   
        typeComboBox.getItems().addAll("ALL","Multiplex", "Odeon", "Mom & Pop Movies", "Indie Art Theaters");
        typeComboBox.setValue("All");
        
        typeComboBox.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
			            data.clear();
			            try {
			    			  Connection conn = getConnection();
			    			  Statement st = conn.createStatement();
			    			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
			    			  while(re.next()) {
			    				  if(t1.equals("ALL")) {
			    					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
			    				  }
			    			  }
			    		}catch(Exception ex){
			    			System.out.println("ERROR: " + ex.getMessage());
			    		}

			            try {
			            	Connection conn = getConnection();
			            	Statement st = conn.createStatement();
			  			  	ResultSet re = st.executeQuery("SELECT * FROM project4.location");
			  				while(re.next()) {
			  					if(t1.equals(re.getString("type"))) {
			  						 data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
			  					}
			  				 }
			  			}catch(Exception ex){
			  				System.out.println("ERROR: " + ex.getMessage());
			  			}
			           
		            } 
        		}
        );
        
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(label,typeComboBox);
        vbox.getChildren().addAll(table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }

 
    public static class CinemaDetails {
    	private final SimpleStringProperty cinemaName;
        private final SimpleStringProperty X;
        private final SimpleStringProperty Y;
        private final SimpleStringProperty type;
        private CinemaDetails(String col1, String col2, String col3, String col4) {
            this.cinemaName = new SimpleStringProperty(col1);
            this.X = new SimpleStringProperty(col2);
            this.Y = new SimpleStringProperty(col3);
            this.type = new SimpleStringProperty(col4);
        }
        public String getCinemaName() {
            return cinemaName.get();
        }
        public void setCinemaName(String col1) {
        	cinemaName.set(col1);
        }
        public String getX() {
            return X.get();
        }
        public void setX(String col2) {
        	X.set(col2);
        }
        public String getY() {
            return Y.get();
        }
        public void setY(String col3) {
        	Y.set(col3);
        }
        public String getType() {
            return type.get();
        }
        public void setType(String col4) {
        	type.set(col4);
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