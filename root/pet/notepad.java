/**
 *
 * @author Nayan Wadekar
 */
package myNotepad;

import javax.swing.tree.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.*;

public class Notepad extends JFrame implements ActionListener, ItemListener {

    JScrollPane scrl;
    JTextArea ta;
    JTextField stbar;
    JMenuBar mbar;
    JMenu file, format, edit, help;
    JMenuItem open, saveAs, exit, bcolor, fcolor, font, cut, copy, paste, del, about, htopics, selectall, newf, replace, find, time, pcut, pcopy, ppaste, pselect, pdel;
    JCheckBoxMenuItem wrap;
    Container cp;
    JPopupMenu jpm;
    JToolBar toolbar;
    JFileChooser jfc;
    JDialog setfont;
    JPanel jp, status;
    JButton nw, cop, ct, pst, opn, sv;
    Boolean saved = false;
    Point p;
    Runtime r;

    notepad() {

        r = Runtime.getRuntime();
        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setSize(800, 600);
        setUndecorated(true);
        setLocation((int) p.getX() / 5, (int) p.getX() / 5);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(" Notepad");
        initcomp();
        new startup();
        setVisible(true);
        r.gc();
    }

    void initcomp() {

        //===============   FILE MENU   ===========================//

        file = new JMenu("File");
        open = new JMenuItem("Open", 'O');
        saveAs = new JMenuItem("Save As", 'S');
        exit = new JMenuItem("Exit", 'X');
        newf = new JMenuItem("New", 'N');
        file.add(newf);
        file.add(open);
        file.add(saveAs);
        file.addSeparator();
        file.add(exit);
        file.setMnemonic('F');

        newf.addActionListener(this);
        open.addActionListener(this);
        saveAs.addActionListener(this);
        exit.addActionListener(this);

        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        newf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        //===============  FORMAT MENU  ===========================//

        format = new JMenu("Format");
        bcolor = new JMenuItem("Back Color", 'B');
        fcolor = new JMenuItem("Fore Color", 'R');
        font = new JMenuItem("Font", 'f');
        wrap = new JCheckBoxMenuItem("Word Wrap");
        format.add(wrap);
        format.add(font);
        format.addSeparator();
        format.add(fcolor);
        format.add(bcolor);
        format.setMnemonic('O');
        wrap.setMnemonic('W');
        bcolor.addActionListener(this);
        fcolor.addActionListener(this);
        font.addActionListener(this);
        wrap.addItemListener(this);

        //===============  EDIT MENU  ===========================//

        edit = new JMenu("Edit");
        cut = new JMenuItem("Cut", 'T');
        copy = new JMenuItem("Copy", 'C');
        paste = new JMenuItem("Paste", 'P');
        selectall = new JMenuItem("Select All", 'A');
        replace = new JMenuItem("Replace", 'R');
        find = new JMenuItem("Find", 'F');
        time = new JMenuItem("Time/Date", 'D');
        del = new JMenuItem("Delete", 'L');

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(del);
        edit.addSeparator();
        edit.add(find);
        edit.add(replace);
        edit.addSeparator();
        edit.add(selectall);
        edit.add(time);
        edit.setMnemonic('E');

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        del.addActionListener(this);
        selectall.addActionListener(this);
        find.addActionListener(this);
        replace.addActionListener(this);
        time.addActionListener(this);

        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        time.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_MASK));

        //===============  HELP MENU  ===========================//

        help = new JMenu("Help");
        htopics = new JMenuItem("Topics", 'T');
        about = new JMenuItem("About", 'A');
        help.add(htopics);
        help.add(about);
        help.setMnemonic('H');
        htopics.addActionListener(this);
        about.addActionListener(this);
        htopics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));

        //===============  MENUBAR  ===========================//

        mbar = new JMenuBar();
        mbar.add(file);
        mbar.add(edit);
        mbar.add(format);
        mbar.add(help);

        //===============  TEXT AREA  ===========================//

        ta = new JTextArea();
        ta.setFont(new Font("Book Antiqua", 0, 14));
        
        scrl = new JScrollPane(ta);
        ta.setLineWrap(false);
        
        ta.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                int pos = me.getButton();
                if (pos == 3) {
                    jpm.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });

        //===============  Status - Bar  ===========================//

        stbar = new JTextField("  For help , press Ctrl+F1");
        stbar.setEditable(false);
        stbar.setDisabledTextColor(Color.DARK_GRAY);
        stbar.setEnabled(false);
        stbar.setFont(new Font("Arial", 1, 12));

        status = new JPanel();
        status.setLayout(new BorderLayout());
        status.add(stbar);

        //===============  POPUP MENU  ===========================//

        jpm = new JPopupMenu();
        jpm.add(pcut = new JMenuItem("Cut", 'T'));
        jpm.add(pcopy = new JMenuItem("Copy", 'C'));
        jpm.add(ppaste = new JMenuItem("Paste", 'P'));
        jpm.add(pdel = new JMenuItem("Delete", 'D'));
        jpm.addSeparator();
        jpm.add(pselect = new JMenuItem("Select All", 'A'));

        pcut.addActionListener(this);
        pcopy.addActionListener(this);
        pselect.addActionListener(this);
        ppaste.addActionListener(this);
        pdel.addActionListener(this);

        //===============  TOOLBAR MENU  ===========================//

        toolbar = new JToolBar();

        toolbar.add(nw = new JButton("", new ImageIcon("new.gif")));
        toolbar.add(opn = new JButton("", new ImageIcon("open.gif")));
        toolbar.add(sv = new JButton("", new ImageIcon("save.gif")));
        toolbar.add(ct = new JButton("", new ImageIcon("cut.gif")));
        toolbar.add(cop = new JButton("", new ImageIcon("copy.gif")));
        toolbar.add(pst = new JButton("", new ImageIcon("paste.gif")));

        nw.addActionListener(this);
        opn.addActionListener(this);
        sv.addActionListener(this);
        ct.addActionListener(this);
        cop.addActionListener(this);
        pst.addActionListener(this);

        nw.setToolTipText("New File (Ctrl+N)");
        opn.setToolTipText("Open File (Ctrl+O)");
        sv.setToolTipText("Save As (Ctrl+S)");
        ct.setToolTipText("Cut (Ctrl+X)");
        cop.setToolTipText("Copy (Ctrl+C)");
        pst.setToolTipText("Paste (Ctrl+V)");

        //===============  CONTENT PANE   ===========================//

        jp = new JPanel(new BorderLayout());
        jp.add(toolbar, BorderLayout.NORTH);
        jp.add(scrl, BorderLayout.CENTER);

        cp.add(mbar, BorderLayout.NORTH);
        cp.add(jp, BorderLayout.CENTER);
        cp.add(status, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        new notepad();

    }

    public void itemStateChanged(ItemEvent e) {

        Object sr = e.getSource();

        if (sr == wrap) {
            if (wrap.isSelected()) {
                ta.setWrapStyleWord(true);

            } else {
                ta.setWrapStyleWord(false);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        String cmd = e.getActionCommand();

        if (cmd.equals("Back Color")) {

            Color c = JColorChooser.showDialog(this, "Background Color", Color.WHITE);
            ta.setBackground(c);

        } else if (cmd.equals("Exit")) {

            System.exit(0);

        } else if (cmd.equals("Fore Color")) {

            Color c = JColorChooser.showDialog(this, "Foreground Color", Color.BLACK);
            ta.setForeground(c);

        } else if (cmd.equals("Cut") || src == ct) {

            ta.cut();

        } else if (cmd.equals("Copy") || src == cop) {

            ta.copy();

        } else if (cmd.equals("Paste") || src == pst) {

            ta.paste();

        } else if (cmd.equals("Delete")) {

            ta.replaceSelection("");

        } else if (cmd.equals("Select All")) {

            ta.selectAll();

        } else if (cmd.equals("Open") || src == opn) {

            if (saved == false && !(ta.getText().equals(""))) {
                new saved(this);
            }

            jfc = new JFileChooser();
            jfc.addActionListener(this);
            jfc.setFileFilter(new FileNameExtensionFilter("Supported Format", "txt", "java", "html", "htm", "xml", "jsp", "ini", "log", "htt", "css","c","cpp"));
            jfc.setFileHidingEnabled(false);
            jfc.setApproveButtonText("Select");
            jfc.showOpenDialog(this);
            ta.setCaretPosition(0);

        } else if (cmd.equals("New") || src == nw) {

            if (saved == false && !(ta.getText().equals(""))) {
                new saved(this);
            } else {
                setTitle(" Notepad");
                stbar.setText("  For help , press Ctrl+F1");
                ta.setText("");
                saved = false;
            }
            r.gc();
        } else if (cmd.equals("Topics")) {

            new helpTopics();

        } else if (cmd.equals("About")) {

            final JWindow ab = new JWindow();
            JLabel name = new JLabel("    By :  Nayan.M.Wadekar");
            JLabel logo = new JLabel(new ImageIcon("148.png"));
            ab.setSize(350, 315);
            ab.setLocation((int) p.getY(), (int) p.getY() / 2);
            ab.setAlwaysOnTop(true);
            JPanel j = new JPanel();
            JPanel j2 = new JPanel(new FlowLayout());
            j.add(logo);
            j2.add(name);
            j.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
            j2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
            j.setBackground(Color.WHITE);
            j2.setBackground(Color.WHITE);
            ab.add(j, BorderLayout.NORTH);
            ab.add(j2, BorderLayout.CENTER);
            ab.setVisible(true);

            ab.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {

                    ab.dispose();
                }
            });


        } else if (cmd.equals("Font")) {
            new fontDialog(this);

        } else if (cmd.equals("Save As") || src == sv) {

            new savefunc(this);
            r.gc();

        } else if (src == find) {

            new find(this);
        } else if (src == replace) {

            new replace(this);
        } else if (src == time) {

            String ampm[] = {"AM", "PM"};
            String mon[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
            Calendar calendar = GregorianCalendar.getInstance();
            String dtstamp = calendar.get(Calendar.DATE) + "-" + mon[calendar.get(Calendar.MONTH)] + "-" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + " " + ampm[calendar.get(Calendar.AM_PM)];

            int i = ta.getCaretPosition();
            ta.insert(dtstamp, i);
            
        } else if (cmd.equals("ApproveSelection")) {

            try {

                String path = jfc.getSelectedFile().getPath();

                FileInputStream in = new FileInputStream(path);
                int ch;
                String data = "";
                while ((ch = in.read()) != -1) {
                    data += (char) ch;
                }
                ta.setText(data);
                in.close();
                setTitle(jfc.getSelectedFile().getName() + " - Notepad");
                stbar.setText("   Lines : " + ta.getLineCount() + "\tFile :  " + jfc.getSelectedFile().getPath());
                ta.setCaretPosition(data.length());
                ta.requestFocus();

            } catch (Exception ce) {
                System.out.println("Internal error : " +ce);
            }

        } else if (cmd.equals("CancelSelection")) {
        } else {
            System.out.println("Internal error : " + e.getActionCommand());
        }
    }
}

class fontDialog extends JDialog implements ActionListener {

    JList fontname, fontstyle, size;
    JButton ok, cancel, pre;
    notepad jmd;
    JScrollPane namescroll, sizescroll, stylescroll;
    JLabel preview;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String fontlist[] = ge.getAvailableFontFamilyNames();
    String fstyle[] = {"Plain", "Bold", "Italic", "Bold/Italic"};
    String fsize[] = {"14", "16", "18", "20", "22", "24", "28", "30", "32"};
    Point p;

    fontDialog(notepad jmd) {

        this.jmd = jmd;

        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        fontname = new JList(fontlist);
        fontstyle = new JList(fstyle);
        size = new JList(fsize);

        namescroll = new JScrollPane(fontname);
        sizescroll = new JScrollPane(fontstyle);
        stylescroll = new JScrollPane(size);

        JPanel j1 = new JPanel(new FlowLayout());
        j1.setBorder(javax.swing.BorderFactory.createTitledBorder("Name - Style - Size"));
        j1.add(namescroll);
        j1.add(sizescroll);
        j1.add(stylescroll);

        JPanel j2 = new JPanel(new FlowLayout());
        preview = new JLabel("sample SAMPLE");
        j2.add(preview);
        j2.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));

        JPanel j3 = new JPanel();
        j3.setLayout(new FlowLayout());

        pre = new JButton("Preview");
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");

        pre.setMnemonic('P');
        ok.setMnemonic('O');
        cancel.setMnemonic('C');

        j3.add(pre);
        j3.add(ok);
        j3.add(cancel);

        fontname.setSelectionMode(0);
        fontname.setVisibleRowCount(4);
        fontstyle.setSelectionMode(0);
        fontstyle.setVisibleRowCount(4);
        size.setSelectionMode(0);
        size.setVisibleRowCount(4);

        size.setSelectedIndex(0);
        fontname.setSelectedIndex(0);
        fontstyle.setSelectedIndex(0);

        ok.addActionListener(this);
        cancel.addActionListener(this);
        pre.addActionListener(this);

        contentPane.add(j1, BorderLayout.NORTH);
        contentPane.add(j3, BorderLayout.SOUTH);
        contentPane.add(j2, BorderLayout.CENTER);

        setSize(330, 240);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation((int) p.getX() / 2, (int) p.getX() / 2);
        setAlwaysOnTop(true);
        setTitle("Font");
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        Object fname = fontname.getSelectedValue();
        Object style = fontstyle.getSelectedValue();
        Object fontsize = size.getSelectedValue();
        int temp = 0;

        if (style.toString().equals("Bold")) {
            temp = 1;
        } else if (style.toString().equals("Plain")) {
            temp = 0;
        } else if (style.toString().equals("Italic")) {
            temp = 2;
        } else if (style.toString().equals("Bold/Italic")) {
            temp = 3;
        }

        Font f = new Font(fname.toString(), temp, Integer.parseInt(fontsize.toString()));
        
        if (ae.getSource() == ok) {

            jmd.ta.setFont(f);
            this.dispose();

        } else if (ae.getSource() == pre) {
            
            preview.setFont(f);
            
        } else {

            dispose();
        }
    }
}

class startup extends JWindow {

    JProgressBar jpb;
    JLabel name = new JLabel("             Notepad");
    JLabel load = new JLabel(" ");
    Point p;
    JPanel j1, j;

    startup() {

        jpb = new JProgressBar();
        jpb.setBorderPainted(false);

        Container c = getContentPane();

        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        name.setFont(new Font("Haettenschweiler", 0, 68));
        setLocation((int) p.getY(), (int) p.getX() / 2);
        setSize(360, 120);
        Point g = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

        j1 = new JPanel();
        j1.add(name, BorderLayout.WEST);

        j = new JPanel(new BorderLayout());
        j.add(jpb, BorderLayout.CENTER);
        j.add(load, BorderLayout.SOUTH);

        c.setLayout(new BorderLayout());
        c.add(j1, BorderLayout.CENTER);
        c.add(j, BorderLayout.SOUTH);

        setVisible(true);
        setAlwaysOnTop(true);

        try {
            for (int i = 0; i <= 100; i++) {
                jpb.setValue(i);
                Thread.sleep(15);
            }
            setVisible(false);
        } catch (Exception e) {
            System.out.println("Internal Error : "+e);
        }
    }
}

class replace extends JDialog implements ActionListener {

    notepad n;
    JTextField find, rep;
    JButton replace, replaceall,findnext,cancel;
    JCheckBox match;
    JLabel f, r;
    JPanel jp1, jp2,jp3;
    Point p;
    int ind = 0, indx;

    replace(notepad n) {

        this.n = n;

        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setSize(350, 160);
        setLocation((int) p.getY(), (int) p.getX() / 2);
        setTitle("Replace");

        find = new JTextField(10);
        rep = new JTextField(10);

        f = new JLabel("Find what :");
        r = new JLabel("Replace with :");
        
        replace = new JButton("Replace");
        replaceall = new JButton("Replace All");
        findnext=new JButton("Find");
        cancel=new JButton("Cancel");

        match=new JCheckBox("Match case");
        
        replace.setMnemonic('R');
        replaceall.setMnemonic('l');
        findnext.setMnemonic('F');
        match.setMnemonic('C');
        
        match.setSelected(true);
        
        replace.addActionListener(this);
        replaceall.addActionListener(this);
        findnext.addActionListener(this);
        cancel.addActionListener(this);

        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();       
        
        jp1.add(f, BorderLayout.WEST);
        jp1.add(find, BorderLayout.CENTER);
        jp1.add(findnext, BorderLayout.EAST);

        jp2.add(r, BorderLayout.WEST);
        jp2.add(rep, BorderLayout.CENTER);
        jp2.add(replace, BorderLayout.EAST);
        
        jp3.add(match,BorderLayout.WEST);
        jp3.add(replaceall,BorderLayout.CENTER);
        jp3.add(cancel,BorderLayout.EAST);
        
        cp.add(jp1, BorderLayout.NORTH);
        cp.add(jp2, BorderLayout.CENTER);
        cp.add(jp3,BorderLayout.SOUTH);

        setAlwaysOnTop(true);
        setTitle("Replace");
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        String what = find.getText();
        String in = n.ta.getText();
        String rel = rep.getText();

        if (src == replace) {
            
            n.ta.replaceSelection(rel);

        } else if (src == replaceall) {

            n.ta.setText(in.replaceAll(what, rel));

        } else if (src == findnext) {

         
            indx = in.toLowerCase().indexOf(what.toLowerCase(), 0);
            int wlen = what.length();

            if (indx == 0 && (!in.toLowerCase().startsWith(what.toLowerCase(), 0))) {
            } else if (indx == 0 && in.toLowerCase().startsWith(what.toLowerCase(), 0)) {

                if (match.isSelected() && in.startsWith(what, 0)) {
                    indx = in.indexOf(what, ind);
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                } else if (match.isSelected() == false) {
                    indx = in.toLowerCase().indexOf(what.toLowerCase(), ind);
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                }

            } else if (match.isSelected() && !in.startsWith(what, 0)) {

                indx = in.indexOf(what, ind);

                if (indx != -1) {
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                }
            } else if (match.isSelected() == false) {
                if (indx != -1) {
                    indx = in.toLowerCase().indexOf(what.toLowerCase(), ind);
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                }
            }
            if (ind == in.lastIndexOf(what) + wlen && match.isSelected()) {
                ind = 0;
            } else if (ind == in.toLowerCase().lastIndexOf(what.toLowerCase()) + wlen && match.isSelected() == false) {
                ind = 0;
            }

        } else {
            this.dispose();
        }
    }
}

class find extends JDialog implements ActionListener {

    notepad n;
    JTextField find;
    JButton findnext, cancel;
    JCheckBox match;
    JLabel f;
    JPanel jp;
    Point p;
    int ind = 0;

    find(notepad n) {

        this.n = n;

        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setSize(300, 125);
        setLocation((int) p.getY(), (int) p.getY());
        setAlwaysOnTop(true);
        setResizable(false);

        find = new JTextField(10);
        findnext = new JButton("Find Next");
        cancel = new JButton("Cancel");
        match = new JCheckBox("Match case");
        match.setSelected(true);
        f = new JLabel("Find what :");

        findnext.setMnemonic('F');
        match.setMnemonic('c');
        cancel.setMnemonic('l');

        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        jp = new JPanel();
        jp.add(f, BorderLayout.WEST);
        jp.add(find, BorderLayout.CENTER);
        jp.add(match, BorderLayout.EAST);
        cp.add(jp, BorderLayout.NORTH);
        cp.add(findnext);
        cp.add(cancel);

        findnext.addActionListener(this);
        cancel.addActionListener(this);
        setTitle("Find");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        int wlen, indx;

        if (src == findnext) {

            String what = find.getText();
            String in = n.ta.getText();
            indx = in.toLowerCase().indexOf(what.toLowerCase(), 0);
            wlen = what.length();

            if (indx == 0 && (!in.toLowerCase().startsWith(what.toLowerCase(), 0))) {
            } else if (indx == 0 && in.toLowerCase().startsWith(what.toLowerCase(), 0)) {

                if (match.isSelected() && in.startsWith(what, 0)) {
                    indx = in.indexOf(what, ind);
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                } else if (match.isSelected() == false) {
                    indx = in.toLowerCase().indexOf(what.toLowerCase(), ind);
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                }

            } else if (match.isSelected() && !in.startsWith(what, 0)) {

                indx = in.indexOf(what, ind);

                if (indx != -1) {
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                }
            } else if (match.isSelected() == false) {
                if (indx != -1) {
                    indx = in.toLowerCase().indexOf(what.toLowerCase(), ind);
                    n.ta.setSelectionStart(indx);
                    n.ta.setSelectionEnd(indx + wlen);
                    ind = wlen + indx;
                }
            }
            if (ind == in.lastIndexOf(what) + wlen && match.isSelected()) {
                ind = 0;
            } else if (ind == in.toLowerCase().lastIndexOf(what.toLowerCase()) + wlen && match.isSelected() == false) {
                ind = 0;
            }
        } else {
            dispose();
        }

    }
}

class helpTopics extends JDialog implements ActionListener {

    JTextArea jtp;
    Point p;

    helpTopics() {

        jtp = new JTextArea();
        jtp.setEditable(false);
        jtp.setLineWrap(true);
        jtp.setWrapStyleWord(true);

        try {
            int ch;
            String data = "";
            FileInputStream in = new FileInputStream("help.txt");
            while ((ch = in.read()) != -1) {
                data += (char) ch;
            }
            jtp.setText(data);
            in.close();

        } catch (Exception exc) {
            System.out.println("Internal Error : "+exc);
        }

        JPanel j = new JPanel(new BorderLayout());
        j.add(jtp);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(j);

        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setSize(400, 280);
        setAlwaysOnTop(true);
        setTitle("Help topics");
        setLocation((int) p.getX() / 2, (int) p.getX() / 2);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}

class saved extends JDialog implements ActionListener {

    JLabel l;
    JButton yes, no, can, pic;
    JPanel jpl, jpl2;
    Point p;
    notepad n;

    saved(notepad n) {

        this.n = n;

        l = new JLabel("    Do you want to save changes ?");

        yes = new JButton("Yes");
        no = new JButton("No");
        can = new JButton("Cancel");

        yes.addActionListener(this);
        no.addActionListener(this);
        can.addActionListener(this);

        yes.setMnemonic('Y');
        no.setMnemonic('N');
        can.setMnemonic('C');

        Container ctr = getContentPane();
        setSize(250, 100);
        setResizable(false);

        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation((int) p.getY(), (int) p.getY());
        setAlwaysOnTop(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jpl = new JPanel();
        jpl.add(yes);
        jpl.add(no);
        jpl.add(can);

        jpl2 = new JPanel();
        jpl2.add(l, BorderLayout.NORTH);
        jpl2.add(jpl, BorderLayout.SOUTH);

        ctr.add(jpl2);
        setModal(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("yes")) {

            this.dispose();
            new savefunc(n);

        } else if (e.getActionCommand().equalsIgnoreCase("no")) {
            n.ta.setText("");
            n.stbar.setText("  For help , press Ctrl+F1");
            n.setTitle("Notepad");
            n.saved = false;
            this.dispose();
        } else {
            this.dispose();
        }
    }
}

class savefunc {

    notepad n;

    savefunc(notepad n) {

        this.n = n;

        String ext=null;
        
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Supported format", "txt", "html", "xml", "htm", "java", "jsp", "css", "htt", "ini", "log","c","cpp"));
        jfc.setFileHidingEnabled(false);

        String items[] = {".txt",".rtf",".doc",".c",".cpp", ".java", ".jsp",".html", ".xml"};

        JList jcb = new JList(items);
        jfc.setAccessory(jcb);
        
        jfc.showSaveDialog(n);
        
        try {
            
        if(jcb.isSelectionEmpty()==false){
            ext=(String) jcb.getSelectedValue();
        }
        else if(jfc.getSelectedFile().getName().contains(".")){
            ext="";
        }
        else ext=".txt";
       
        
            String path = jfc.getSelectedFile().getPath();
            FileOutputStream out = new FileOutputStream(path + ext);
            String data = n.ta.getText();
            byte buf[] = data.getBytes();
            for (int i = 0; i < buf.length; i++) {
                out.write(buf[i]);
            }
            out.close();
            n.setTitle("Notepad :  " + jfc.getSelectedFile().getName() + ext);
            n.stbar.setText("   Saved to  " + path + ext);
            n.saved = true;
        } catch (Exception re) {
            n.stbar.setText("Internal Error : "+re);
        }
    }
}
    
