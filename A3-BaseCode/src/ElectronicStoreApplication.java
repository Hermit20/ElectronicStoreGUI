import javafx.application.Application;
import javafx.scene.Scene;
import javafx.event.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class ElectronicStoreApplication extends Application {
    private ElectronicStore model;
    private ElectronicStoreView view = new ElectronicStoreView();
    public ElectronicStoreApplication() { model = ElectronicStore.createStore(); }
    public void start(Stage primaryStage){
        Pane aPane = new Pane();

        aPane.getChildren().add(view);
        view.update(model,0);



        view.getsList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model,0);
                view.getRemoveButton().setDisable(true);
                view.getCompleteSaleButton().setDisable(true);
            }
        });

        view.getcList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.update(model,0);
                view.getAddButton().setDisable(true);
            }
        });

        view.get_pList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.update(model,0);
                view.getRemoveButton().setDisable(true);
                view.getCompleteSaleButton().setDisable(true);
                view.getAddButton().setDisable(true);
            }
        });

        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = view.getsList().getSelectionModel().getSelectedIndex();
                Product x = model.getStock_list()[index];
                if(model.getBuy_list().contains(x)){
                    x.setAmount(x.getAmount() + 1);
                    view.update(model,0);
                }
                else{
                    model.getBuy_list().add(x);
                    view.update(model,0);
                }
                x.setStockQuantity(x.getStockQuantity() - 1);
                view.update(model,0);
            }
        });


        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = view.getcList().getSelectionModel().getSelectedIndex();
                Product x = model.getBuy_list().get(index);
                System.out.println(index);
                if(model.getBuy_list().contains(x)){
                    if(x.getAmount() == 1){
                            model.getBuy_list().remove(x);
                            view.update(model,0);
                        }
                    }
                    x.setAmount(x.getAmount() - 1);
                    x.setStockQuantity(x.getStockQuantity() + 1);
                    view.update(model,index);
            }

        });

        view.getCompleteSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(Product i: model.getBuy_list()){
                    i.setSoldQuantity(i.getSoldQuantity() + i.getAmount());
                }

                int t = 0;
                for(Product i: model.getBuy_list()){
                    t += (i.getAmount() * i.getPrice());
                }
                model.addsale();
                model.sellProducts(1,t);
                view.getsField().setText(model.getSales()+"");
                view.get_rField().setText(model.getRevenue()+t+"");
                view.getSaField().setText((model.getRevenue()+t)/model.getSales()+"");
                model.setRevenue((int)model.getRevenue() + t);
                for(Product i: model.getBuy_list()){
                    i.setAmount(0);
                }
                model.getBuy_list().clear();
                view.update(model,0);
            }
        });

        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int t = 0;
                for(Product i: model.getBuy_list()){
                    t += (i.getAmount() * i.getPrice());
                }
                model = ElectronicStore.createStore();
                view.update(model,0);
                view.getsField().setText(model.getSales()+"");
                view.get_rField().setText(model.getRevenue()+t+"");
                view.getSaField().setText((model.getRevenue()+t)/model.getSales()+"");
            }
        });

        primaryStage.setTitle("Electronic Store Application" +" - "+ model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

