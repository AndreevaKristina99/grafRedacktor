package sample;
import Model.Model;
import Model.Points;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller implements Initializable {
    public Canvas canva;
    public ColorPicker colorpik;
    public Button butt_lastick;
    public RadioButton rad;
    public RadioButton rad_lastick;
    public RadioButton rad_line;
    public  String flag;
    public Slider sliders;
    public Button lastick;
    public Button NewLine;
    Model model;
    private GraphicsContext gr;
    //класс для работы с изображениями
    Image bgImage;
    double bgX, bgY, bgW = 100.0, bgH = 100.0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model=new Model();
        gr = canva.getGraphicsContext2D();
        SliderTol();
    }
    public void SliderTol() {//толщина линии
        sliders.setMin(1);
        sliders.setMax(10);
        sliders.setValue(1);
        colorpik.setValue(Color.BLACK);
        flag =NewLine.getId();
    }
    public void update(Model model) {
        gr.clearRect(0, 0, 500, 500);//очищаем канву

        for (int i = 0; i < model.getPointCount(); i++) {
            gr.setFill(model.getPoint(i).getColor());
            gr.fillOval(model.getPoint(i).getX(),model.getPoint(i).getY(),model.getPoint(i).getwP() ,model.getPoint(i).gethP());
        }
    }
//////////////
    public void clik_canvas(MouseEvent mouseEvent) {
     //   model.addPoint(new Points((int) mouseEvent.getX(), (int) mouseEvent.getY()));
        //update(mode);
    }
//////////////////
    public void sohranit(ActionEvent actionEvent) throws IOException {//сохранение картинки

       FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Сохранение файла....");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =new FileChooser.ExtensionFilter("Картинка", "*.png");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File        file = fileChooser.showSaveDialog(canva.getScene().getWindow());//Указываем текущую сцену CodeNote.mainStage
       BufferedImage bi = new BufferedImage((int)canva.getWidth(),(int)canva.getHeight(),BufferedImage.TYPE_INT_RGB);

        if (file != null) {
           ImageIO.write(bi,"png",file);
            System.out.println(" "+file);
        }

    }

    public void zagruzit(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();//класс работы с диалоговым окном
        fileChooser.setTitle("Выберите изображениe...");//заголовок диалога
//задает фильтр для указанного расшиерения
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображение", "*.bmp"));

        File loadImageFile = fileChooser.showOpenDialog(canva.getScene().getWindow());

        if (loadImageFile != null) {
            //Open
            System.out.println("Процесс открытия файла");
            initDraw(gr,loadImageFile);
            update(model);
        }
    }
    private void initDraw(GraphicsContext gc,File file){
        String str=file.getPath();//переменная для пути к картинки
//получаем длину и ширину канваса
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        bgImage = new Image(file.toURI().toString());
        bgX = canvasWidth/2;
        bgY = canvasHeight/2 ;
        gc.drawImage(bgImage, bgX, bgY, bgW, bgH);//помещаем изображение на канву

        PixelReader pixelReader = bgImage.getPixelReader();
        double yr=canva.getHeight()/bgImage.getHeight();
        double xr=canva.getWidth()/bgImage.getWidth();
        //попиксельная отрисовка изображения на канву
        for (int y = 0; y < bgImage.getHeight(); y++) {
            for (int x = 0; x < bgImage.getWidth(); x++) {
              Color color = pixelReader.getColor(x, y);
                Points point =new Points(x,y);
                point.setColor(color);
                point.setSizePoint(xr,yr);
                model.addPoint(point);

                //mod.addPoint(new Point(x,y));
               // mod.addColor(color);
            }
        }
       // update(mod);
    }



    public void clean(ActionEvent actionEvent) {
        //очищает и канву и лист цветов и лист точек
        gr.clearRect(0,0,canva.getHeight(),canva.getWidth());
        model.deleteArray();

    }

    public void print(MouseEvent mouseEvent) {//для неприрывной линии
        //update();
        Points points = new Points((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (flag == NewLine.getId()) {
            points.setColor(colorpik.getValue());
            points.setSizePoint(sliders.getValue(), sliders.getValue());
            model.addPoint(points);

        } else {
            model.remuvePoint(points);
        }
        update(model);
        // }
    }

    public void m_lastick_but(ActionEvent actionEvent) {
        flag=NewLine.getId();
    }

    public void m_line(ActionEvent actionEvent) {
        flag=lastick.getId();
    }
}