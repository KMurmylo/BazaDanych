import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionSearch extends JWindow {
    JTextField idField;
    JTextField idKartyField;
    JTextField odbiorcaField;
    JTextField nazwaField;
    DateField dataOdField;
    DateField dataDoField;
    JTextField iloscOdField;
    JTextField iloscDoField;
    JButton searchButton;
    JButton cancelButton;
    Boolean firstCondition;
    MainMenu parent;
    JLabel warningLabel;
    TransactionSearch(MainMenu parent){
        super(parent);
        this.parent=parent;
        setBounds(parent.getWindowBounds());
        GridBagLayout layout=new GridBagLayout();
        layout.columnWidths=new int[]{100,100};
        GridBagConstraints gbc=new GridBagConstraints();
        setLayout(layout);
        gbc.gridy=0;
        add(new JLabel("ID: "),gbc);
        gbc.gridx=1;
        idField=new JTextField(15);
        add(idField,gbc);
        gbc.gridy++;gbc.gridx=0;
        add(new JLabel("Id karty:"),gbc);
        idKartyField=new JTextField(15);
        gbc.gridx=1;
        add(idKartyField,gbc);
        gbc.gridy++;gbc.gridx=0;
        add(new JLabel("Odbiorca:"),gbc);
        gbc.gridx=1;
        odbiorcaField=new JTextField(15);
        add(odbiorcaField,gbc);
        gbc.gridy++; gbc.gridx=0;
        add(new JLabel("Nazwa:"),gbc);
        nazwaField=new JTextField(15);
        gbc.gridx=1;
        add(nazwaField,gbc);
        gbc.gridy++;gbc.gridx=0;
        add(new JLabel("Data od:"),gbc);
        gbc.gridx=1;
        dataOdField=new DateField(15);
        add(dataOdField,gbc);
        gbc.gridx=2;
        add(new JLabel("do:"),gbc);

        gbc.gridx=3;
        dataDoField=new DateField(15);
        add(dataDoField,gbc);

        gbc.gridy++;gbc.gridx=0;
        add(new JLabel("Ilosc od:"),gbc);
        iloscOdField=new JTextField(15);
        gbc.gridx=1;
        add(iloscOdField,gbc);
        gbc.gridx=2;
        add(new JLabel("do:"),gbc);
        iloscDoField=new JTextField(15);
        gbc.gridx=3;
        add(iloscDoField,gbc);
        searchButton=new JButton("Search");
        cancelButton=new JButton("Cancel");
        searchButton.addActionListener(e-> search());
        cancelButton.addActionListener(e-> dispose());
        gbc.gridy++;
        gbc.gridx=0;
        add(searchButton,gbc);
        gbc.gridx=1;
        add(cancelButton,gbc);
        gbc.gridy++;
        gbc.gridx=0;
        gbc.gridwidth=2;
        warningLabel=new JLabel("");
        add(warningLabel,gbc);

        setVisible(true);

    }
    private void search(){

        ResultSet results;

        String query=buildQuery();
        System.out.println(query);


        try{
            results=DBConnection.getConnection().prepareStatement(query).executeQuery();
            new ResultsWindow(parent,new String[]{"id","idKarty","odbiorca","DataWykonania","nazwa","ilosc"},results);
        }
        catch (SQLException e){

            warningLabel.setText(e.getMessage());
        }
    }
    private String buildQuery(){
        firstCondition=true;

        return "Select * from platnoscKarta "
                +equalCondition("id",idField)
                +equalCondition("idKarty",idKartyField)
                +equalCondition("odbiorca",odbiorcaField)
                +likeCondition("nazwa",nazwaField)
                +greaterCondition("ilosc",iloscOdField)
                +smallerCondition("ilosc",iloscDoField)
                +greaterCondition("dataWykonania",dataOdField)
                +smallerCondition("dataWykonania",dataDoField);

    }
    private String equalCondition(String name,JTextField textField){
        String result;
        if(textField.getText().length()>0){
            result=(firstCondition?" where ":" AND ")+name+"="+textField.getText();
            firstCondition=false;}
        else result="";
        return result;
    }
    private String likeCondition(String name,JTextField textField){
        String result;
        if(textField.getText().length()>0){
            result=(firstCondition?" where ":" AND ")+name+" LIKE \"%"+textField.getText()+"%\"";
            firstCondition=false;}
        else result="";
        return result;

    }
    private String greaterCondition(String name,DateField textField){
        String result;
        if(textField.getText().length()>0&& !textField.getText().equals(DateField.prompt) ){
            result=(firstCondition?" where ":" AND ")+name+" > \""+textField.getText()+"\"";
            firstCondition=false;}
        else result="";
        return result;
    }
    private String smallerCondition(String name,DateField textField){
        String result;
        if(textField.getText().length()>0&& !textField.getText().equals(DateField.prompt)){
            result=(firstCondition?" where ":" AND ")+name+" < \""+textField.getText()+"\"";
            firstCondition=false;}
        else result="";
        return result;
    }
    private String greaterCondition(String name,JTextField textField){
        String result;
        if(textField.getText().length()>0 ){
            result=(firstCondition?" where ":" AND ")+name+" > \""+textField.getText()+"\"";
            firstCondition=false;}
        else result="";
        return result;
    }
    private String smallerCondition(String name,JTextField textField){
        String result;
        if(textField.getText().length()>0){
            result=(firstCondition?" where ":" AND ")+name+" < \""+textField.getText()+"\"";
            firstCondition=false;}
        else result="";
        return result;
    }
    class DateField extends JTextField implements FocusListener {
        static final String prompt="rrrr-mm-dd";
        DateField(int i){
            super(i);
            setText(prompt);
            addFocusListener(this);
        }
        @Override
        public void focusGained(FocusEvent e) {
            if(getText().equals(prompt))
                setText("");
        }

        @Override
        public void focusLost(FocusEvent e) {
            if(getText().length()==0)
                setText(prompt);
        }
    }
}
