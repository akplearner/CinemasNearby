import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class admovie extends Application {
	
	TextField fldAdd = new TextField();
	TextField ratingfldAdd = new TextField();
	final Button del = new Button("Delete");
	final Button in = new Button("Insert");
    final Button cin = new Button("Cinemas");
    final HBox hb = new HBox();
	private final TableView<MovieDetails> table = new TableView<>();
	private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();    
	private String movieName;

	public admovie() {
		System.out.println("admovie.java class opened: Empty String");
	}
	public admovie(String movieName) {
		this.movieName=movieName;
	}
	public void start(Stage stage) {
		stage.setTitle("Admin Movie Database");
        Scene scene = new Scene(new Group());
        stage.setWidth(700);
        stage.setHeight(600);
        
        TableColumn firstCol = new TableColumn("Movie Name");
        firstCol.setMinWidth(200);
        firstCol.setCellValueFactory( new PropertyValueFactory<>("movieName"));
 
        TableColumn secondCol = new TableColumn("Rating");
        secondCol.setMinWidth(200);
        secondCol.setCellValueFactory( new PropertyValueFactory<>("rating"));
        
        TableColumn thirdCol = new TableColumn("Release Date");
        thirdCol.setMinWidth(200);
        thirdCol.setCellValueFactory( new PropertyValueFactory<>("releaseDate"));
        
        //if()
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.movies ");
			  while(re.next()) {
				  data.add(new MovieDetails(re.getString("title"),re.getString("rating"), re.getString("releaseDate")));
				  }
			  }
        catch(Exception ex){
        	System.out.println("ERROR: " + ex.getMessage()); 
        	}
        
        table.setRowFactory(
                listen->{
               	TableRow<MovieDetails> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    	MovieDetails rowData = row.getItem();
                        System.out.println("Double click on: "+rowData.getMovieName());
                        Stage menuStage = new Stage();
                        
            			adtime time = new adtime(rowData.getMovieName());
                        time.start(menuStage);
            			
            			//Cinemas cinema = new Cinemas(newValue.getMovieName());
                        //cinema.start(menuStage);
                        menuStage.show();
                        
                    }
                });
                return row;
                });
        table.setItems(data);
        table.getColumns().addAll(firstCol, secondCol, thirdCol);
        hb.getChildren().addAll(del, in, cin);
        hb.getChildren().addAll(fldAdd, ratingfldAdd);
        hb.setSpacing(10);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(table, hb);
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        stage.setScene(scene);
        stage.show();
        
        cin.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent e) {
       			Stage menuStage = new Stage();
       			adcinemas n = new adcinemas();
       			n.start(menuStage);
       		 	menuStage.show();
       		 	}
           }); 
        
        del.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		System.out.println("Delete button selected: "+table.getSelectionModel().getSelectedIndex()+ "-"+ table.getSelectionModel().getSelectedItem().getMovieName());
        		String itemToRemove = table.getSelectionModel().getSelectedItem().getMovieName();
        		String sql="DELETE FROM project4.movies WHERE (title = ?);";
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
        			ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
        			while(re.next()) {
        				data.add(new MovieDetails(re.getString("title"),re.getString("rating"), re.getString("releaseDate")));
        				}
        			}
        			catch(Exception ex){
        				System.out.println("ERROR: " + ex.getMessage());
        				}
        		}
        	});
        
        
        in.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
    			System.out.println("Add button press");
    			if(!fldAdd.getText().isEmpty()) {
    				if (fldAdd.getText().contains("")) {
    					String temp = fldAdd.getText();
    					String tempRating=ratingfldAdd.getText(); 
    					try {
    						Connection conn = getConnection();
    						System.out.println(temp);
    						String query = "INSERT INTO `project4`.`movies` (`title`, `rating`) VALUES (?, ?);";// "insert													
    						PreparedStatement preparedStmt = conn.prepareStatement(query);
    						preparedStmt.setString(1, temp);
    						preparedStmt.setString(2, tempRating);
    						preparedStmt.execute();
    						System.out.println("Congrats, you have saved it as draft!");
    						}
    					catch (Exception ex) {
    						System.out.println("ERROR: " + ex.getMessage());
    						}
    					try { 
    						data.clear();
    						Connection conn = getConnection();
    						Statement st = conn.createStatement();
    						ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
    						while(re.next()) {
    							data.add(new MovieDetails(re.getString("title"),re.getString("rating"), re.getString("releaseDate")));		  
    						}
    					}
    					catch(Exception ex){
    						System.out.println("ERROR: " + ex.getMessage());
    					}
    					System.out.println("Inserted: "+temp+"--" + tempRating);
    				}
    				else 
    					System.out.println("Error: format cinema inputs");
    			}
    			else 
    				System.out.println("Error add info in text");			
    			}
        	}
        );
	}
    public static class MovieDetails {
    	private final SimpleStringProperty movieName;
        private final SimpleStringProperty rating;
        private final SimpleStringProperty releaseDate;
        
        private MovieDetails(String col1, String col2, String col3) {
            this.movieName = new SimpleStringProperty(col1);
            this.rating = new SimpleStringProperty(col2);
            this.releaseDate = new SimpleStringProperty(col3);
        }
 
        public String getMovieName() {
            return movieName.get();
        }
 
        public void setMovieName(String col1) {
        	movieName.set(col1);
        }
 
        public String getRating() {
            return rating.get();
        }
 
        public void setRating(String col2) {
        	rating.set(col2);
        }
        public String getReleaseDate() {
        	return releaseDate.get();
        }
        public void setReleaseDate(String col3) {
        	releaseDate.set(col3);
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