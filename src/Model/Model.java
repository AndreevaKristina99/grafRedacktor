package Model;
import javafx.scene.paint.Color;


import java.util.ArrayList;

public class Model {
    public  int pointCount;
public  ArrayList<Points>p;

    public Model() {

        this.pointCount = pointCount;
        this.p = new ArrayList<Points>();

    }


    public int getPointCount() {
        return p.size();//возвращает размер листа с точками
    }

    public void addPoint(Points point)
    {
    p.add(point);//добавляет точку в лист
    }
public void  remuvePoint(Points point)
{
   this.p.remove(point);
}
    public Points getPoint(int i){
        return this.p.get(i);//вернет элемент по индексу
    }
    public  void  deleteArray()
    {
    p.clear();///полностью очишает массив с точками
    }

    //поиск точки по координатам, если такая есть, то вернется ее индекс в массиве, иначе -1
    public int serchPoint(int x, int y){
        int index = -1;
        for (int i = 0; i < this.p.size(); i++){
            if (this.p.get(i).x == x && this.p.get(i).y == y) index = i;
        }
        return index;
    }
/*public  void  deleteColors()
{
    this.colors.clear();//очищает лист цветов
}
    public void addColor(Color color)
    {

        colors.add(color);//добавляем цвет в лист
    }

    public Color getColor(int i){
        return colors.get(i);//получаем цвет по ндуксу из листа
    }

 */

}
