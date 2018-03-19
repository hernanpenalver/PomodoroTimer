package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private LocalTime time;
    private Timer timer;

    @FXML
    private Label chronometer;

    @FXML
    private Button play;

    @FXML
    private Button stop;

    public void playChronometer(MouseEvent mouseEvent){
        chronometer.setBorder(Border.EMPTY);
        play.setDisable(true);
        stop.setDisable(false);

        time = LocalTime.of(0,25, 0);

        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 0, 1000);
    }

    public void stopChronometer(MouseEvent mouseEvent){
        timer.cancel();
        stop.setDisable(true);
        play.setDisable(false);
        chronometer.setText("25 : 00");
    }

    private TimerTask getTimerTask(){
        Border customBorder = new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2,2,2,2)));

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String min = String.format("%02d", time.getMinute()); // Dos digitos obligatoriamente
                    String sec = String.format("%02d", time.getSecond());

                    if (time.getMinute() + time.getSecond() != 0) {
                        chronometer.setText(min + " : " + sec);
                        time = time.minusSeconds(1);

                        //Intercalar bordes cada segundo
                        if (chronometer.getBorder().equals(Border.EMPTY)) {
                            chronometer.setBorder(customBorder);
                        } else {
                            chronometer.setBorder(Border.EMPTY);
                        }

                    } else {
                        chronometer.setBorder(customBorder);

                        if (chronometer.getText().compareTo("00 : 00") == 0) {
                            chronometer.setText(" : ");
                        } else {
                            chronometer.setText(min + " : " + sec);
                        }

                    }
                });
            }
        };

        return timerTask;
    }
}
