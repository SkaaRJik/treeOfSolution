package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;

import java.util.*;

public class Controller {
    @FXML
    Accordion accordion;

    private class Cube{
        Color color;
        int x, y;
        int level;
        int size = 20;
        public Cube(Color color, int x, int y, int level) {
            this.color = color;
            this.x = x;
            this.y = y;
            this.level = level;
        }

        public Color getColor() {
            return color;
        }

        public void putCubeUnder(Cube cube){
            cube.x = this.x;
            cube.y = this.y+size;
        }

        public void drawCube(GraphicsContext graphicsContext){
            graphicsContext.setFill(color);
            graphicsContext.fillRect(x, y,size,size);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getLevel() {
            return level;
        }

        public int getSize() {
            return size;
        }
    }



    public void init(){

        Cube[] cubes = new Cube[6];
        cubes[0] = new Cube(Color.DARKBLUE, 30, 10, 1);
        cubes[1] = new Cube(Color.RED, 55, 10, 1);
        cubes[2] = new Cube(Color.GREEN, 80, 10, 1);
        cubes[3] = new Cube(Color.PURPLE, 105, 10, 1);
        cubes[4] = new Cube(Color.LIGHTBLUE, 130, 10, 1);
        cubes[5] = new Cube(Color.ORANGE, 155, 10, 1);

        HashMap<Integer, List<Cube>> em = new HashMap<>();
        HashMap<Integer, List<Cube>> er = new HashMap<>();
        HashMap<Integer, List<Cube>> on = new HashMap<>();


        em.put(new Integer(2), new ArrayList<Cube>(1){{add(cubes[1]);}});
        em.put(new Integer(3), new ArrayList<Cube>(1){{add(cubes[2]);}});
        em.put(new Integer(4), new ArrayList<Cube>(1){{add(cubes[3]);}});
        em.put(new Integer(5), new ArrayList<Cube>(1){{add(cubes[4]);}});

        er.put(new Integer(1), new ArrayList<Cube>(1){{add(cubes[0]);}});
        er.put(new Integer(2), new ArrayList<Cube>(1){{add(cubes[1]);}});
        er.put(new Integer(3), new ArrayList<Cube>(1){{add(cubes[2]);}});
        er.put(new Integer(6), new ArrayList<Cube>(1){{add(cubes[5]);}});

        HashSet<Cube> cubesSet ;

        if (er.size() > 1 || !em.isEmpty()) {
            for (Integer key : er.keySet()) {


                Canvas canvas = new Canvas(300, 275);

                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                cubesSet = new HashSet<>(6);
                for (int i = 0; i < cubes.length; i++) {
                    /*if(er.get(i) != null )
                        cubesSet.add(er.get(i).get(0));
                    if(em.get(i) != null )
                        cubesSet.add(em.get(i).get(0));*/
                    cubes[i].drawCube(graphicsContext);
                }

                for (int i = 1; i <= 6; i++) {
                    if (key != i) byPass(key, i, makeACopy(em), makeACopy(er), makeACopy(on), graphicsContext);
                }
                TitledPane pane = new TitledPane();
                ScrollPane scrollPane = new ScrollPane();
                pane.setContent(scrollPane);
                scrollPane.setContent(canvas);
                this.accordion.getPanes().add(pane);
            }
        }
        this.accordion.getPanes().get(0);

    }

    /*
    1)	On(x,y) – "Кубик x находится на кубике y";
    2)	Em(y)    – "Кубик y не находится под другим кубиком";
    3)	Er(х)      – "Кубик x находится на земле".

    r1 = <C1, A1, D1>, где
        C1 = {Em(y), Er(y), Er(x)}
        A1 = {On(x,y)}
        D1 = {Em(y), Er(x)}

    r2 = <C2, A2, D2>, где
        C2 = { Em(x), Er(x), Em(y), On(y,z) }
        A2 = {On(x,y)}
        D2 = {Em(x), Er(y)}

     */


    public boolean r1(int x, int y, HashMap<Integer, List<Cube>> em, HashMap<Integer, List<Cube>> er, HashMap<Integer, List<Cube>> on){
        if(em.containsKey(x) & er.containsKey(x) & em.containsKey(y)){
            on.put(x, new ArrayList<Cube>(2) {{add(er.get(x).get(0)); add(er.get(y).get(0));}});
            on.get(x).get(0).putCubeUnder(on.get(x).get(1));
            em.remove(y);
            er.remove(x);
            return true;
        };
        return false;
    }

   /* public boolean c2(int x, int y, int z, HashMap<Integer, List<Cube>> em, HashMap<Integer, List<Cube>> er, HashMap<Integer, List<Cube>> on){
        return em.containsKey(x) & er.containsKey(x) & em.containsKey(y) & on.get(0).containsKey(y) & on.get(1).containsKey(z);
    }*/

    public int byPass(int x, int y,
                      HashMap<Integer, List<Cube>> em,
                      HashMap<Integer, List<Cube>> er,
                      HashMap<Integer, List<Cube>> on, GraphicsContext graphicsContext){





        if (er.size() > 1 || !em.isEmpty()){

            HashMap<Integer, List<Cube>> copyEm = makeACopy(em);
            HashMap<Integer, List<Cube>> copyEr = makeACopy(er);
            HashMap<Integer, List<Cube>> copyOn = makeACopy(on);

                HashSet<Integer> freeKeys = new HashSet<>();
                freeKeys.addAll(copyEm.keySet());
                freeKeys.addAll(copyEr.keySet());
                freeKeys.remove(y);
                for (int key : freeKeys) {
                    byPass(y, key, copyEm, copyEr, copyOn, graphicsContext);
                }
            }




        return 0;

    }



    private HashMap<Integer, List<Cube>> makeACopy(HashMap<Integer, List<Cube>> original){
        HashMap<Integer, List<Cube>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<Cube>> entry : original.entrySet())
        {
            List<Cube> list = entry.getValue();
            List<Cube> copyList = new ArrayList<>(list.size());
            for(Cube cube : list){
                copyList.add(new Cube(cube.getColor(), cube.getX(), cube.getY(), cube.getLevel()));
            }
            copy.put(entry.getKey(), copyList);
        }
        return copy;
    }




}
