package wtxt;

import com.formdev.flatlaf.util.SystemInfo;
import wtxt.core.SoftwareInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.Key;

public class WaveTextEditor {
    static UndoManager undoManager = new UndoManager();
    static File currentFile;
    static JFileChooser chooser = new JFileChooser();
    static JFrame frame = new JFrame();
    static JTextArea textArea = new JTextArea();
    static JScrollPane scrollPane = new JScrollPane(textArea);
    //Menu Bar
    static JMenuBar menuBar = new JMenuBar();
    //Menu
    static JMenu fileMenu = new JMenu("File");
    static JMenu editMenu = new JMenu("Edit");
    static JMenu viewMenu = new JMenu("View");
    static JMenu helpMenu = new JMenu("Help");
    static JMenu formatMenu = new JMenu("Format");
    static JMenu windowMenu = new JMenu("Window");
    //File Menu Items
    static JMenuItem newFile = new JMenuItem("New File..");
    static JMenuItem open = new JMenuItem("Open");
    static JMenuItem openRecent = new JMenuItem("Open Recent");
    static JMenuItem save = new JMenuItem("Save");
    static JMenuItem saveAs = new JMenuItem("Save As");
    //View Menu Items
    static JMenuItem zoomIn = new JMenuItem("Zoom In");
    static JMenuItem zoomOut = new JMenuItem("Zoom Out");
    //Edit Menu Items
    static JMenuItem undo = new JMenuItem("Undo");
    static JMenuItem redo = new JMenuItem("Redo");
    static JMenuItem cut = new JMenuItem("Cut");
    static JMenuItem copy = new JMenuItem("Copy");
    static JMenuItem paste = new JMenuItem("Paste");
    //Help Menu Items
    static JMenuItem about = new JMenuItem("About...");
    //Format Menu Items
    static JMenuItem bold = new JMenuItem("Bold");
    static JMenuItem italic = new JMenuItem("Italic");
    static JMenuItem underline = new JMenuItem("Italic");
    static JMenuItem Underline = new JMenuItem("Italic");
    static JMenuItem strikeThrough = new JMenuItem("Strike-Through");
    static JMenuItem superscript = new JMenuItem("Superscript");
    static JMenuItem subscript = new JMenuItem("Subscript");
    //Window Menu Items
    static JMenuItem minimize = new JMenuItem("Minimize");
    static JMenuItem zoom = new JMenuItem("Zoom");
    static JMenuItem moveWindowToLeft = new JMenuItem("Move Window To Left Side Of The Screen");
    static JMenuItem moveWindowToRight = new JMenuItem("Move Window To Right Side Of The Screen");
    static JMenuItem moveWindowToTopRight = new JMenuItem("Move Window To Top Right Of The Screen");
    static JMenuItem moveWindowToTopLeft = new JMenuItem("Move Window To Top Left Of The Screen");
    static JMenuItem moveWindowToBottomLeft = new JMenuItem("Move Window To Bottom Left Of The Screen");
    static JMenuItem moveWindowToBottomRight = new JMenuItem("Move Window To Bottom Right Of The Screen");
    //Window Menu Items

    //Undo Command
    String[] undoText;
    //Redo Command
    String[] redoText;

    static JPanel container = new JPanel();

    public static void main(String[] args) {
        frame.setTitle("Untitled");
        final Toolkit tk = Toolkit.getDefaultToolkit();
        newFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentFile = null;
                frame.setTitle("Untitled");
                textArea.setText("");
            }
        });
        undo.addActionListener(e -> {
            try {
                undoManager.undo();
            } catch (CannotUndoException cre) {
                cre.printStackTrace();
            }
        });
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                } catch (CannotRedoException cre) {
                    cre.printStackTrace();
                }
            }
        });
        copy.addActionListener(e -> textArea.copy());
        undo.addActionListener(e -> {

        });
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });
        saveAs.addActionListener(e -> {
            int response = chooser.showSaveDialog(frame);
            if (response == JFileChooser.APPROVE_OPTION) {
                try {
                    if(chooser.getSelectedFile().exists()) {
                        if (JOptionPane.showConfirmDialog(frame, "File exist... Overwrite the text file?"
                                ,"File Exists"
                                , JOptionPane.YES_NO_OPTION
                                , JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION){
                            textArea.write(new FileWriter(chooser.getSelectedFile()));
                            currentFile = chooser.getSelectedFile();
                            frame.setTitle(currentFile.getName());
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        save.addActionListener(e -> {
            if(currentFile == null || !currentFile.exists()) {
                int response = chooser.showSaveDialog(frame);
                if (response == JFileChooser.APPROVE_OPTION) {
                    try {
                        if(chooser.getSelectedFile().exists()) {
                            if (JOptionPane.showConfirmDialog(frame, "File exist... Overwrite the text file?"
                                    ,"File Exists"
                                    , JOptionPane.YES_NO_OPTION
                                    , JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION){
                                textArea.write(new FileWriter(chooser.getSelectedFile()));
                                currentFile = chooser.getSelectedFile();
                                frame.setTitle(currentFile.getName());
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
            else{
                try {
                    textArea.write(new FileWriter(currentFile));
                    //Refreshing the file
                    currentFile = new File(currentFile.getAbsolutePath());
                    frame.setTitle(currentFile.getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        textArea.getDocument().addUndoableEditListener(
                e -> {
                    undoManager.addEdit(e.getEdit());
                });
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, tk.getMenuShortcutKeyMaskEx()));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, tk.getMenuShortcutKeyMaskEx()));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, tk.getMenuShortcutKeyMaskEx()));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, tk.getMenuShortcutKeyMaskEx()));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, tk.getMenuShortcutKeyMaskEx()));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, tk.getMenuShortcutKeyMaskEx()));
        openRecent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, tk.getMenuShortcutKeyMaskEx()));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, tk.getMenuShortcutKeyMaskEx()));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, tk.getMenuShortcutKeyMaskEx() | InputEvent.SHIFT_DOWN_MASK));
        //Menu Bar properties on macOS
        if(SoftwareInfo.getPlatform().equals("Mac OS X")){
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );
            System.setProperty( "apple.awt.application.name", "WaveTextEditor" );
            System.setProperty( "apple.awt.application.appearance", "system" );
            if( SystemInfo.isMacFullWindowContentSupported ) {
                frame.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
            }
            frame.getRootPane().putClientProperty( "apple.awt.fullscreenable", true );
        }
        //If it's not macOS, sets to Windows/Linux look with toolbar within the application
        else frame.setJMenuBar(menuBar);

        open.addActionListener(e -> {

            int returnVal = chooser.showOpenDialog(null); //replace null with your swing container
            File file = null;
            if(returnVal == JFileChooser.APPROVE_OPTION)
                file = chooser.getSelectedFile();
            BufferedReader in;
            try {
                assert file != null;
                in = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String line;
            try {
                line = in.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            while(line != null){
                textArea.append(line + "\n");
                try {
                    line = in.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                in.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            currentFile=chooser.getSelectedFile();
            frame.setTitle(currentFile.getName());
        });
        fileMenu.add(newFile);
        fileMenu.add(open);
        fileMenu.add(openRecent);
        fileMenu.addSeparator();
        fileMenu.add(save);
        fileMenu.add(saveAs);
        menuBar.add(fileMenu);
        editMenu.add(copy);
        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.add(cut);
        editMenu.add(paste);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
        scrollPane.setAutoscrolls(true);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

}
