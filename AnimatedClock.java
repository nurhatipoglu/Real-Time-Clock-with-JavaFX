package sample.OKUL.odev2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class HareketliSaat extends Application {
    @Override
    public void start(Stage primaryStage) {
        SaatOlustur saat = new SaatOlustur();
        BorderPane pane = new BorderPane();
        pane.setCenter(saat); //saat i pane e ekliyorum.

        Scene scene = new Scene(pane, 400, 400); //scene e pane i ekliyorum ve scene in boyutlarını ayarlıyorum.
        primaryStage.setScene(scene); // scene start metotunun parametresinden gelen stage içerisine eklenir
        primaryStage.setTitle("Hareketli Saat"); // GUI nin başlığı.
        primaryStage.show(); // Stage göserilir.
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class SaatOlustur extends Pane {
    private int saniye, dakika, saat;
    public SaatOlustur() {
        //Timeline kullanarak saate hareket özelliğini katıyoruz.
        Timeline hareket = new Timeline(new KeyFrame(Duration.millis(5),e -> {setAnlikZaman();}));
        hareket.setCycleCount(Timeline.INDEFINITE);
        hareket.play(); // Harekeli saatin oluşmasını başlatıyoruz.
    }

    public void setAnlikZaman() {
        Calendar calendar = new GregorianCalendar();
        //bilgisayardan saniye, dakika, saat değerlerini alıyoruz. Yani anlık saati alıyoruz.
        this.saat = calendar.get(Calendar.HOUR_OF_DAY);
        this.dakika = calendar.get(Calendar.MINUTE);
        this.saniye = calendar.get(Calendar.SECOND);
        //saatGorunumu fonksiyonun içinde saati çizeceğim.
        saatGorunumu();
    }

    private void saatGorunumu() {
        // dairenin merkezini, yarıçağıını belirliyoruz.
        double saatYaricap = Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
        double merkezX = getWidth() / 2;
        double merkezY = getHeight() / 2;

        Circle daire = new Circle(merkezX, merkezY, saatYaricap); //scene nin ortasına daire oluşturuyoruz.
        daire.setFill(Color.rgb(255,255,255)); //dairenin arka planını beyaz yaptık.
        daire.setStroke(Color.BLACK); //dairenin etrafındaki çizgiyi siyah yaptık.

        //dairenin içine saatin rakamlarını konumlandırıyoruz.
        Text t1 = new Text(merkezX - 5, merkezY - saatYaricap + 12, "12");
        Text t2 = new Text(merkezX - saatYaricap + 3, merkezY + 5, "9");
        Text t3 = new Text(merkezX + saatYaricap - 10, merkezY + 3, "3");
        Text t4 = new Text(merkezX - 3, merkezY + saatYaricap - 3, "6");

        //saniye gösteren ok için özellikler veriyoruz.
        double sLength = saatYaricap * 0.8;
        double secondX = merkezX + sLength * Math.sin(saniye * (2 * Math.PI / 60));
        double secondY = merkezY - sLength * Math.cos(saniye * (2 * Math.PI / 60));
        Line saniyeOK = new Line(merkezX, merkezY, secondX, secondY);
        saniyeOK.setStroke(Color.PURPLE); //saniye oku mor olsun

        //ok şeklinde yelkovan için özellikler veriyoruz.
        double mLength = saatYaricap * 0.65;
        double xMinute = merkezX + mLength * Math.sin(dakika * (2 * Math.PI / 60));
        double minuteY = merkezY - mLength * Math.cos(dakika * (2 * Math.PI / 60));
        Line yelkovan = new Line(merkezX, merkezY, xMinute, minuteY);
        yelkovan.setStroke(Color.ORANGE); //yelkovan turuncu olsun

        //ok şeklinde akrep için özellikler veriyoruz.
        double hLength = saatYaricap * 0.5;
        double saatX = merkezX + hLength * Math.sin((saat % 12 + dakika / 60.0) * (2 * Math.PI / 12));
        double saatY = merkezY - hLength * Math.cos((saat % 12 + dakika / 60.0) * (2 * Math.PI / 12));
        Line akrep = new Line(merkezX, merkezY, saatX, saatY);
        akrep.setStroke(Color.BLUE); //akrep mavi olsun.

        getChildren().clear();
        getChildren().addAll(daire, t1, t2, t3, t4, saniyeOK, yelkovan, akrep);
        //saat için oluşturduklarımı ekliyorum.
    }

    @Override
    public void setWidth(double yukseklik) {
        super.setWidth(yukseklik);
        saatGorunumu();
    }

    @Override
    public void setHeight(double genislik) {
        super.setHeight(genislik);
        saatGorunumu();
    }
}