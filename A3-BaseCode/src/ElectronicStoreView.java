
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class ElectronicStoreView extends Pane {
    private TextField sField, rField, saField;
    private ListView<Product> pList,sList;
    private ListView<String> cList;
    private Button ResetButton, AddButton , RemoveButton, CompleteSaleButton;
    private Label label2;



    public TextField getsField(){return sField;}
    public TextField get_rField(){return rField;}
    public TextField getSaField(){return saField;}
    public ListView<Product> get_pList(){return pList;}
    public ListView<Product> getsList(){return sList;}
    public ListView<String> getcList(){return cList;}
    public Button getResetButton(){return ResetButton;}
    public Button getAddButton(){return AddButton;}
    public Button getRemoveButton(){return RemoveButton;}
    public Button getCompleteSaleButton(){return CompleteSaleButton;}



    public void update(ElectronicStore model, int selected_product){

        sList.setItems(FXCollections.observableArrayList(model.Stocklist()));
        AddButton.setDisable(sList.getSelectionModel().getSelectedIndex() < 0);

        ArrayList<Product> popular_list = new ArrayList<Product>();
        for(int i = 0; i < 3; i++){
            int max = 0;
            for(Product j: model.getStock_list())
                if(j.getSoldQuantity() > max && !(popular_list.contains(j))){
                    max = j.getSoldQuantity();
                }

            for(Product k: model.getStock_list()){
                if(max == k.getSoldQuantity() && max != 0){
                    popular_list.add(k);
                }
            }
        }
        pList.setItems(FXCollections.observableArrayList(popular_list));


        ArrayList<String> cart = new ArrayList<String>();
        for(Product i: model.getBuy_list()){
            if(i == null){
                continue;
            }
            if(i.getAmount() == 0){
                i.setAmount(1);
                continue;
            }
            cart.add(i.getAmount()+" x "+i);
        }

        double t = 0;
        for(Product i: model.getBuy_list()){
            t += (i.getAmount() * i.getPrice());
        }
        label2.setText("Current Cart"+"( $"+t+ "):");

        cList.setItems(FXCollections.observableArrayList(cart));
        CompleteSaleButton.setDisable(cList.getSelectionModel().getSelectedIndex() < 0);
        RemoveButton.setDisable(cList.getSelectionModel().getSelectedIndex() < 0);



    }

    public ElectronicStoreView(){
        Label label = new Label("Store Summary:");
        label.relocate(30,2);
        Label label1 = new Label("Store Stock:");
        label2 = new Label("Current Cart"+"( $" + 0.00 + "):");
        label2.relocate(500,5);
        label1.relocate(250,5);
        Label label3 = new Label("# Sales:");
        label3.relocate(20, 23);
        Label label4 = new Label("Revenue:");
        label4.relocate(15,53);
        Label label5 = new Label("$ / Sale:");
        label5.relocate(20,83);
        Label label6 = new Label("Most Popular Items:");
        label6.relocate(20 ,105);

        sField = new TextField();
        sField.relocate(65,20); sField.setPrefSize(100,20);
        rField = new TextField();
        rField.relocate(65,50); rField.setPrefSize(100,20);
        saField = new TextField();
        saField.relocate(65,80); saField.setPrefSize(100,20);

        pList = new ListView<Product>();
        pList.relocate(10,125); pList.setPrefSize(155,150);
        sList = new ListView<Product>();
        sList.relocate(175,25); sList.setPrefSize(230,250);
        cList = new ListView<String>();
        cList.relocate(410,25); cList.setPrefSize(230,250);

        ResetButton = new Button("Reset Store");
        ResetButton.relocate(35,279 );
        ResetButton.setPrefSize(105,35);
        AddButton = new Button("Add Cart");
        AddButton.relocate(230,279 );
        AddButton.setPrefSize(105,35);
        RemoveButton = new Button("Remove from Cart");
        RemoveButton.relocate(410,279 );
        RemoveButton.setPrefSize(130,35);
        CompleteSaleButton = new Button("Complete Sale");
        CompleteSaleButton.relocate(540,279 );
        CompleteSaleButton.setPrefSize(100,35);
        sField.setText("0");
        rField.setText("0.00");
        saField.setText("N/A");

        getChildren().addAll(label,label1, label2, label3, label4, label5, label6, sField, rField, saField, pList, sList, cList,
                ResetButton, AddButton, RemoveButton, CompleteSaleButton);

        setPrefSize(655, 327.5);
    }
}
