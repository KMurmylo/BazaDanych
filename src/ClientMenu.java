package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientMenu extends JWindow {
    JButton backButton;
    JButton showAllButton;
    JButton searchButton;
    JButton addButton;
    ClientMenu(MainMenu parent) {
        super(parent);
        setBounds(parent.getWindowBounds());
        //setBounds(200,200,400,600);
        GridBagLayout layout=new GridBagLayout();
        setLayout(layout);
        layout.rowHeights=new int[]{40,40,40,40};
        GridBagConstraints gbc=new GridBagConstraints();
        showAllButton=new JButton("Show all");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        searchButton=new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addButton=new JButton("Add client");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        backButton=new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        gbc.gridy=0;
        add(showAllButton,gbc);
        gbc.gridy=1;
        add(searchButton,gbc);
        gbc.gridy=2;
        add(addButton,gbc);
        gbc.gridy=3;
        add(backButton,gbc);
        setVisible(true);
    }
}
