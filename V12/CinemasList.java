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
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CinemasList extends Application {
	
	String name;
	String[] cinemas;
	public CinemasList(String s) {
		name =s;
	}
	public CinemasList(String[] c) {
		cinemas=c;
		System.out.println("cinemalist:"+cinemas[0]);
	}
	
    final TextArea text = new TextArea ("");
    private final TableView<CinemaDetails> table = new TableView<>();
	private final ObservableList<CinemaDetails> data = FXCollections.observableArrayList();    
 
	public void start(Stage stage) {
        stage.setTitle("CinemasList");
        Scene scene = new Scene(new Group(), 900, 450);

        TableColumn firstCol = new TableColumn("Cinema Name");
        firstCol.setMinWidth(200);
        firstCol.setCellValueFactory( new PropertyValueFactory<>("cinemaName"));
 
        TableColumn secondCol = new TableColumn("X ");
        secondCol.setMinWidth(200);
        secondCol.setCellValueFactory( new PropertyValueFactory<>("x"));
        
        TableColumn thirdCol = new TableColumn(" Y ");
        thirdCol.setMinWidth(200);
        thirdCol.setCellValueFactory( new PropertyValueFactory<>("y"));
        
        TableColumn fourthCol = new TableColumn("Cinema Type");
        fourthCol.setMinWidth(200);
        fourthCol.setCellValueFactory( new PropertyValueFactory<>("type"));
        
        
        try {
			  Connection conn = getConnection();
			  Statement st = conn.createStatement();
			  ResultSet re = st.executeQuery("SELECT * FROM project4.location");
			  String str;
			  if(name == "all") {
				  System.out.println("all");
				  while(re.next()) {
					  
					  System.out.println(re.getString("cinemas"));
					  data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
					  }
			  }
			  else {
				  System.out.println("hi");
					  int i=0;
					 while(re.next()) {
						 str = re.getString("cinemas");
						 if(cinemas[i].equals(str)&&str!=null) {
							 System.out.println("if stateememt"+cinemas[i]);
							 data.add(new CinemaDetails(re.getString("cinemas"),re.getString("X"),re.getString("Y"),re.getString("type")));
							 i++;
							 } 					
						 }
					 }
			  }
        catch(Exception ex){
			System.out.println("ERROR: " + ex.getMessage()); 
			}
        table.setItems(data);
        table.getColumns().addAll(firstCol, secondCol, thirdCol, fourthCol);
        table.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
        
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 0, 0, 30));
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        }
	public class SelectionListener implements ChangeListener<CinemaDetails> {
		public void changed(ObservableValue<? extends CinemaDetails> observable, CinemaDetails oldValue, CinemaDetails newValue) {
			System.out.println(newValue.getCinemaName());	
			Stage menuStage = new Stage();
			clientCinemas cinema = new clientCinemas(newValue.getCinemaName());
			cinema.start(menuStage);
			menuStage.show();	
			}
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