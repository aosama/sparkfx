package com.its.sparkfx.forms.main;

import com.its.sparkfx.context.LocalSparkContext;
import com.its.sparkfx.jobs.StringArrayFilterJob;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

/**
 *
 * @author aosama
 */
public class FrmMainPresenter implements Initializable
{

    @FXML
    WebView webView;

    @FXML
    Button btnStartStringArrayFilterJob;

    @FXML
    TextField txtUrl;

    @FXML
    Label memoryLabel;

    LocalSparkContext sparkContext;
    WebEngine webEnginer;
    StringArrayFilterJob stringArrayFilterJob;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("frm main initialized");

        webEnginer = webView.getEngine();
        webEnginer.load("http://localhost:4040");

        //getting the spark context from the singleton class and passing it to
        //the new instance of the job class
        sparkContext = LocalSparkContext.getInstance();
        stringArrayFilterJob = new StringArrayFilterJob(sparkContext.getSparkContext());

        //Timer operation for RAM usage in toolbar
        Timeline memUsageTimer = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->
        {
            updateMemoryUsage();
        }));
        memUsageTimer.setCycleCount(Timeline.INDEFINITE);
        memUsageTimer.play();
    }

    @FXML
    void StartStringArrayFilterJob(ActionEvent event)
    {
        new Thread(() -> stringArrayFilterJob.startJob()).start();
    }

    public void loadPage()
    {
        webEnginer.load(txtUrl.getText());
    }

    private void updateMemoryUsage()
    {
        memoryLabel.setText(getUsedMemoryAsString() + " / " + getTotalMemoryAsString());
    }

    private String getTotalMemoryAsString()
    {
        Double m = (Runtime.getRuntime().totalMemory() / (1024d));
        m = m / 1024d;
        DecimalFormat formatter = new DecimalFormat("#,###.0");
        return formatter.format(m);
    }

    private String getUsedMemoryAsString()
    {
        Double m = ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024d));
        m = m / 1024d;
        DecimalFormat formatter = new DecimalFormat("#,###.0");
        return formatter.format(m);
    }

}
