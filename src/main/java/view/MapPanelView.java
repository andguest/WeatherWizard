package view;

import interface_adapter.CompareCities.CompareCitiesController;
import interface_adapter.CompareCities.CompareCitiesViewModel;
import interface_adapter.SearchResult.SearchResultController;
import interface_adapter.alert_pop.AlertPopController;
import interface_adapter.converter.ConverterController;
import interface_adapter.nearby_list.NearbyListController;
import interface_adapter.weather.WeatherController;
import interface_adapter.weather.WeatherViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* This class responsible for creating the Map Subpanel of the main. The Map subpanel itself contains 4 parts:
*   1. city input panel where user can type the city name. This is connected to an Action Lisenter, which pass infor
* to our weatherContoller Class.
*   2. date input panel
*   4. compare to button
*   3. mapimagepanel.getDisplayfield where we display the image of the map using Jlabel format.
 */
@SuppressWarnings("checkstyle:WriteTag")
public class MapPanelView extends JPanel implements ActionListener {
    private final LabelTextPanel cityinputpanel;
    private final LabelTextPanel dateinputpanel;
    private final LabelTextPanel comparetopanel;
    private final MapImagepanel mapimagepanel;

    private final JTextField cityinputfield1 = new JTextField(20);
    private final JTextField dateinputfield = new JTextField(20);
    private final JTextField cityinputfield2 = new JTextField(20);
    private final int mappanelwidth = 370;
    private final int mappanelheight = 500;

    private SearchResultController searchResultController;
    private WeatherController weatherController;
    private CompareCitiesController compareCitiesController;
    private NearbyListController nearbyListController;
    private AlertPopController alertPopController;
    private final double torontoLatitude = 43.70011;
    private final double torontoLongitude = -79.4163;

    public MapPanelView(WeatherViewModel weatherViewModel) {
        // by default set the map center be Toronto.
        mapimagepanel = new MapImagepanel(weatherViewModel, torontoLatitude, torontoLongitude);
        // when we get one city name -> weather contoller
        cityinputfield1.addActionListener(
                event -> {
                    // if the event is coming from cityinput field, execute weather controller, check if empty
                    if (event.getSource() == cityinputfield1 && cityinputfield1.getText().length() > 0) {
                        weatherController.execute(cityinputfield1.getText());
                        cityinputfield1.setText("");
                    }
                    else {
                        cityinputfield1.setText("can not return empty");
                    }
                }
        );
        // if Compare to another city -> CompareCityController
        cityinputfield2.addActionListener(
                event -> {
                    if (cityinputfield1.getText().length() > 0 && cityinputfield2.getText().length() > 0) {
                        compareCitiesController.execute(cityinputfield1.getText(), cityinputfield2.getText());
                        final CompareCitiesViewModel compareCitiesViewModel = new CompareCitiesViewModel();
                        new CompareCitiesView(compareCitiesViewModel);
                        cityinputfield1.setText("");
                        cityinputfield2.setText("");
                    }
                    else {
                        cityinputfield2.setText("can not return empty");
                    }
                }
        );
        cityinputpanel = new LabelTextPanel(new JLabel("search city"), cityinputfield1);
        dateinputpanel = new LabelTextPanel(new JLabel("date"), dateinputfield);
        comparetopanel = new LabelTextPanel(new JLabel("Compare To"), cityinputfield2);

        dateinputfield.addActionListener(
                // if this event is coming from dateinput field, execute searchresult contoller
                event -> {
                    if (event.getSource() == dateinputfield) {
                        searchResultController.execute(cityinputfield1.getText(), dateinputfield.getText());
                        cityinputfield1.setText("");
                        dateinputfield.setText("");
                    }
                });
//        this.setSize(mappanelwidth, mappanelheight);
        this.setPreferredSize(new java.awt.Dimension(mappanelwidth, mappanelheight));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(cityinputpanel);
        this.add(dateinputpanel);
        this.add(comparetopanel);
        // adding a Jlabel
        this.add(mapimagepanel.getDisplayfield());

    }

    @Override
    /*
    * prints a message to the console when an action event occurs. Will look something like "Enter city name"
     */
    public void actionPerformed(ActionEvent event) {
        System.out.println("Enter" + event.getActionCommand());

    }
//    public void propertyChange(PropertyChangeEvent evt) {
//        final WeatherState state = (WeatherState) evt.getNewValue();
//        setFields(state);
//        if (state.getError() != null) {
//            JOptionPane.showMessageDialog(this, state.getError(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void setFields(WeatherState state) {
//        cityinputfield.setText(state.getWeather());
//    }

    public void setWeatherController(WeatherController weathercontroller) {
        this.weatherController = weathercontroller;
    }

    public void setSearchResultController(SearchResultController searchresultcontroller) {
        this.searchResultController = searchresultcontroller;
    }

    public void setNearbyListController(NearbyListController nearbyListController) {
        this.nearbyListController = nearbyListController;
    }

    public void setAlertPopController(AlertPopController alertPopController) {
        this.alertPopController = alertPopController;
    }

    public void setCompareCitiesController(CompareCitiesController compareCitiesController) {
        this.compareCitiesController = compareCitiesController;
    }
}

