/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wright.airviewer2;

import edu.wright.airviewer2.AIRViewer;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author erik
 */
public class AIRViewerController implements Initializable {

    static final String DEFAULT_PATH = "sample.pdf";

    @FXML
    private Pagination pagination;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem extractTextMenuItem;

    @FXML
    private MenuItem undoMenuItem;

    @FXML
    private MenuItem redoMenuItem;

    @FXML
    private MenuItem addBoxAnnotationMenuItem;

    @FXML
    private MenuItem addEllipseAnnotationMenuItem;

    @FXML
    private MenuItem addTextAnnotationMenuItem;

    @FXML
    private MenuItem deleteAnnotationMenuItem;
    
    @FXML
    private MenuItem addPagesMenuItem;
	
    @FXML
    private MenuItem deletePagesMenuItem;
	
    @FXML
    private MenuItem toDocMenuItem;
    
    @FXML
    private MenuItem toPPTMenuItem;
    
    @FXML
    private MenuItem toHTMLMenuItem;
	
    @FXML
    private MenuItem highlightTextMenuItem;
	
    @FXML
    private MenuItem replaceTextMenuItem;
    
    @FXML
    private MenuItem setFontSizeMenuItem;
	
    @FXML
    private MenuItem passwordMenuItem;

    private AIRViewerModel model;

    private ImageView currentPageImageView;

    private Group pageImageGroup;

    private AIRViewerModel promptLoadModel(String startPath) {

        AIRViewerModel loadedModel = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open PDF File");
            fileChooser.setInitialFileName(startPath);
            Stage stage = (Stage) pagination.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (null != file) {
                String path = file.getCanonicalPath();
                loadedModel = new AIRViewerModel(Paths.get(path));
            }
        } catch (IOException ex) {
//            Logger.getLogger(AIRViewerController.class.getName()).log(
//                    Level.INFO,
//                    "Unable to open <" + ex.getLocalizedMessage() + ">",
//                    "");
            loadedModel = null;
        }

        return loadedModel;
    }

    private void synchronizeSelectionKnobs() {
        if (null != model && null != currentPageImageView && null != pageImageGroup) {
            List<java.awt.Rectangle> selectedAreas = model.getSelectedAreas();
            ArrayList<Node> victims = new ArrayList<>(pageImageGroup.getChildren());
            
            // Delete everything in teh group that isn't currentPageImageView
            victims.stream().filter((n) -> (n != currentPageImageView)).forEach((n) -> {
                pageImageGroup.getChildren().remove(n);
            });
            
            // Add knobs to thegroup to indicate selection
            for (java.awt.Rectangle r : selectedAreas) {
                Circle knobA = new Circle(r.getX(),  (int)pageImageGroup.prefHeight(0) - r.getY(), 4);
                knobA.setStroke(Color.YELLOW);
                knobA.setStrokeWidth(2);
                pageImageGroup.getChildren().add(knobA);
                Circle knobB = new Circle(r.getX() + r.getWidth(), (int)pageImageGroup.prefHeight(0) - r.getY(), 4);
                knobB.setStroke(Color.YELLOW);
                knobB.setStrokeWidth(2);
                pageImageGroup.getChildren().add(knobB);
                Circle knobC = new Circle(r.getX() + r.getWidth(),  (int)pageImageGroup.prefHeight(0) - (r.getY() + r.getHeight()), 4);
                knobC.setStroke(Color.YELLOW);
                knobC.setStrokeWidth(2);
                pageImageGroup.getChildren().add(knobC);
                Circle knobD = new Circle(r.getX(),  (int)pageImageGroup.prefHeight(0) - (r.getY() + r.getHeight()), 4);
                knobD.setStroke(Color.YELLOW);
                knobD.setStrokeWidth(2);
                pageImageGroup.getChildren().add(knobD);
            }
        }

    }

    private void refreshUserInterface() {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'simple.fxml'.";
        assert openMenuItem != null : "fx:id=\"openMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert saveAsMenuItem != null : "fx:id=\"saveAsMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert closeMenuItem != null : "fx:id=\"closeMenuItem\" was not injected: check your FXML file 'simple.fxml'.";

        assert extractTextMenuItem != null : "fx:id=\"extractTextMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert undoMenuItem != null : "fx:id=\"undoMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert redoMenuItem != null : "fx:id=\"redoMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert addBoxAnnotationMenuItem != null : "fx:id=\"addBoxAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert addEllipseAnnotationMenuItem != null : "fx:id=\"addEllipseAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert addTextAnnotationMenuItem != null : "fx:id=\"addTextAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert deleteAnnotationMenuItem != null : "fx:id=\"deleteAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        
        assert addPagesMenuItem != null : "fx:id=\"addPagesMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	assert deletePagesMenuItem != null : "fx:id=\"deletePagesMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	    
	assert toDocMenuItem != null : "fx:id=\"toDocMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert toPPTMenuItem != null : "fx:id=\"toPPTMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert toHTMLMenuItem != null : "fx:id=\"toHTMLMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	    
	assert highlightTextMenuItem != null : "fx:id=\"highlightTextMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	assert replaceTextMenuItem != null : "fx:id=\"replaceTextMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	assert setFontSizeMenuItem != null : "fx:id=\"setFontSizeMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	    
	assert passwordMenuItem != null : "fx:id=\"passwordMenuItem\" was not injected: check your FXML file 'simple.fxml'.";

        if (null != model) {
            pagination.setPageCount(model.numPages());
            pagination.setDisable(false);
            saveAsMenuItem.setDisable(false);
            extractTextMenuItem.setDisable(false);
            undoMenuItem.setDisable(!model.getCanUndo());
            undoMenuItem.setText("Undo " + model.getSuggestedUndoTitle());
            redoMenuItem.setDisable(!model.getCanRedo());
            redoMenuItem.setText("Redo " + model.getSuggestedRedoTitle());
            addBoxAnnotationMenuItem.setDisable(false);
            addEllipseAnnotationMenuItem.setDisable(false);
            addTextAnnotationMenuItem.setDisable(false);
            deleteAnnotationMenuItem.setDisable(0 >= model.getSelectionSize());
            
            addPagesMenuItem.setDisable(false);
	    deletePagesMenuItem.setDisable(false);
		
	    toDocMenuItem.setDisable(false);
            toPPTMenuItem.setDisable(false);
            toHTMLMenuItem.setDisable(false);
		
	    highlightTextMenuItem.setDisable(false);
	    replaceTextMenuItem.setDisable(false);
	    setFontSizeMenuItem.setDisable(false);
		
	    passwordMenuItem.setDisable(false);


            if (null != currentPageImageView) {
                int pageIndex = pagination.getCurrentPageIndex();
                currentPageImageView.setImage(model.getImage(pageIndex));
                currentPageImageView.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent me) {
                        float flippedY = (float) currentPageImageView.getBoundsInParent().getHeight() - (float) me.getY();
                        System.out.println("Mouse pressed X: " + me.getX()
                                + " Y: " + Float.toString(flippedY));

                        float xInPage = (float) me.getX();
                        float yInPage = flippedY;

                        if (null != model) {
                            int pageIndex = pagination.getCurrentPageIndex();
                            if (!me.isMetaDown() && !me.isShiftDown()) {
                                model.deselectAll();
                            }
                            model.extendSelectionOnPageAtPoint(pageIndex,
                                    xInPage, yInPage);
                            refreshUserInterface();
                        }
                    }
                });
            }

            synchronizeSelectionKnobs();

        } else {
            pagination.setPageCount(0);
            pagination.setPageFactory(index -> {
                if (null == pageImageGroup) {
                    pageImageGroup = new Group();
                }
                currentPageImageView = new ImageView();
                pageImageGroup.getChildren().clear();
                pageImageGroup.getChildren().add(currentPageImageView);
                return pageImageGroup;
            });
            pagination.setDisable(true);
            saveAsMenuItem.setDisable(true);
            extractTextMenuItem.setDisable(true);
            undoMenuItem.setDisable(true);
            redoMenuItem.setDisable(true);
            addBoxAnnotationMenuItem.setDisable(true);
            addEllipseAnnotationMenuItem.setDisable(true);
            addTextAnnotationMenuItem.setDisable(true);
            deleteAnnotationMenuItem.setDisable(true);
            
            addPagesMenuItem.setDisable(true);
	    deletePagesMenuItem.setDisable(true);
		
	    toDocMenuItem.setDisable(true);
            toPPTMenuItem.setDisable(true);
            toHTMLMenuItem.setDisable(true);
		
	    highlightTextMenuItem.setDisable(true);
	    replaceTextMenuItem.setDisable(true);
	    setFontSizeMenuItem.setDisable(true);
		
	    passwordMenuItem.setDisable(true);
        }
    }

    private AIRViewerModel reinitializeWithModel(AIRViewerModel aModel) {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'simple.fxml'.";
        assert openMenuItem != null : "fx:id=\"openMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert saveAsMenuItem != null : "fx:id=\"saveAsMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert closeMenuItem != null : "fx:id=\"closeMenuItem\" was not injected: check your FXML file 'simple.fxml'.";

        assert extractTextMenuItem != null : "fx:id=\"extractTextMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert undoMenuItem != null : "fx:id=\"undoMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert redoMenuItem != null : "fx:id=\"redoMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert addBoxAnnotationMenuItem != null : "fx:id=\"addBoxAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert addEllipseAnnotationMenuItem != null : "fx:id=\"addEllipseAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert addTextAnnotationMenuItem != null : "fx:id=\"addTextAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert deleteAnnotationMenuItem != null : "fx:id=\"deleteAnnotationMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        
        assert addPagesMenuItem != null : "fx:id=\"addPagesMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	assert deletePagesMenuItem != null : "fx:id=\"deletePagesMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	    
	assert toDocMenuItem != null : "fx:id=\"toDocMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert toPPTMenuItem != null : "fx:id=\"toPPTMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
        assert toHTMLMenuItem != null : "fx:id=\"toHTMLMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	    
	assert highlightTextMenuItem != null : "fx:id=\"highlightTextMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	assert replaceTextMenuItem != null : "fx:id=\"replaceTextMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	assert setFontSizeMenuItem != null : "fx:id=\"setFontSizeMenuItem\" was not injected: check your FXML file 'simple.fxml'.";
	    
	assert passwordMenuItem != null : "fx:id=\"passwordMenuItem\" was not injected: check your FXML file 'simple.fxml'.";

        model = aModel;

        openMenuItem.setOnAction((ActionEvent e) -> {
            System.out.println("Open ...");
            reinitializeWithModel(promptLoadModel(AIRViewerController.DEFAULT_PATH));
        });
        openMenuItem.setDisable(false);
        closeMenuItem.setOnAction((ActionEvent e) -> {
            System.out.println("closeMenuItem ...");
            Platform.exit();
        });
        closeMenuItem.setDisable(false);

        if (null != model) {
            Stage stage = AIRViewer.getPrimaryStage();
            assert null != stage;

            model.deselectAll();

            pagination.setPageCount(model.numPages());
            pagination.setPageFactory(index -> {
                if (null == pageImageGroup) {
                    pageImageGroup = new Group();
                }
                currentPageImageView = new ImageView(model.getImage(index));
                pageImageGroup.getChildren().clear();
                pageImageGroup.getChildren().add(currentPageImageView);
                model.deselectAll();
                refreshUserInterface();
                return pageImageGroup;
            });
            saveAsMenuItem.setOnAction((ActionEvent event) -> {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog((Stage) pagination.getScene().getWindow());
                if (null != file) {
                    model.save(file);
                }
            });
            extractTextMenuItem.setOnAction((ActionEvent e) -> {
                System.out.println("extractTextMenuItem ...");
            });
            undoMenuItem.setOnAction((ActionEvent e) -> {
                model.undo();
                refreshUserInterface();
            });
            redoMenuItem.setOnAction((ActionEvent e) -> {
                model.redo();
                refreshUserInterface();
            });
            addBoxAnnotationMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    int pageIndex = pagination.getCurrentPageIndex();
                    model.executeDocumentCommandWithNameAndArgs("AddBoxAnnotation",
                            new String[]{Integer.toString(pageIndex), "36.0", "36.0", "72.0", "72.0"});
                    refreshUserInterface();
                }
            });
            addEllipseAnnotationMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    int pageIndex = pagination.getCurrentPageIndex();
                    model.executeDocumentCommandWithNameAndArgs("AddCircleAnnotation",
                            new String[]{Integer.toString(pageIndex), "288", "576", "144.0", "72.0", "Sample Text!"});
                    refreshUserInterface();
                }
            });
            addTextAnnotationMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    int pageIndex = pagination.getCurrentPageIndex();
                    model.executeDocumentCommandWithNameAndArgs("AddTextAnnotation",
                            new String[]{Integer.toString(pageIndex), "36", "576", "144.0", "19.0", "A Bit More Sample Text!"});
                    refreshUserInterface();
                }
            });
            deleteAnnotationMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    int pageIndex = pagination.getCurrentPageIndex();
                    model.executeDocumentCommandWithNameAndArgs("DeleteSelectedAnnotation",
                            new String[]{Integer.toString(pageIndex)});
                    refreshUserInterface();
                }
            });
            addPagesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
              public void handle(ActionEvent e) {
              	// logic to take input 
              	
               	
              	Dialog<String>  dialog = new Dialog<String>();
              	dialog.setTitle("Add Pages Dialog Box");
              	
              	ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
              	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                  
                  //Setting the content of the dialog
                  dialog.setContentText("Add Pages");
                
                  //Adding buttons to the dialog pane
                  dialog.getDialogPane().getButtonTypes().add(ok);
                  dialog.getDialogPane().getButtonTypes().add(cancel);
                  //Setting the label

                 Label numberLabel = new Label("Enter the number of Blank Pages to add");
              	
                 TextField number = new TextField();
                 
                 
                 VBox vBox = new VBox();

                 vBox.setSpacing(8);
                 vBox.setPadding(new Insets(10,10,10,10));
                 vBox.getChildren().addAll(
                    numberLabel, number);
                 
                 dialog.getDialogPane().setContent(vBox); 
              	
                 dialog.showAndWait();
                 
                 System.out.println(number.getText());
          	   
                 AddPages addObj = new AddPages(model.getStrPath(), number.getText());
                 
                 try {
					addObj.addPages();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                  
                  refreshUserInterface();
              }

            });
	     deletePagesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
              public void handle(ActionEvent e) {
              	// logic to take input 
              	
               	
              	Dialog<String>  dialog = new Dialog<String>();
              	dialog.setTitle("Delete Page Dialog Box");
              	
              	ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
              	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                  
                  //Setting the content of the dialog
                  dialog.setContentText("Delete Page");
                
                  //Adding buttons to the dialog pane
                  dialog.getDialogPane().getButtonTypes().add(ok);
                  dialog.getDialogPane().getButtonTypes().add(cancel);
                  //Setting the label

                 Label numberLabel = new Label("Enter the valid page number of PDF to delete");
              	
                 TextField number = new TextField();
                 
                 
                 VBox vBox = new VBox();

                 vBox.setSpacing(8);
                 vBox.setPadding(new Insets(10,10,10,10));
                 vBox.getChildren().addAll(
                    numberLabel, number);
                 
                 dialog.getDialogPane().setContent(vBox); 
              	
                 dialog.showAndWait();
                 
                 System.out.println(number.getText());
          	   
                 DeletePages delObj = new DeletePages(number.getText(), model.getStrPath());
                 
                 try {
					delObj.deletePage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                 
                  refreshUserInterface();
              }

            });
		
	    toDocMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                	
                	ToDoc docObj = new ToDoc(model.getStrPath());
                    docObj.convertToDoc();
                    refreshUserInterface();
                }
            });
            toPPTMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    ToPPT pptObj = new ToPPT(model.getStrPath());
                    pptObj.convertToPPT();
                    refreshUserInterface();
                }
            });
            toHTMLMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                	ToHTML htmlObj = new ToHTML(model.getStrPath());
                    htmlObj.convertToHTML();
                    refreshUserInterface();
                }
            });
		
	     highlightTextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
              public void handle(ActionEvent e) {
              	// logic to take input 
              	
               	
              	Dialog<String>  dialog = new Dialog<String>();
              	dialog.setTitle("Highlight Text Dialog Box");
              	
              	ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
              	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                  
                  //Setting the content of the dialog
                  dialog.setContentText("Highlight Text");
                
                  //Adding buttons to the dialog pane
                  dialog.getDialogPane().getButtonTypes().add(ok);
                  dialog.getDialogPane().getButtonTypes().add(cancel);
                  //Setting the label

                 Label numberLabel = new Label("Enter the text in selected pdf to highlight");
              	
                 TextField number = new TextField();
                 
                 
                 VBox vBox = new VBox();

                 vBox.setSpacing(8);
                 vBox.setPadding(new Insets(10,10,10,10));
                 vBox.getChildren().addAll(
                    numberLabel, number);
                 
                 dialog.getDialogPane().setContent(vBox); 
              	
                 dialog.showAndWait();
                 
                 System.out.println(number.getText());
          	   
                 HighlightText highlightObj = new HighlightText(number.getText(), model.getStrPath());
                 
                 highlightObj.highlightText();
          	   
              //model.getStrPath() 
                 
                  refreshUserInterface();
              }

            });
	    replaceTextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
              public void handle(ActionEvent e) {
              	// logic to take input 
              	
               	
              	Dialog<String>  dialog = new Dialog<String>();
              	dialog.setTitle("Replace Text Dialog Box");
              	
              	ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
              	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                  
                  //Setting the content of the dialog
                  dialog.setContentText("Replace Text");
                
                  //Adding buttons to the dialog pane
                  dialog.getDialogPane().getButtonTypes().add(ok);
                  dialog.getDialogPane().getButtonTypes().add(cancel);
                  //Setting the label

                 Label numberLabel = new Label("Enter the valid text in PDF to replace");
              	
                 TextField number = new TextField();
                 
                 Label numberLabel1 = new Label("Enter the text to replace with");
               	
                 TextField number1 = new TextField();
                 
                 
                 VBox vBox = new VBox();

                 vBox.setSpacing(8);
                 vBox.setPadding(new Insets(10,10,10,10));
                 vBox.getChildren().addAll(
                    numberLabel, number, numberLabel1, number1);
                 
                 dialog.getDialogPane().setContent(vBox); 
              	
                 dialog.showAndWait();
                 
          	   
                 ReplaceText replaceObj = new ReplaceText(model.getStrPath(), number.getText(), number1.getText());
                 
                 replaceObj.replaceText();
          	   
              //model.getStrPath() 
                 
                  refreshUserInterface();
              }

            });
	    setFontSizeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
              public void handle(ActionEvent e) {
              	// logic to take input 
              	
               	
              	Dialog<String>  dialog = new Dialog<String>();
              	dialog.setTitle("Set Font Size Dialog Box");
              	
              	ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
              	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                  
                  //Setting the content of the dialog
                  dialog.setContentText("Set Font Size");
                
                  //Adding buttons to the dialog pane
                  dialog.getDialogPane().getButtonTypes().add(ok);
                  dialog.getDialogPane().getButtonTypes().add(cancel);
                  //Setting the label

                 Label numberLabel = new Label("Enter the valid text in PDF to set font size");
              	
                 TextField number = new TextField();
                 
                 Label numberLabel1 = new Label("Enter the font size to change");
               	
                 TextField number1 = new TextField();
                 
                 
                 VBox vBox = new VBox();

                 vBox.setSpacing(8);
                 vBox.setPadding(new Insets(10,10,10,10));
                 vBox.getChildren().addAll(
                    numberLabel, number, numberLabel1, number1);
                 
                 dialog.getDialogPane().setContent(vBox); 
              	
                 dialog.showAndWait();
                 
          	   
                 SetFontSize fontObj = new SetFontSize(model.getStrPath(), number.getText(), number1.getText());
                 
                 fontObj.setFontSize();
          	   
              //model.getStrPath() 
                 
                  refreshUserInterface();
              }

            });
	    
	     passwordMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
              public void handle(ActionEvent e) {
              	// logic to take input 
              	
               	
              	Dialog<String>  dialog = new Dialog<String>();
              	dialog.setTitle("Set Password Dialog Box");
              	
              	ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
              	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                  
                  //Setting the content of the dialog
                  dialog.setContentText("Set Password");
                
                  //Adding buttons to the dialog pane
                  dialog.getDialogPane().getButtonTypes().add(ok);
                  dialog.getDialogPane().getButtonTypes().add(cancel);
                  //Setting the label

                 Label Label = new Label("Enter the password you want to set");
              	
                 TextField password = new TextField();
                 
                 
                 VBox vBox = new VBox();

                 vBox.setSpacing(8);
                 vBox.setPadding(new Insets(10,10,10,10));
                 vBox.getChildren().addAll(
                    Label, password);
                 
                 dialog.getDialogPane().setContent(vBox); 
              	
                 dialog.showAndWait();
                 
                 System.out.println(password.getText());
          	   
                 SetPassword passObj = new SetPassword(model.getStrPath(), password.getText());
                 
                 try {
					passObj.setPassword();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          	   
                  refreshUserInterface();
              }

            });



        }

        refreshUserInterface();

        return model;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'simple.fxml'.";

        Stage stage = AIRViewer.getPrimaryStage();
        stage.addEventHandler(WindowEvent.WINDOW_SHOWING, (WindowEvent window) -> {
            reinitializeWithModel(promptLoadModel(DEFAULT_PATH));
        });

    }

}
