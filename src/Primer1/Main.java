package Primer1;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Main extends Application{

    public static void main(String[] args){
    launch(args);
    }
    private files r = new files();
    private File[] list;

    private LinkedList<String> list1 = new LinkedList<>();
    private LinkedList<String> list2 = new LinkedList<>();
    private int n;
    private int[] x;
    private int o = 0;

    private String parametar1 = "";
    private String parametar2 = "";
    private String parametar3 = "";
    private String parametar4 = "";
    private String parametar5 = "";
    private String parametar6 = "";
    private String text = "";
    private String name = "";
    private List<InputStream> inputPdfList;
    private OutputStream outputStream;



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PDF Merge");
        VBox master = new VBox(10);

        // ucitavanje fajlova sa odredjene lokacije
        Button importFiles = new Button("Import PDF files");
        VBox importFilesVB = new VBox(10);
        importFilesVB.getChildren().addAll(importFiles);
        importFilesVB.setPadding(new Insets(10, 0, 0, 0 ));
        importFilesVB.setAlignment(Pos.CENTER);

        // parametri pretrage
        VBox parametriVB = new VBox(10);
        HBox parametriHBGornji = new HBox(10);

        //HB gornji
        TextField btnP1 = new TextField();
        TextField btnP2 = new TextField();
        TextField btnP3 = new TextField();
        TextField btnP4 = new TextField();
        TextField btnP5 = new TextField();
        TextField btnP6 = new TextField();
        parametriHBGornji.getChildren().addAll(btnP1, btnP2, btnP3, btnP4, btnP5, btnP6);

        Label lbl = new Label("Parametri:");
        parametriVB.getChildren().addAll(lbl, parametriHBGornji);
        parametriVB.setPadding(new Insets(0, 0, 0, 30));


        // pretraga po parametrima
        Button searchFiles = new Button("Search PDF files");
        VBox searchFilesVB = new VBox(10);
        searchFilesVB.getChildren().addAll(searchFiles);
        searchFilesVB.setPadding(new Insets(10, 0, 0, 0 ));
        searchFilesVB.setAlignment(Pos.CENTER);

        // display results
        TextArea displayResultsTA = new TextArea();
        displayResultsTA.setMaxWidth(980);
        displayResultsTA.setMinHeight(400);
        VBox displayResultsVB = new VBox(10);
        displayResultsVB.getChildren().addAll(displayResultsTA);
        displayResultsVB.setPadding(new Insets(10, 0, 0, 10));
        //displayResultsTA.setDisable(true);


        // redosled spajanja
        Label lblName = new Label("Ime krajnjeg PDF fajla:");
        TextField nameTF = new TextField();
        nameTF.setMaxWidth(980);
        VBox nameVB = new VBox(10);
        nameVB.setPadding(new Insets(10, 0, 0, 10));
        nameVB.getChildren().addAll(lblName, nameTF);

        // Merge
        Button mergeBtn = new Button("Merge");
        VBox mergeBtnVB = new VBox(10);
        mergeBtnVB.getChildren().addAll(mergeBtn);
        mergeBtnVB.setAlignment(Pos.CENTER);


        master.getChildren().addAll(importFilesVB, parametriVB, searchFilesVB, displayResultsVB, nameVB, mergeBtnVB);
        Scene scene = new Scene(master, 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();


        importFiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               // list = r.listFilesAndFolders(".\\fajlovi za spajanje\\fajlovi\\");
                list = new File(".\\fajlovi za spajanje\\fajlovi\\").listFiles();
                n = list.length;
                for (int i = 0; i < n; i++) {
                    list1.add(list[i].getName());
                    list2.add(list[i].getName().substring(0, list[i].getName().length() - 4));
                }

                Collections.sort(list1);
                Collections.sort(list2);

                x = new int[list2.size()];

            }
        });
        searchFiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parametar1 = btnP1.getText();
                parametar2 = btnP2.getText();
                parametar3 = btnP3.getText();
                parametar4 = btnP4.getText();
                parametar5 = btnP5.getText();
                parametar6 = btnP6.getText();

                o = 0;

                for (int j = 0; j < list1.size(); j++) {
                    String nameOfFiles = list1.get(j);
                    if (nameOfFiles.contains(parametar1) && nameOfFiles.contains(parametar2) && nameOfFiles.contains(parametar3)
                     && nameOfFiles.contains(parametar4) && nameOfFiles.contains(parametar5) && nameOfFiles.contains(parametar6)) {
                        x[o] = j;
                        o++;
                    }
                }
                if (o > 0) {
                    String name = "";

                    try {
                        //Prepare input pdf file list as list of input stream.
                        inputPdfList = new ArrayList<InputStream>();
                        for (int b = 0; b < o; b++) {
                            inputPdfList.add(new FileInputStream(".\\Fajlovi za spajanje\\fajlovi\\" + list1.get(x[b])));

                        }
                        String text = "";
                        for (int i = 0; i < o; i++) {
                            int br;
                            br = i + 1;
                            text += br + ". " + list1.get(x[i]) + "\n";
                        }

                        displayResultsTA.setText(text);
                        //Prepare output stream for merged pdf file.


                        //call method to merge pdf files.
                        // mergePdfFiles(inputPdfList, outputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    displayResultsTA.setText("Fajlovi nisu pronadjeni proverite parametre!!!");
                }

            }
        });

        mergeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    name =nameTF.getText();
                    outputStream = new FileOutputStream(".\\fajlovi za spajanje\\merge\\" + name + ".pdf");
                    mergePdfFiles(inputPdfList, outputStream);
                    displayResultsTA.setText("Uspesno spojeni :D");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });


    }


    static void mergePdfFiles(List<InputStream> inputPdfList, OutputStream outputStream) throws Exception{
        //Create document and pdfReader objects.
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        List<PdfReader> readers = new ArrayList<PdfReader>();
        int totalPages = 0;

        //Create pdf Iterator object using inputPdfList.
        Iterator<InputStream> pdfIterator =
                inputPdfList.iterator();

        // Create reader list for the input pdf files.
        while (pdfIterator.hasNext()) {
            InputStream pdf = pdfIterator.next();
            PdfReader pdfReader = new PdfReader(pdf);
            readers.add(pdfReader);
            totalPages = totalPages + pdfReader.getNumberOfPages();
        }

        // Create writer for the outputStream
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        //Open document.
        document.open();

        //Contain the pdf data.
        PdfContentByte pageContentByte = writer.getDirectContent();

        PdfImportedPage pdfImportedPage;
        int currentPdfReaderPage = 1;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();

        // Iterate and process the reader list.
        while (iteratorPDFReader.hasNext()) {
            PdfReader pdfReader = iteratorPDFReader.next();
            //Create page and add content.
            while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
                document.newPage();
                pdfImportedPage = writer.getImportedPage(
                        pdfReader,currentPdfReaderPage);
                pageContentByte.addTemplate(pdfImportedPage, 0, 0);
                currentPdfReaderPage++;
            }
            currentPdfReaderPage = 1;
        }

        //Close document and outputStream.
        outputStream.flush();
        document.close();
        outputStream.close();

    }
}
