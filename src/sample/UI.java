package sample;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FilenameFilter;
import java.io.OutputStream;



public class UI extends Thread{
    private Settings settings;
    @FXML
    private AnchorPane container;

    private final Object lock = new Object();
    @FXML
    private CheckBox skip1,skip2,skip3,skip4,skip5,skip6,skip7,skip8,play,light1,light2,light3,light4,light5,light6,light7,light8;
    @FXML
    private ComboBox instrument;
    @FXML
    private Slider PulseCount1,PulseCount2,PulseCount3,PulseCount4,PulseCount5,PulseCount6,PulseCount7,PulseCount8,
            Gate1,Gate2,Gate3,Gate4,Gate5,Gate6,Gate7,Gate8,steps,tempo,Pitch1,Pitch2,Pitch3,Pitch4,Pitch5,Pitch6,Pitch7,Pitch8, Decay;

    private File directory;

    private String[] Instrument_Names(){
        directory = new File("src/SoundBank");
        String[] instrument_folders = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return instrument_folders;
    }

    @FXML
    public void initialize(){
        ObservableList<String> options =  FXCollections.observableArrayList(Instrument_Names());
        instrument.setItems(options);
        instrument.setValue(options.get(0));
    }



    public void changeplay(){
        settings.setPlay(play.isSelected());
        synchronized (lock) {
            if (play.isSelected()) {
                lock.notify();
            }
        }
    }

    public void changeinstrument(){settings.setInstrument(instrument.getValue().toString());}

    public void changetempo() { settings.setTempo((int)tempo.getValue()); }

    public void changesteps(){  settings.setSteps((int)steps.getValue());  }

    public void changeskip(){
        settings.setSkip(new boolean[]{
                skip1.isSelected(),
                skip2.isSelected(),
                skip3.isSelected(),
                skip4.isSelected(),
                skip5.isSelected(),
                skip6.isSelected(),
                skip7.isSelected(),
                skip8.isSelected()
        });
    }

    public void changepulsecounts(){
        settings.setPulseCount(new int[]{
                (int)PulseCount1.getValue(),
                (int)PulseCount2.getValue(),
                (int)PulseCount3.getValue(),
                (int)PulseCount4.getValue(),
                (int)PulseCount5.getValue(),
                (int)PulseCount6.getValue(),
                (int)PulseCount7.getValue(),
                (int)PulseCount8.getValue(),

        });
    }

    public void changemode(){
        settings.setGateMode(new int[]{
                (int)Gate1.getValue(),
                (int)Gate2.getValue(),
                (int)Gate3.getValue(),
                (int)Gate4.getValue(),
                (int)Gate5.getValue(),
                (int)Gate6.getValue(),
                (int)Gate7.getValue(),
                (int)Gate8.getValue(),

        });
    }

    public void changepitch(){
        settings.setPitch(new int[]{
                (int)Pitch1.getValue(),
                (int)Pitch2.getValue(),
                (int)Pitch3.getValue(),
                (int)Pitch4.getValue(),
                (int)Pitch5.getValue(),
                (int)Pitch6.getValue(),
                (int)Pitch7.getValue(),
                (int)Pitch8.getValue(),

        });
    }

    public void createlights() {
        SimpleBooleanProperty l1 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l2 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l3 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l4 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l5 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l6 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l7 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty l8 = new SimpleBooleanProperty(false);

        light1.selectedProperty().bind(l1);
        light2.selectedProperty().bind(l2);
        light3.selectedProperty().bind(l3);
        light4.selectedProperty().bind(l4);
        light5.selectedProperty().bind(l5);
        light6.selectedProperty().bind(l6);
        light7.selectedProperty().bind(l7);
        light8.selectedProperty().bind(l8);

        settings.setLights(new SimpleBooleanProperty[]{l1,l2,l3,l4,l5,l6,l7,l8 });
    }

    public void changedecay(){ settings.setDecay((int)Decay.getValue()); }

    public void setSettings( Settings value ) {
        settings = value;
        changeplay();
        changetempo();
        changesteps();
        changeskip();
        changepulsecounts();
        changemode();
        changeinstrument();
        changepitch();
        changedecay();
        createlights();
    }

    public Object getLock(){
        return lock;
    }
}
