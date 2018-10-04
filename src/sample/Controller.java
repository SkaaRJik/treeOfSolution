package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    Accordion accordion;

    public class Cube{
        Color color;

        public Cube(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }


    public void init(){

        Cube[] cubes = new Cube[6];
        cubes[0] = new Cube(Color.DARKBLUE);
        cubes[1] = new Cube(Color.RED);
        cubes[2] = new Cube(Color.GREEN);
        cubes[3] = new Cube(Color.PURPLE);
        cubes[4] = new Cube(Color.LIGHTBLUE);
        cubes[5] = new Cube(Color.ORANGE);

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

        if (er.size() > 1 || !em.isEmpty()) {
            for (Integer key : er.keySet()) {
                TitledPane pane = new TitledPane();
                ScrollPane scrollPane = new ScrollPane();

                Canvas canvas = new Canvas();
                scrollPane.setContent(canvas);
                for (int i = 1; i <= 6; i++) {
                    if (key != i) byPass(key, i, makeACopy(em), makeACopy(er), makeACopy(on));
                }
                this.accordion.getPanes().add(pane);
            }
        }

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
        if(em.containsKey(y) & er.containsKey(y) & em.containsKey(x)){
            on.put(x, new ArrayList<Cube>(2) {{add(er.get(x).get(0)); add(er.get(y).get(0));}});
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
                      HashMap<Integer, List<Cube>> on){





        if (er.size() > 1 || !em.isEmpty()){

            HashMap<Integer, List<Cube>> copyEm = makeACopy(em);
            HashMap<Integer, List<Cube>> copyEr = makeACopy(er);
            HashMap<Integer, List<Cube>> copyOn = makeACopy(on);

            if(r1(x, y, copyEm, copyEr, copyOn)){
                HashSet<Integer> freeKeys = new HashSet<>();
                freeKeys.addAll(copyEm.keySet());
                freeKeys.addAll(copyEr.keySet());
                freeKeys.remove(y);
                for (int key : freeKeys) {
                    byPass(y, key, copyEm, copyEr, copyOn);
                }

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
                copyList.add(new Cube(cube.getColor()));
            }
            copy.put(entry.getKey(), copyList);
        }
        return copy;
    }




}
