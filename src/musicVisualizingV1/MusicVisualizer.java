package musicVisualizingV1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import musicVisualizingV1.jfugue.JFugueException;
import musicVisualizingV1.jfugue.Pattern;
import musicVisualizingV1.jfugue.Player;

public class MusicVisualizer extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/** True if the game is currently "running", i.e. the game loop is looping */
	
	private static File source;
	private String[] v = new String[16];
	private String[][] vtokens = new String[16][];
	private int voices = 0;
	private int[] vpos = new int[16];
	
	private Color[] noteColors = {
			new Color(0  , 0  , 255),
			new Color(102, 0  , 204),
			new Color(102, 0  , 153),
			new Color(204, 0  , 153),
			new Color(255, 0  , 0  ),
			new Color(255, 153, 0  ),
			new Color(255, 255, 0  ),
			new Color(204, 204, 0  ),
			new Color(0  , 170, 0  ),
			new Color(0  , 255, 153),
			new Color(0  , 255, 255),
			new Color(0  , 102, 255),
	};
	
	private DrawNote[] channels = {
			new DrawNote(0  , 0  , 200, 200, Color.black),
			new DrawNote(200, 0  , 200, 200, Color.black),
			new DrawNote(400, 0  , 200, 200, Color.black),
			new DrawNote(600, 0  , 200, 200, Color.black),
			new DrawNote(0  , 200, 200, 200, Color.black),
			new DrawNote(200, 200, 200, 200, Color.black),
			new DrawNote(400, 200, 200, 200, Color.black),
			new DrawNote(600, 200, 200, 200, Color.black),
			new DrawNote(0  , 400, 200, 200, Color.black),
			new DrawNote(200, 400, 200, 200, Color.black),
			new DrawNote(400, 400, 200, 200, Color.black),
			new DrawNote(600, 400, 200, 200, Color.black),
			new DrawNote(0  , 600, 200, 200, Color.black),
			new DrawNote(200, 600, 200, 200, Color.black),
			new DrawNote(400, 600, 200, 200, Color.black),
			new DrawNote(600, 600, 200, 200, Color.black),
	};

	public MusicVisualizer () {
		JFrame container = new JFrame("Music Visualizer");
		
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800,800));
		panel.setLayout(null);
		
		setBounds(0,0,800,800);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	
	public Color parseNote(String note) {
		try {
			//Assuming canon.mid input therefore D major
			int index = 0;
			int slen = note.length();
			int noteNumber;
			switch(note.charAt(index)) {
		        case 'D' : noteNumber = 0; break;
		        case 'E' : noteNumber = 2; break;
		        case 'F' : noteNumber = 3; break;
		        case 'G' : noteNumber = 5; break;
		        case 'A' : noteNumber = 7; break;
		        case 'B' : noteNumber = 9; break;
		        case 'C' : noteNumber = 10; break;
		        default : throw new JFugueException(JFugueException.NOTE_EXC, note);
		    }
		    index++;
		
		    // Check for #, b, or n (sharp, flat, or natural) modifier
		    boolean checkForModifiers = true;
		    while (checkForModifiers) {
		        if (index < slen)
		        {
		            switch(note.charAt(index)) {
		                case '#' : index++; noteNumber++;  if (noteNumber == 12) noteNumber = 0;  break;
		                case 'B' : index++; noteNumber--;  if (noteNumber == -1) noteNumber = 11; break;
		                case 'N' : index++; checkForModifiers = false; break;
		                default : checkForModifiers = false; break;
		            }
		        } else {
		            checkForModifiers = false;
		        }
		    }
		    return noteColors[noteNumber];
		} catch (Exception e) {
			return Color.black;
		}
	}
	
	public void animate() {
		Sequencer sequencer = null;
		try {
	        sequencer = MidiSystem.getSequencer();
	        sequencer.setSequence(MidiSystem.getSequence(source));
//	        float songLength = sequencer.getMicrosecondLength();
//	        float tempoFactor = sequencer.getTempoFactor();
//	        float tempoBPM = sequencer.getTempoInBPM();
//	        float tempoMPQ = sequencer.getTempoInMPQ();
//	        System.out.println(songLength);
//	        System.out.println(tempoFactor);
//	        System.out.println(tempoBPM);
//	        System.out.println(tempoMPQ);
	        
	        sequencer.open();
	        sequencer.start();
        	// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,800);
	        while(sequencer.isRunning()) {
                try {
                    long runTime = sequencer.getMicrosecondPosition();
//	    	        	System.out.print("@" + runTime + " ");

                    g = (Graphics2D) strategy.getDrawGraphics();
//	    				g.setColor(Color.black);


                    //Assuming program will loop fast enough to clear out the non-time/note tokens
                    for(int i = 0; i  < voices; i++) {
                        if (vpos[i] < vtokens[i].length) {
//		                		System.out.println(i + " " + vpos[i]);

                            float time = Long.parseLong(vtokens[i][vpos[i]].substring(1));
                            time = time / 120;
                            time = time * 500000;
                            if(time <= runTime) {

                                channels[i].setColor(parseNote(vtokens[i][vpos[i]+1]));
                                channels[i].draw(g);
                                vpos[i] += 2;
                            }
                        }
                    }


                    // finally, we've completed drawing so clear up the graphics
                    // and flip the buffer over
                    g.dispose();
                    strategy.show();

                    Thread.sleep(10); // Check every 10 millisecond
                } catch(InterruptedException ignore) {
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Error comparing time tokens to sequence position");
                    e.printStackTrace();
                    System.exit(0);
                    break;
                } catch (Exception e) {
                    System.err.println("Some other error(s) occurred: " + e.getMessage());
                    System.exit(0);
                }
	        }

		} catch(Exception e) {
			System.err.println(e.toString());
		} finally {
		    // Close resources
		    sequencer.stop();
		    sequencer.close();
		}
	}
	
	public void init() {
		Player jFuguePlayer = new Player();
		Pattern jFuguePattern = null;
		try {
			jFuguePattern = jFuguePlayer.loadMidi(source);
//			System.out.println(jFuguePattern);
//			jFuguePlayer.play(jFuguePattern);
//			jFuguePlayer.playMidiDirectly(source);
		} catch (Exception e) {
			System.err.println("Error loading midi file");
			System.exit(1);
		}
		int channel = 0;
		for (int i = 0; i < 16; i++) {
			v[i] = "";
			vpos[i] = 2;
		}
		String[] token = jFuguePattern.getTokens();
		for (int i = 0; i < token.length; i++) {
			switch(token[i]) {
				case "V0":
					channel = 0;
					break;
				case "V1":
					channel = 1;
					break;
				case "V2":
					channel = 2;
					break;
				case "V3":
					channel = 3;
					break;
				case "V4":
					channel = 4;
					break;
				case "V5":
					channel = 5;
					break;
				case "V6":
					channel = 6;
					break;
				case "V7":
					channel = 7;
					break;
				case "V8":
					channel = 8;
					break;
				case "V9":
					channel = 9;
					break;
				case "V10":
					channel = 10;
					break;
				case "V11":
					channel = 11;
					break;
				case "V12":
					channel = 12;
					break;
				case "V13":
					channel = 13;
					break;
				case "V14":
					channel = 14;
					break;
				case "V15":
					channel = 15;
					break;
				default:
					v[channel] += (token[i] + " ");
					break;
			}
		}
		
		for (int i = 0; i < 16; i++) {
			vtokens[i] = v[i].split(" ");
			if(vtokens[i].length > 1) {
				voices++;
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			source = new File(in.readLine());
		} catch (Exception e) {
			System.err.println("Error finding file");
			System.exit(0);
		}
		MusicVisualizer mv = new MusicVisualizer();
		mv.init();
		mv.animate();

	}
}
