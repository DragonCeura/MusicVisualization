package musicVisualizingV2;

import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.staccato.StaccatoParserListener;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class MVMidiParser {

    private static final int NOTE_ON = 0x90; // 144
    private static final int NOTE_OFF = 0x80; // 128
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final HashMap<Integer, String> NOTE_MAP;
    static {
        NOTE_MAP = new HashMap<>();
        NOTE_MAP.put(0, "C");
        NOTE_MAP.put(1, "C#");
        NOTE_MAP.put(2, "D");
        NOTE_MAP.put(3, "D#");
        NOTE_MAP.put(4, "E");
        NOTE_MAP.put(5, "F");
        NOTE_MAP.put(6, "F#");
        NOTE_MAP.put(7, "G");
        NOTE_MAP.put(8, "G#");
        NOTE_MAP.put(9, "A");
        NOTE_MAP.put(10, "A#");
        NOTE_MAP.put(11, "B");
    }

    public static void parse() throws InvalidMidiDataException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Please provide the MIDI file to play: ");
        String midiPath = input.next();
        File midiFile = new File(midiPath);
        Sequence sequence = MidiSystem.getSequence(midiFile);

        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else if (message instanceof MetaMessage) {
                    System.out.println("MetaMessage: " + ((MetaMessage) message).getType());
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }
            trackNumber++;

            System.out.println();
        }

        MidiParser parser = new MidiParser();
        StaccatoParserListener listener = new StaccatoParserListener();
        parser.addParserListener(listener);
        parser.parse(MidiSystem.getSequence(new File(midiPath)));
        Pattern staccatoPattern = listener.getPattern();
        System.out.println(staccatoPattern);

        /**
         Sequencer sequencer = null;
         try {
             sequencer = MidiSystem.getSequencer();
             sequencer.setSequence(MidiSystem.getSequence(midiFile));

             sequencer.open();
             sequencer.start();
             while(sequencer.isRunning()) {
                 try {
                    long runTime = sequencer.getMicrosecondPosition();
                 } catch (Exception e) {
                     e.printStackTrace();
                     System.err.println("Some other error(s) occurred: " + e.getMessage());
                     System.exit(0);
                 }
             }

         } catch(Exception e) {
             e.printStackTrace();
         } finally {
             // Close resources
             sequencer.stop();
             sequencer.close();
         }
         **/
    }
}
