package Examples.DemoExample.UI;

import Examples.DemoExample.Library.Library;
import Examples.DemoExample.Library.Release.Release;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UIController implements UserInterface {

    private static final int LABEL_HEIGHT = 10;
    private static final int LABEL_WIDTH = 50;
    private static final int INPUT_HEIGHT = 20;
    private static final int INPUT_WIDTH = 100;

    private UIEventHandler eventHandler;

    private Frame frame;
    private Label titleLabel;
    private Label artistLabel;
    private Label dateLabel;
    private TextField titleField;
    private TextField artistField;
    private TextField yearField;
    private Button addButton;
    private Button listButton;

    public UIController() {
        setupUi();
    }

    private void setupUi() {
        titleLabel = new Label("Title");
        titleLabel.setBounds(10,50,LABEL_WIDTH,LABEL_HEIGHT);

        artistLabel = new Label("Artist");
        artistLabel.setBounds(10,70, LABEL_WIDTH, LABEL_HEIGHT);

        dateLabel = new Label("Date");
        dateLabel.setBounds(10, 90, LABEL_WIDTH, LABEL_HEIGHT);

        titleField = new TextField();
        titleField.setBounds(70, 50, INPUT_WIDTH, INPUT_HEIGHT);

        artistField = new TextField();
        artistField.setBounds(70, 70, INPUT_WIDTH, INPUT_HEIGHT);

        yearField = new TextField();
        yearField.setBounds(70, 90, INPUT_WIDTH, INPUT_HEIGHT);

        addButton = new Button("Add");
        addButton.setBounds(10, 120, 50, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(addButton)) {
                    eventHandler.onAddButtonClick();
                }
            }
        });

        listButton = new Button("List");
        listButton.setBounds(70, 120, 50, 25);
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(listButton)) {
                    eventHandler.onListButtonClick();
                }
            }
        });

        frame = new Frame();
        frame.add(titleLabel);
        frame.add(titleField);
        frame.add(artistLabel);
        frame.add(artistField);
        frame.add(dateLabel);
        frame.add(yearField);
        frame.add(addButton);
        frame.add(listButton);
        frame.setLayout(null);
        frame.setSize(200,175);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventHandler.onClose();
                System.exit(0);
            }
        });
    }

    @Override
    public String getTitleInput() {
        return titleField.getText();
    }

    @Override
    public String getArtistInput() {
        return artistField.getText();
    }

    @Override
    public int getReleaseYear() {
        return Integer.parseInt(yearField.getText());
    }

    @Override
    public void displayLibrary(Library library) {
        Frame libFrame = new Frame();
        List libList = new List(library.size());


        for(Release release : library) {
            libList.add(release.getStringRepresentation());
        }

        libFrame.add(libList);
        libFrame.setSize(200,200);
        libFrame.setVisible(true);
        libFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                libFrame.dispose();
            }
        });
    }

    @Override
    public void registerEventHandler(UIEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }
}
