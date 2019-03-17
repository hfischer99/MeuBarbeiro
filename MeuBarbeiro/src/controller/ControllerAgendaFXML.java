package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.agendaDAO;
import entidade.Agenda;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ui.tableView;

public class ControllerAgendaFXML {
	
	@FXML
	private TableView<Agenda> table;


    @FXML
    private TextField txData;

    @FXML
    private TextField txCliente;

    @FXML
    private Button btnInsereAgendamento;

    @FXML
    private Button btnPesquisar;

    @FXML
    private TableColumn<?, ?> colCliente;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colHorario;

    @FXML
    private Button btnAtualiza;
    
    @FXML
    private Button btnSair;

    @FXML
    private TableColumn<?, ?> colServico;

    @FXML
    private Button btnOKData;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private Button btnCancelaAgendamento;

    @FXML
    private TableColumn<?, ?> colBarbeiro;

    @FXML
    private TableColumn<?, ?> colStatus;

    
    private ArrayList<Agenda> ListContact = new ArrayList<>();
	private Agenda selecionada;
	
	private ObservableList<Agenda> agendamento = FXCollections.observableArrayList();
	
	public void deleta() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("SAIR");
		String s = "Você tem certeza que deseja excluir esse agendamento?";
		alert.setContentText(s);

		Optional<ButtonType> result = alert.showAndWait();

		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			if(selecionada != null) {
		agendaDAO dao = new agendaDAO();
		dao.excluiAgendamento(selecionada);
		table.setItems(atualizatabela());
		Alert b = new Alert(AlertType.INFORMATION);
		b.setHeaderText("agendamento excluido com sucesso!!!");
		b.show();
	
	}else{
		Alert a = new Alert(AlertType.WARNING);
		a.setHeaderText("Selecione um cliente!");
		a.show();
	}
		}

}
	
	
	public void initialize(URL location, ResourceBundle resources) {
		
		btnPesquisar.setOnMouseClicked((MouseEvent e) ->{
			table.setItems(busca());
		});
		
		btnAtualiza.setOnMouseClicked((MouseEvent e) ->{
			table.setItems(atualizatabela());
		});
				
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colBarbeiro.setCellValueFactory(new PropertyValueFactory<>("barbeiro"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colServico.setCellValueFactory(new PropertyValueFactory<>("servico"));	
		colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));	
		colData.setCellValueFactory(new PropertyValueFactory<>("data"));	
		
		popula();
        table.setItems(atualizatabela());
		
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agenda>()
		{
			@Override
			public void changed(ObservableValue<? extends Agenda> observable, Agenda oldValue, Agenda newValue) {
				selecionada = (Agenda) newValue ;				
		}
		});
	}
	
	
	private void popula() {
		ListContact = new agendaDAO().listarTodosCliente();
	}
	
	private void abreCalendario() {
		DatePicker datePicker = new DatePicker();
	}
	
	public ObservableList<Agenda> atualizatabela(){
		agendaDAO dao = new agendaDAO();
		agendamento = FXCollections.observableArrayList(dao.listarTodosCliente());
		return agendamento;
	}
	
	private ObservableList<Agenda> busca(){
	ObservableList<Agenda> clientePesquisa = FXCollections.observableArrayList();
	for(int x=0; x<agendamento.size(); x++) {
		if(agendamento.get(x).getCliente().toLowerCase().contains(txCliente.getText().toLowerCase())) {
			clientePesquisa.add(agendamento.get(x));
		}
	}	
	return clientePesquisa;	
	}
	
	public void fechar() {
    	tableView.getStage().close();
    }
}
