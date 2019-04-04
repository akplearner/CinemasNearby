import javafx.scene.control.Label;
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
public class adtime extends Application {
	private final String name;
	private final TableView<MovieDetails> table = new TableView<>();
	private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
	final HBox hb = new HBox();
            
	public adtime(String n) {
    	 name = n;
    	 System.out.println(name);
     }
    public void start(Stage stage) {
    	Scene scene = new Scene(new Group());
        stage.setWidth(900);
        stage.setHeight(600);

        TableColumn titleCol = new TableColumn("Cinemas");
        titleCol.setMinWidth(200);
        titleCol.setCellValueFactory( new PropertyValueFactory<>("cinemas"));
 
        TableColumn timeCol = new TableColumn("Show Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory( new PropertyValueFactory<>("time"));
        
        TableColumn locationCol = new TableColumn("location");
        locationCol.setMinWidth(300);
        locationCol.setCellValueFactory( new PropertyValueFactory<>("location"));

        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.cinemas");
			  String concastTime="";
			  String cinemas;
			 
			  while(re.next()) {
				  cinemas =re.getString("location");
				  
				 if(name.equals(cinemas)) {	
						 data.add(new MovieDetails(re.getString("title"),re.getString("time"),re.getString("location")));
						 System.out.println(re.getString("title") + re.getString("time") + re.getString("location"));
				 }
			  }
	  
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}
        
        table.setRowFactory(
                listen->{
               	TableRow<MovieDetails> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    	MovieDetails rowData = row.getItem();
                        System.out.println("Double click on: "+rowData.getCinemas());
                        Stage menuStage = new Stage();
                        
            			admovie movie = new admovie(rowData.getCinemas());
                        movie.start(menuStage);
            			
            			//Cinemas cinema = new Cinemas(newValue.getMovieName());
                        //cinema.start(menuStage);
                        menuStage.show();
                        
                    }
                });
                return row;
                });
        table.setItems(data);
        table.getColumns().addAll(titleCol, timeCol,locationCol);
        
        TextField titleAdd = new TextField();
    	TextField timeAdd = new TextField();
    	TextField locationAdd = new TextField();
    	final Label timeLabel =  new Label("Select Time: ");
    	final Label cinemaLabel =  new Label("Select Cinema: ");
    	final Label movieLabel =  new Label("Select Movie: ");
    	timeLabel.setFont(new Font("Arial", 14));
    	cinemaLabel.setFont(new Font("Arial", 14));
    	movieLabel.setFont(new Font("Arial", 14));
    	final ComboBox<String> timeComboBox = new ComboBox();   
    	
    	timeComboBox.getItems().addAll(
    			"00:00am","01:00am","02:00am","03:00am","04:00am","05:00am","06:00am","07:00am","08:00am","09:00am","010:00am","11:00am"
    			,"12:00pm","1:00pm","2:00pm","3:00pm","5:00pm","6:00pm","7:00pm","8:00pm","9:00pm","10:00pm","11:00pm"
    			);
    	
    	
    	String tempTimeName;
    	timeComboBox.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
		        		System.out.println("time Selected:"+t1);
		        		timeAdd.setText(t1);
		        	}
        });
 
        final Button addButton = new Button("Insert");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		System.out.println("Add button press");
        		data.clear();
        		if(!titleAdd.getText().isEmpty()) {
        			if (titleAdd.getText().contains("")) {
        				String tempTitleName = titleAdd.getText();
        				String tempTimeName = timeAdd.getText();
        				String tempLocName = locationAdd.getText();
        				System.out.println(tempTitleName);
        				try {
        					Connection conn = getConnection();
        					System.out.println(tempTitleName);
        					String query = "INSERT INTO `project4`.`cinemas` (`title`, `time`, `location`) VALUES (?,?,?);";// "insert
        					PreparedStatement preparedStmt = conn.prepareStatement(query);
        					preparedStmt.setString(1, tempTitleName);
        					preparedStmt.setString(2, tempTimeName);
        					preparedStmt.setString(3, tempLocName);
        					preparedStmt.execute();
        					System.out.println("Congrats, you have saved it as draft!");
        					}
        				catch (Exception ex) {
        					System.out.println("ERROR: " + ex.getMessage());
        				}
        				data.add(new MovieDetails(tempTitleName,tempTimeName,tempLocName));

        				try {
        					data.clear();
        					Connection conn = getConnection();
        					Statement st = conn.createStatement();
        					ResultSet re = st.executeQuery("SELECT * FROM project4.cinemas");
        					String cinemas;
		    				 
        					while(re.next()) {
        						cinemas =re.getString("location");
		    				  
        						if(name.equals(cinemas)) {	
		    						 data.add(new MovieDetails(re.getString("title"),re.getString("time"),re.getString("location")));
		    						 }
        						}
        					}
        				catch(Exception ex){
        					System.out.println("ERROR: " + ex.getMessage());
        				}
        				System.out.println("Inserted: "+tempTitleName+"--"+tempTimeName+"--"+tempLocName);
        			}
        			else 
        				System.out.println("Error: format cinema inputs");
        		}
        		else 
        			System.out.println("Error add info in text");			
        	}
        
        });
        final Button del = new Button("Delete");
        
        del.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		System.out.println("Delete button selected: "+table.getSelectionModel().getSelectedIndex()+ "-"+ table.getSelectionModel().getSelectedItem().getCinemas());	
            		String titleToRemove = table.getSelectionModel().getSelectedItem().getCinemas();
            		String timeToRemove = table.getSelectionModel().getSelectedItem().getTime();
            		String locationToRemove = table.getSelectionModel().getSelectedItem().getLocation();
            		String sql="DELETE FROM project4.cinemas WHERE (location = ?) and (time = ?);";
            		try 
            		{
            			Connection conn = getConnection();
        				PreparedStatement stmt = conn.prepareStatement(sql);
        				stmt.setString(1, locationToRemove);
        				stmt.setString(2, timeToRemove);
        				stmt.executeUpdate();
        				
        				System.out.println(titleToRemove+"--"+timeToRemove+"--"+locationToRemove+": is deleted!!");
          			}
            		catch(Exception ex)
          			{
            			System.out.println("ERROR: " + ex.getMessage());
          			}	
            		

            		try {
           				data.clear();
    		    			  Connection conn = getConnection();
    		    			  Statement st = conn.createStatement();
    		    			  ResultSet re = st.executeQuery("SELECT * FROM project4.cinemas");
    		    			  String cinemas;
    		    				 
    		    			  while(re.next()) {
    		    				  cinemas =re.getString("location");
    		    				  
    		    				 if(name.equals(cinemas)) {	
    		    						 data.add(new MovieDetails(re.getString("title"),re.getString("time"),re.getString("location")));
    		    						 }
    		    				 }	
    		    			  }
            			catch(Exception ex){
    		    			System.out.println("ERROR: " + ex.getMessage());
    		    			}
            		}
        	});
        
        hb.getChildren().addAll(movieLabel,titleAdd);
        hb.getChildren().addAll(timeLabel,timeComboBox);
        hb.getChildren().addAll(cinemaLabel,locationAdd);
        hb.getChildren().addAll(addButton);
        hb.getChildren().addAll(del);
        hb.setSpacing(10);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        //vbox

        vbox.getChildren().addAll(table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    
    public static class MovieDetails {
    
        private final SimpleStringProperty cinemas;
        private final SimpleStringProperty time;
        private final SimpleStringProperty location;
 
        private MovieDetails(String col1, String col2, String col3) {  
            this.cinemas = new SimpleStringProperty(col1);
            this.time = new SimpleStringProperty(col2);
            this.location = new SimpleStringProperty(col3);
        }
 
        public String getCinemas() {
            return cinemas.get();
        }
 
        public void setCinemas(String col1) {
        	cinemas.set(col1);
        }
        
        public String getTime() {
            return time.get();
        }
 
        public void setTime(String col2) {
        	time.set(col2);
        }
        
        public String getLocation() {
            return location.get();
        }
 
        public void setLocation(String col3) {
        	location.set(col3);
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
