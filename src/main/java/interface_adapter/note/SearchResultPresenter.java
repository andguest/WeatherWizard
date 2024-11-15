package interface_adapter.note;

import use_case.note.search_result.SearchResultOutputBoundary;
import use_case.note.search_result.SearchResultOutputData;


public class SearchResultPresenter implements SearchResultOutputBoundary {

    private final WeatherViewModel viewModel;

    public SearchResultPresenter(WeatherViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccessView(SearchResultOutputData searchResultOutputData) {
        viewModel.getState().setWeather(searchResultOutputData.getWeather());
    }

    @Override
    public void presentFailView(String errorMessage) {
        viewModel.getState().setError(errorMessage);
    }
}
