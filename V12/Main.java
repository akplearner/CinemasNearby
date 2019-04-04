import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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

public class Main extends Application {
	private final TableView<MovieDetails> table = new TableView<>();
	private final ObservableList<MovieDetails> data = FXCollections.observableArrayList();
    final HBox hb = new HBox();
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setWidth(700);
        stage.setHeight(610);

        TableColumn firstCol = new TableColumn("Movie Name");
        firstCol.setMinWidth(200);
        firstCol.setCellValueFactory( new PropertyValueFactory<>("movieName"));
 
        TableColumn secondCol = new TableColumn("Rating");
        secondCol.setMinWidth(200);
        secondCol.setCellValueFactory( new PropertyValueFactory<>("rating"));
        
        TableColumn thirdCol = new TableColumn("Release Date");
        thirdCol.setMinWidth(200);
        thirdCol.setCellValueFactory( new PropertyValueFactory<>("releaseDate"));

        
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
			  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                          while(re.next()) {
				data.add(new MovieDetails(re.getString("title"),re.getString("rating"),sdf.format(re.getDate("releaseDate"))));
			  					     
                          }
		}catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
		}
        
        
        table.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
        table.setItems(data);
        table.getColumns().addAll(firstCol, secondCol,thirdCol);
        
 
        final Button addButton = new Button("Admin");
        addButton.setOnAction((ActionEvent e) -> {
        	Stage menuStage = new Stage();

        	Admin ad = new Admin();
	
        	ad.start(menuStage);
        	menuStage.show();
        
         });
        final Button SearchButton = new Button("Search");
        SearchButton.setOnAction((ActionEvent e) -> {
        	Stage menuStage = new Stage();
        	Search search = new Search();
        	search.start(menuStage);
        	menuStage.show();
        
         });
        
        hb.getChildren().addAll(addButton);
        hb.getChildren().addAll(SearchButton);
        hb.setSpacing(10);
        
        
        final Label label =  new Label("Filter by rating: ");
        label.setFont(new Font("Arial", 18));
        final Label label2 =  new Label("please select to view cinemas playing:");
        label2.setFont(new Font("Arial", 18));
        
        final ComboBox<String> ratingComboBox = new ComboBox();   
        ratingComboBox.getItems().addAll("ALL","G", "PG", "PG-13", "R", "NC-17");
        ratingComboBox.setValue("All");
        
        ratingComboBox.valueProperty().addListener(
        		new ChangeListener<String>() {
		        	public void changed(ObservableValue ov, String t, String t1) {
			            data.clear();
			            try {
			    			  Connection conn = getConnection();
			    			  Statement st = conn.createStatement();
			    			  ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
			    			  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                  while(re.next()) {
			    				  if(t1.equals("ALL")) {
			    					data.add(new MovieDetails(re.getString("title"),re.getString("rating"),sdf.format(re.getDate("releaseDate"))));
			  					
                                                          }
			    			  }
			    		}catch(Exception ex){
			    			System.out.println("ERROR: " + ex.getMessage());
			    		}

			            try {
			            	Connection conn = getConnection();
			            	Statement st = conn.createStatement();
			  			  	ResultSet re = st.executeQuery("SELECT * FROM project4.movies");
                                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  				while(re.next()) {
			  					if(t1.equals(re.getString("rating"))) {
			  						 data.add(new MovieDetails(re.getString("title"),re.getString("rating"),sdf.format(re.getDate("releaseDate"))));
			  					
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
        vbox.getChildren().addAll(label,ratingComboBox);
        vbox.getChildren().addAll(label2, table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
   public class SelectionListener implements ChangeListener<MovieDetails> {
		public void changed(ObservableValue<? extends MovieDetails> observable, MovieDetails oldValue,MovieDetails newValue) {
			System.out.println(newValue.getMovieName());
			
			Stage menuStage = new Stage();
            
			//adtime time = new adtime(newValue.getMovieName());
            //time.start(menuStage);
			Cinemas cinema = new Cinemas(newValue.getMovieName());
            cinema.start(menuStage);
            menuStage.show();	
		}
    }
   
    public static class MovieDetails {
 
        //private final SimpleStringProperty firstName;
    	private final SimpleStringProperty movieName;
        private final SimpleStringProperty rating;
        private final SimpleStringProperty releaseDate;

        
        
        private MovieDetails(String col1, String col2,String col3) {
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